package dev.psyGamer.immersiveTracks.proxy;

import dev.psyGamer.immersiveTracks.init.TileEntities;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	
	public void registerModel(final Item item, final int metadata) {
	}
	
	public void registerModel(final Item item, final int metadata, final String variant) {
	}
	
	public void preInit(final FMLPreInitializationEvent event) {
		TileEntities.registerTileEntities();
	}
	
	public void init(final FMLInitializationEvent event) {
	}
	
	public void postInit(final FMLPostInitializationEvent event) {
	}
}
