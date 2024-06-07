package gov.ny.its;

import io.github.novacrypto.base58.Base58;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.odds.crypto.Symmetrical;

public class Main {
    public static void doSymmetrical() throws DecoderException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        String myText = "Testing this encryption. I believe in magic. I won't let them hurt you.";

        //SecretKey key = Symmetrical.generateKey();
        SecretKey key = Symmetrical.getKeyFromPassword("password".toCharArray(), "salty".getBytes(StandardCharsets.UTF_8));
        String userKey = Base58.base58Encode(key.getEncoded());
        System.out.printf("secret key (encoded): %s\n", userKey);

        /**
         * this is like a password, but must be 16 bytes and match
         * for both encryption and decryption.
         * @TODO either pad strings that are less than 16 bytes,
         * or figure out a way to use varying length IVs
         * @IDEA save hex string of both secret-key, and IV
         * save IV and secret key separately!
         */
        byte[] iv = Symmetrical.createIV(16);
        System.out.printf("iv (encoded): %s\n", Base58.base58Encode(iv));
        String ivStr = Base58.base58Encode(iv);

        byte[] enc = Symmetrical.encrypt(
                myText.getBytes(StandardCharsets.UTF_8),
                userKey,
                ivStr
        );
        System.out.println("encrypted message (base64): ".concat(Base64.encodeBase64String(enc)));

        byte[] dec = Symmetrical.decrypt(
                enc,
                userKey,
                ivStr
        );
        System.out.println("decrypted message (string): ".concat(new String(dec, StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) {
        try {
            doSymmetrical();
        } catch (DecoderException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                 IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException |
                 InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}