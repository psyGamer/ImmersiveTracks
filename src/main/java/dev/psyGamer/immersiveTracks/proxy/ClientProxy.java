package dev.psyGamer.immersiveTracks.proxy;

import dev.psyGamer.immersiveTracks.init.BlockRegistry;
import dev.psyGamer.immersiveTracks.init.TileEntiyRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerModel(final Item item, final int metadata) {
		this.registerModel(item, metadata, "");
	}
	
	@Override
	public void registerModel(final Item item, final int metadata, final String variant) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName() + variant, "inventory"));
	}
	
	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);
		
		TileEntiyRegistry.registerTESRs();
	}
	
	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);
		
		BlockRegistry.registerBlockColors();
	}
	
	@Override
	public void postInit(final FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
