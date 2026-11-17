package Negocio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ReprodutorAudio {

    private volatile Clip clip;
    private long posicaoPausa = 0;   
    private volatile boolean pausado = false;
    private float volume = 1.0f;    
    private volatile String ultimoErro; 


    public synchronized boolean tocar(String caminho) {
        parar(); 
        ultimoErro = null;

        if (caminho == null || caminho.isBlank()) {
            return falhar("Esta música não tem caminho de arquivo.");
        }
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            return falhar("Arquivo não encontrado:\n" + arquivo.getAbsolutePath());
        }
        String minusculo = caminho.toLowerCase();
        boolean mp3 = minusculo.endsWith(".mp3");
        if (!mp3 && !minusculo.endsWith(".wav")) {
            return falhar("Formato não suportado (use .mp3 ou .wav):\n" + arquivo.getName());
        }

        AudioInputStream original = null;
        AudioInputStream decodificado = null;
        try {
            original = AudioSystem.getAudioInputStream(arquivo);
            decodificado = paraPcm(original);

            final Clip novo = AudioSystem.getClip();
            novo.open(decodificado);  
            clip = novo;

            
            novo.addLineListener(evento -> {
                if (evento.getType() == LineEvent.Type.STOP && !pausado && clip == novo) {
                    liberarRecursos();
                }
            });

            aplicarVolume();
            novo.start();
            return true;

        } catch (UnsupportedAudioFileException e) {
            if (mp3) {
                return falhar("Não foi possível ler o MP3. Confira se os jars mp3spi, jlayer e "
                        + "tritonus-share estão no Build Path do projeto.\n" + e.getMessage());
            }
            return falhar("Arquivo de áudio não suportado (precisa ser WAV PCM):\n" + e.getMessage());
        } catch (IOException | LineUnavailableException | IllegalArgumentException e) {
            return falhar("Erro ao tocar áudio: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            return falhar("A música é grande demais para carregar na memória. "
                    + "Use um arquivo menor ou aumente a memória do Java (-Xmx).");
        } finally {
            fechar(decodificado);
            fechar(original);
        }
    }

    private static AudioInputStream paraPcm(AudioInputStream origem) {
        AudioFormat base = origem.getFormat();
        AudioFormat.Encoding codificacao = base.getEncoding();
        if (AudioFormat.Encoding.PCM_SIGNED.equals(codificacao)
                || AudioFormat.Encoding.PCM_UNSIGNED.equals(codificacao)) {
            return origem;
        }

        AudioFormat pcm = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                base.getSampleRate(),
                16,
                base.getChannels(),
                base.getChannels() * 2,
                base.getSampleRate(),
                false);
        return AudioSystem.getAudioInputStream(pcm, origem);
    }

    private static void fechar(AudioInputStream stream) {
        if (stream == null) return;
        try {
            stream.close();
        } catch (IOException ignorar) {

        }
    }

 
    public void pausar() {
        if (clip != null && clip.isRunning()) {
            posicaoPausa = clip.getMicrosecondPosition();
            pausado = true;
            clip.stop();
        }
    }


    public void retomar() {
        if (clip != null && pausado) {
            clip.setMicrosecondPosition(posicaoPausa);
            pausado = false;
            clip.start();
        }
    }


    public void parar() {
        pausado = false;
        posicaoPausa = 0;
        liberarRecursos();
    }


    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("O volume deve estar entre 0.0 e 1.0");
        }
        this.volume = volume;
        aplicarVolume();
    }

    public float getVolume() {
        return volume;
    }

    public boolean isTocando() {
        return clip != null && clip.isRunning();
    }

    public boolean isPausado() {
        return pausado;
    }


    public long getPosicaoSegundos() {
        Clip atual = clip;
        return atual == null ? 0 : atual.getMicrosecondPosition() / 1_000_000;
    }


    public long getDuracaoSegundos() {
        Clip atual = clip;
        return atual == null ? 0 : atual.getMicrosecondLength() / 1_000_000;
    }


    public String getUltimoErro() {
        return ultimoErro;
    }

 

    private boolean falhar(String mensagem) {
        ultimoErro = mensagem;
        System.err.println("[ReprodutorAudio] " + mensagem);
        return false;
    }


    private void aplicarVolume() {
        if (clip == null || !clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;
        }
        FloatControl ganho = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        float dB = (volume == 0f)
                ? ganho.getMinimum()
                : (float) (20.0 * Math.log10(volume));


        dB = Math.max(ganho.getMinimum(), Math.min(ganho.getMaximum(), dB));
        ganho.setValue(dB);
    }

    private void liberarRecursos() {
        Clip atual = clip;
        clip = null;
        if (atual != null) {
            atual.stop();
            atual.close();
        }
    }
}
