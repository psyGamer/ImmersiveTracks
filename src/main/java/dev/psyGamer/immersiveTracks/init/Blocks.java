package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.blocks.ModelBlockBase;
import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase;
import dev.psyGamer.immersiveTracks.blocks.signal.SignalControllerBlock;
import dev.psyGamer.immersiveTracks.util.model.AdvancedBoundingBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class Blocks {
	
	private static final List<Block> blocks = new ArrayList<>();
	
	public static void addBlock(final Block block) {
		Blocks.blocks.add(block);
	}
	
	public static Block[] getBlocks() {
		return Blocks.blocks.toArray(new Block[0]);
	}
	
	public static final Block SIGNAL = new SignalBlockBase("signal");
	public static final Block SIGNAL_POLE = new ModelBlockBase("signal_pole", Material.IRON, ImmersiveTracks.SIGNALS_TAB,
			new AdvancedBoundingBox(2, 16, 2).center()
	);
	public static final Block SIGNAL_CONTROLLER = new SignalControllerBlock();
}
