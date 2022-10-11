package com.github.msx80.simpleconf.impl;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.github.msx80.simpleconf.Configuration;
import com.github.msx80.simpleconf.ConfigurationException;
import com.github.msx80.simpleconf.MissingKeyException;

public class PropertiesConfiguration implements Configuration {

	Properties prop;
	
	private Path file;

	public PropertiesConfiguration(Path file) {
		this.file = file;
		doLoad(file);
	}

	private void doLoad(Path file) {
		try {
			try(FileReader r = new FileReader(file.toFile()))
			{
				prop = new Properties();
				prop.load(r);
			}
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}
	
	@Override
	public String getString(String key) {
		
		return getOrThrow(key);
	}

	private String getOrThrow(String key) {
		String val = prop.getProperty(key);
		if(val == null) throw new MissingKeyException("Missing configuration key: "+key);
		return val;
	}

	@Override
	public boolean containsKey(String key) {
		return prop.containsKey(key);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Set<String> getKeys() {
		return new HashSet(prop.keySet());
	}

	@Override
	public void reload() throws UnsupportedOperationException {
		doLoad(this.file);
		
	}

}
