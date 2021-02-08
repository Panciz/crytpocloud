package org.dpoletti.cryptocloud.core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.dpoletti.cryptocloud.core.model.RequestHeader;
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
		RequestHeader requestHeader = RequestHeader.parseHeader(userNameFileName);
		
		
		
		assertEquals(expRh, requestHeader);
		assertEquals(userNameFileName,RequestHeader.serializeHeader(requestHeader) );
		assertEquals(userNameFileName,RequestHeader.serializeHeader(expRh) );


		
	}	
	
}
