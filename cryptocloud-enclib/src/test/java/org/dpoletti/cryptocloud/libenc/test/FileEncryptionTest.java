package org.dpoletti.cryptocloud.libenc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dpoletti.cryptocloud.libenc.FileCryptoUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

 class FileEncryptionTest {

	
	private static final String TEST_FILE_NAME="test/test.enc1";
	private static final String TEST_PERSISTANCE_FILE_NAME="test/persitance.enc1";
	private static final String TEST_PERSISTANCE_TEXT="perfoobar";
	private static final String TEST_PERSISTANCE_KEY_FILE_NAME="test/persitance.key";

	
	@BeforeAll
	public static void addBouncyProvider() throws IOException {
		Security.addProvider(new BouncyCastleProvider());
		Path path = Paths.get("test/");
		Files.createDirectories(path);	}
	
	public static void cleanExistingFile() {
			
		new File(TEST_FILE_NAME).delete();

	}
	
	public static void cleanExistingPersistingFile() {
		
		new File(TEST_PERSISTANCE_FILE_NAME).delete();
		new File(TEST_PERSISTANCE_KEY_FILE_NAME).delete();

	}
	
	@Test
	 void andcryptAndDecryptfile() throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		cleanExistingFile();
		
		
		String originalContent = "foobar";
	    SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

	    
	    FileCryptoUtil fileEncrypterDecrypter
	      = new FileCryptoUtil(secretKey);
	    fileEncrypterDecrypter.encrypt(originalContent,TEST_FILE_NAME);

	    String decryptedContent = fileEncrypterDecrypter.decrypt(TEST_FILE_NAME);
	    assertEquals(decryptedContent, originalContent);
	    cleanExistingFile();
	}
	
	@Test
	 void cryptAndDecryptAsStream() throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		cleanExistingPersistingFile();
		String originalContent =TEST_PERSISTANCE_TEXT;

		 FileCryptoUtil encrypt
	      = new FileCryptoUtil(new File(TEST_PERSISTANCE_KEY_FILE_NAME));
		try(FileOutputStream fos = new FileOutputStream(new File(TEST_PERSISTANCE_FILE_NAME));
				CipherOutputStream cos = encrypt.getOutputStream(fos);
				){
			cos.write(originalContent.getBytes());
		}
		 FileCryptoUtil decrypt
	      = new FileCryptoUtil(new File(TEST_PERSISTANCE_KEY_FILE_NAME));
		 String decryptedContent;
		try(  FileInputStream fis = new FileInputStream(new File(TEST_PERSISTANCE_FILE_NAME));
				CipherInputStream cipherIn =  decrypt.getInputStream(fis);
                InputStreamReader inputReader = new InputStreamReader(cipherIn);
                BufferedReader reader = new BufferedReader(inputReader)
				){
		    StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            decryptedContent = sb.toString();
           
		}
	    assertEquals(decryptedContent, originalContent);

	}
	
	
	/**
	 * Encryt and save key on disc
	 * 
	 * 
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchProviderException
	 */
	@Test
	 void encrypt() throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		cleanExistingPersistingFile();
		String originalContent =TEST_PERSISTANCE_TEXT;
	  
	    FileCryptoUtil fileEncrypterDecrypter
	      = new FileCryptoUtil(new File(TEST_PERSISTANCE_KEY_FILE_NAME));
	    fileEncrypterDecrypter.encrypt(originalContent, TEST_PERSISTANCE_FILE_NAME);
	    assertTrue(new File(TEST_PERSISTANCE_KEY_FILE_NAME).exists());
	   
	}
	/**
	 * decrypt using he key on disk
	 * 
	 * 
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchProviderException
	 */
	@Test
	 void decrypt() throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		
		String originalContent =TEST_PERSISTANCE_TEXT;
	    FileCryptoUtil fileEncrypterDecrypter
	      = new FileCryptoUtil(new File(TEST_PERSISTANCE_KEY_FILE_NAME));
	    String decryptedContent = fileEncrypterDecrypter.decrypt(TEST_PERSISTANCE_FILE_NAME);
	    assertEquals(decryptedContent, originalContent);

		cleanExistingPersistingFile();

	}
}
