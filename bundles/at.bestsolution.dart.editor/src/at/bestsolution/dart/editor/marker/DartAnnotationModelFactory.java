package at.bestsolution.dart.editor.marker;

import org.eclipse.fx.code.compensator.editor.Input;
import org.eclipse.fx.code.compensator.editor.services.AnnotationModelFactory;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.osgi.service.component.annotations.Component;

import at.bestsolution.dart.editor.doc.DartInput;

@Component
public class DartAnnotationModelFactory implements AnnotationModelFactory {

	@Override
	public boolean applies(Input<?> input) {
		return input instanceof DartInput;
	}

	@Override
	public Class<? extends IAnnotationModel> getAnnotationModelType() {
		return DartAnnotationModel.class;
	}

}
