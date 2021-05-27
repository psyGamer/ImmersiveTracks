package dev.psyGamer.immersiveTracks;

import dev.psyGamer.immersiveTracks.gui.GUIHandler;
import dev.psyGamer.immersiveTracks.proxy.IProxy;
import dev.psyGamer.immersiveTracks.registry.TileEntiyRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ImmersiveTracks.MODID, name = ImmersiveTracks.NAME, version = "Development")
public class ImmersiveTracks {
	
	public static final String MODID = "immersivetracks";
	public static final String NAME = "Immersive Tracks";
	
	private static Logger logger = LogManager.getLogger(ImmersiveTracks.MODID);
	
	@Mod.Instance
	private static ImmersiveTracks instance;
	
	@SidedProxy(
			serverSide = "dev.psyGamer.immersiveTracks.proxy.CommonProxy",
			clientSide = "dev.psyGamer.immersiveTracks.proxy.ClientProxy"
	)
	private static IProxy proxy;
	
	@Mod.EventHandler
	public static void preInit(final FMLPreInitializationEvent event) {
		ImmersiveTracks.logger = event.getModLog();
		ImmersiveTracks.proxy.preInit(event);
		
		TileEntiyRegistry.registerTileEntities();
	}
	
	@Mod.EventHandler
	public static void init(final FMLInitializationEvent event) {
		ImmersiveTracks.proxy.init(event);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(ImmersiveTracks.instance, new GUIHandler());
	}
	
	@Mod.EventHandler
	public static void postInit(final FMLPostInitializationEvent event) {
		ImmersiveTracks.proxy.postInit(event);
	}
	
	public static ImmersiveTracks getInstance() {
		return ImmersiveTracks.instance;
	}
	
	public static IProxy getProxy() {
		return ImmersiveTracks.proxy;
	}
	
	public static Logger getLogger() {
		return ImmersiveTracks.logger;
	}
}
