package org.dpoletti.cryptocloud.springserver.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserFileRepository extends CrudRepository<UserFile, Long> {

	
	public List<UserFile>  findByUserName(String username);
}
