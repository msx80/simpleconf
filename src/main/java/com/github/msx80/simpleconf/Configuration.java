package com.github.msx80.simpleconf;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.msx80.simpleconf.impl.FuncConfig;
import com.github.msx80.simpleconf.impl.MapConfig;
import com.github.msx80.simpleconf.impl.PropertiesConfiguration;

public interface Configuration 
{

	String getString(String key) throws MissingKeyException;
	
	boolean containsKey(String string);
	
	Set<String> getKeys() throws UnsupportedOperationException;
	
	void reload() throws UnsupportedOperationException;
	
	default String getString(String key, String defaul)
	{
		try {
			return getString(key);
		} catch (MissingKeyException e) {
			return defaul;
		}
	}
	
	default int getInt(String key) throws MissingKeyException
	{
		return Integer.parseInt(getString(key));
	}
	default int getInt(String key, int defaul)
	{
		try {
			return Integer.parseInt(getString(key));
		} catch (MissingKeyException e) {
			return defaul;
		}
	}

	default long getLong(String key) throws MissingKeyException
	{
		return Long.parseLong(getString(key));
	}
	default long getLong(String key, long defaul)
	{
		try {
			return Long.parseLong(getString(key));
		} catch (MissingKeyException e) {
			return defaul;
		}
	}
	

	default boolean getBool(String key) throws MissingKeyException
	{
		return Boolean.parseBoolean(getString(key));
	}
	default boolean getBool(String key, boolean defaul)
	{
		try {
			return Boolean.parseBoolean(getString(key));
		} catch (MissingKeyException e) {
			return defaul;
		}
	}
	
	default <T> T getGeneric(String key, T defaul, Function<String, T> parser)
	{
		if (this.containsKey(key)) {
			return parser.apply(get(key));
		}
		else
		{
			return defaul;
		}
	}

	default <T> T getGeneric(String key, Function<String, T> parser) throws MissingKeyException
	{
		return parser.apply(get(key));
	}

	default Duration getDuration(String key, Duration defaul)
	{
		return getGeneric(key, defaul, Duration::parse);
	}

	default BigDecimal getBigDecimal(String key) throws MissingKeyException
	{
		return getGeneric(key, BigDecimal::new);
	}

	default BigDecimal getBigDecimal(String key, BigDecimal defaul)
	{
		return getGeneric(key, defaul, BigDecimal::new);
	}

	default Class<?> getClass(String key) throws MissingKeyException
	{
		return getGeneric(key, t -> {
			try {
				return Class.forName(t);
			} catch (ClassNotFoundException e) {
				throw new ConfigurationException("Unable to find class", e);
			}
		});
	}

	@SuppressWarnings("unchecked")
	default <T> Class<? extends T> getClass(String key, Class<? extends T> defaul)
	{
		return getGeneric(key, defaul, t -> {
			try {
				return (Class<? extends T>) Class.forName(t);
			} catch (ClassNotFoundException e) {
				throw new ConfigurationException("Unable to find class", e);
			}
		});
	}

	default LocalDate getLocalDate(String key) throws MissingKeyException
	{
		return getGeneric(key, LocalDate::parse);
	}

	default LocalDate getLocalDate(String key, LocalDate defaul)
	{
		return getGeneric(key, defaul, LocalDate::parse);
	}

	default Duration getDuration(String key) throws MissingKeyException
	{
		return getGeneric(key, Duration::parse);
	}
	
	default String get(String key) throws MissingKeyException
	{
		return getString(key);
	}
	
	default String get(String key, String defaul)
	{
		return getString(key, defaul);
	}

	/**
	 * Load a configuration from the default path "config.properties".
	 * You can change the default path with -Dconfiguration.file=my/other/myconfig.properties
	 * @return
	 */
	public static Configuration load()
	{
		String path = System.getProperty("configuration.file", "config.properties");
		return new PropertiesConfiguration(Paths.get(path));
	}
	
	public static Configuration load(String filename)
	{
		return new PropertiesConfiguration(Paths.get(filename));
	}
	
	public static Configuration load(Path file)
	{
		return new PropertiesConfiguration(file);
	}

	public static Configuration ofMap(Map<String, String> map)
	{
		return new MapConfig(map);
	}

	public static Configuration of(String... keysAndValues)
	{
		if( (keysAndValues.length % 2) != 0) throw new ConfigurationException("Keys and values length must be even");
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i+=2) {
			map.put(keysAndValues[i], keysAndValues[i+1]);
		}
		return new MapConfig(map);
	}
	
	public static Configuration computed(Function<String, String> func)
	{
		return new FuncConfig(func);
	}
	
	public default Map<String, String> asMap()
	{
		return Collections.unmodifiableMap( getKeys().stream().collect(Collectors.toMap(Function.identity(), this::get)) );
	}
}
