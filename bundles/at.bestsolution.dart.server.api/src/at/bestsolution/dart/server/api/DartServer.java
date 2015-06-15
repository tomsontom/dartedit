package at.bestsolution.dart.server.api;

public interface DartServer {
	public String getId();
	public <S extends DartService> S getService(Class<S> serviceType);
}
