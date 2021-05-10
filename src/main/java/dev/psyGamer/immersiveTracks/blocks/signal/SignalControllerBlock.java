package dev.psyGamer.immersiveTracks.blocks.signal;

import dev.psyGamer.immersiveTracks.ModConfig;
import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.util.Pair;
import dev.psyGamer.immersiveTracks.util.RedstoneUtil;
import dev.psyGamer.immersiveTracks.init.Blocks;
import dev.psyGamer.immersiveTracks.blocks.BlockBase;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class SignalControllerBlock extends BlockBase {
	
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	/* The signals are cache to reduce the lag produced by checking if a signal is above a controller. */
	private static final Map<Pair<World, BlockPos>,  BlockPos> signalCache = new HashMap<>();
	
	public SignalControllerBlock() {
		super("signal_controller", Material.IRON, ImmersiveTracks.SIGNALS_TAB);
		
		setDefaultState(getDefaultState().withProperty(ACTIVE, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ACTIVE, meta != 0);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		updateSignal(worldIn, pos);
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return side != null && side != EnumFacing.UP && side != EnumFacing.DOWN;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		worldIn.setBlockState(pos, state.withProperty(ACTIVE, RedstoneUtil.isBlockPowered(worldIn, pos)));
		
		updateSignal(worldIn, pos);
	}
	
	public static void removeSignalFromCache(World world, BlockPos signal) {
		if (signalCache.containsValue(signal))
			signalCache.forEach((controllerPos, signalPos) -> {
				if (controllerPos.first() == world && signalPos == signal)
					signalCache.remove(controllerPos);
			});
	}
	
	private static void updateSignal(World world, BlockPos pos) {
		SignalTileEntity signal = getSignal(world, pos);
		
		if (signal != null) {
			signalCache.put(Pair.of(world, pos), signal.getPos());
			
			signal.setBulbColor(0, world.getBlockState(pos).getValue(ACTIVE) ? 0x222222 : 0xEE0000);
			signal.setBulbColor(1, world.getBlockState(pos).getValue(ACTIVE) ? 0x00EE00 : 0x222222);
		}
	}
	
	private static SignalTileEntity getSignal(World world, BlockPos pos) {
		if (signalCache.containsKey(Pair.of(world, pos))) {
			TileEntity tileEntity = world.getTileEntity(signalCache.get(Pair.of(world, pos)));
			
			if (tileEntity instanceof SignalTileEntity)
				return (SignalTileEntity) tileEntity;
		}
		
		if (!world.isRemote) {
			for (int controllerYCheck = 1 ; controllerYCheck < ModConfig.Signals.maxControllerDepth ; controllerYCheck++) {
				Block block = world.getBlockState(pos.up(controllerYCheck)).getBlock();
				
				if (block == Blocks.SIGNAL)
					return (SignalTileEntity) world.getTileEntity(pos.up(controllerYCheck));
					
				if (block == Blocks.SIGNAL_POLE) {
					for (int poleYCheck = controllerYCheck ; poleYCheck < controllerYCheck + ModConfig.Signals.maxSignalHeight ; poleYCheck++) {
						if (block == Blocks.SIGNAL)
							return (SignalTileEntity) world.getTileEntity(pos.up(poleYCheck));
					}
					
					break;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		signalCache.clear();
	}
}
