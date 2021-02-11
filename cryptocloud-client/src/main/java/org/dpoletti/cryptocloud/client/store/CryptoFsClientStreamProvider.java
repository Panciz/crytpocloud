package org.dpoletti.cryptocloud.client.store;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;

import org.dpoletti.cryptocloud.core.exeption.ProviderStreamGenerationException;
import org.dpoletti.cryptocloud.libenc.FileCryptoUtil;

public class CryptoFsClientStreamProvider extends FilesystemClientStreamProvider {

	private final FileCryptoUtil cryptoUtil;
	
	public CryptoFsClientStreamProvider(String username, String filePath,File file) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException {
		super(username, filePath);
		//TODO add option to avoid autogeneration key if not present
		cryptoUtil=new FileCryptoUtil(file);
		
	}


	@Override
	public OutputStream getFilterOutputStoreStream(OutputStream output) throws ProviderStreamGenerationException {
		try {
			return cryptoUtil.getOutputStream( super.getFilterOutputStoreStream(output));
		} catch (InvalidKeyException  | IOException e) {
			throw new ProviderStreamGenerationException("Error generating Crypto output Exception "+e.getMessage(),e);
		}
	}
	
	@Override
	public InputStream getFilterRecieveNetwordStream(InputStream input) throws ProviderStreamGenerationException {
		try {
			return cryptoUtil.getInputStream(super.getFilterRecieveNetwordStream(input));
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IOException e) {
			throw new ProviderStreamGenerationException("Error generating Crypto Input Exception "+e.getMessage(),e);
		}
	}
	



}
