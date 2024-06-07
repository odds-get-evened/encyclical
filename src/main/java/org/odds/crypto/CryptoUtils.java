package org.odds.crypto;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

public class CryptoUtils {
    public static final int ITERATIONS = 65536;
    public static final int KEYSIZE = 256;

    public static byte[] getRandomNonce(int n) {
        byte[] iv = new byte[n];
        new SecureRandom().nextBytes(iv);

        return iv;
    }

    public static SecretKey getAESKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(n, SecureRandom.getInstanceStrong());

        return gen.generateKey();
    }

    public static SecretKey getAESKeyFromPassword(char[] passwd, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(passwd, salt, ITERATIONS, KEYSIZE);

        return new SecretKeySpec(f.generateSecret(spec).getEncoded(), "AES");
    }

    public static String hex(byte[] b) {
        return Hex.encodeHexString(b);
    }

    public static String hex(byte[] b, int n) {
        String hex = hex(b);

        n = n * 2;

        List<String> res = new ArrayList<>();
        int i = 0;
        while(i < hex.length()) {
            res.add(hex.substring(i, Math.min(i + n, hex.length())));
            i += n;
        }

        return res.toString();
    }
}
