package dev.psyGamer.immersiveTracks.blocks.signal;

import dev.psyGamer.immersiveTracks.blocks.ModelBlockBase;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.registry.CreativeTabRegistry;
import dev.psyGamer.immersiveTracks.signals.SignalPreset;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import dev.psyGamer.immersiveTracks.util.Pair;
import dev.psyGamer.immersiveTracks.util.linking.ILinkableTarget;
import dev.psyGamer.immersiveTracks.util.model.AdvancedBoundingBox;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.*;

@SuppressWarnings("deprecation")
public class SignalBlock extends ModelBlockBase implements ILinkableTarget {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool UPDATE = PropertyBool.create("update"); // TODO don't
	
	private static final List<SignalBlock> SIGNAL_BLOCKS = new ArrayList<>();
	private final Map<String, List<Integer>> bulbColors = new HashMap<>();
	private final Map<Integer, String> colorNames = new HashMap<>();
	
	public static int getTintColor(final IBlockAccess world, final BlockPos pos, final int bulbIndex) {
		if (bulbIndex < 0 || !(world.getTileEntity(pos) instanceof SignalTileEntity)) {
			return 0xFFFFFF;
		}
		
		//noinspection ConstantConditions
		return ((SignalTileEntity) world.getTileEntity(pos)).getBulbColor(bulbIndex);
	}
	
	public static List<SignalBlock> getSignalBlocks() {
		return Collections.unmodifiableList(SignalBlock.SIGNAL_BLOCKS);
	}
	
	public Map<String, List<Integer>> getBulbColors() {
		return Collections.unmodifiableMap(this.bulbColors);
	}
	
	public String getColorName(final int color) {
		return this.colorNames.get(color);
	}
	
	@SuppressWarnings("unchecked")
	public SignalBlock(final String name, final Pair<String, Map<Integer, String>>... bulbData) {
		super(name, Material.IRON, CreativeTabRegistry.SIGNALS_TAB, new AdvancedBoundingBox(12, 16, 2).center());
		
		if (bulbData == null) {
			throw new IllegalArgumentException("Bulb data may not be null");
		}
		
		for (final Pair<String, Map<Integer, String>> data : bulbData) {
			if (data.first().isEmpty()) {
				throw new IllegalArgumentException("Bulb data may not contain an empty string");
			}
			if (data.second().isEmpty()) {
				throw new IllegalArgumentException("Bulb data may not contain an empty color list");
			}
			
			this.bulbColors.put(data.first(), new ArrayList<>(data.second().keySet()));
			
			data.second().forEach(this.colorNames::put);
		}
		
		this.setDefaultState(this.getDefaultState()
				.withProperty(SignalBlock.FACING, EnumFacing.NORTH)
				.withProperty(SignalBlock.UPDATE, false)
		);
		
		SignalBlock.SIGNAL_BLOCKS.add(this);
		
		new SignalPreset("Default", this);
	}
	
	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
		if (!worldIn.isRemote) {
			final EnumFacing direction = placer.getHorizontalFacing().getOpposite();
			final IBlockState blockState = this.getDefaultState().withProperty(SignalBlock.FACING, direction);
			final TileEntity tileEntity = worldIn.getTileEntity(pos);
			
			worldIn.setBlockState(pos, blockState);
			
			if (tileEntity instanceof SignalTileEntity) {
				final SignalTileEntity signal = (SignalTileEntity) tileEntity;
				
				int index = 0;
				
				for (final List<Integer> colors : this.bulbColors.values()) {
					signal.setBulbColor(index, colors.get(0));
					
					index++;
				}
				
				signal.markForUpdate();
			}
		}
	}
	
	@Override
	public int getMetaFromState(final IBlockState state) {
		return (state.getValue(SignalBlock.FACING).getHorizontalIndex() + 2) % 4;
	}
	
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState()
				.withProperty(SignalBlock.FACING, EnumFacing.getHorizontal((meta - 2) % 4));
	}
	
	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		worldIn.setBlockState(pos, state.withProperty(SignalBlock.UPDATE, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SignalBlock.FACING, SignalBlock.UPDATE);
	}
	
	@Override
	public TileEntity createTileEntity(final World world, final IBlockState state) {
		return new SignalTileEntity();
	}
	
	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return true;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
		return this.getRotatedBoundingBox(state.getValue(SignalBlock.FACING));
	}
	
	@Override
	public boolean isValidSource(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getBlock() == BlockRegistry.SIGNAL_CONTROLLER;
	}
}
