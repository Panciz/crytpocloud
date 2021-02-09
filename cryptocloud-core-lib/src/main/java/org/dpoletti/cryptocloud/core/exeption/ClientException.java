package org.dpoletti.cryptocloud.core.exeption;

public class ClientException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClientException(String message) {
		super(message);
	}
	
	public ClientException(String message,Throwable e) {
		super(message,e);
	}
}
