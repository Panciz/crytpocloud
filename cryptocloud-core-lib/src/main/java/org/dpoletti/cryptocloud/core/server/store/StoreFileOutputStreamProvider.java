package org.dpoletti.cryptocloud.core.server.store;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.dpoletti.cryptocloud.core.exeption.StoreException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/*
 * Simple implementation of the outputstream store provider, it saved the file on the filesystem.
 * It use the temporary dir to save the file and it move it only when the whole file has been recieved.
 * 
 * 
 * 
 */
public class StoreFileOutputStreamProvider implements StoreOutputProvider{
	private static final Logger logger   = LoggerFactory.getLogger(StoreFileOutputStreamProvider.class);
	
	private final static String TEMP_FILE_PREFIX = "crycl-";
	
	@Override
	public OutputStream getStoreOutputStream(RequestHeader rh) throws StoreException  {
		try {
			Path tempFile = Files.createTempFile(TEMP_FILE_PREFIX, rh.getUsername());
			logger.debug("saving to temp file "+tempFile.toAbsolutePath());
			return new FileOutputStream(tempFile.toFile());
			
			
		} catch (IOException e) {
			throw new StoreException("Error writing temp file",e.getCause());
		}
		
	}

}
