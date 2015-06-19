package at.bestsolution.dart.app;

import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;
import org.osgi.service.component.annotations.Component;

@Component
public class DirtWindowCloseHandler implements IWindowCloseHandler {

	@Override
	public boolean close(MWindow window) {
		return window.getContext().get(EPartService.class).saveAll(true);
	}

}
