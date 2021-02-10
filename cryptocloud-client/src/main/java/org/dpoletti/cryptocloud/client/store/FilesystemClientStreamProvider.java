package org.dpoletti.cryptocloud.client.store;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.dpoletti.cryptocloud.core.exeption.ProviderStreamGenerationException;
import org.dpoletti.cryptocloud.core.model.RequestHeader;

public class FilesystemClientStreamProvider implements ClientStreamProvider {

	private final Path path;

	private final RequestHeader request;
	
	public FilesystemClientStreamProvider(String username,String filePath) {
		super();
		//TODO file validation

		this.path=Paths.get(filePath);
		request=new RequestHeader();
		request.setFilename(path.getFileName().toFile().getName());
		request.setUsername(username);
	}


	@Override
	public InputStream getSendFileStream() throws ProviderStreamGenerationException {
		try {
			return new FileInputStream(path.toFile());
		} catch (FileNotFoundException e) {
			throw new ProviderStreamGenerationException("Error generating input Stream "+e.getMessage(), e);
		}
	}
	@Override
	public OutputStream getRecieveFileStream() throws ProviderStreamGenerationException {
		try {
			return new FileOutputStream(path.toFile());
		} catch (FileNotFoundException e) {
			throw new ProviderStreamGenerationException("Error generating output Stream "+e.getMessage(), e);
		}
	}

	@Override
	public RequestHeader getRequestHeader() {
		return request;
	}


	

}
