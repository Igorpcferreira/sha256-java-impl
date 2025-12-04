package br.com.igor.crypto.hash.sha256;

import br.com.igor.crypto.hash.HashFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Sha256Test {

    private final HashFunction sha = new Sha256();

    @Test
    void testEmptyString() {
        String h = sha.digestHex(new byte[0]);
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", h);
    }

    @Test
    void testABC() {
        String h = sha.digestHex("abc");
        assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", h);
    }

    @Test
    void testLongRFCVector() {
        String input = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
        String h = sha.digestHex(input);
        assertEquals("248d6a61d20638b8e5c026930c3e6039a33ce45964ff2167f6ecedd419db06c1", h);
    }
}
