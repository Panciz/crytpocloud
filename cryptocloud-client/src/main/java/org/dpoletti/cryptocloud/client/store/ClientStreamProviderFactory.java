package org.dpoletti.cryptocloud.client.store;

import org.dpoletti.cryptocloud.core.exeption.ProviderGenerationException;

public interface ClientStreamProviderFactory {

	ClientStreamProvider getClientStoreProvider(String username, String filePath) throws ProviderGenerationException;
	
}
