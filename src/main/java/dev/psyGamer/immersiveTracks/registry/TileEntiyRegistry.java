package dev.psyGamer.immersiveTracks.registry;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class TileEntiyRegistry {
	public static void registerTileEntities() {
		final Map<Class, String> tileEntities = new HashMap() {{
			this.put(SignalTileEntity.class, "signal");
			this.put(SignalControllerTileEntity.class, "signal_controller");
		}};
		
		for (final Class tileEntityClass : tileEntities.keySet()) {
			final String tileEntityName = tileEntities.get(tileEntityClass);
			
			GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(ImmersiveTracks.MODID, tileEntityName));
		}
	}
	
	public static void registerTESRs() {
		final Map<Class, TileEntitySpecialRenderer> tileEntitySpecialRenderers = new HashMap() {{
			// No TESRs yet //
		}};
		
		for (final Class tileEntityClass : tileEntitySpecialRenderers.keySet()) {
			final TileEntitySpecialRenderer renderer = tileEntitySpecialRenderers.get(tileEntityClass);
			
			ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
		}
	}
}
