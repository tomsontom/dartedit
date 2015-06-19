package at.bestsolution.dart.app;

import org.eclipse.fx.ui.workbench.renderers.fx.services.FlyInTransitionService;
import org.eclipse.fx.ui.workbench.renderers.fx.services.LightweightDialogTransitionService;
import org.osgi.service.component.annotations.Component;

@Component(service=LightweightDialogTransitionService.class)
public class DialogAnimationService extends FlyInTransitionService {
	public DialogAnimationService() {
		super(true);
	}
}
