package at.bestsolution.dart.editor.doc;

import org.eclipse.fx.code.compensator.editor.services.InputFactory;
import org.osgi.service.component.annotations.Component;

@Component(property={"service.ranking:Integer=1"})
public class DartInputFactory implements InputFactory<DartInput> {

	@Override
	public boolean applies(String url) {
		return url.endsWith(".dart");
	}

	@Override
	public Class<DartInput> createInput(String url) {
		return DartInput.class;
	}
	
}
