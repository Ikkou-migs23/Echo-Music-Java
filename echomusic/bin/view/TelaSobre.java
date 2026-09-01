package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaSobre {
	
	  	private static final Color FUNDO = new Color(247, 248, 250);
	    
	    
	  public static void main(String[] args) {
	        JFrame janela = new JFrame("Echo Music - Sua Biblioteca");
	        janela.setSize(900, 600);
	        janela.setResizable(false);
	        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        janela.setLocationRelativeTo(null);
	        
	        JPanel sobre = new JPanel(new BorderLayout());
	        sobre.setBackground(FUNDO);
	        sobre.add(new BarraLateral("Sobre"), BorderLayout.WEST);

	        janela.add(sobre);
	        janela.setVisible(true);
	  }
	  
	  		JPanel sobre = new JPanel(new BorderLayout());
	  		sobre.add(CriarConteudo(), BorderLayout.CENTER);

  }
	  
	  private static JPanel CriarConteudo() {
	        JPanel painel = new JPanel(new BorderLayout());
	        painel.setBackground(FUNDO);
	        JPanel conteudo = new JPanel(new BorderLayout());
	        conteudo.setBackground(FUNDO);
	        conteudo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
	        conteudo.add(CriarCabecalho(), BorderLayout.NORTH);

	        painel.add(conteudo);
	        return painel;
	    }
	  
	  
	  private static JPanel CriarCabecalho() {
	        JPanel painel = new JPanel(new BorderLayout());
	        painel.setBackground(FUNDO);

	        JLabel titulo = new JLabel("SOBRE O ECHO MUSIC");
	        titulo.setFont(new Font("Arial", Font.BOLD, 18));

	        painel.add(titulo, BorderLayout.WEST);

	        return painel;
	  	}
}
	  

