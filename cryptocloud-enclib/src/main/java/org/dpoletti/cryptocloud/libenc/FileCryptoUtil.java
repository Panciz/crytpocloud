package org.dpoletti.cryptocloud.libenc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCryptoUtil {

	
	private static final Logger logger   = LoggerFactory.getLogger(FileCryptoUtil.class);

    private SecretKey secretKey;
    private Cipher cipher;
    private static final  String CIPHER_CONF = "AES/CBC/PKCS5Padding";
    private static final  String BOUNCY_PROVIDER = "BC";
    private static final  String KEY_ALGORITM = "AES";

    private static final  int KEY_SIZE = 16;

   public FileCryptoUtil(SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        this.secretKey = secretKey;
        this.cipher = Cipher.getInstance(CIPHER_CONF,BOUNCY_PROVIDER);
    }

   
   private byte[] readKeyFromFile(File file) throws IOException {
	    byte[] keyBytes = new byte[KEY_SIZE];
	    try(FileInputStream fis = new FileInputStream(file)) {
	    	fis.read(keyBytes);
	    }
	    return keyBytes;
   }
   
   private SecretKey generateAndSaveKey(File file) throws IOException, NoSuchAlgorithmException {
	   SecretKey key = KeyGenerator.getInstance(KEY_ALGORITM).generateKey();
	    byte[] keyBytes =  key.getEncoded();
	    try(FileOutputStream fos = new FileOutputStream(file)){
	    	fos.write(keyBytes);
	    	fos.flush();
	    }
	    return key;
  }
   
   public FileCryptoUtil(File file) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException  {
	   if(file.exists()){
		   byte[] key = readKeyFromFile(file);
       	   this.secretKey = new SecretKeySpec(key, KEY_ALGORITM);
		   logger.warn("Key loaded from "+file.getAbsolutePath());

	   }else {
		   logger.warn("file "+file.getAbsolutePath()+" do not exists generating new key ");
		   this.secretKey=generateAndSaveKey(file);
	   }
       this.cipher = Cipher.getInstance(CIPHER_CONF,BOUNCY_PROVIDER);
   }
   
   public void encrypt(String content, String fileName) throws InvalidKeyException, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();

        try (
                FileOutputStream fileOut = new FileOutputStream(fileName);
                CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)
        ) {
            fileOut.write(iv);
            cipherOut.write(content.getBytes());
        }

    }

   public String decrypt(String fileName) throws InvalidAlgorithmParameterException, InvalidKeyException, IOException {

        String content;

        try (FileInputStream fileIn = new FileInputStream(fileName)) {
            byte[] fileIv = new byte[KEY_SIZE];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));

            try (
                    CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                    InputStreamReader inputReader = new InputStreamReader(cipherIn);
                    BufferedReader reader = new BufferedReader(inputReader)
                ) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
            }

        }
        return content;
    }


public CipherOutputStream getOutputStream(FileOutputStream fos) throws InvalidKeyException, IOException {
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] iv = cipher.getIV();
    fos.write(iv);
    return new CipherOutputStream(fos, cipher);
}


public CipherInputStream getInputStream(FileInputStream fis) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException {
	   byte[] fileIv = new byte[KEY_SIZE];
	   fis.read(fileIv);
       cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));
	return new CipherInputStream(fis, cipher);
}
 

}