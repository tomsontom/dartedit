package at.bestsolution.dart.app.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.fx.ui.services.theme.ThemeManager;

public class SwitchTheme {
	@Execute
	void switchTheme(ThemeManager m) {
		if( m.getCurrentTheme().getId().equals("theme.default") ) {
			m.setCurrentThemeId("dark");
		} else {
			m.setCurrentThemeId("theme.default");
		}
	}
}
