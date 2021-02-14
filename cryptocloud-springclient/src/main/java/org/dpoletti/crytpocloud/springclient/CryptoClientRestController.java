package org.dpoletti.crytpocloud.springclient;

import org.dpoletti.cryptocloud.core.exeption.ProviderGenerationException;
import org.dpoletti.cryptocloud.core.model.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoClientRestController {
	
	@PostMapping("/file/put")
	public void postCommand(@RequestBody CryptoCommand operation) throws ProviderGenerationException {
		clientWrapper.executeOperation(OperationType.PUT, operation.getUsername(), operation.getFilename());
	}
	@PostMapping("/file/get")
	public void getCommand(@RequestBody CryptoCommand operation) throws ProviderGenerationException {
		clientWrapper.executeOperation(OperationType.GET, operation.getUsername(), operation.getFilename());
	}
	
	@Autowired
	ClientWrapper clientWrapper;
	
}
