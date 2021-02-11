package org.dpoletti.cryptocloud.client.store;

import java.io.InputStream;
import java.io.OutputStream;

import org.dpoletti.cryptocloud.core.exeption.ProviderStreamGenerationException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;

/**
 * 
 * Classes that manage the stream of data incoming/outcoming from/to the network
 * to/from the persitance must implements this interface
 * 
 * @author dpol
 *
 */
public interface ClientStreamProvider {

	RequestHeader getRequestHeader();

	/**
	 * Get the InputStream to read the data from the store to be sent in the network
	 * 
	 * @return
	 * @throws ProviderStreamGenerationException
	 */
	InputStream getInputStoreStream() throws ProviderStreamGenerationException;

	
	
	/**
	 * Allows to add some transformation to the outgoing traffic before send it to the network
	 * 
	 * 
	 * @param output
	 * @return
	 * @throws ProviderStreamGenerationException
	 */
	default OutputStream getFilterOutputStoreStream(OutputStream output) throws ProviderStreamGenerationException {
		return output;
	}
	/**
	 * Allows to add some transformation to the incoming traffic before storing it
	 * 
	 * 
	 * @param output
	 * @return
	 * @throws ProviderStreamGenerationException
	 */
	default InputStream getFilterRecieveNetwordStream(InputStream input) throws ProviderStreamGenerationException {
		return input;
	}

	/**
	 * Get the OutputStream to store the data recieved from the network
	 * 
	 * @return
	 * @throws ProviderStreamGenerationException
	 */
	OutputStream getOutputNetworkStream() throws ProviderStreamGenerationException;
}
