package dev.psyGamer.immersiveTracks.creativeTabs;

import dev.psyGamer.immersiveTracks.init.Blocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SignalTab extends CreativeTabs {
	
	public SignalTab() {
		super("signals");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Blocks.SIGNAL);
	}
}
