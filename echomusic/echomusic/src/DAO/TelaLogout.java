package DAO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class TelaLogout {



public static void main(String[] args) {

    JFrame janela = new JFrame("Echo Music - Sair do app");

    janela.setResizable(false);
    janela.setSize(640, 390);
    janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    janela.setLocationRelativeTo(null);

    // Painel principal
    JPanel painel = new JPanel(new GridBagLayout());
    painel.setBackground(Color.WHITE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(4, 5, 4, 5);

    // =========================
    // LOGO
    // =========================

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(15, 5, 8, 5);



    // =========================
    // TÍTULO
    // =========================

    JLabel titulo = new JLabel("Sair do ECHO MUSIC?");
    titulo.setFont(new Font("Arial", Font.BOLD, 15));

    gbc.gridy = 1;
    gbc.insets = new Insets(5, 5, 12, 5);

    painel.add(titulo, gbc);

    // =========================
    // BOTÃO SAIR?
    // =========================

    JButton entrar = new JButton("sair?");
    entrar.setPreferredSize(new Dimension(275, 22));
    entrar.setFont(new Font("Arial", Font.BOLD, 13));

    gbc.gridy = 7;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(0, 0, 10, 0);

    painel.add(entrar, gbc);
    // =========================
    // BOTÃO CANCELAR
    // =========================

    JButton cancelar = new JButton("CANCELAR");
    cancelar.setPreferredSize(new Dimension(275, 22));
    cancelar.setFont(new Font("Arial", Font.BOLD, 9));

    gbc.gridy = 10;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(0, 0, 20, 0);

    painel.add(cancelar,gbc);
    
    // =========================
    // EXIBIR JANELA
    // =========================

    	janela.add(painel);
    	janela.setVisible(true);
	}		
}
