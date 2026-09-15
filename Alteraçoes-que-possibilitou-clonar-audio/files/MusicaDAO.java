package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import negocio.LeitorMp3;
import negocio.Musica;

/**
 * Responsável por persistir o arquivo de áudio escolhido pelo usuário na
 * biblioteca local do Echo Music ({@code ~/EchoMusic/Musicas}) e por montar
 * o objeto de domínio {@link Musica} correspondente — nome do arquivo e
 * duração lida de verdade (via {@link LeitorMp3}).
 *
 * Hoje "salvar uma música" significa copiar o arquivo para essa pasta, pra
 * que a playlist continue funcionando mesmo se o arquivo original (de onde
 * o usuário selecionou) for movido ou apagado depois. Quando o Postgres
 * entrar, este DAO passa a gravar também uma linha na tabela {@code musicas}
 * (nome, duração, caminho_arquivo) — a assinatura pública ({@link #salvar})
 * continua a mesma, então quem já chama este DAO não precisa mudar.
 */
public class MusicaDAO {

    private static final Set<String> EXTENSOES_ACEITAS = Set.of("mp3");

    private static final Path PASTA_BIBLIOTECA =
            Paths.get(System.getProperty("user.home"), "EchoMusic", "Musicas");

    static {
        try {
            Files.createDirectories(PASTA_BIBLIOTECA);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Não foi possível criar a pasta da biblioteca: " + PASTA_BIBLIOTECA, e);
        }
    }

    /**
     * Copia o arquivo escolhido pelo usuário para a biblioteca local (com
     * um nome único, pra não colidir com outro arquivo de mesmo nome) e
     * devolve a {@link Musica} já pronta — nome do arquivo original e
     * duração lida do próprio áudio — para ser adicionada à playlist.
     *
     * @throws IOException se a extensão não for .mp3 ou a cópia falhar
     */
    public Musica salvar(File arquivoOriginal) throws IOException {
        validarExtensao(arquivoOriginal);

        File arquivoCopiado = copiarParaBiblioteca(arquivoOriginal);
        String nome = removerExtensao(arquivoOriginal.getName());
        String duracao = LeitorMp3.duracaoFormatada(arquivoCopiado);

        return new Musica(nome, duracao);
    }

    private File copiarParaBiblioteca(File arquivoOriginal) throws IOException {
        String nomeUnico = UUID.randomUUID() + "_" + arquivoOriginal.getName();
        Path destino = PASTA_BIBLIOTECA.resolve(nomeUnico);
        Files.copy(arquivoOriginal.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

        confirmarCopia(arquivoOriginal.toPath(), destino);
        return destino.toFile();
    }

    /**
     * Não confia que Files.copy() funcionou só porque não lançou exceção:
     * confere que o arquivo existe no destino e que o tamanho bate com o
     * original. Se algo estiver errado, falha alto (throw) em vez de
     * deixar passar uma cópia corrompida ou incompleta silenciosamente.
     */
    private void confirmarCopia(Path origem, Path destino) throws IOException {
        if (!Files.exists(destino)) {
            throw new IOException("Cópia não encontrada em: " + destino);
        }

        long tamanhoOrigem = Files.size(origem);
        long tamanhoDestino = Files.size(destino);
        if (tamanhoOrigem != tamanhoDestino) {
            throw new IOException(String.format(
                    "Cópia incompleta: original tem %d bytes, destino tem %d bytes (%s)",
                    tamanhoOrigem, tamanhoDestino, destino));
        }

        System.out.println("[MusicaDAO] Áudio salvo com sucesso em: " + destino.toAbsolutePath());
    }

    private void validarExtensao(File arquivo) throws IOException {
        String nome = arquivo.getName().toLowerCase();
        int ponto = nome.lastIndexOf('.');
        String extensao = ponto == -1 ? "" : nome.substring(ponto + 1);
        if (!EXTENSOES_ACEITAS.contains(extensao)) {
            throw new IOException("Formato não suportado: ." + extensao);
        }
    }

    private String removerExtensao(String nomeArquivo) {
        int ponto = nomeArquivo.lastIndexOf('.');
        return ponto > 0 ? nomeArquivo.substring(0, ponto) : nomeArquivo;
    }

    public Path pastaBiblioteca() {
        return PASTA_BIBLIOTECA;
    }

    /**
     * Abre a pasta da biblioteca no explorador de arquivos do sistema
     * operacional (Explorer/Finder/Nautilus) — útil pra confirmar
     * visualmente, sem sair do app, que os áudios estão caindo no lugar
     * certo.
     */
    public void abrirPastaNoExplorador() throws IOException {
        if (!java.awt.Desktop.isDesktopSupported()
                || !java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)) {
            throw new IOException("Este sistema operacional não permite abrir pastas pelo app.");
        }
        java.awt.Desktop.getDesktop().open(PASTA_BIBLIOTECA.toFile());
    }
}
