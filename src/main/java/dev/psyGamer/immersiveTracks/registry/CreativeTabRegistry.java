package dev.psyGamer.immersiveTracks.registry;

import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.ItemStack;

public class CreativeTabRegistry {
	
	public static final CreativeTab SIGNALS_TAB = new CreativeTab(
			"immersivetracks.signals", () -> new ItemStack(ItemRegistry.SIGNAL_ITEM, 1)
	);
}
