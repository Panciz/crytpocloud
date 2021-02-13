package org.dpoletti.cryptocloud.springserver.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERFILE")
public class UserFile {

	@Id
	@Column(name = "id")
	private Long id;
	

	
	@Column(name = "username")
	private String username;
	

	
	@Column(name = "filename")
	private String filename;
	

	@Column(name = "lastupdate")
	private Date lastupdate;
	
	
	
}
