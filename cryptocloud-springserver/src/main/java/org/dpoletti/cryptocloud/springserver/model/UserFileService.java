package org.dpoletti.cryptocloud.springserver.model;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFileService {

	private static final Logger logger 
	  = LoggerFactory.getLogger(UserFileService.class);
	@Autowired
	UserFileRepository repository;
	

	List<UserFile> getFilesByUser(String username){
		return repository.findByUsername(username);
	}
	
	UserFile getFileByUserAndName(String username,String fileName){
		return repository.findFirstByUsernameAndFilename(username, fileName);
	}
	
	@Transactional
	public UserFile updateFileRegistry(String username,String fileName) {
		UserFile userFile = getFileByUserAndName(username, fileName);
		if(userFile!=null) {
			logger.debug("File "+username+"/"+fileName+" exists ");
			userFile.setLastupdate(new Date());
			return userFile;
		}else {
			logger.debug("File "+username+"/"+fileName+" created ");

			UserFile  newUserFile = new UserFile();
			newUserFile.setFilename(fileName);
			newUserFile.setUsername(username);
			newUserFile.setLastupdate(new Date());
		
			 return repository.save(newUserFile);
		}
		
	}
}
