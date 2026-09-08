package negocio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Serviço central de reprodução, usado pela BarraReprodutor e por qualquer
 * tela que precise tocar uma música (ex.: TelaPlaylist).
 *
 * Esta versão usa apenas {@code javax.sound.sampled}, nativo do JDK, sem
 * nenhuma dependência externa — por isso só reproduz WAV/AIFF/AU
 * nativamente. Para MP3/M4A/FLAC, é necessário trocar a implementação
 * interna por JavaFX Media (javafx-media) ou JLayer, mantendo a mesma
 * assinatura pública desta classe, para não precisar alterar quem já a usa.
 *
 * Fica em {@code negocio} por ser a regra de comportamento mais próxima do
 * domínio disponível nesta arquitetura (não é acesso a dado, nem um modelo
 * puro como Musica/Playlist) — se o projeto crescer, considere um pacote
 * {@code servico} dedicado só para isso.
 */
public class ServicoReprodutor {

    private static ServicoReprodutor instancia;
    private Clip clipAtual;

    private ServicoReprodutor() {
    }

    public static synchronized ServicoReprodutor obter() {
        if (instancia == null) {
            instancia = new ServicoReprodutor();
        }
        return instancia;
    }

    public void tocar(File arquivo)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        parar();

        AudioInputStream fluxo = AudioSystem.getAudioInputStream(arquivo);
        clipAtual = AudioSystem.getClip();
        clipAtual.open(fluxo);
        clipAtual.start();
    }

    public void pausar() {
        if (clipAtual != null && clipAtual.isRunning()) {
            clipAtual.stop();
        }
    }

    public void retomar() {
        if (clipAtual != null) {
            clipAtual.start();
        }
    }

    public void parar() {
        if (clipAtual != null) {
            clipAtual.stop();
            clipAtual.close();
            clipAtual = null;
        }
    }

    public boolean estaTocando() {
        return clipAtual != null && clipAtual.isRunning();
    }

    /** @param volume valor entre 0.0 (mudo) e 1.0 (máximo) */
    public void definirVolume(float volume) {
        if (clipAtual == null || !clipAtual.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;
        }
        FloatControl controle = (FloatControl) clipAtual.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log10(Math.max(volume, 0.0001)) * 20);
        dB = Math.max(controle.getMinimum(), Math.min(controle.getMaximum(), dB));
        controle.setValue(dB);
    }
}