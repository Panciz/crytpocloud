package org.dpoletti.cryptocloud.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.dpoletti.cryptocloud.client.store.ClientStreamProviderFactory;
import org.dpoletti.cryptocloud.client.store.CrytoFsClientStreamProviderFactory;
import org.dpoletti.cryptocloud.client.store.FilesystemClientStreamProviderFactory;
import org.dpoletti.cryptocloud.core.exeption.ProviderGenerationException;
import org.dpoletti.cryptocloud.core.model.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoCloudSimpleClient {
	private static final Logger logger   = LoggerFactory.getLogger(CryptoCloudSimpleClient.class);

	public static void main(String[] args) throws IOException, NumberFormatException, ProviderGenerationException {
			
		//TODO args validation
		//TODO use properties file as default
		if(args.length<5) {
			logger.info("CryptoCloudSimpleClient <GET|PUT> <file> <user> <addr> <port> [encKeyFile]");
			System.exit(-1);
			return;
		}
		
		OperationType operation = OperationType.valueOf(args[0]);
		String file = args[1];
		String username = args[2];
		String addr = args[3];
		String port = args[4];
		
		ClientStreamProviderFactory fact;
		if(args.length>=6) {
			
			File keyFile=Paths.get(args[5]).toFile();
			logger.info("Using encription key "+keyFile.getAbsolutePath());
			if(!keyFile.exists()) {
				if(operation==OperationType.GET) {
					logger.error("Operation GET but key file "+keyFile.getAbsolutePath()+" do not exixsts");
					System.exit(-1);
				}
				logger.warn("The key doesn't exists will be created "+keyFile.getAbsolutePath());
				logger.warn("Keep the file in order to decrypt the file "+keyFile.getAbsolutePath());
				
			}
			fact=new CrytoFsClientStreamProviderFactory(keyFile);
			
		}else {
		
			fact = new FilesystemClientStreamProviderFactory();
		}
		
		CryptoCloudFileClient fc = new CryptoCloudFileClient(
				addr,
				Integer.valueOf(port), 
				fact.getClientStoreProvider(username, file));
		
		switch (operation) {
		case PUT:
			fc.sendFile();
			break;
		case GET:
			fc.recieveFile();
			break;
		default:
			logger.error("Unknonwn Operation "+operation);
			break;
		}

	}

}
