package org.dpoletti.cryptocloud.springserver.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.ServerException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.dpoletti.cryptocloud.server.CryptoCloudFileServer;
import org.dpoletti.cryptocloud.server.store.StoreFileOutputProviderFactory;
import org.dpoletti.cryptocloud.springserver.model.UserFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CryptoCloudWrapper {

	@Autowired
	UserFileService service;

	@Value("${crypto.destdir}")
	private String destDirStr;

	CryptoCloudFileServer cs;

	ExecutorService pool = Executors.newFixedThreadPool(1);

	@Value("${crypto.port}")
	private int port;
	private static final Logger logger = LoggerFactory.getLogger(CryptoCloudWrapper.class);

	@PostConstruct
	public void initServer() throws IOException {
		Path destDir = Paths.get(destDirStr);
		if (!destDir.toFile().exists()) {
			Files.createDirectories(destDir);
		}
		logger.info("FileServer Listen to port " + port + " destDir " + destDir);

		cs = new CryptoCloudFileServer(port);
		cs.setOutproviderFactory(new HibStoreFileOutputProviderFactory(service, destDir));

		pool.submit(() -> {

			try {
				cs.startServer();
			} catch (ServerException e) {
				logger.error("Crypto Cloud server exceptio " + e.getMessage());

			}

		});
	}

	@PreDestroy
	public void stopServer() throws IOException, InterruptedException {
		cs.stopServer();
		pool.shutdownNow();
		logger.info("Crypt cloud server stopped");
	}

}
