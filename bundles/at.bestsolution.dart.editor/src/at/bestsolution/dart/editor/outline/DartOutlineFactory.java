package at.bestsolution.dart.editor.outline;

import javax.inject.Inject;

import org.eclipse.fx.code.compensator.editor.Input;
import org.eclipse.fx.code.compensator.editor.Outline;
import org.eclipse.fx.code.compensator.editor.services.OutlineFactory;
import org.eclipse.fx.ui.services.resources.GraphicsLoader;

import at.bestsolution.dart.editor.doc.DartInput;
import at.bestsolution.dart.server.api.DartServer;
import at.bestsolution.dart.server.api.services.ServiceAnalysis;
import javafx.collections.ObservableList;

public class DartOutlineFactory implements OutlineFactory {

	@Override
	public boolean applies(Input<?> input) {
		return input instanceof DartInput;
	}

	@Override
	public Class<? extends Outline> createOutline(Input<?> input) {
		// TODO Auto-generated method stub
		return null;
	}

	private static class DartOutline implements Outline {

		private  OutlineItem i;

		@Inject
		public DartOutline(Input<?> input, GraphicsLoader graphicsLoader, DartServer server) {
			this.i = builtOutline(server,input,graphicsLoader);
		}
		private OutlineItem builtOutline(DartServer server, Input<?> input, GraphicsLoader graphicsLoader) {
			
			server.getService(ServiceAnalysis.class).outline( e -> {});
			return null;
		}


		@Override
		public ObservableList<OutlineItem> getRootItems() {
			return i.getChildren();
		}
	}
}
