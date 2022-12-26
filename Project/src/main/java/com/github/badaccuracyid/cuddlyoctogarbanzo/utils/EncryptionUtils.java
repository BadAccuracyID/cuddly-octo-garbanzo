package com.github.badaccuracyid.cuddlyoctogarbanzo.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

// AES encryption/decryption
public class EncryptionUtils {

    private static final SecretKeySpec keySpec;
    private static final Cipher cipher;

    static {
        keySpec = new SecretKeySpec(Arrays.copyOf(Base64.getDecoder().decode("Us3rS3CR3TD4T4SS"), 16), "AES");
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(1, keySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(str.trim().getBytes()));
    }

    public static String decrypt(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(2, keySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(str)));
    }

}
