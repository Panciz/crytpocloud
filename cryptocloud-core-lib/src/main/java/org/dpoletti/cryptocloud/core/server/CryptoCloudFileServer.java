package org.dpoletti.cryptocloud.core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 *
 * Server that listen for incoming files.
 * 
 *  
 * 
 * 
 * */
public class CryptoCloudFileServer {

	private static final Logger logger 
	  = LoggerFactory.getLogger(CryptoCloudFileServer.class);
	//Limit the number of active connections 
	private static final int MAX_ACTIVE_CONNECTIONS = 100;
	private final int port;

	
	private volatile boolean running;
	private ServerSocket server;
	
	private ExecutorService threadPool = Executors.newFixedThreadPool(MAX_ACTIVE_CONNECTIONS);
	
	
	public CryptoCloudFileServer(int port) {
		super();
		this.port = port;
	}
	
	public void startServer() throws ServerException {
		
		logger.info("FileServer Listen to port "+port);
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
				threadPool.submit(new CryptoFileRequestHandler(socket));
				
			} catch (IOException e) {
				running =false;
				throw new ServerException("File server error", e);
			}
			
			
		}
		
	}
	
	
}
