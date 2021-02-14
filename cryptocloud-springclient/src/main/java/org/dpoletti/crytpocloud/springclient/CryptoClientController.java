package org.dpoletti.crytpocloud.springclient;

import java.util.Arrays;

import org.dpoletti.cryptocloud.core.exeption.ProviderGenerationException;
import org.dpoletti.cryptocloud.core.model.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class CryptoClientController {

	
	@Value("${cryptoserver.host}")
	private String host;
	@Value("${crypto.keyfile}")
	private String key;
	@Value("${cryptoserver.restport}")
	private int port;

	
	private  UserFile[] getList(String username)
	{
	    final String uri = "http://"+host+":"+port+"/"+username;
	    RestTemplate restTemplate = new RestTemplate();
	     
	    ResponseEntity<UserFile[]> response =
	    		  restTemplate.getForEntity(
	    				  uri,
	    				  UserFile[].class);
	    return response.getBody();
	     
	   
	}
	
	
	@GetMapping("/index")
	public String getIndeg(@RequestParam(name="username", required=true) String username, Model model) throws ProviderGenerationException {
	
		model.addAttribute("key", key);

		model.addAttribute("username", username);
		model.addAttribute("files",Arrays.asList(getList(username)));
		return "index";
		
	}
}
