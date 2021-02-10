package org.dpoletti.cryptocloud.libenc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dpoletti.cryptocloud.libenc.FileCryptoUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileEncryptionTest {

	
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
	public void andcryptAndDecryptfile() throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
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
	public void encrypt() throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		cleanExistingPersistingFile();
		String originalContent =TEST_PERSISTANCE_TEXT;
	  
	    FileCryptoUtil fileEncrypterDecrypter
	      = new FileCryptoUtil(new File(TEST_PERSISTANCE_KEY_FILE_NAME));
	    fileEncrypterDecrypter.encrypt(originalContent, TEST_PERSISTANCE_FILE_NAME);

	   
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
	public void decrypt() throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		
		String originalContent =TEST_PERSISTANCE_TEXT;
	    FileCryptoUtil fileEncrypterDecrypter
	      = new FileCryptoUtil(new File(TEST_PERSISTANCE_KEY_FILE_NAME));
	    String decryptedContent = fileEncrypterDecrypter.decrypt(TEST_PERSISTANCE_FILE_NAME);
	    assertEquals(decryptedContent, originalContent);

		cleanExistingPersistingFile();

	}
}
