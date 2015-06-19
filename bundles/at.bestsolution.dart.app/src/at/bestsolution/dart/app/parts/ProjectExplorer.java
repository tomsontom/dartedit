package at.bestsolution.dart.app.parts;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.fx.ui.controls.filesystem.FileItem;
import org.eclipse.fx.ui.controls.filesystem.ResourceEvent;
import org.eclipse.fx.ui.controls.filesystem.ResourceItem;
import org.eclipse.fx.ui.controls.filesystem.ResourceTreeView;

import at.bestsolution.dart.server.api.DartServer;
import at.bestsolution.dart.server.api.services.ServiceAnalysis;
import javafx.collections.FXCollections;
import javafx.scene.layout.BorderPane;

public class ProjectExplorer {
	@Inject
	EModelService modelService;
	
	@Inject
	EPartService partService;
	
	MPartStack partStack;
	
	@Inject
	public void init(BorderPane p, DartServer server, MApplication application) {
		partStack = (MPartStack) modelService.find("at.bestsolution.dart.app.editorstack", application);
		server.getService(ServiceAnalysis.class).setAnalysisRoots(new String[] {"/Users/tomschindl/dart-samples/"}, new String[0], null);
		ResourceTreeView viewer = new ResourceTreeView();
		viewer.setRootDirectories(FXCollections.observableArrayList(ResourceItem.createObservedPath(Paths.get("/Users/tomschindl/dart-samples/"))));
		viewer.addEventHandler(ResourceEvent.openResourceEvent(), this::handleEvent);
		p.setCenter(viewer);
	}

	private void handleEvent(ResourceEvent<ResourceItem> e) {
		e.getResourceItems()
			.stream()
			.filter( r -> r instanceof FileItem)
			.map( r -> (FileItem)r)
			.forEach(this::handle);
	}
	
	private void handle(FileItem item) {
		if( ! partStack.getChildren().stream().map( p -> p.getPersistedState().get("documentUrl")).filter( c -> item.getUri().endsWith(c)).findFirst().isPresent() ) {
			MPart part = modelService.createModelElement(MPart.class);
			part.setCloseable(true);
			part.setLabel(((Path)item.getNativeResourceObject()).getFileName().toString());
			part.setContributionURI("bundleclass://org.eclipse.fx.code.compensator.editor/org.eclipse.fx.code.compensator.editor.TextEditor");
			part.setIconURI("platform:/plugin/at.bestsolution.dart.app/css/icons/16/dart.png");
			part.getPersistedState().put("documentUrl", item.getUri());
			part.getTags().add("removeOnHide");
			partStack.getChildren().add(part);
			partService.activate(part);
		}
	}
}
