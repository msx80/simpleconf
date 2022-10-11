package com.github.msx80.simpleconf;

public class MissingKeyException extends ConfigurationException {

	private static final long serialVersionUID = 8489929447585579126L;

	public MissingKeyException() {
	}

	public MissingKeyException(String message) {
		super(message);
	}

	public MissingKeyException(Throwable cause) {
		super(cause);
	}

	public MissingKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingKeyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
