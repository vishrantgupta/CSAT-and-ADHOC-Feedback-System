package com.vishrant.cf.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public final class DeEncrypter {

    private static DeEncrypter instance = new DeEncrypter();

    private Cipher cipher;
    private Key key;

    public static DeEncrypter getInstance() {
        return DeEncrypter.instance;
    }

    private DeEncrypter() {

        try {
            this.cipher = Cipher.getInstance("AES");
            byte[] raw = {(byte) 0xA5, (byte) 0x01, (byte) 0x7B, (byte) 0xE5,
                (byte) 0x23, (byte) 0xCD, (byte) 0xD4, (byte) 0xD2,
                (byte) 0xC6, (byte) 0x5F, (byte) 0x7D, (byte) 0x8B,
                (byte) 0x0B, (byte) 0x9A, (byte) 0x3C, (byte) 0xF1};
            this.key = new SecretKeySpec(raw, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String aData) throws Exception {

        String result = "";
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] utf8 = aData.getBytes("UTF8");
            byte[] encryptedData = cipher.doFinal(utf8);
            result = Base64.encode(encryptedData);
        } catch (InvalidKeyException oException) {
            oException.printStackTrace();
        } catch (IllegalBlockSizeException oException) {
            oException.printStackTrace();
        } catch (BadPaddingException oException) {
            oException.printStackTrace();
        } catch (IOException oException) {
            oException.printStackTrace();
        }

        return result;
    }

    public String decrypt(String aData) throws Exception {

        String result = "";
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedData = Base64.decode(aData);
            byte[] utf8 = cipher.doFinal(decodedData);
            result = new String(utf8, "UTF8");
        } catch (InvalidKeyException oException) {
            oException.printStackTrace();
        } catch (Base64DecodingException oException) {
            oException.printStackTrace();
        } catch (IllegalBlockSizeException oException) {
            oException.printStackTrace();
        } catch (BadPaddingException oException) {
            oException.printStackTrace();
        } catch (UnsupportedEncodingException oException) {
            oException.printStackTrace();
        }

        return result;
    }
    
}