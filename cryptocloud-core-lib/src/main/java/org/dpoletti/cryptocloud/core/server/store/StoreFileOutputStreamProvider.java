package org.dpoletti.cryptocloud.core.server.store;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.dpoletti.cryptocloud.core.exeption.StoreException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/*
 * Simple implementation of the outputstream store provider, it saved the file on the filesystem.
 * It use the temporary dir to save the file and it move it only when the whole file has been recieved.
 * 
 * It implements also CryptoFileRequestSuccessHandler since it move the file to the final dest path when transmission is over
 * 
 */
public class StoreFileOutputStreamProvider implements StoreOutputProvider{
	
	private static final Logger logger   = LoggerFactory.getLogger(StoreFileOutputStreamProvider.class);
	
	private static final  String TEMP_FILE_PREFIX = "crycl-";
	private final Path destDir;
	private Path tempFile;
	
	public StoreFileOutputStreamProvider(Path destDir){
		this.destDir=destDir;
	}
	

	@Override
	public OutputStream getStoreOutputStream(RequestHeader rh) throws StoreException  {
		try {
			tempFile = Files.createTempFile(TEMP_FILE_PREFIX, rh.getUsername());
			logger.debug("saving to temp file "+tempFile.toAbsolutePath());
			return new FileOutputStream(tempFile.toFile());
			
			
		} catch (IOException e) {
			throw new StoreException("Error writing temp file",e.getCause());
		}
		
	}

	@Override
	public void endTransmissionSuccess(RequestHeader rh, long fileZise) throws StoreException {
		
		String destFile = destDir.toFile().getAbsolutePath()+File.separator+rh.getFilename();
		try {
			logger.debug("Movind file to "+destFile);
			Files.move(tempFile,Paths.get(destFile),REPLACE_EXISTING,ATOMIC_MOVE );
		} catch (IOException e) {
			throw new StoreException(rh+"Error saving dest file "+destFile,e.getCause());
		}
		
	}

}
