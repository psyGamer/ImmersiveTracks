package dev.psyGamer.immersiveTracks.registry;

import dev.psyGamer.immersiveTracks.item.BlockItem;
import dev.psyGamer.immersiveTracks.item.LinkerItem;

public class ItemRegistry {
	public static final LinkerItem LINKER = new LinkerItem("linker");
	
	public static final BlockItem SIGNAL_ITEM = new BlockItem(BlockRegistry.SIGNAL);
	public static final BlockItem SIGNAL_POLE_ITEM = new BlockItem(BlockRegistry.SIGNAL_POLE);
	public static final BlockItem SIGNAL_CONTROLLER_ITEM = new BlockItem(BlockRegistry.SIGNAL_CONTROLLER);
	
	public static void load() {
	}
}
