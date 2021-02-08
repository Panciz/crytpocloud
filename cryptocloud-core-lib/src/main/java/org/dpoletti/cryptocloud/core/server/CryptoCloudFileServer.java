package org.dpoletti.cryptocloud.core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dpoletti.cryptocloud.core.server.store.StoreOutputProviderFactory;


/***
 *
 * Server that listen for incoming files.
 * 
 *  
 * 
 * 
 * */
public class CryptoCloudFileServer {


	//Limit the number of active connections 
	private static final int MAX_ACTIVE_CONNECTIONS = 100;
	private final int port;

	
	private volatile boolean running;
	private ServerSocket server;
	private StoreOutputProviderFactory outproviderFactory;

	
	public StoreOutputProviderFactory getOutproviderFactory() {
		return outproviderFactory;
	}

	public void setOutproviderFactory(StoreOutputProviderFactory outproviderFactory) {
		this.outproviderFactory = outproviderFactory;
	}

	private ExecutorService threadPool = Executors.newFixedThreadPool(MAX_ACTIVE_CONNECTIONS);
	
	
	public CryptoCloudFileServer(int port) {
		super();
		this.port = port;
	}
	
	public void startServer() throws ServerException {
		
		running = true;
		try {
			
			server=new ServerSocket(port);			
			
		} catch (IOException e) {
			running =false;
			throw new ServerException("Error starting file server ", e);
		}
		
		while(running) {
			
			try {
				Socket socket= server.accept();
				threadPool.submit(new CryptoFileRequestHandler(socket,outproviderFactory.getOutputProviderInstance()));
				
			} catch (IOException e) {
				running =false;
				throw new ServerException("File server error", e);
			}
			
			
		}
		
	}
	
	
}
