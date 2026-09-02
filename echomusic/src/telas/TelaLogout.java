package view;

import java.awt.*;
import javax.swing.*;

public class TelaLogout {

    public static void main(String[] args) {
        JFrame janela = new JFrame("Echo Music - Sair");
        janela.setSize(640, 390);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("Sair do ECHO MUSIC");
        titulo.setFont(new Font("Arial", Font.BOLD, 15));

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 12, 0);
        painel.add(titulo, gbc);

        JButton sair = new JButton("SAIR");
        sair.setPreferredSize(new Dimension(275, 22));
        sair.setFont(new Font("Arial", Font.BOLD, 9));

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 10, 0);
        painel.add(sair, gbc);

        JButton cancelar = new JButton("CANCELAR");
        cancelar.setPreferredSize(new Dimension(275, 22));
        cancelar.setFont(new Font("Arial", Font.BOLD, 9));

        gbc.gridy = 10;
        gbc.insets = new Insets(0, 0, 20, 0);
        painel.add(cancelar, gbc);

        janela.add(painel);
        janela.setVisible(true);
    }
}
