package dev.psyGamer.immersiveTracks.items;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.registry.ItemRegistry;
import dev.psyGamer.immersiveTracks.util.model.IModelRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IModelRegistry {
	
	public ItemBase(final String name, final CreativeTabs tab) {
		this.setUnlocalizedName(ImmersiveTracks.MODID + "." + name);
		this.setRegistryName(name);
		
		this.setCreativeTab(tab);
		
		ItemRegistry.registerItem(this);
	}
	
	@Override
	public void registerModel() {
		ImmersiveTracks.getProxy().registerModel(this, 0);
	}
}
