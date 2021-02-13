package org.dpoletti.cryptocloud.springserver.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFileService {

	
	@Autowired
	UserFileRepository repository;
	
	
	List<UserFile> getFilesByUser(String username){
		return repository.findByUserName(username);
	}
	
}
