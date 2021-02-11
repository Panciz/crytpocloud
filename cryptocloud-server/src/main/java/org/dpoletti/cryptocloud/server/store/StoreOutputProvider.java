package org.dpoletti.cryptocloud.server.store;

import java.io.InputStream;
import java.io.OutputStream;

import org.dpoletti.cryptocloud.core.exeption.StoreException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;
/**
 * Interface to implement for classes that provide the logic to store and to retrieve the file must.
 * 
 * 
 * @param rh
 * @return
 * @throws StoreException
 */
public interface StoreOutputProvider {

	

	/**
	 * Provides the stream to store the file
	 * 
	 * 
	 * @param rh
	 * @return
	 * @throws StoreException
	 */
	OutputStream getStoreOutputStream(RequestHeader rh) throws StoreException;
	
	/**
	 * Allows to add custom operation after that a transmission successfully ends
	 * 
	 * 
	 * @param rh
	 * @return
	 * @throws StoreException
	 */
  void endTransmissionSuccess(RequestHeader rh, long fileZise) throws StoreException;
	/**
	 * Provides the stream to retrieve the file
	 * 
	 * 
	 * @param rh
	 * @return
	 * @throws StoreException
	 */
InputStream getStoreInputStream(RequestHeader rh) throws StoreException;
}
