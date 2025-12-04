package br.com.igor.crypto.hash.sha256;

import br.com.igor.crypto.hash.HashFunction;
import java.util.Arrays;

/**
 * Implementação do SHA-256 baseada na especificação FIPS PUB 180-4.
 * Somente para fins educacionais. Não usa java.security.MessageDigest.
 */
public final class Sha256 implements HashFunction {

    // Constantes de inicialização (H0..H7)
    private static final int[] H0 = {
            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
            0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
    };

    // Tabela de constantes K[0..63]
    private static final int[] K = {
            0x428a2f98,0x71374491,0xb5c0fbcf,0xe9b5dba5,0x3956c25b,0x59f111f1,0x923f82a4,0xab1c5ed5,
            0xd807aa98,0x12835b01,0x243185be,0x550c7dc3,0x72be5d74,0x80deb1fe,0x9bdc06a7,0xc19bf174,
            0xe49b69c1,0xefbe4786,0x0fc19dc6,0x240ca1cc,0x2de92c6f,0x4a7484aa,0x5cb0a9dc,0x76f988da,
            0x983e5152,0xa831c66d,0xb00327c8,0xbf597fc7,0xc6e00bf3,0xd5a79147,0x06ca6351,0x14292967,
            0x27b70a85,0x2e1b2138,0x4d2c6dfc,0x53380d13,0x650a7354,0x766a0abb,0x81c2c92e,0x92722c85,
            0xa2bfe8a1,0xa81a664b,0xc24b8b70,0xc76c51a3,0xd192e819,0xd6990624,0xf40e3585,0x106aa070,
            0x19a4c116,0x1e376c08,0x2748774c,0x34b0bcb5,0x391c0cb3,0x4ed8aa4a,0x5b9cca4f,0x682e6ff3,
            0x748f82ee,0x78a5636f,0x84c87814,0x8cc70208,0x90befffa,0xa4506ceb,0xbef9a3f7,0xc67178f2
    };

    private static int ch(int x, int y, int z) { return (x & y) ^ (~x & z); }
    private static int maj(int x, int y, int z) { return (x & y) ^ (x & z) ^ (y & z); }
    private static int bigSigma0(int x) { return BitUtils.rotr(x, 2) ^ BitUtils.rotr(x, 13) ^ BitUtils.rotr(x, 22); }
    private static int bigSigma1(int x) { return BitUtils.rotr(x, 6) ^ BitUtils.rotr(x, 11) ^ BitUtils.rotr(x, 25); }
    private static int smallSigma0(int x) { return BitUtils.rotr(x, 7) ^ BitUtils.rotr(x, 18) ^ (x >>> 3); }
    private static int smallSigma1(int x) { return BitUtils.rotr(x, 17) ^ BitUtils.rotr(x, 19) ^ (x >>> 10); }

    private byte[] pad(byte[] message) {
        long bitLen = (long) message.length * 8L;

        // Comprimento final deve ser congruente a 56 (mod 64) antes de anexar 8 bytes do comprimento.
        int paddingBytes = (int) ((56 - (message.length + 1) % 64 + 64) % 64);
        int newLen = message.length + 1 + paddingBytes + 8;
        byte[] out = Arrays.copyOf(message, newLen);

        // 0x80
        out[message.length] = (byte) 0x80;

        // anexa comprimento em bits (big-endian, 64 bits)
        for (int i = 0; i < 8; i++) {
            out[newLen - 1 - i] = (byte) (bitLen >>> (8 * i));
        }
        return out;
    }

    @Override
    public byte[] digest(byte[] input) {
        byte[] data = pad(input);

        int[] H = Arrays.copyOf(H0, H0.length);
        int[] W = new int[64];

        for (int off = 0; off < data.length; off += 64) {
            // prepara W[0..63]
            for (int i = 0; i < 16; i++) {
                W[i] = BitUtils.readIntBigEndian(data, off + i * 4);
            }
            for (int t = 16; t < 64; t++) {
                W[t] = smallSigma1(W[t-2]) + W[t-7] + smallSigma0(W[t-15]) + W[t-16];
            }

            int a = H[0], b = H[1], c = H[2], d = H[3], e = H[4], f = H[5], g = H[6], h = H[7];

            for (int t = 0; t < 64; t++) {
                int T1 = h + bigSigma1(e) + ch(e,f,g) + K[t] + W[t];
                int T2 = bigSigma0(a) + maj(a,b,c);
                h = g;
                g = f;
                f = e;
                e = d + T1;
                d = c;
                c = b;
                b = a;
                a = T1 + T2;
            }

            H[0] += a; H[1] += b; H[2] += c; H[3] += d;
            H[4] += e; H[5] += f; H[6] += g; H[7] += h;
        }

        byte[] out = new byte[32];
        for (int i = 0; i < 8; i++) {
            BitUtils.writeIntBigEndian(out, i * 4, H[i]);
        }
        return out;
    }
}
