package org.dpoletti.cryptocloud.client.store;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.dpoletti.cryptocloud.core.model.RequestHeader;

public class FilesystemClientStreamProvider implements ClientStreamProvider {

	private final Path originaFile;

	private final RequestHeader request;
	
	public FilesystemClientStreamProvider(String username,String filePath) {
		super();
		//TODO file validation

		this.originaFile=Paths.get(filePath);
		request=new RequestHeader();
		request.setFilename(originaFile.getFileName().toFile().getName());
		request.setUsername(username);
	}


	@Override
	public InputStream getSendFileStream() throws FileNotFoundException {
		return new FileInputStream(originaFile.toFile());
	}


	@Override
	public RequestHeader getRequestHeader() {
		return request;
	}


	

}
