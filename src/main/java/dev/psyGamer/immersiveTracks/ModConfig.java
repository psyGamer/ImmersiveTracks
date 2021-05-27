package dev.psyGamer.immersiveTracks;

import net.minecraftforge.common.config.Config;

@Config(modid = ImmersiveTracks.MODID, name = "immersiveTracks", type = Config.Type.INSTANCE)
public class ModConfig {
	@Config.RangeInt(min = 4, max = 64)
	@Config.Comment({
			"Maximum distance for linking"
	})
	public static int maxLinkingDistance = 16;
}