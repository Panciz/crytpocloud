package org.dpoletti.cryptocloud.core;

import java.rmi.ServerException;

import org.dpoletti.cryptocloud.core.server.CryptoCloudFileServer;

public class SimpleServer {

	
	public final static void main(String[] args) throws ServerException {
		
		CryptoCloudFileServer cs = new CryptoCloudFileServer(9000);
		cs.startServer();
		
	} 
}
