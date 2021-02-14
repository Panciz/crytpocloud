package org.dpoletti.cryptocloud.springserver.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERFILE")
public class UserFile {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;
		
	@Column(name = "username")
	private String username;
	
	@Column(name = "filename")
	private String filename;
	
	@Column(name = "lastupdate")
	private Date lastupdate;

}
