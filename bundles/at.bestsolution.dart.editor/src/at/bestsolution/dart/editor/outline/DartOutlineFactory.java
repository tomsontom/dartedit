package at.bestsolution.dart.editor.outline;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.fx.code.compensator.editor.Input;
import org.eclipse.fx.code.compensator.editor.Outline;
import org.eclipse.fx.code.compensator.editor.Outline.OutlineItem;
import org.eclipse.fx.code.compensator.editor.services.OutlineFactory;
import org.eclipse.fx.ui.services.resources.GraphicsLoader;
import org.eclipse.fx.ui.services.sync.UISynchronize;
import org.osgi.service.component.annotations.Component;

import at.bestsolution.dart.editor.doc.DartInput;
import at.bestsolution.dart.server.api.DartServer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

@Component
public class DartOutlineFactory implements OutlineFactory {

	@Override
	public boolean applies(Input<?> input) {
		return input instanceof DartInput;
	}

	@Override
	public Class<? extends Outline> createOutline(Input<?> input) {
		return DartOutline.class;
	}

	private static class DartOutline implements Outline {

		private UISynchronize sync;
		private ObservableList<OutlineItem> items = FXCollections.observableArrayList();
		private DartInput input;

		@Inject
		public DartOutline(UISynchronize sync, Input<?> input, GraphicsLoader graphicsLoader, DartServer server) {
			this.sync = sync;
			this.input = (DartInput) input;
			this.input.outlineProperty().addListener( this::handleOutlineChange );
			handleOutlineChange(this.input.outlineProperty());
		}
		
		private void handleOutlineChange(Observable obs) {
			if( this.input.outlineProperty().get() != null ) {
				List<DartOutlineItem> list = Stream.of(this.input.outlineProperty().get().getChildren()).map(o -> new DartOutlineItem(null, o)).collect(Collectors.toList());
				sync.asyncExec(() -> items.setAll(list));	
			} else {
				sync.asyncExec(() -> items.clear());
			}
		}
		
		@Override
		public ObservableList<OutlineItem> getRootItems() {
			return items;
		}
	}
	
	private static class DartOutlineItem implements OutlineItem {
		private final at.bestsolution.dart.server.api.model.Outline outline;
		private final OutlineItem parent;
		
		public DartOutlineItem(OutlineItem parent, at.bestsolution.dart.server.api.model.Outline outline) {
			this.outline = outline;
			this.parent = parent;
		}
		
		@Override
		public CharSequence getLabel() {
			return outline.getElement().getName();
		}

		@Override
		public Node getGraphic() {
			return null;
		}

		@Override
		public OutlineItem getParent() {
			return parent;
		}

		@Override
		public ObservableList<OutlineItem> getChildren() {
			if( outline.getChildren() != null ) {
				return FXCollections.observableArrayList(Stream.of(outline.getChildren()).map( o -> new DartOutlineItem(this,o)).collect(Collectors.toList()));	
			} else {
				return FXCollections.emptyObservableList();
			}
		}
		
	}
}
