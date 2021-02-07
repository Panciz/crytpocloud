package org.dpoletti.cryptocloud.core.exeption;

public class ProtocolException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProtocolException(String message) {
		super(message);
	}
	
	public ProtocolException(String message,Throwable e) {
		super(message,e);
	}
	
}
