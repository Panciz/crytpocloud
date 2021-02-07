package org.dpoletti.cryptocloud.core.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.dpoletti.cryptocloud.core.exeption.ProtocolException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;
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
	//Create output provider
	
	public CryptoFileRequestHandler(Socket socket) {
		super();
		this.socket = socket;
	}

	private static final Logger logger 
	  = LoggerFactory.getLogger(CryptoFileRequestHandler.class);
	private static final int BUFFER_SIZE = 1024;
	private static final char HEADER_END_CHAR= '\n';

	private static final int HEADER_MAX_SIZE= 500;

	
	private static final String HEADER_OK_MSG= "OK_HEADER";

	@Override
	public void run() {

		logger.debug("Connection start");
		
		 try(BufferedInputStream bif = new BufferedInputStream(socket.getInputStream(),BUFFER_SIZE);
		           PrintWriter out = new PrintWriter(socket.getOutputStream(), true);){
		
			String header= readHeader(bif);
			logger.debug("Header "+header);
			RequestHeader rh = parseHeader(header);
			//TODO header validation
			out.println(HEADER_OK_MSG);
			
			
		 } catch (Exception e) {
			logger.error("Error while receving file submition: "+e.getMessage());
		 }
		
		
	}
	
	private String readHeader(BufferedInputStream bif) throws ProtocolException, IOException {
		
		 StringBuilder headerBuffer = new StringBuilder();
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

	public RequestHeader parseHeader(String userNameFileName) {
		String[] vals = userNameFileName.split("@");
		RequestHeader rh = new RequestHeader();
		if(vals.length>0) {
			rh.setUsername(vals[0]);
		}
		
		if(vals.length>1) {
			rh.setFilename(vals[1]);
		}
		return rh;
	}
	
	


	
	
}
