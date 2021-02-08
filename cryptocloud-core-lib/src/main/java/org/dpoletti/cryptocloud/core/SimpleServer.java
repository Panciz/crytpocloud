package org.dpoletti.cryptocloud.core;

import java.rmi.ServerException;

import org.dpoletti.cryptocloud.core.server.CryptoCloudFileServer;
import org.dpoletti.cryptocloud.core.server.store.StoreFileOutputStreamProvider;

public class SimpleServer {

	
	public static final void main(String[] args) throws ServerException {
		
		CryptoCloudFileServer cs = new CryptoCloudFileServer(9000);
		cs.setOutprovider(new StoreFileOutputStreamProvider());
		
		cs.startServer();
		
	} 
}
