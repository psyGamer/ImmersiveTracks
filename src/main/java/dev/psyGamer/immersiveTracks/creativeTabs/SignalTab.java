package dev.psyGamer.immersiveTracks.creativeTabs;

import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SignalTab extends CreativeTabs {
	
	public SignalTab() {
		super("immersivetracks.signals");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(BlockRegistry.SIGNAL);
	}
}
