package Tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import Negocio.Musica;
import Negocio.ReprodutorAudio;


public class BarraReprodutor extends JPanel {

    private List<Musica> fila;
    private int indiceAtual = -1;
    private boolean tocando = false;
    private int volume = 55;

    private final ReprodutorAudio reprodutor = new ReprodutorAudio();

    private JLabel labelNome;
    private JLabel labelTempo;
    private JButton botaoPlayPause;

    private Consumer<Boolean> aoMudarEstado;

    private volatile long pedidoAtual = 0;   
    private boolean carregando = false;

    private final Timer cronometro;   
    private int ticksParado = 0;      

    public BarraReprodutor() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 50));
        setBackground(Tema.BRANCO);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Tema.BORDA));

        add(criarMusica(), BorderLayout.WEST);
        add(criarControles(), BorderLayout.CENTER);
        add(criarVolume(), BorderLayout.EAST);

        reprodutor.setVolume(volume / 100f);
        atualizarExibicao();

        cronometro = new Timer(250, e -> aoTique());
        cronometro.start();
    }

    public void tocar(List<Musica> musicas, int indice) {
        if (musicas == null || musicas.isEmpty()) return;

        this.fila = musicas;
        this.indiceAtual = Math.max(0, Math.min(indice, musicas.size() - 1));
        iniciarMusicaAtual();
    }


    public void alternarPlayPause() {
        if (fila == null || indiceAtual < 0) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma música selecionada.",
                    "Echo Music",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (carregando) {
            pedidoAtual++;
            carregando = false;
            tocando = false;
        } else if (tocando) {
            reprodutor.pausar();
            tocando = false;
        } else if (reprodutor.isPausado()) {
            reprodutor.retomar();
            tocando = true;
        } else {
            iniciarMusicaAtual();
            return;
        }
        atualizarExibicao();
        notificarMudancaEstado();
    }


    public void pararSe(Musica musica) {
        if (fila == null || indiceAtual < 0 || fila.get(indiceAtual) != musica) return;
        pedidoAtual++;
        carregando = false;
        reprodutor.parar();
        tocando = false;
        indiceAtual = -1;
        atualizarExibicao();
        notificarMudancaEstado();
    }

    public void ajustarIndiceAposRemocao(int indiceRemovido) {
        if (indiceAtual > indiceRemovido) indiceAtual--;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        Window janela = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (janela != null) {
            janela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    cronometro.stop();
                    pedidoAtual++;
                    reprodutor.parar();
                }
            });
        }
    }

    public boolean estaReproduzindo(List<Musica> musicas) {
        return fila == musicas;
    }


    public void aoMudarEstado(Consumer<Boolean> ouvinte) {
        this.aoMudarEstado = ouvinte;
    }

    private void notificarMudancaEstado() {
        if (aoMudarEstado != null) aoMudarEstado.accept(tocando);
    }
    private JPanel criarMusica() {
        JPanel musica = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        musica.setBackground(Tema.BRANCO);

        JPanel capa = new JPanel();
        capa.setPreferredSize(new Dimension(34, 34));
        capa.setBackground(new Color(235, 235, 235));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(Tema.BRANCO);

        labelNome = new JLabel("Nenhuma música selecionada");
        labelTempo = new JLabel("--:-- / --:--");
        textos.add(labelNome);
        textos.add(labelTempo);

        musica.add(capa);
        musica.add(textos);
        return musica;
    }

    private JPanel criarControles() {
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 13));
        controles.setBackground(Tema.BRANCO);

        JButton botaoAnterior = criarBotaoControle("⏮");
        botaoAnterior.addActionListener(e -> mudarMusica(-1));

        botaoPlayPause = criarBotaoControle("▶");
        botaoPlayPause.addActionListener(e -> alternarPlayPause());

        JButton botaoProxima = criarBotaoControle("⏭");
        botaoProxima.addActionListener(e -> mudarMusica(1));

        controles.add(botaoAnterior);
        controles.add(botaoPlayPause);
        controles.add(botaoProxima);
        return controles;
    }

    private JButton criarBotaoControle(String texto) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(32, 23));
        botao.setFocusPainted(false);
        botao.setMargin(new Insets(0, 0, 0, 0));
        botao.setBackground(Tema.ROXO);
        botao.setForeground(Tema.BRANCO);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private JPanel criarVolume() {
        JPanel volumePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 13));
        volumePanel.setBackground(Tema.BRANCO);

        volumePanel.add(new JLabel("Volume"));

        JSlider slider = new JSlider(0, 100, volume);
        slider.setBackground(Tema.BRANCO);
        slider.setPreferredSize(new Dimension(100, 18));
        slider.setToolTipText("Volume: " + volume + "%");
        slider.addChangeListener(e -> {
            volume = slider.getValue();
            reprodutor.setVolume(volume / 100f);
            slider.setToolTipText("Volume: " + volume + "%");
        });

        volumePanel.add(slider);
        return volumePanel;
    }
    private void mudarMusica(int direcao) {
        if (fila == null || fila.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma música selecionada.",
                    "Echo Music",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        indiceAtual = (indiceAtual + direcao + fila.size()) % fila.size();
        iniciarMusicaAtual();
    }

    private void iniciarMusicaAtual() {
        final Musica musica = fila.get(indiceAtual);
        final long pedido = ++pedidoAtual;

        reprodutor.parar();
        carregando = true;
        tocando = true;
        atualizarExibicao();
        notificarMudancaEstado();

        new SwingWorker<Boolean, Void>() {
            private String erro;

            @Override
            protected Boolean doInBackground() {
                try {
                    Path arquivo = musica.arquivoLocal();
                    synchronized (reprodutor) {
                        if (pedido != pedidoAtual) return null;
                        boolean ok = reprodutor.tocar(arquivo.toString());
                        if (!ok) erro = reprodutor.getUltimoErro();
                        return ok;
                    }
                } catch (IOException e) {
                    erro = e.getMessage();
                    return false;
                }
            }

            @Override
            protected void done() {
                if (pedido != pedidoAtual) return; 

                boolean ok = false;
                try {
                    Boolean resultado = get();
                    ok = resultado != null && resultado;
                } catch (Exception e) {
                    erro = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
                }

                carregando = false;
                tocando = ok;
                atualizarExibicao();
                notificarMudancaEstado();

                if (!ok) {
                    JOptionPane.showMessageDialog(BarraReprodutor.this,
                            "Não foi possível tocar a música:\n" + erro,
                            "Echo Music",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }


    private void aoTique() {
        if (carregando) return; 
        if (fila == null || indiceAtual < 0 || !tocando) {
            ticksParado = 0;
            return;
        }
        if (!reprodutor.isTocando()) {
            if (++ticksParado >= 2) {
                ticksParado = 0;
                tocando = false;
                atualizarExibicao();
                notificarMudancaEstado();
            }
            return;
        }
        ticksParado = 0;
        labelTempo.setText(textoTempo(fila.get(indiceAtual)));
    }

    private String textoTempo(Musica musica) {
        long duracao = reprodutor.getDuracaoSegundos();
        if (duracao <= 0) {
            return "--:-- / " + musica.getDuracao();
        }
        return formatar(reprodutor.getPosicaoSegundos()) + " / " + formatar(duracao);
    }

    private static String formatar(long segundos) {
        return String.format("%02d:%02d", segundos / 60, segundos % 60);
    }

    private void atualizarExibicao() {
        if (fila == null || indiceAtual < 0) {
            labelNome.setText("Nenhuma música selecionada");
            labelTempo.setText("--:-- / --:--");
            botaoPlayPause.setText("▶");
            return;
        }

        Musica musica = fila.get(indiceAtual);
        labelNome.setText(musica.getNome());
        labelTempo.setText(carregando ? "Carregando..." : textoTempo(musica));
        botaoPlayPause.setText(tocando ? "||" : "▶");
    }
}
