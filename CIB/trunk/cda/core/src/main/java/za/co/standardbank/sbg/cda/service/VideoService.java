package za.co.standardbank.sbg.cda.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.apache.log4j.Logger;

import za.co.standardbank.sbg.cda.exception.CdaRuntimeException;

import com.vignette.as.config.ConfigUtil;
import com.vignette.config.client.common.ConfigException;

/**
 * Provides information related to Vidavee video Service configuration. Reads
 * information from configconsole
 * 
 */
public class VideoService {
	private static final Logger logger = Logger.getLogger(VideoService.class);

	private static String videoServerHost = null;
	private static String videoServerPort = null;
	private static String videoServerPath = null;
	private static Properties vsConfigProperties = new Properties();

	static {
		try {
			String videoServiceConfiguration = ConfigUtil.getGenericResourceValue("MediaResourceType", "MediaResource");
			if (videoServiceConfiguration != null) {
				StringReader reader = new StringReader(videoServiceConfiguration);
				try {
					vsConfigProperties.load(reader);

					videoServerHost = vsConfigProperties.getProperty("advVideoServices.apiServerName");
					videoServerPort = vsConfigProperties.getProperty("advVideoServices.servicePort");
					videoServerPath = vsConfigProperties.getProperty("advVideoServices.apiServerPath");
					if (logger.isDebugEnabled()) {
						logger.debug("Video Server Host::" + videoServerHost);
						logger.debug("Video Server Port::" + videoServerPort);
						logger.debug("Video Server Path::" + videoServerPath);
					}
				} catch (IOException e) {
					logger.error("IOException in loading video Service configurations.::" + e);
					throw new CdaRuntimeException("IOException in loading video Service configurations.::" + e);
				}
			} else {
				logger.error("Information related to Video Service (vidavee) configuration is missing. Please configure the values in config console.");
			}
		} catch (ConfigException e) {
			logger.error("ConfigException in reading Video Service (vidavee) configuration.::" + e);
			throw new CdaRuntimeException("ConfigException in reading Video Service (vidavee) configuration.::" + e);
		}
	}

	public static String getVideoServerHost() {
		return videoServerHost;
	}

	public static String getVideoServerPort() {
		return videoServerPort;
	}

	public static String getVideoServerPath() {
		return videoServerPath;
	}

}
