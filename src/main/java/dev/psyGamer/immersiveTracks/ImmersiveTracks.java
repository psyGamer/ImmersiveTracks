package dev.psyGamer.immersiveTracks;

import dev.psyGamer.immersiveTracks.creativeTabs.SignalTab;
import dev.psyGamer.immersiveTracks.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ImmersiveTracks.MODID, name = "Immersive Tracks", version = "Development")
public class ImmersiveTracks {
	
	public static final String MODID = "immersivetracks";
	
	public static final CreativeTabs SIGNALS_TAB = new SignalTab();
	
	@Mod.Instance
	private static ImmersiveTracks instance;
	
	@SidedProxy(
		serverSide = "dev.psyGamer.immersiveTracks.proxy.CommonProxy",
		clientSide = "dev.psyGamer.immersiveTracks.proxy.ClientProxy"
	)
	private static CommonProxy proxy;
	
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
	
	public static ImmersiveTracks getInstance() {
		return instance;
	}
	
	public static CommonProxy getProxy() {
		return proxy;
	}
}
