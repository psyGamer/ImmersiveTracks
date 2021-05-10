package dev.psyGamer.immersiveTracks;

import net.minecraftforge.common.config.Config;

@Config(modid = ImmersiveTracks.MODID, name = ImmersiveTracks.NAME, type = Config.Type.INSTANCE)
public class ModConfig {
	@Config.Comment({
			"Configurations related to signals"
	})
	public static Signals Signals = new Signals();
	
	public static class Signals {
		@Config.RangeInt(min = 1, max = 8)
		@Config.Comment({
				"The maximum depth a controller can be under a signal post to recognize the signal."
		})
		public int maxControllerDepth = 3;
		@Config.RangeInt(min = 1, max = 16)
		@Config.Comment({
				"The maximum height of a signal post for a controller to detect it"
		})
		public int maxSignalHeight = 6;
	}
}