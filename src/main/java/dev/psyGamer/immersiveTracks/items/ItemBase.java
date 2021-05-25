package dev.psyGamer.immersiveTracks.items;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.init.ModItems;
import dev.psyGamer.immersiveTracks.util.model.IModelRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IModelRegistry {
	
	public ItemBase(final String name, final CreativeTabs tab) {
		this.setUnlocalizedName(ImmersiveTracks.MODID + "." + name);
		this.setRegistryName(name);
		
		this.setCreativeTab(tab);
		
		ModItems.addItem(this);
	}
	
	@Override
	public void registerModel() {
		ImmersiveTracks.getProxy().registerModel(this, 0);
	}
}
