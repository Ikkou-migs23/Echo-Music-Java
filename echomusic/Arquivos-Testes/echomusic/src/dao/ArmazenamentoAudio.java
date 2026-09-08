package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

/**
 * Responsável por copiar arquivos de áudio escolhidos pelo usuário para a
 * pasta local da biblioteca do Echo Music ({@code ~/EchoMusic/Musicas}), e
 * por localizar esses arquivos depois a partir do nome salvo no banco.
 *
 * Cada arquivo importado recebe um nome único (UUID), preservando a
 * extensão original, para evitar colisão entre músicas com o mesmo nome.
 * É esse nome único (não o caminho inteiro) que deve ser persistido na
 * coluna {@code caminho_arquivo} da tabela {@code musicas}, já que a pasta
 * raiz é sempre recalculada a partir do usuário do sistema operacional.
 */
public class ArmazenamentoAudio {

    private static final Set<String> EXTENSOES_ACEITAS =
            Set.of("mp3", "wav", "m4a", "flac", "ogg", "aac");

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

    private ArmazenamentoAudio() {
        // classe utilitária: não deve ser instanciada
    }

    /**
     * Copia o arquivo escolhido pelo usuário para a pasta da biblioteca,
     * com um nome único, e devolve o {@link File} já apontando para o novo
     * local. É esse arquivo (via {@code getName()}) que deve ser gravado
     * no banco.
     *
     * @throws IOException se a extensão não for suportada ou a cópia falhar
     */
    public static File importar(File arquivoOriginal) throws IOException {
        validarExtensao(arquivoOriginal);

        String extensao = obterExtensao(arquivoOriginal.getName());
        String nomeUnico = UUID.randomUUID() + "." + extensao;
        Path destino = PASTA_BIBLIOTECA.resolve(nomeUnico);

        Files.copy(arquivoOriginal.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

        return destino.toFile();
    }

    /** Reconstrói o caminho completo a partir do nome salvo no banco. */
    public static File localizar(String nomeArmazenado) {
        return PASTA_BIBLIOTECA.resolve(nomeArmazenado).toFile();
    }

    /** Remove um arquivo da biblioteca (ex.: ao excluir a música). */
    public static void remover(String nomeArmazenado) throws IOException {
        Files.deleteIfExists(PASTA_BIBLIOTECA.resolve(nomeArmazenado));
    }

    public static Path pastaBiblioteca() {
        return PASTA_BIBLIOTECA;
    }

    private static void validarExtensao(File arquivo) throws IOException {
        String extensao = obterExtensao(arquivo.getName());
        if (!EXTENSOES_ACEITAS.contains(extensao)) {
            throw new IOException("Formato não suportado: ." + extensao);
        }
    }

    private static String obterExtensao(String nomeArquivo) {
        int ponto = nomeArquivo.lastIndexOf('.');
        if (ponto == -1 || ponto == nomeArquivo.length() - 1) return "";
        return nomeArquivo.substring(ponto + 1).toLowerCase();
    }
}