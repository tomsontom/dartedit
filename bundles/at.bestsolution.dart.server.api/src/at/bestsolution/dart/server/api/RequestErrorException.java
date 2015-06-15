package at.bestsolution.dart.server.api;

import at.bestsolution.dart.server.api.model.RequestError;

public class RequestErrorException extends RuntimeException {
	private final RequestError error;

	public RequestErrorException(RequestError error) {
		this.error = error;
	}
	
	public RequestError getError() {
		return error;
	}
}
