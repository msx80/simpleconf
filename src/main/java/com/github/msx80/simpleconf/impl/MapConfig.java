package com.github.msx80.simpleconf.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.msx80.simpleconf.Configuration;
import com.github.msx80.simpleconf.MissingKeyException;

public class MapConfig implements Configuration {

	private Map<String, String> map;
		
	public MapConfig(Map<String, String> map) {
		super();
		this.map = map;
	}

	@Override
	public String getString(String key) throws MissingKeyException {
		String s = map.get(key);
		if(s == null) throw new MissingKeyException("Missing key: "+key);
		return s;
	}

	@Override
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	@Override
	public Set<String> getKeys() {
		
		return new HashSet<>(map.keySet());
	}

	@Override
	public void reload() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("MapConfig doesn't implements reload");
		
	}

}
