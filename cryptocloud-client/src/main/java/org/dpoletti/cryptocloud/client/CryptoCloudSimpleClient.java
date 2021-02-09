package org.dpoletti.cryptocloud.client;

import java.io.IOException;

import org.dpoletti.cryptocloud.client.store.FilesystemClientStreamProviderFactory;
import org.dpoletti.cryptocloud.core.model.OperationType;

public class CryptoCloudSimpleClient {

	public static void main(String[] args) throws IOException {
		
		//TODO manage permission
	
		
		//TODO args validation
		//TODO use properties file as default
		if(args.length<5) {
			System.out.println("CryptoCloudSimpleClient [GET|PUT] <file> <user> <addr> <port> ");
			System.exit(-1);
			return;
		}
		
		OperationType operation = OperationType.valueOf(args[0]);
		String file = args[1];
		String username = args[2];
		String addr = args[3];
		String port = args[4];
		FilesystemClientStreamProviderFactory fact = new FilesystemClientStreamProviderFactory();

		if(operation==OperationType.PUT){
			CryptoCloudFileClient fc = new CryptoCloudFileClient(
				addr,
				Integer.valueOf(port), 
				fact.getClientStoreProvider(username, file));
			fc.sendFile();
		}
	}

}
