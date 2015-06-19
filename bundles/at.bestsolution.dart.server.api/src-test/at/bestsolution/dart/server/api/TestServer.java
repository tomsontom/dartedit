package at.bestsolution.dart.server.api;

import at.bestsolution.dart.server.api.internal.local.LocalDartServer;
import at.bestsolution.dart.server.api.services.ServiceAnalysis;

public class TestServer {
	public static void main(String[] args) throws InterruptedException {
		LocalDartServer s = new LocalDartServer("my-server");
		ServiceAnalysis service = s.getService(ServiceAnalysis.class);
//		service.errors( n -> System.err.println(n.getErrors()));
		Thread.sleep(1000);
		service.setAnalysisRoots(new String[] { "/Users/tomschindl/dart-samples" }, new String[0], null);
	}
}
