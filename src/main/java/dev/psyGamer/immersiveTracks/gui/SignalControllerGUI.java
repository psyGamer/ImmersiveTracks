package dev.psyGamer.immersiveTracks.gui;

import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;
import net.minecraft.client.gui.GuiScreen;

public class SignalControllerGUI extends GuiScreen {
	
	private final SignalControllerTileEntity signalController;
	
	public SignalControllerGUI(final SignalControllerTileEntity signalController) {
		this.signalController = signalController;
	}
	
	@Override
	public void initGui() {
	
	}
}
