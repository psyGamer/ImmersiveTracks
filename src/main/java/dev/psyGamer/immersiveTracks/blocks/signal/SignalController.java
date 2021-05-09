package dev.psyGamer.immersiveTracks.blocks.signal;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignalController extends BlockBase {
	
	private SignalBlockBase signal;
	
	public SignalController() {
		super("signal_controller", Material.IRON, ImmersiveTracks.SIGNALS_TAB);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
//			if (worldIn.getBlock)
		}
	}
}
