package at.bestsolution.dart.editor.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;

import at.bestsolution.dart.server.api.DartServer;
import at.bestsolution.dart.server.api.model.AnalysisService;
import at.bestsolution.dart.server.api.services.ServiceAnalysis;


public class DartRemoteFileManager {
	public static final String DART_INPUT_CREATED = "at/bestsolution/dart/editor/input/created";
	public static final String DART_INPUT_DISPOSED = "at/bestsolution/dart/editor/input/disposed";
	
	private List<String> activeInputList = new ArrayList<>();
	private DartServer server;
	
	@Inject
	public DartRemoteFileManager(IEventBroker eventBroker, DartServer server) {
		this.server = server;
		eventBroker.subscribe(DART_INPUT_CREATED, this::handleInputOpened);
		eventBroker.subscribe(DART_INPUT_DISPOSED, this::handleInputOpened);
	}
	
	void handleInputOpened(Event e) {
		synchronized (activeInputList) {
			activeInputList.add((String)e.getProperty(IEventBroker.DATA));
		}
		refreshOutlineSubscriptions();
	}
	
	void handleInputDisposed(Event e) {
		synchronized (activeInputList) {
			activeInputList.remove((String)e.getProperty(IEventBroker.DATA));	
		}
		refreshOutlineSubscriptions();
	}
	
	void refreshOutlineSubscriptions() {
		String[] files;
		synchronized (activeInputList) {
			files = activeInputList.toArray(new String[activeInputList.size()]);
		}
		server.getService(ServiceAnalysis.class).setSubscriptions(Collections.singletonMap(AnalysisService.OUTLINE, files));
	}
}
