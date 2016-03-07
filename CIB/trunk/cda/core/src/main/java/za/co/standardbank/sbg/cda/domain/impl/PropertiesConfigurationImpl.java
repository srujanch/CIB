package za.co.standardbank.sbg.cda.domain.impl;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import za.co.standardbank.sbg.cda.domain.Configuration;
import za.co.standardbank.sbg.cda.exception.ConfigurationException;
import za.co.standardbank.sbg.cda.service.Service;

public class PropertiesConfigurationImpl implements Configuration, Service {
	private Properties properties;

	public boolean containsKey(final String key) {
		return this.properties.containsKey(key);
	}

	public List getKeys() {
		return (List) this.properties.keys();
	}

	public Object getProperty(final String key) {
		return this.properties.get(key);
	}

	public String getString(final String key) {
		return (String) this.properties.getProperty(key);
	}

	public PropertiesConfigurationImpl(final String fileName) {
		try {
			this.properties = new Properties();
			FileInputStream in = new FileInputStream(fileName);
			this.properties.load(in);
			in.close();
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}

	}

}
