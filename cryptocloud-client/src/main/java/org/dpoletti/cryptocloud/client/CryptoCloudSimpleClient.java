package org.dpoletti.cryptocloud.client;

import java.io.IOException;

import org.dpoletti.cryptocloud.client.store.FilesystemClientStreamProviderFactory;
import org.dpoletti.cryptocloud.core.model.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoCloudSimpleClient {
	private static final Logger logger   = LoggerFactory.getLogger(CryptoCloudSimpleClient.class);

	public static void main(String[] args) throws IOException {
			
		//TODO args validation
		//TODO use properties file as default
		if(args.length<5) {
			logger.info("CryptoCloudSimpleClient [GET|PUT] <file> <user> <addr> <port> ");
			System.exit(-1);
			return;
		}
		
		OperationType operation = OperationType.valueOf(args[0]);
		String file = args[1];
		String username = args[2];
		String addr = args[3];
		String port = args[4];
		FilesystemClientStreamProviderFactory fact = new FilesystemClientStreamProviderFactory();
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
