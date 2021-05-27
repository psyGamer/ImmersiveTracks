package dev.psyGamer.immersiveTracks.proxy;

import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.registry.TileEntiyRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {
	
	@Override
	public void registerModel(final Item item, final int metadata) {
		this.registerModel(item, metadata, "");
	}
	
	@Override
	public void registerModel(final Item item, final int metadata, final String variant) {
		if (variant.equalsIgnoreCase("")) {
			ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		} else {
			ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName() + "_" + variant, "inventory"));
		}
	}
	
	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		TileEntiyRegistry.registerTESRs();
	}
	
	@Override
	public void init(final FMLInitializationEvent event) {
		BlockRegistry.registerBlockColors();
	}
	
	@Override
	public void postInit(final FMLPostInitializationEvent event) {
	}
}
