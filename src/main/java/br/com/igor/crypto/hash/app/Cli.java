package br.com.igor.crypto.hash.app;

import br.com.igor.crypto.hash.HashFunction;
import br.com.igor.crypto.hash.sha256.HexUtils;
import br.com.igor.crypto.hash.sha256.Sha256;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Pequena CLI para calcular SHA-256. */
public final class Cli {

    private static void usage() {
        System.out.println("""
            Usage: sha256 [-s <texto>] [-f <arquivo>] [--raw]
              -s, --string <texto>   Faz hash do texto em UTF-8
              -f, --file <arquivo>   Faz hash do conteúdo de um arquivo
                  --raw              Imprime também em Base64 (além do hex)
              -h, --help             Ajuda
            """);
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0 || List.of(args).contains("-h") || List.of(args).contains("--help")) {
            usage();
            return;
        }

        boolean raw = List.of(args).contains("--raw");
        String text = null;
        Path file = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s" -> {
                    if (i + 1 >= args.length) {
                        System.err.println("Faltou o texto após -s");
                        return;
                    }
                    text = args[++i];
                }
                case "--string" -> {
                    if (i + 1 >= args.length) {
                        System.err.println("Faltou o texto após --string");
                        return;
                    }
                    text = args[++i];
                }
                case "-f", "--file" -> {
                    if (i + 1 >= args.length) {
                        System.err.println("Faltou o caminho do arquivo após -f/--file");
                        return;
                    }
                    file = Path.of(args[++i]);
                }
                default -> {}
            }
        }

        if ((text == null && file == null) || (text != null && file != null)) {
            System.err.println("Informe **apenas um**: -s <texto> **ou** -f <arquivo>\n");
            usage();
            return;
        }

        HashFunction sha = new Sha256();
        byte[] data;
        if (text != null) {
            data = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        } else {
            data = Files.readAllBytes(file);
        }
        byte[] digest = sha.digest(data);
        String hex = HexUtils.toHex(digest);
        System.out.println(hex);
        if (raw) {
            System.out.println(HexUtils.toBase64(digest));
        }
    }
}
