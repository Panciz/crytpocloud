package org.dpoletti.cryptocloud.client;

import java.io.IOException;

import org.dpoletti.cryptocloud.client.store.FilesystemClientStreamProviderFactory;

public class CryptoCloudSimpleClient {

	public static void main(String[] args) throws IOException {
		
		//TODO manage permission
	
		
		//TODO args validation
		if(args.length<4) {
			System.out.println("CryptoCloudSimpleClient <file> <user> <addr> <port> ");
			System.exit(-1);
			return;
		}
		
		String file = args[0];
		String username = args[1];
		String addr = args[2];
		String port = args[3];

		FilesystemClientStreamProviderFactory fact = new FilesystemClientStreamProviderFactory();
		CryptoCloudFileClient fc = new CryptoCloudFileClient(addr,Integer.valueOf(port), fact.getClientStoreProvider(username, file));
		fc.sendFile();
	}

}
