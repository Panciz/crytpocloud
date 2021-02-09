package org.dpoletti.cryptocloud.client.store;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dpoletti.cryptocloud.core.model.RequestHeader;

public interface ClientStreamProvider {
	
	RequestHeader getRequestHeader();
	
	InputStream getSendFileStream() throws FileNotFoundException;

	OutputStream getRecieveFileStream() throws FileNotFoundException;
}
