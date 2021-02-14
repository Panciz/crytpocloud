package org.dpoletti.crytpocloud.springclient;

import org.dpoletti.cryptocloud.core.exeption.ProviderGenerationException;
import org.dpoletti.cryptocloud.core.model.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CryptoClientController {

	@Autowired
	ClientWrapper clientWrapper;
	
	@Value("${cryptoserver.host}")
	private String host;

	@Value("${cryptoserver.restport}")
	private int port;
	
	@PutMapping
	public void postCommand(@RequestBody CryptoCommand operation) throws ProviderGenerationException {
		clientWrapper.executeOperation(OperationType.PUT, operation.getUsername(), operation.getFilename());
	}
	@GetMapping
	public void getCommand(@RequestBody CryptoCommand operation) throws ProviderGenerationException {
		clientWrapper.executeOperation(OperationType.GET, operation.getUsername(), operation.getFilename());
	}
	
	@GetMapping(path =  "/{username}")
	public  UserFile[] getList(@PathVariable(value = "username") String username)
	{
	    final String uri = "http://"+host+":"+port+"/"+username;
	    RestTemplate restTemplate = new RestTemplate();
	     
	    ResponseEntity<UserFile[]> response =
	    		  restTemplate.getForEntity(
	    				  uri,
	    				  UserFile[].class);
	    return response.getBody();
	     
	   
	}
}
