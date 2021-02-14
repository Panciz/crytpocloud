package org.dpoletti.crytpocloud.springclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.crypto.NoSuchPaddingException;

import org.dpoletti.cryptocloud.client.CryptoCloudFileClient;
import org.dpoletti.cryptocloud.client.store.CrytoFsClientStreamProviderFactory;
import org.dpoletti.cryptocloud.core.exeption.ProviderGenerationException;
import org.dpoletti.cryptocloud.core.model.OperationType;
import org.dpoletti.cryptocloud.libenc.FileCryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientWrapper {
	private static final Logger logger = LoggerFactory.getLogger(CryptoCloudFileClient.class);

	@Value("${cryptoserver.host}")
	private String host;

	@Value("${cryptoserver.port}")
	private int port;

	@Value("${crypto.keyfile}")
	private String keyfile;

	private CrytoFsClientStreamProviderFactory factory;

	private ExecutorService pool = Executors.newFixedThreadPool(10);

	@PostConstruct
	public void init() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException {

		File keyFile = Paths.get(keyfile).toFile();
		logger.info("Using encription key " + keyFile.getAbsolutePath());
		if (!keyFile.exists()) {
			logger.warn("The key doesn't exists will be created " + keyFile.getAbsolutePath());
			logger.warn("Keep the file in order to decrypt the file " + keyFile.getAbsolutePath());

		}
		factory = new CrytoFsClientStreamProviderFactory(keyFile);

	}

	@PreDestroy
	public void destroy() {
		pool.shutdownNow();
	}

	public void executeOperation(OperationType op, String userName, String fileName)
			throws ProviderGenerationException {
		final CryptoCloudFileClient fc = new CryptoCloudFileClient(host, Integer.valueOf(port),
				factory.getClientStoreProvider(userName, fileName));
		switch (op) {
		case PUT:
			pool.submit(() -> {
				try {
					fc.sendFile();
				} catch (IOException e) {
					logger.error("Error sending file " + e.getMessage());
				}
			});
			break;
		case GET:
			pool.submit(() -> {
				fc.recieveFile();
			});
			break;
		default:
			logger.error("Unknonwn Operation " + op);
			break;
		}

	}

}
