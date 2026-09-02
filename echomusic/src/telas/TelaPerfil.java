package telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 * Tela "Meu Perfil" — edição de nome, e-mail, senha e foto do usuário.
 * Segue o mesmo layout (BarraLateral + BarraSuperior + BarraReprodutor)
 * usado nas demais telas internas.
 */
public class TelaPerfil extends JFrame {

    private static final int LADO_FOTO = 190;

    public TelaPerfil() {
        setTitle("Echo Music - Perfil");
        setSize(1080, 700);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.FUNDO);
        raiz.setBorder(BorderFactory.createLineBorder(Tema.BORDA));

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(Tema.FUNDO);

        BarraLateral barraLateral = new BarraLateral("Perfil");
        barraLateral.aoClicar(this::navegar);

        principal.add(barraLateral, BorderLayout.WEST);
        principal.add(criarConteudo(), BorderLayout.CENTER);
        principal.add(new BarraReprodutor(), BorderLayout.SOUTH);

        raiz.add(new BarraTitulo(this), BorderLayout.NORTH);
        raiz.add(principal, BorderLayout.CENTER);

        add(raiz);
    }

    private void navegar(String tela) {
        switch (tela) {
            case "Home", "Playlists" -> {
                dispose();
                new TelaPrincipal().setVisible(true);
            }
            case "Perfil" -> {
                // já estamos no Perfil, não faz nada
            }
            case "Sobre" -> {
                dispose();
                new TelaSobre().setVisible(true);
            }
        }
    }

    private JPanel criarConteudo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Tema.FUNDO);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(Tema.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        conteudo.add(criarCabecalho(), BorderLayout.NORTH);
        conteudo.add(criarAreaPerfil(), BorderLayout.CENTER);

        painel.add(conteudo, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(Tema.FUNDO);

        JLabel titulo = new JLabel("Meu Perfil");
        titulo.setFont(Tema.FONTE_TITULO);
        titulo.setForeground(Tema.TEXTO);

        cabecalho.add(titulo, BorderLayout.WEST);
        return cabecalho;
    }

    private JPanel criarAreaPerfil() {
        JPanel area = new JPanel(new BorderLayout());
        area.setBackground(Tema.FUNDO);
        area.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        area.add(criarColunaFoto(), BorderLayout.WEST);
        area.add(criarColunaInformacoes(), BorderLayout.CENTER);
        return area;
    }

    // ---------- coluna esquerda: foto de perfil ----------
    private JPanel criarColunaFoto() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Tema.FUNDO);
        painel.setPreferredSize(new Dimension(230, 280));

        JLabel titulo = new JLabel("Foto de perfil");
        titulo.setFont(Tema.FONTE_SUBTITULO);
        titulo.setForeground(Tema.TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        SeletorImagem seletorFoto = new SeletorImagem("FOTO", LADO_FOTO);
        seletorFoto.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ajuda = new JLabel("Clique na foto para alterar");
        ajuda.setFont(Tema.FONTE_TEXTO_PEQUENA);
        ajuda.setForeground(Tema.TEXTO_SECUNDARIO);
        ajuda.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(8));
        painel.add(seletorFoto);
        painel.add(Box.createVerticalStrut(9));
        painel.add(ajuda);
        return painel;
    }

    // ---------- coluna direita: dados do perfil ----------
    private JPanel criarColunaInformacoes() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 0));

        adicionarCampoTexto(painel, "Nome", "Insira seu nome");
        adicionarCampoSenha(painel, "Nova senha", "Digite a nova senha");
        adicionarCampoSenha(painel, "Confirmar nova senha", "Repita a nova senha");

        JButton salvar = new JButton("SALVAR ALTERAÇÕES");
        salvar.setFont(Tema.FONTE_SUBTITULO);
        salvar.setForeground(Tema.BRANCO);
        salvar.setBackground(Tema.AZUL);
        salvar.setFocusPainted(false);
        salvar.setBorderPainted(false);
        salvar.setPreferredSize(new Dimension(180, 32));
        salvar.setMaximumSize(new Dimension(180, 32));
        salvar.setAlignmentX(Component.LEFT_ALIGNMENT);
        salvar.addActionListener(e -> salvarAlteracoes());

        painel.add(Box.createVerticalStrut(6));
        painel.add(salvar);
        return painel;
    }

    private void salvarAlteracoes() {
        // TODO: persistir as alterações assim que houver um serviço de perfil
        JOptionPane.showMessageDialog(this,
                "Alterações salvas com sucesso!",
                "Echo Music",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private CampoTextoPlaceholder adicionarCampoTexto(JPanel painel, String rotuloTexto, String placeholder) {
        JPanel campo = criarLinhaCampo(rotuloTexto);

        CampoTextoPlaceholder texto = new CampoTextoPlaceholder(placeholder);
        texto.setFont(Tema.FONTE_TEXTO_PEQUENA);
        texto.setForeground(Tema.TEXTO);
        texto.setPreferredSize(new Dimension(340, 32));
        texto.setBorder(criarBordaCampo());

        campo.add(texto, BorderLayout.CENTER);
        painel.add(campo);
        painel.add(Box.createVerticalStrut(10));
        return texto;
    }

    private CampoSenhaPlaceholder adicionarCampoSenha(JPanel painel, String rotuloTexto, String placeholder) {
        JPanel campo = criarLinhaCampo(rotuloTexto);

        CampoSenhaPlaceholder senha = new CampoSenhaPlaceholder(placeholder);
        senha.setFont(Tema.FONTE_TEXTO_PEQUENA);
        senha.setPreferredSize(new Dimension(340, 32));
        senha.setBorder(criarBordaCampo());

        campo.add(senha, BorderLayout.CENTER);
        painel.add(campo);
        painel.add(Box.createVerticalStrut(10));
        return senha;
    }

    private JPanel criarLinhaCampo(String rotuloTexto) {
        JPanel campo = new JPanel(new BorderLayout(0, 5));
        campo.setBackground(Tema.FUNDO);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel rotulo = new JLabel(rotuloTexto);
        rotulo.setFont(Tema.FONTE_SUBTITULO);
        rotulo.setForeground(Tema.TEXTO);

        campo.add(rotulo, BorderLayout.NORTH);
        return campo;
    }

    private javax.swing.border.Border criarBordaCampo() {
        return BorderFactory.createCompoundBorder(
                new LineBorder(Tema.BORDA),
                BorderFactory.createEmptyBorder(4, 9, 4, 9)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPerfil().setVisible(true));
    }
}
