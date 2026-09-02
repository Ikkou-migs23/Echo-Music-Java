package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * Card individual de playlist (capa colorida + nome + quantidade + botão "...").
 * Clicar em qualquer parte do card (menos no botão "...") abre a TelaPlaylist
 * daquela playlist, através do callback aoClicar.
 */
public class CardPlaylist extends JPanel {

    private static final Color BORDA = new Color(220, 223, 228);
    private static final Color FUNDO_HOVER = new Color(242, 244, 248);

    public CardPlaylist(String nome, String quantidade, Color cor, String textoCapa, Runnable aoClicar) {
        setLayout(new BorderLayout(8, 0));
        setPreferredSize(new Dimension(245, 85));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA),
                BorderFactory.createEmptyBorder(7, 7, 7, 7)
        ));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(criarCapa(cor, textoCapa), BorderLayout.WEST);
        add(criarInformacoes(nome, quantidade), BorderLayout.CENTER);
        add(criarBotaoMenu(), BorderLayout.EAST);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (aoClicar != null) aoClicar.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(FUNDO_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
            }
        });
    }

    private JPanel criarCapa(Color cor, String textoCapa) {
        JPanel capa = new JPanel(new GridBagLayout());
        capa.setPreferredSize(new Dimension(65, 65));
        capa.setBackground(cor);

        JLabel texto = new JLabel(
                "<html><center>" + textoCapa.replace("\n", "<br>") + "</center></html>"
        );
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Arial", Font.BOLD, 10));
        capa.add(texto);

        return capa;
    }

    private JPanel criarInformacoes(String nome, String quantidade) {
        JPanel informacoes = new JPanel();
        informacoes.setLayout(new BoxLayout(informacoes, BoxLayout.Y_AXIS));
        informacoes.setOpaque(false);

        JLabel nomeLabel = new JLabel(nome);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel quantidadeLabel = new JLabel(quantidade);
        quantidadeLabel.setFont(new Font("Arial", Font.PLAIN, 9));
        quantidadeLabel.setForeground(Color.GRAY);

        informacoes.add(Box.createVerticalGlue());
        informacoes.add(nomeLabel);
        informacoes.add(Box.createVerticalStrut(3));
        informacoes.add(quantidadeLabel);
        informacoes.add(Box.createVerticalGlue());

        return informacoes;
    }

    private JButton criarBotaoMenu() {
        JButton menu = new JButton("...");
        menu.setPreferredSize(new Dimension(25, 25));
        menu.setFocusPainted(false);
        menu.setMargin(new Insets(0, 0, 0, 0));
        // impede que o clique no "..." também dispare a abertura da playlist
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
            }
        });
        return menu;
    }
}