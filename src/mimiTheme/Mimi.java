package mimiTheme;

import com.formdev.flatlaf.FlatLightLaf;

@SuppressWarnings("serial")
public class Mimi
	extends FlatLightLaf
{
	public static final String NAME = "Mimi";

	public static boolean setup() {
		return setup( new Mimi() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, Mimi.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
