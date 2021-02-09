package org.dpoletti.cryptocloud.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.dpoletti.cryptocloud.core.exeption.ProtocolException;
import org.dpoletti.cryptocloud.core.model.ProtocolMessages;
import org.dpoletti.cryptocloud.core.model.RequestHeader;
import org.dpoletti.cryptocloud.server.store.StoreOutputProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * It manages the incoming files.
 * 
 * 1. It waits for the header in the format <username>@<filename>
 * 2. It sends OK_HEADER and it waits for the file
 * 3. When the file is received it send OK_END and it closes the socket
 * 
 * */
public class CryptoFileRequestHandler implements Runnable {

	private Socket socket;
	
	private StoreOutputProvider outprovider;

	
	public CryptoFileRequestHandler(Socket socket,StoreOutputProvider outprovider) {
		super();
		this.socket = socket;
		this.outprovider=outprovider;
	}

	private static final Logger logger 
	  = LoggerFactory.getLogger(CryptoFileRequestHandler.class);
	private static final int BUFFER_SIZE = 1024;

	private static final int HEADER_MAX_SIZE= 500;

	


	@Override
	public void run() {

		logger.debug("Connection start");
		
		 try(BufferedInputStream bis = new BufferedInputStream(socket.getInputStream(),BUFFER_SIZE);
		           PrintWriter out = new PrintWriter(socket.getOutputStream(), true);){
		
			String header= readHeader(bis);
			logger.debug("Header "+header);
			RequestHeader rh = RequestHeader.parseHeader(header);
			long totalSize = -1l;
			//TODO header validation
			out.println(ProtocolMessages.HEADER_OK_MSG);
			try(BufferedOutputStream bos= new BufferedOutputStream(outprovider.getStoreOutputStream(rh))){
			    logger.debug(rh+" Waiting for file ");
				 totalSize =  storeFile(bis,bos);
			    logger.debug("File size  "+totalSize+" Stored");

			}
			out.println(ProtocolMessages.END_OK_MSG);
			logger.debug("Handling success ");
			outprovider.endTransmissionSuccess(rh, totalSize);
			
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
				 throw new ProtocolException("Invalid file submition no complete header provided");

			 }
			 char readChar = (char)readInt;
			 if(readChar==ProtocolMessages.HEADER_END_CHAR){
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
	
	
	private long storeFile(BufferedInputStream bif,BufferedOutputStream bof ) throws IOException {
		
	    int len;
	    byte[] buff = new byte[BUFFER_SIZE];
	    long totalSize=0;
	    while ((len = bif.read(buff)) > 0) {
	    	bof.write(buff, 0, len);
	    	totalSize+=len;
	    	
	    }
	    bof.flush();
		return totalSize;
	}


	
	


	
	
}
