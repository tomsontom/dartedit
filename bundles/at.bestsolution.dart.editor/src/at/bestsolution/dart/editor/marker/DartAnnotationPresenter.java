package at.bestsolution.dart.editor.marker;

import java.util.Arrays;
import java.util.List;

import org.eclipse.fx.core.URI;
import org.eclipse.fx.ui.services.resources.GraphicsLoader;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationPresenter;
import org.osgi.service.component.annotations.Component;

import at.bestsolution.dart.server.api.model.AnalysisErrorType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

@Component
public class DartAnnotationPresenter implements AnnotationPresenter {

	@Override
	public List<String> getTypes() {
		return Arrays.asList("dart.annotation.WARNING","dart.annotation.ERROR","dart.annotation.INFO");
	}

	@Override
	public Node getPresentation(Annotation annotation, GraphicsLoader loader) {
		Node n = null;
		if( annotation instanceof DartAnnotation ) {
			DartAnnotation ja = (DartAnnotation) annotation;
			
			if( ja.getError().getType() == AnalysisErrorType.TODO ) {
				n = loader.getGraphicsNode(URI.createPlatformPluginURI("at.bestsolution.dart.editor", "css/icons/16/showtsk_tsk.png"));
			} else {
				switch (ja.getError().getSeverity()) {
				case ERROR:
					n = loader.getGraphicsNode(URI.createPlatformPluginURI("at.bestsolution.dart.editor", "css/icons/16/message_error.png"));
					break;
				case INFO:
					n = loader.getGraphicsNode(URI.createPlatformPluginURI("at.bestsolution.dart.editor", "css/icons/16/message_info.png"));
					break;
				case WARNING:
					n = loader.getGraphicsNode(URI.createPlatformPluginURI("at.bestsolution.dart.editor", "css/icons/16/message_warning.png"));
					break;
				default:
					break;
				}
			} 
			
//			else if( ja.getMarker().getType() == Type.TASK ) {
//				n = loader.getGraphicsNode(URI.createPlatformPluginURI("org.eclipse.fx.code.compensator.project", "css/icons/16/showtsk_tsk.png"));
//			}

			//TODO me

			if( n != null ) {
				Label l = new Label(null, n);
				l.setTooltip(new Tooltip(ja.getError().getMessage()));
				n = l;
			}

		}
		return n;
	}

}
