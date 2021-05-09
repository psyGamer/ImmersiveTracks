package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.blocks.SignalBlock;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Blocks {
	
	private static final List<Block> blocks = new ArrayList<>();
	
	public static void addBlock(Block block) {
		blocks.add(block);
	}
	public static Block[] getBlocks() {
		return blocks.toArray(new Block[0]);
	}
	
	public static final Block SIGNAL = new SignalBlock();
}
