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
    	   BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
    	 ){
    	   
    	   RequestHeader rh = clientStoreProvider.getRequestHeader();
    	   logger.debug("Sending header "+rh);
    	   sendHeader(rh, bos, reader);
    	   logger.debug("Starting file transimission "+rh);
    	   long bytes =  sendBinaryfile( bos, reader);
    	   logger.debug("Sent "+bytes+" bytes");
    	  // String result = reader.readLine();
    	  // logger.debug("resutl "+result);
    	   	//TODO read OK response
       }catch(Exception e ) {
    	   logger.error("Error connecting "+e.getMessage());
       }
  
    }

    
    private void sendHeader(RequestHeader rh,BufferedOutputStream bos,BufferedReader reader) throws Exception {
    	try {
			bos.write(RequestHeader.serializeHeader(rh).getBytes());
			bos.write(ProtocolMessages.HEADER_END_CHAR);

			bos.flush();
			String res = reader.readLine();
			if(!ProtocolMessages.HEADER_OK_MSG.equals(res)) {
				throw new ProtocolException("Not valid header: "+res);
			}
		} catch (IOException e) {
			throw new ClientException("Error sending header ",e.getCause() );
		}
    }
    
    private long sendBinaryfile(BufferedOutputStream bos,BufferedReader reader) throws Exception {
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
			throw new ClientException("Error sending header ",e.getCause() );
		}
    }
  
}
