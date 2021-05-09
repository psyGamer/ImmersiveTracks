package dev.psyGamer.immersiveTracks;

import dev.psyGamer.immersiveTracks.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MOD_ID, name = "Immersive Tracks", version = "Development")
public class Main {
	
	public static final String MOD_ID = "immersivetracks";
	
	@Mod.Instance
	public static Main instance;
	
	@SidedProxy(
		serverSide = "dev.psyGamer.immersiveTracks.proxy.CommonProxy",
		clientSide = "dev.psyGamer.immersiveTracks.proxy.ClientProxy"
	)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}
	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		
	}
}
