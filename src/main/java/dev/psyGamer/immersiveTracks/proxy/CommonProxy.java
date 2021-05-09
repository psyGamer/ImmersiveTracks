package dev.psyGamer.immersiveTracks.proxy;

import dev.psyGamer.immersiveTracks.init.TileEntities;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void registerModel(Item item, int metadata) {}
	
	public void preInit(FMLPreInitializationEvent event) {
		TileEntities.registerTileEntities();
	}
	public void init(FMLInitializationEvent event) {
	}
	public void postInit(FMLPostInitializationEvent event) {
	}
}
