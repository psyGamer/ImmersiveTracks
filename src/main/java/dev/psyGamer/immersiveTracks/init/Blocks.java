package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.blocks.ModelBlockBase;
import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase;
import dev.psyGamer.immersiveTracks.blocks.signal.SignalControllerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import javax.vecmath.Vector3d;
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
			new Vector3d(7, 0, 7),
			new Vector3d(9, 16, 9)
	);
	public static final Block SIGNAL_CONTROLLER = new SignalControllerBlock();
}
