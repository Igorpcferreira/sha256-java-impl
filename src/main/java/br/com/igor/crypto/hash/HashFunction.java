package br.com.igor.crypto.hash;

public interface HashFunction {
    /**
     * Retorna o digest (hash) em bytes.
     */
    byte[] digest(byte[] input);

    /**
     * Retorna o digest em hexadecimal (minúsculo).
     */
    default String digestHex(byte[] input) {
        return br.com.igor.crypto.hash.sha256.HexUtils.toHex(digest(input));
    }

    /**
     * Atalho: calcula hash de uma string (UTF-8) e devolve em hex.
     */
    default String digestHex(String input) {
        return digestHex(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
