package at.bestsolution.dart.server.api.internal.local;

import java.util.HashMap;
import java.util.Map;

import at.bestsolution.dart.server.api.DartServer;
import at.bestsolution.dart.server.api.DartServerFactory;

public class LocalDartServerFactory implements DartServerFactory {
	private Map<String, DartServer> serverMap = new HashMap<>();

	@Override
	public DartServer getServer(String id) {
		DartServer s = serverMap.get(id);
		if (s == null) {
			s = new LocalDartServer(id);
			serverMap.put(id, s);
		}
		return s;
	}

}
