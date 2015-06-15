package at.bestsolution.dart.server.api;

import at.bestsolution.dart.server.api.internal.local.LocalDartServer;
import at.bestsolution.dart.server.api.services.ServiceAnalysis;

public class TestServer {
	public static void main(String[] args) {
		LocalDartServer s = new LocalDartServer("my-server");
		ServiceAnalysis service = s.getService(ServiceAnalysis.class);
		service.errors( n -> System.err.println(n.getErrors()));
		service.setAnalysisRoots(new String[] { "/Users/tomschindl/dart-samples" }, new String[0], null);
	}
}
