package dev.psyGamer.immersiveTracks.registry;

import cam72cam.mod.block.BlockType;

import dev.psyGamer.immersiveTracks.block.SignalBlock;
import dev.psyGamer.immersiveTracks.block.SignalControllerBlock;
import dev.psyGamer.immersiveTracks.block.SignalPoleBlock;
import dev.psyGamer.immersiveTracks.util.Pair;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {
	
	private static final List<Block> blocks = new ArrayList<>();
	
	public static final BlockType SIGNAL = new SignalBlock("signal",
			Pair.ofMap("Top", Pair.of(0xEE0000, "Red"), Pair.of(0x222222, "Black")),
			Pair.ofMap("Bottom", Pair.of(0x222222, "Black"), Pair.of(0x00EE00, "Green"))
	);
	
	public static final BlockType SIGNAL_POLE = new SignalPoleBlock("signal_pole");
	public static final BlockType SIGNAL_CONTROLLER = new SignalControllerBlock();
}
