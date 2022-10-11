package com.github.msx80.simpleconf.impl;

import java.util.Set;
import java.util.function.Function;

import com.github.msx80.simpleconf.Configuration;
import com.github.msx80.simpleconf.MissingKeyException;

public class FuncConfig implements Configuration {

	private Function<String, String> func;

	public FuncConfig(Function<String, String> f) {
		this.func = f;
	}

	@Override
	public String getString(String key) throws MissingKeyException {
		String res = func.apply(key);
		if(res==null) throw new MissingKeyException("Missing key: "+key);
		return res;
	}

	@Override
	public boolean containsKey(String key) 
	{
		return func.apply(key) != null;
	}

	@Override
	public Set<String> getKeys() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("FuncConfig doesn't implements getKeys");
	}

	@Override
	public void reload() throws UnsupportedOperationException {
		// nothing to do as values are generated on the fly
		
	}

}
