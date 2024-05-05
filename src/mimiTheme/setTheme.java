package mimiTheme;

import javax.swing.UIManager;

public class setTheme {
	public setTheme() {
		Mimi.setup();
		try {
			UIManager.setLookAndFeel(new Mimi());
		} catch (Exception e) {
			System.err.println("Failed to initialize LaF");
		}
	}
}
