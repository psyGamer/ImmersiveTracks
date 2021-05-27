package dev.psyGamer.immersiveTracks.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
	
	void registerModel(final Item item, final int metadata);
	
	void registerModel(final Item item, final int metadata, final String variant);
	
	void preInit(final FMLPreInitializationEvent event);
	
	void init(final FMLInitializationEvent event);
	
	void postInit(final FMLPostInitializationEvent event);
}
