package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockEntity;

import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import dev.psyGamer.immersiveTracks.util.Pair;

import java.util.Map;

public class SignalBlock extends BlockTypeEntityBase {
	
	@SafeVarargs
	public SignalBlock(final String name, final Pair<String, Map<Integer, String>>... bulbData) {
		super(name);
	}
	
	@Override
	protected BlockEntity constructBlockEntity() {
		return new SignalTileEntity();
	}
}
