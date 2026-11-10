package Tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import Negocio.Musica;


public class BarraReprodutor extends JPanel {

    private List<Musica> fila;
    private int indiceAtual = -1;
    private boolean tocando = false;
    private int volume = 55;

    private JLabel labelNome;
    private JLabel labelTempo;
    private JButton botaoPlayPause;

    private Consumer<Boolean> aoMudarEstado;

    public BarraReprodutor() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 50));
        setBackground(Tema.BRANCO);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Tema.BORDA));

        add(criarMusica(), BorderLayout.WEST);
        add(criarControles(), BorderLayout.CENTER);
        add(criarVolume(), BorderLayout.EAST);

        atualizarExibicao();
    }
// --- API ---
    public void tocar(List<Musica> musicas, int indice) {
        if (musicas == null || musicas.isEmpty()) return;

        this.fila = musicas;
        this.indiceAtual = Math.max(0, Math.min(indice, musicas.size() - 1));
        this.tocando = true;

        atualizarExibicao();
        notificarMudancaEstado();
    }


    public void alternarPlayPause() {
        if (fila == null || indiceAtual < 0) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma música selecionada.",
                    "Echo Music",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        tocando = !tocando;
        atualizarExibicao();
        notificarMudancaEstado();
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
// --- UI ---
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
            slider.setToolTipText("Volume: " + volume + "%");
        });

        volumePanel.add(slider);
        return volumePanel;
    }
// --- lógica ---
    private void mudarMusica(int direcao) {
        if (fila == null || fila.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma música selecionada.",
                    "Echo Music",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        indiceAtual = (indiceAtual + direcao + fila.size()) % fila.size();
        tocando = true;
        atualizarExibicao();
        notificarMudancaEstado();
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
        labelTempo.setText((tocando ? "00:00" : "--:--") + " / " + musica.getDuracao());
        botaoPlayPause.setText(tocando ? "||" : "▶");
    }
}
