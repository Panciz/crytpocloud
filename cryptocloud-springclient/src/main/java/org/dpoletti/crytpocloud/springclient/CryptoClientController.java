package org.dpoletti.crytpocloud.springclient;

import javax.websocket.server.PathParam;

import org.dpoletti.cryptocloud.core.exeption.ProviderGenerationException;
import org.dpoletti.cryptocloud.core.model.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoClientController {

	@Autowired
	ClientWrapper clientWrapper;
	
	
	@PutMapping
	public void postCommand(@RequestBody CryptoCommand operation) throws ProviderGenerationException {
		clientWrapper.executeOperation(OperationType.PUT, operation.getUsername(), operation.getFilename());
	}
	@GetMapping
	public void getCommand(@RequestBody CryptoCommand operation) throws ProviderGenerationException {
		clientWrapper.executeOperation(OperationType.GET, operation.getUsername(), operation.getFilename());
	}
	
}
