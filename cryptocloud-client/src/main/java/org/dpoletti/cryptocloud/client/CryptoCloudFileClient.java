package org.dpoletti.cryptocloud.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


import org.dpoletti.cryptocloud.client.store.ClientStreamProvider;
import org.dpoletti.cryptocloud.core.exeption.ClientException;
import org.dpoletti.cryptocloud.core.exeption.ProtocolException;
import org.dpoletti.cryptocloud.core.model.OperationType;
import org.dpoletti.cryptocloud.core.model.ProtocolMessages;
import org.dpoletti.cryptocloud.core.model.RequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 
 * Upload an inputfile stream to the CryptoCloudServer.
 *
 * 
 * 
 */
public class CryptoCloudFileClient {


	private static final Logger logger   = LoggerFactory.getLogger(CryptoCloudFileClient.class);

	private static final int BUFFER_SIZE = 1024;

	private final ClientStreamProvider clientStoreProvider;

	private final String addr;
	private final int port;

	public CryptoCloudFileClient(String addr, int port,ClientStreamProvider provider) {
		super();
		this.addr = addr;
		this.port = port;
		this.clientStoreProvider=provider;
	}

    public void sendFile() throws IOException {
       try(Socket clientSocket = new Socket(addr,port);
    	   BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
    	   BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream())
    	 ){
    	   
    	   RequestHeader rh = clientStoreProvider.getRequestHeader();
    	   rh.setOpType(OperationType.PUT);
    	   logger.debug("Sending header "+rh);
    	   sendHeader(rh, bos, bis);
    	   logger.debug("Starting file transimission "+rh);
    	   long bytes =  sendBinaryfile( bos);
    	   logger.debug("Sent "+bytes+" bytes");
    	  // String result = reader.readLine();
    	  // logger.debug("resutl "+result);
    	   	//TODO read OK response
       }catch(Exception e ) {
    	   logger.error("Error connecting "+e.getMessage());
       }
  
    }

    public void recieveFile(){
    	 try(Socket clientSocket = new Socket(addr,port);
    	    	   BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
    	    	   BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream()))
    	    	 {
    	    	   
    	    	   RequestHeader rh = clientStoreProvider.getRequestHeader();
    	    	   rh.setOpType(OperationType.GET);
    	    	   logger.debug("Sending header "+rh);
    	    	   sendHeader(rh, bos, bis);
    	    	   logger.debug("Revceiving file "+rh);
    	    	   long bytes =  recieveBinaryFile( bis);
    	    	   logger.debug("Recieved "+bytes+" bytes");

	       }catch(Exception e ) {
	    	   logger.error("Error connecting "+e.getMessage());
	       }
    }
    
    private String readResponse(BufferedInputStream bif) throws ProtocolException, IOException {
		
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
		 }
		return headerBuffer.toString(); 
		
	}
    
    private long recieveBinaryFile(BufferedInputStream bis) throws ClientException {
    	try (BufferedOutputStream bos = new BufferedOutputStream(clientStoreProvider.getRecieveFileStream())){		
		    int len;
		    byte[] buff = new byte[BUFFER_SIZE];
		    long totalSize=0;
		    while ((len = bis.read(buff)) > 0) {
		    	bos.write(buff, 0, len);
		    	totalSize+=len;
		    	
		    }
		    bos.flush();
			return totalSize;
 
		} catch (IOException e) {
			throw new ClientException("Error reading file ",e.getCause() );
		}
	}

	private void sendHeader(RequestHeader rh,BufferedOutputStream bos,BufferedInputStream bis) throws ClientException, ProtocolException {
    	try {
			bos.write(RequestHeader.serializeHeader(rh).getBytes());
			bos.write(ProtocolMessages.HEADER_END_CHAR);
			bos.flush();
			String res = readResponse(bis);
			if(!ProtocolMessages.HEADER_OK_MSG.equals(res)) {
				throw new ProtocolException("Not valid header: "+res);
			}
		} catch (IOException e) {
			throw new ClientException("Error sending header ",e.getCause() );
		}
    }
    
    private long sendBinaryfile(BufferedOutputStream bos) throws ClientException {
    	try (BufferedInputStream bif = new BufferedInputStream(clientStoreProvider.getSendFileStream())){		
		    int len;
		    byte[] buff = new byte[BUFFER_SIZE];
		    long totalSize=0;
		    while ((len = bif.read(buff)) > 0) {
		    	bos.write(buff, 0, len);
		    	totalSize+=len;
		    	
		    }
		    bos.flush();
			return totalSize;
 
		} catch (IOException e) {
			throw new ClientException("Error sending file ",e.getCause() );
		}
    }
  
}
