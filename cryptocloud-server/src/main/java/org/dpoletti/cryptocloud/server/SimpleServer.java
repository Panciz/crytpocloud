package org.dpoletti.cryptocloud.server;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.ServerException;

import org.dpoletti.cryptocloud.server.store.StoreFileOutputProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServer {
	private static final Logger logger 
	  = LoggerFactory.getLogger(SimpleServer.class);
	
	public static final void main(String[] args) throws ServerException {
		
		
		Path destDir = Paths.get(args[0]);
		//TODO dest validation
		logger.info("FileServer Listen to port 9000 destDir "+destDir);

		CryptoCloudFileServer cs = new CryptoCloudFileServer(9000);
		cs.setOutproviderFactory(new StoreFileOutputProviderFactory(destDir));
		
		cs.startServer();
		
	} 
}
