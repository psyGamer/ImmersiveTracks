package dev.psyGamer.immersiveTracks;

import cam72cam.mod.ModCore;
import cam72cam.mod.ModEvent;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ImmersiveTracks extends ModCore.Mod {
	
	public static final String MODID = "immersivetracks";
	public static final String NAME = "Immersive Tracks";
	public static final String VERSION = "0.1.1";
	
	public static final Logger LOGGER = LogManager.getLogger(ImmersiveTracks.MODID);
	
	@Override
	public String modID() {
		return ImmersiveTracks.MODID;
	}
	
	@Override
	public void commonEvent(final ModEvent event) {
	
	}
	
	@Override
	public void clientEvent(final ModEvent event) {
	
	}
	
	@Override
	public void serverEvent(final ModEvent event) {
	
	}
}
