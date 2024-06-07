package org.odds.crypto;

import io.github.novacrypto.base58.Base58;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class Symmetrical {
    private static final String AES = "AES";
    private static final int KEYSIZE = 256;
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static final String SHA1PRNG = "SHA1PRNG";

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance(AES);
        gen.init(KEYSIZE, new SecureRandom());

        return gen.generateKey();
    }

    public static SecretKey getKeyFromPassword(char[] passwd, byte[] salt) throws InvalidKeySpecException,
            NoSuchAlgorithmException {
        return CryptoUtils.getAESKeyFromPassword(passwd, salt);
    }

    public static byte[] createIV(int n) throws NoSuchAlgorithmException {
        byte[] iv = new byte[n];
        SecureRandom.getInstance(SHA1PRNG).nextBytes(iv);

        return iv;
    }

    public static byte[] encrypt(byte[] clear, String secret, String iv) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, DecoderException {
        Cipher c = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        // c.init(Cipher.ENCRYPT_MODE, keyFromBytes(Hex.decodeHex(secret)), new IvParameterSpec(iv));
        c.init(Cipher.ENCRYPT_MODE, keyFromBytes(Base58.base58Decode(secret)), new IvParameterSpec(Base58.base58Decode(iv)));

        return c.doFinal(clear);
    }

    public static byte[] decrypt(byte[] cipher, String secret, String iv) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, DecoderException {
        Cipher c = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        // c.init(Cipher.DECRYPT_MODE, keyFromBytes(Hex.decodeHex(secret)), new IvParameterSpec(iv));
        c.init(Cipher.DECRYPT_MODE, keyFromBytes(Base58.base58Decode(secret)), new IvParameterSpec(Base58.base58Decode(iv)));

        return c.doFinal(cipher);
    }

    public static SecretKey keyFromBytes(byte[] b) {
        return new SecretKeySpec(b, 0, b.length, AES);
    }
}
