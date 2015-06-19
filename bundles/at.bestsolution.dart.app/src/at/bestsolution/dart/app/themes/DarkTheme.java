package at.bestsolution.dart.app.themes;

import org.eclipse.fx.ui.theme.AbstractTheme;

public class DarkTheme extends AbstractTheme {

	public DarkTheme() {
		super("dark", "Dark theme", DefaultTheme.class.getClassLoader().getResource("css/dark.css"));
	}

}
