package org.dpoletti.cryptocloud.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.dpoletti.cryptocloud.core.exeption.ProtocolException;
import org.dpoletti.cryptocloud.core.exeption.ServerException;
import org.dpoletti.cryptocloud.core.exeption.StoreException;
import org.dpoletti.cryptocloud.core.model.OperationType;
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
				 BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream(),BUFFER_SIZE);
		           PrintWriter out = new PrintWriter(socket.getOutputStream(), true);){
		
			String header= readHeader(bis);
			logger.debug("Header "+header);
			RequestHeader rh = RequestHeader.parseHeader(header);
			//TODO header validation
			out.println(ProtocolMessages.HEADER_OK_MSG);
			
			
			switch(rh.getOpType()) {
			case PUT:
					long totalSize = recieveFile(bis,rh);
					out.println(ProtocolMessages.END_OK_MSG);
					logger.debug(rh+" receive succesfully: "+totalSize+" bytes");
					outprovider.endTransmissionSuccess(rh, totalSize);
					logger.debug(rh+" store succesfully ");

					break;
			case GET:
				long sentSize = sendFile(bos,rh);
				logger.debug(rh+": senf successvully "+sentSize+" bytes");
				break;
			default:
					throw new ProtocolException("Operation not supported "+rh.getOpType());
			}
			
		 } catch (Exception e) {
			logger.error("Error file transmission: "+e.getMessage());
		 }
		
		
	}
	
	private long recieveFile(BufferedInputStream bis,RequestHeader rh) throws  StoreException, ServerException {
		try(BufferedOutputStream bos= new BufferedOutputStream(outprovider.getStoreOutputStream(rh))){
		    logger.debug(rh+" Waiting for file ");
			 long totalSize=  storeFile(bis,bos);
		    logger.debug("File size  "+totalSize+" Stored");
			return totalSize;

		}catch(IOException io) {
			throw new ServerException("Error while receiving file ",io.getCause());
		}
	}
	
	private long sendFile(BufferedOutputStream bos,RequestHeader rh) throws StoreException, ServerException {
		try(BufferedInputStream bis= new BufferedInputStream(outprovider.getStoreInputStream(rh))){
		    logger.debug(rh+" Sending file ");
		    int len;
		    byte[] buff = new byte[BUFFER_SIZE];
		    long totalSize=0;
		    while ((len = bis.read(buff)) > 0) {
		    	bos.write(buff, 0, len);
		    	totalSize+=len;
		    	
		    }
		    bos.flush();
			return totalSize;

		} catch(IOException io) {
			throw new ServerException("Error while sending file ",io.getCause());
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
