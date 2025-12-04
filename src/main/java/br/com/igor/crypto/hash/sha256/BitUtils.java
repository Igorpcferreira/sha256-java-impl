package br.com.igor.crypto.hash.sha256;

/** Utilitários de operações bit a bit para inteiros de 32 bits (SHA-256). */
final class BitUtils {
    private BitUtils() {}

    static int rotr(int x, int n) {
        return (x >>> n) | (x << (32 - n));
    }

    static long toUnsignedLong(int x) {
        return x & 0xffffffffL;
    }

    static int readIntBigEndian(byte[] b, int off) {
        return ((b[off] & 0xFF) << 24)
             | ((b[off+1] & 0xFF) << 16)
             | ((b[off+2] & 0xFF) << 8)
             | ((b[off+3] & 0xFF));
    }

    static void writeIntBigEndian(byte[] out, int off, int v) {
        out[off]   = (byte) (v >>> 24);
        out[off+1] = (byte) (v >>> 16);
        out[off+2] = (byte) (v >>> 8);
        out[off+3] = (byte) (v);
    }
}
