package org.dpoletti.cryptocloud.core.model;

import java.util.Optional;

public class RequestHeader {
	private static final String HEADER_SEP_CHAR= "@";

	@Override
	public String toString() {
		return "RequestHeader [username=" + username + ", filename=" + filename + "]";
	}

	private String username;
	private String  filename;


	public void setUsername(String username) {
		this.username = username;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUsername() {
		return username;
	}

	public String getFilename() {
		return filename;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestHeader other = (RequestHeader) obj;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	public static String serializeHeader(RequestHeader header) {
		return String.format("%s%s%s",
				Optional.of(header.username).orElse(""),
				HEADER_SEP_CHAR,
				Optional.of(header.filename).orElse("")
				);
		
	}
	
	public static RequestHeader parseHeader(String userNameFileName) {
		String[] vals = userNameFileName.split(HEADER_SEP_CHAR);
		RequestHeader rh = new RequestHeader();
		if(vals.length>0) {
			rh.setUsername(vals[0]);
		}
		
		if(vals.length>1) {
			rh.setFilename(vals[1]);
		}
		return rh;
	}
}
