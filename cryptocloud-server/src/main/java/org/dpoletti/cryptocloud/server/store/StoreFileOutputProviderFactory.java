package org.dpoletti.cryptocloud.server.store;

import java.nio.file.Path;

public class StoreFileOutputProviderFactory implements StoreOutputProviderFactory {

	private final  Path destDir;
	
	public StoreFileOutputProviderFactory(Path destDir) {
		
		this.destDir=destDir;
	}

	@Override
	public StoreOutputProvider getOutputProviderInstance() {
		return new StoreFileOutputStreamProvider(destDir);
	}

}
