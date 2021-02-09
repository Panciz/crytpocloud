package org.dpoletti.cryptocloud.client.store;

public class FilesystemClientStreamProviderFactory implements ClientStreamProviderFactory {

	
	public FilesystemClientStreamProviderFactory() {
		super();
	}


	
	@Override
	public ClientStreamProvider getClientStoreProvider(String username,String filePath) {
		return new FilesystemClientStreamProvider(username, filePath);
	}

}
