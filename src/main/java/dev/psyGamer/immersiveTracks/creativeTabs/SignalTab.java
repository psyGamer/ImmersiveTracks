package dev.psyGamer.immersiveTracks.creativeTabs;

import dev.psyGamer.immersiveTracks.init.Blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

public class SignalTab extends CreativeTabs {
	
	public SignalTab() {
		super("immersivetracks.signals");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Blocks.SIGNAL);
	}
}
