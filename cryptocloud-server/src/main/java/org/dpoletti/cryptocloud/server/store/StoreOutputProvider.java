package org.dpoletti.cryptocloud.server.store;

import java.io.InputStream;
import java.io.OutputStream;

import org.dpoletti.cryptocloud.core.exeption.StoreException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;

public interface StoreOutputProvider {

	
	/**
	 * Retrieve the output stream to store the file
	 * 
	 * 
	 * @param rh
	 * @return
	 * @throws StoreException
	 */
	OutputStream getStoreOutputStream(RequestHeader rh) throws StoreException;
  void endTransmissionSuccess(RequestHeader rh, long fileZise) throws StoreException;
InputStream getStoreInputStream(RequestHeader rh) throws StoreException;
}
