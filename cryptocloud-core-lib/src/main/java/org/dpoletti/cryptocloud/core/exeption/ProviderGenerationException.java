package org.dpoletti.cryptocloud.core.exeption;

public class ProviderGenerationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProviderGenerationException(String message) {
		super(message);
	}
	
	public ProviderGenerationException(String message,Throwable e) {
		super(message,e);
	}
}
