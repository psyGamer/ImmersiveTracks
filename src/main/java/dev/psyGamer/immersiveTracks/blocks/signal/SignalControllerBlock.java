package dev.psyGamer.immersiveTracks.blocks.signal;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.ModConfig;
import dev.psyGamer.immersiveTracks.blocks.BlockBase;
import dev.psyGamer.immersiveTracks.init.ModBlocks;
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
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		SignalControllerBlock.updateSignal(worldIn, pos);
	}
	
	@Override
	public boolean canConnectRedstone(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
		return side != null && side != EnumFacing.UP && side != EnumFacing.DOWN;
	}
	
	@Override
	public void neighborChanged(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
		worldIn.setBlockState(pos, state.withProperty(SignalControllerBlock.ACTIVE, RedstoneUtil.isBlockPowered(worldIn, pos)));
		
		SignalControllerBlock.updateSignal(worldIn, pos);
	}
	
	private static void updateSignal(final World world, final BlockPos pos) {
		if (!world.isBlockLoaded(pos)) {
			return;
		}
		
		final SignalTileEntity signal = SignalControllerBlock.getSignal(world, pos);
		
		if (signal != null) {
			signal.setBulbColor(0, world.getBlockState(pos).getValue(SignalControllerBlock.ACTIVE) ? 0x222222 : 0xEE0000);
			signal.setBulbColor(1, world.getBlockState(pos).getValue(SignalControllerBlock.ACTIVE) ? 0x00EE00 : 0x222222);
		}
	}
	
	private static SignalTileEntity getSignal(final World world, final BlockPos pos) {
		if (!world.isRemote) {
			for (int controllerYCheck = 1 ; controllerYCheck <= ModConfig.Signals.maxControllerDepth ; controllerYCheck++) {
				Block block = world.getBlockState(pos.up(controllerYCheck)).getBlock();
				
				if (block == ModBlocks.SIGNAL) {
					return (SignalTileEntity) world.getTileEntity(pos.up(controllerYCheck));
				}
				
				if (block == ModBlocks.SIGNAL_POLE) {
					for (int poleYCheck = controllerYCheck ; poleYCheck < controllerYCheck + ModConfig.Signals.maxSignalHeight ; poleYCheck++) {
						block = world.getBlockState(pos.up(poleYCheck)).getBlock();
						
						if (block == ModBlocks.SIGNAL) {
							return (SignalTileEntity) world.getTileEntity(pos.up(poleYCheck));
						}
						if (block != ModBlocks.SIGNAL_POLE) {
							break;
						}
					}
					
					break;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public void onLink(final World world, final BlockPos target) {
		final SignalTileEntity signalTileEntity = (SignalTileEntity) world.getTileEntity(target);
	}
	
	@Override
	public boolean isValidTarget(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getBlock() == ModBlocks.SIGNAL;
	}
}
