package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.block.BlockTypeEntity;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;

public class SignalControllerBlock extends BlockTypeEntity {
	
	public SignalControllerBlock() {
		super(ImmersiveTracks.MODID, "signal_controller");
	}
	
	@Override
	protected BlockEntity constructBlockEntity() {
		return new SignalControllerTileEntity();
	}
}
