package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.block.BlockTypeEntity;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import dev.psyGamer.immersiveTracks.util.Pair;

import java.util.Map;

public class SignalBlock extends BlockTypeEntity {
	
	@SafeVarargs
	public SignalBlock(final String name, final Pair<String, Map<Integer, String>>... bulbData) {
		super(ImmersiveTracks.MODID, name);
	}
	
	@Override
	protected BlockEntity constructBlockEntity() {
		return new SignalTileEntity();
	}
}
