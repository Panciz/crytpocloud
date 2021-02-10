package org.dpoletti.cryptocloud.core.exeption;

public class ProviderStreamGenerationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ProviderStreamGenerationException(String message) {
		super(message);
	}
	
	public ProviderStreamGenerationException(String message,Throwable e) {
		super(message,e);
	}
}
