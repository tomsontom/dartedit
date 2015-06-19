package at.bestsolution.dart.editor.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fx.code.compensator.editor.Input;
import org.eclipse.fx.code.compensator.hsl.HSLStandaloneSetup;
import org.eclipse.fx.code.compensator.hsl.hSL.Model;
import org.eclipse.fx.code.editor.hsl.HSLModelProvider;
import org.osgi.service.component.annotations.Component;

import at.bestsolution.dart.editor.doc.DartInput;

@Component
public class DartHSLModelProvider implements HSLModelProvider {
	private Model model;
	
	@Override
	public boolean applies(Input<?> input) {
		return input instanceof DartInput;
	}

	@Override
	public Model getModel(Input<?> input) {
		if( model == null ) {
			HSLStandaloneSetup.doSetup();
			ResourceSetImpl rs = new ResourceSetImpl();
			Resource resource = rs.getResource(URI.createURI("platform:/plugin/at.bestsolution.dart.editor/dart.hsl"), true);
			model = (Model) resource.getContents().get(0);
		}
		return model;
	}

}
