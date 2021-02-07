package org.dpoletti.cryptocloud.core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.dpoletti.cryptocloud.core.model.RequestHeader;
import org.dpoletti.cryptocloud.core.server.CryptoCloudFileServer;
import org.dpoletti.cryptocloud.core.server.CryptoFileRequestHandler;
import org.junit.jupiter.api.Test;

public class RequestHalderTest {

	
	@Test
	public void testHeaderParser() {
		
		String userNameFileName="username@testfile.txt";
		RequestHeader expRh = new RequestHeader();
		expRh.setUsername("username");
		expRh.setFilename("testfile.txt");
//
//		
		CryptoFileRequestHandler requestHandler = new CryptoFileRequestHandler(null);
		RequestHeader requestHeader = requestHandler.parseHeader(userNameFileName);
		
		
		
		assertEquals(expRh, requestHeader);
		
		
	}	
	
}
