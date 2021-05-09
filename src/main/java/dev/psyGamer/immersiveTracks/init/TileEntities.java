package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.Main;
import dev.psyGamer.immersiveTracks.tileEntity.*;
import dev.psyGamer.immersiveTracks.tileEntity.render.*;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Map;
import java.util.HashMap;

public class TileEntities {
	public static void registerTileEntities() {
		final Map<Class, String> tileEntities = new HashMap() {{
			put(SignalTileEntity.class, "signal");
		}};
		
		for (Class tileEntityClass : tileEntities.keySet()) {
			String tileEntityName  = tileEntities.get(tileEntityClass);
			
			GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(Main.MOD_ID, tileEntityName));
		}
	}
	
	public static void registerTESRs() {
		final Map<Class, TileEntitySpecialRenderer> tileEntitySpecialRenderers = new HashMap() {{
//			put(SignalTileEntity.class, new SignalTESR());
		}};
		
		for (Class tileEntityClass : tileEntitySpecialRenderers.keySet()) {
			TileEntitySpecialRenderer renderer  = tileEntitySpecialRenderers.get(tileEntityClass);
			
			ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
		}
	}
}
