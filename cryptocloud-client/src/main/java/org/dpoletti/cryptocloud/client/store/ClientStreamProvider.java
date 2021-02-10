package org.dpoletti.cryptocloud.client.store;

import java.io.InputStream;
import java.io.OutputStream;

import org.dpoletti.cryptocloud.core.exeption.ProviderStreamGenerationException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;

public interface ClientStreamProvider {
	
	RequestHeader getRequestHeader();
	
	InputStream getSendFileStream() throws  ProviderStreamGenerationException;

	OutputStream getRecieveFileStream() throws ProviderStreamGenerationException;
}
