package org.dpoletti.cryptocloud.core.server;

import java.io.BufferedInputStream;
import java.net.Socket;

import org.dpoletti.cryptocloud.core.exeption.ProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * It manages the incoming files
 * 
 * */
public class CryptoFileRequestHandler implements Runnable {

	private Socket socket;
	
	
	//TODO 
	
	// Create success handler
	//Header validation wih tests
	// send ok if the header is validated
	//Create output provider
	
	public CryptoFileRequestHandler(Socket socket) {
		super();
		this.socket = socket;
	}

	private static final Logger logger 
	  = LoggerFactory.getLogger(CryptoCloudFileServer.class);
	private static final int BUFFER_SIZE = 1024;
	private static final char HEADER_END_CHAR= '\n';

	private static final int HEADER_MAX_SIZE= 500;

	@Override
	public void run() {

		logger.debug("Connection start");
		
		 try(BufferedInputStream bif = new BufferedInputStream(socket.getInputStream(),BUFFER_SIZE)){
		
			String header= readHeader(bif);
			logger.debug("Header "+header);
			
			
		 } catch (Exception e) {
			logger.error("Error while reading the file submition: "+e.getMessage());
		 }
		
		
	}
	
	private String readHeader(BufferedInputStream bif) throws Exception {
		
		 StringBuffer headerBuffer = new StringBuffer();
		 int  readInt;
		 while(true) {
			 readInt = bif.read();
			 if(readInt==-1) {
				 logger.error("Invalid file submition no complete header provided");
			 }
			 char readChar = (char)readInt;
			 if(readChar==HEADER_END_CHAR){
				 break;
			 }
			 headerBuffer.append(readChar);
			 if(headerBuffer.length()>HEADER_MAX_SIZE) {
				 logger.error("Invalid file submition header length more than "+HEADER_MAX_SIZE);
				 throw new ProtocolException("Invalid file submition header length more than "+HEADER_MAX_SIZE);
			 }
		 }
		return headerBuffer.toString();
		 
		 
		
	}
	


	
	
}
