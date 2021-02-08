package org.dpoletti.cryptocloud.core.server.store;

import java.io.OutputStream;

import org.dpoletti.cryptocloud.core.exeption.StoreException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;

public interface StoreOutputProvider {

	
	OutputStream getStoreOutputStream(RequestHeader rh) throws StoreException;
}
