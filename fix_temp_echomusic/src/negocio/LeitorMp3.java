package negocio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * Leitor simples de metadados de arquivos MP3, usado para descobrir a
 * duração real de um arquivo sem depender de bibliotecas externas (o
 * projeto ainda não usa nenhuma dependência de terceiros).
 *
 * A duração é calculada a partir do cabeçalho do primeiro frame MPEG
 * válido do arquivo:
 * <ul>
 *   <li>se o arquivo tiver um cabeçalho VBR (Xing/Info), a duração é
 *   calculada a partir do número total de frames informado nele;</li>
 *   <li>caso contrário, é estimada a partir do bitrate do primeiro frame
 *   e do tamanho do arquivo, assumindo taxa de bits constante (CBR),
 *   que é o caso mais comum.</li>
 * </ul>
 * Não é um decodificador completo de MP3 — é o suficiente para estimar a
 * duração de arquivos comuns (MPEG-1/2 Layer III).
 */
public final class LeitorMp3 {

    // Tabelas de bitrate (kbps) do cabeçalho MPEG. Índice 0 ("free") é ignorado.
    private static final int[][] TABELA_BITRATE = {
            {0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448}, // MPEG1 Layer I
            {0, 32, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 384},    // MPEG1 Layer II
            {0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320},     // MPEG1 Layer III
            {0, 32, 48, 56, 64, 80, 96, 112, 128, 144, 160, 176, 192, 224, 256},    // MPEG2/2.5 Layer I
            {0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160}          // MPEG2/2.5 Layers II/III
    };

    private static final int[][] TABELA_AMOSTRAGEM = {
            {44100, 48000, 32000}, // MPEG1
            {22050, 24000, 16000}, // MPEG2
            {11025, 12000, 8000}   // MPEG2.5
    };

    private LeitorMp3() {
        // classe utilitária: não deve ser instanciada
    }

    /** Duração formatada como m:ss (ex.: "3:07"), ou "0:00" se não for possível calcular. */
    public static String duracaoFormatada(File arquivo) {
        long segundos = duracaoEmSegundos(arquivo);
        long minutos = segundos / 60;
        long resto = segundos % 60;
        return minutos + ":" + (resto < 10 ? "0" : "") + resto;
    }

    public static long duracaoEmSegundos(File arquivo) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            long tamanhoArquivo = raf.length();
            long posicao = pularId3v2(raf);

            byte[] cabecalho = new byte[4];

            while (posicao < tamanhoArquivo - 4) {
                raf.seek(posicao);
                if (raf.read(cabecalho) < 4) break;

                boolean sincronismoValido = (cabecalho[0] & 0xFF) == 0xFF && (cabecalho[1] & 0xE0) == 0xE0;
                if (sincronismoValido) {
                    InfoFrame info = lerCabecalhoFrame(cabecalho);
                    if (info != null) {
                        Long duracaoVbr = tentarLerVbr(raf, posicao, info);
                        if (duracaoVbr != null && duracaoVbr > 0) return duracaoVbr;

                        long bytesAudio = tamanhoArquivo - posicao;
                        long bitsPorSegundo = info.bitrateKbps * 1000L;
                        if (bitsPorSegundo <= 0) return 0;
                        return (bytesAudio * 8) / bitsPorSegundo;
                    }
                }
                posicao++;
            }
        } catch (IOException e) {
            return 0;
        }
        return 0;
    }

    private static long pularId3v2(RandomAccessFile raf) throws IOException {
        raf.seek(0);
        byte[] cabecalho = new byte[10];
        if (raf.read(cabecalho) < 10) return 0;

        if (cabecalho[0] == 'I' && cabecalho[1] == 'D' && cabecalho[2] == '3') {
            int tamanho = ((cabecalho[6] & 0x7F) << 21)
                    | ((cabecalho[7] & 0x7F) << 14)
                    | ((cabecalho[8] & 0x7F) << 7)
                    | (cabecalho[9] & 0x7F);
            return 10 + tamanho;
        }
        return 0;
    }

    private static InfoFrame lerCabecalhoFrame(byte[] cabecalho) {
        int versaoBits = (cabecalho[1] >> 3) & 0x03;   // 0=MPEG2.5, 2=MPEG2, 3=MPEG1
        int camadaBits = (cabecalho[1] >> 1) & 0x03;   // 1=Layer III, 2=Layer II, 3=Layer I
        int bitrateIndex = (cabecalho[2] >> 4) & 0x0F;
        int amostragemIndex = (cabecalho[2] >> 2) & 0x03;
        int padding = (cabecalho[2] >> 1) & 0x01;

        if (versaoBits == 1 || camadaBits == 0 || bitrateIndex == 0 || bitrateIndex == 15 || amostragemIndex == 3) {
            return null; // combinação reservada/inválida — não é um cabeçalho de frame de verdade
        }

        boolean mpeg1 = versaoBits == 3;
        int camada = camadaBits == 3 ? 1 : (camadaBits == 2 ? 2 : 3);

        int linhaTabelaBitrate = mpeg1 ? (camada - 1) : (camada == 1 ? 3 : 4);
        int bitrateKbps = TABELA_BITRATE[linhaTabelaBitrate][bitrateIndex];

        int linhaAmostragem = versaoBits == 3 ? 0 : (versaoBits == 2 ? 1 : 2);
        int amostragem = TABELA_AMOSTRAGEM[linhaAmostragem][amostragemIndex];

        int amostrasPorFrame = camada == 1 ? 384 : (camada == 2 ? 1152 : (mpeg1 ? 1152 : 576));

        int tamanhoFrame = camada == 1
                ? ((12 * bitrateKbps * 1000 / amostragem) + padding) * 4
                : (amostrasPorFrame / 8 * bitrateKbps * 1000 / amostragem) + padding;

        InfoFrame info = new InfoFrame();
        info.bitrateKbps = bitrateKbps;
        info.amostragem = amostragem;
        info.amostrasPorFrame = amostrasPorFrame;
        info.tamanhoFrame = tamanhoFrame;
        return info;
    }

    /** Procura um cabeçalho Xing/Info (VBR) dentro do primeiro frame para achar a contagem exata de frames. */
    private static Long tentarLerVbr(RandomAccessFile raf, long posicaoFrame, InfoFrame info) throws IOException {
        raf.seek(posicaoFrame);
        int tamanhoLeitura = Math.min(info.tamanhoFrame > 0 ? info.tamanhoFrame : 400, 1000);
        byte[] frame = new byte[tamanhoLeitura];
        int lidos = raf.read(frame);
        if (lidos <= 0) return null;

        String conteudo = new String(frame, 0, lidos, StandardCharsets.ISO_8859_1);
        int indice = conteudo.indexOf("Xing");
        if (indice < 0) indice = conteudo.indexOf("Info");
        if (indice < 0 || indice + 12 > lidos) return null;

        int numeroFrames = ((frame[indice + 8] & 0xFF) << 24)
                | ((frame[indice + 9] & 0xFF) << 16)
                | ((frame[indice + 10] & 0xFF) << 8)
                | (frame[indice + 11] & 0xFF);

        if (numeroFrames <= 0) return null;
        return (long) numeroFrames * info.amostrasPorFrame / info.amostragem;
    }

    private static class InfoFrame {
        int bitrateKbps;
        int amostragem;
        int amostrasPorFrame;
        int tamanhoFrame;
    }
}
