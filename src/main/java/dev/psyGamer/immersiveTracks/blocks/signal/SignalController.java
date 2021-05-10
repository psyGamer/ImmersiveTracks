package dev.psyGamer.immersiveTracks.blocks.signal;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.blocks.BlockBase;
import dev.psyGamer.immersiveTracks.util.RedstoneUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class SignalController extends BlockBase {
	
	private SignalBlockBase signal;
	
	public SignalController() {
		super("signal_controller", Material.IRON, ImmersiveTracks.SIGNALS_TAB);
	}
	
	private boolean requireSignal(World worldIn, BlockPos pos) {
		if (signal != null)
			return true;
		
		if (!worldIn.isRemote) {
			Block blockAbove = worldIn.getBlockState(pos).getBlock();
			
			if (blockAbove instanceof SignalBlockBase) {
				signal = (SignalBlockBase) blockAbove;
				
				return true;
			}
			
			return false;
		}
		
		return false;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		requireSignal(worldIn, pos);
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
		return side != null && side != EnumFacing.UP && side != EnumFacing.DOWN;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		System.out.println("nCh");
		RedstoneUtil.getPoweredSides((World) worldIn, pos);
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		System.out.println("ONnCh");
		if (world instanceof World)
			RedstoneUtil.getPoweredSides((World) world, pos);
		else
			System.out.println("not instance");
	}
}
