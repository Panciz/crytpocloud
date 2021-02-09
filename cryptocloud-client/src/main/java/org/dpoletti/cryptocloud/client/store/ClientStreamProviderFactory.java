package org.dpoletti.cryptocloud.client.store;

public interface ClientStreamProviderFactory {

	ClientStreamProvider getClientStoreProvider(String username, String filePath);
	
}
