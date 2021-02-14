package org.dpoletti.cryptocloud.springserver.core;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.dpoletti.cryptocloud.server.store.StoreFileOutputProviderFactory;
import org.dpoletti.cryptocloud.server.store.StoreOutputProviderFactory;
import org.dpoletti.cryptocloud.springserver.model.UserFileService;
import org.springframework.stereotype.Component;

public class HibStoreFileOutputProviderFactory extends StoreFileOutputProviderFactory {

	
	private UserFileService service;
	

	public HibStoreFileOutputProviderFactory(UserFileService service, Path destDir) {
		super(destDir);
		this.service = service;
	}

	public UserFileService getService() {
		return service;
	}

	public void setService(UserFileService service) {
		this.service = service;
	}
	
	@Override
	public HibStoreFileOutputStreamProvider getOutputProviderInstance() {
		return new HibStoreFileOutputStreamProvider(destDir, service);	
	}

}
