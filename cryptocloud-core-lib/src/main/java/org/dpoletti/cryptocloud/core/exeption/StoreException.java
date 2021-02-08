package org.dpoletti.cryptocloud.core.exeption;

public class StoreException extends Exception{

	private static final long serialVersionUID = 1L;

	public StoreException(String message) {
		super(message);
	}
	
	public StoreException(String message,Throwable e) {
		super(message,e);
	}
	
	
}
