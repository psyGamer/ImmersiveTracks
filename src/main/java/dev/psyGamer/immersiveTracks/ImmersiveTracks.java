package dev.psyGamer.immersiveTracks;

import dev.psyGamer.immersiveTracks.creativeTabs.SignalTab;
import dev.psyGamer.immersiveTracks.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ImmersiveTracks.MODID, name = ImmersiveTracks.NAME, version = "Development")
public class ImmersiveTracks {
	
	public static final String MODID = "immersivetracks";
	public static final String NAME = "Immersive Tracks";
	
	public static Logger logger = LogManager.getLogger(ImmersiveTracks.MODID);
	
	public static final CreativeTabs SIGNALS_TAB = new SignalTab();
	
	@Mod.Instance
	private static ImmersiveTracks instance;
	
	@SidedProxy(
			serverSide = "dev.psyGamer.immersiveTracks.proxy.CommonProxy",
			clientSide = "dev.psyGamer.immersiveTracks.proxy.ClientProxy"
	)
	private static CommonProxy proxy;
	
	@Mod.EventHandler
	public static void preInit(final FMLPreInitializationEvent event) {
		ImmersiveTracks.logger = event.getModLog();
		ImmersiveTracks.proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public static void init(final FMLInitializationEvent event) {
		ImmersiveTracks.proxy.init(event);
	}
	
	@Mod.EventHandler
	public static void postInit(final FMLPostInitializationEvent event) {
		ImmersiveTracks.proxy.postInit(event);
	}
	
	public static ImmersiveTracks getInstance() {
		return ImmersiveTracks.instance;
	}
	
	public static CommonProxy getProxy() {
		return ImmersiveTracks.proxy;
	}
}
