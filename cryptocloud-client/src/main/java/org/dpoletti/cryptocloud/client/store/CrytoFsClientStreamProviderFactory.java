package org.dpoletti.cryptocloud.client.store;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dpoletti.cryptocloud.core.exeption.ProviderGenerationException;

public class CrytoFsClientStreamProviderFactory implements ClientStreamProviderFactory {

	private final  File keyFile;
	
	
	public CrytoFsClientStreamProviderFactory(File keyFile) {
		super();
		this.keyFile = keyFile;
		Security.addProvider(new BouncyCastleProvider());
	}


	@Override
	public ClientStreamProvider getClientStoreProvider(String username, String filePath) throws ProviderGenerationException {
		try {
			return new CryptoFsClientStreamProvider(username,filePath,keyFile);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | IOException e) {
			throw new ProviderGenerationException("Error generating CryptoFsClientStreamProvider",e);
		}
	}

}
