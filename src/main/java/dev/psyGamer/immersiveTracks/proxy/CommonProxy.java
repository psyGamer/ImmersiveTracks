package dev.psyGamer.immersiveTracks.proxy;

import dev.psyGamer.immersiveTracks.registry.TileEntiyRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy {
	
	
	@Override
	public void registerModel(final Item item, final int metadata) {
	}
	
	@Override
	public void registerModel(final Item item, final int metadata, final String variant) {
	}
	
	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		TileEntiyRegistry.registerTileEntities();
	}
	
	@Override
	public void init(final FMLInitializationEvent event) {
	}
	
	@Override
	public void postInit(final FMLPostInitializationEvent event) {
	}
}
