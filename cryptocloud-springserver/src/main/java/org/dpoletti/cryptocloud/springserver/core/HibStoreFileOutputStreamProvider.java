package org.dpoletti.cryptocloud.springserver.core;

import java.nio.file.Path;

import org.dpoletti.cryptocloud.core.exeption.StoreException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;
import org.dpoletti.cryptocloud.server.store.StoreFileOutputStreamProvider;
import org.dpoletti.cryptocloud.springserver.model.UserFileService;

public class HibStoreFileOutputStreamProvider extends StoreFileOutputStreamProvider {

	private UserFileService service;

	
	public HibStoreFileOutputStreamProvider(Path destDir, UserFileService service) {
		super(destDir);
		this.service = service;
	}

	public HibStoreFileOutputStreamProvider(Path destDir) {
		super(destDir);
	}

	@Override
	public void endTransmissionSuccess(RequestHeader rh, long fileZise) throws StoreException {
		super.endTransmissionSuccess(rh, fileZise);
		try{
			service.updateFileRegistry(rh.getUsername(), rh.getFilename());
		}catch (Exception e) {
			throw new StoreException("Error savig file transmission to DB "+e.getLocalizedMessage());
		}
	}	
	
	
}
