package dev.psyGamer.immersiveTracks.blocks.signal;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.blocks.BlockBase;
import dev.psyGamer.immersiveTracks.init.BlockRegistry;
import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import dev.psyGamer.immersiveTracks.util.RedstoneUtil;
import dev.psyGamer.immersiveTracks.util.linking.ILinkableSource;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class SignalControllerBlock extends BlockBase implements ILinkableSource {
	
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public SignalControllerBlock() {
		super("signal_controller", Material.IRON, ImmersiveTracks.SIGNALS_TAB);
		
		this.setDefaultState(this.getDefaultState().withProperty(SignalControllerBlock.ACTIVE, false));
	}
	
	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(final World world, final IBlockState state) {
		return new SignalControllerTileEntity();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SignalControllerBlock.ACTIVE);
	}
	
	@Override
	public int getMetaFromState(final IBlockState state) {
		return state.getValue(SignalControllerBlock.ACTIVE) ? 1 : 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState().withProperty(SignalControllerBlock.ACTIVE, meta != 0);
	}
	
	@Override
	public boolean canConnectRedstone(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
		return side != null && side != EnumFacing.UP && side != EnumFacing.DOWN;
	}
	
	@Override
	public void neighborChanged(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
		final IBlockState oldState = state;
		final IBlockState newState = state.withProperty(SignalControllerBlock.ACTIVE, RedstoneUtil.isBlockPowered(worldIn, pos));
		
		if (!oldState.equals(newState)) {
			worldIn.setBlockState(pos, newState);
			
			if (!worldIn.isRemote) {
				((SignalControllerTileEntity) Objects.requireNonNull(worldIn.getTileEntity(pos)))
						.updateSignals(worldIn.getBlockState(pos).getValue(SignalControllerBlock.ACTIVE));
			}
		}
	}
	
	@Override
	public void onLink(final World world, final BlockPos source, final BlockPos target) {
		final SignalTileEntity signalTileEntity = (SignalTileEntity) world.getTileEntity(target);
		final SignalControllerTileEntity controllerTileEntity = (SignalControllerTileEntity) world.getTileEntity(source);
		
		controllerTileEntity.addSignal(signalTileEntity);
	}
	
	@Override
	public boolean isValidTarget(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getBlock() == BlockRegistry.SIGNAL;
	}
}
