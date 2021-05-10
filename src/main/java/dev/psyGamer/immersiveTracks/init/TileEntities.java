package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.tileEntity.*;

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
			put(SignalControllerTileEntity.class, "signal_controller");
		}};
		
		for (Class tileEntityClass : tileEntities.keySet()) {
			String tileEntityName  = tileEntities.get(tileEntityClass);
			
			GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(ImmersiveTracks.MODID, tileEntityName));
		}
	}
	
	public static void registerTESRs() {
		final Map<Class, TileEntitySpecialRenderer> tileEntitySpecialRenderers = new HashMap() {{
			// No TESRs yet //
		}};
		
		for (Class tileEntityClass : tileEntitySpecialRenderers.keySet()) {
			TileEntitySpecialRenderer renderer  = tileEntitySpecialRenderers.get(tileEntityClass);
			
			ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
		}
	}
}
