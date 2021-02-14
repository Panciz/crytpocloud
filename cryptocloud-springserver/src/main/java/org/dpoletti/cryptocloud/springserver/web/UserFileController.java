package org.dpoletti.cryptocloud.springserver.web;

import java.util.List;

import org.dpoletti.cryptocloud.springserver.model.UserFile;
import org.dpoletti.cryptocloud.springserver.model.UserFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFileController {

	
	@Autowired
	UserFileRepository repository;
	
	@GetMapping("/{username}")
	public List<UserFile> getFilesByUser(@PathVariable(name = "username") String username){
		return repository.findByUsername(username);
	}
}
