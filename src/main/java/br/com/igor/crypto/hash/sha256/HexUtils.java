package br.com.igor.crypto.hash.sha256;

import java.util.Base64;

public final class HexUtils {
    private static final char[] HEX = "0123456789abcdef".toCharArray();
    private HexUtils() {}

    public static String toHex(byte[] data) {
        char[] out = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            int v = data[i] & 0xFF;
            out[i*2]     = HEX[v >>> 4];
            out[i*2 + 1] = HEX[v & 0x0F];
        }
        return new String(out);
    }

    public static String toBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
