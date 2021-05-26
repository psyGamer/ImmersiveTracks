package dev.psyGamer.immersiveTracks.blocks.signal;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.blocks.ModelBlockBase;
import dev.psyGamer.immersiveTracks.init.ModBlocks;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
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

import java.util.Objects;

@SuppressWarnings("deprecation")
public class SignalBlockBase extends ModelBlockBase implements ILinkableTarget {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool UPDATE = PropertyBool.create("update"); // TODO don't
	
	public static int getBulbColor(final IBlockAccess world, final BlockPos pos, final int bulbIndex) {
		if (bulbIndex < 0) {
			return 0xFFFFFF;
		}
		
		return ((SignalTileEntity) Objects.requireNonNull(world.getTileEntity(pos))).getBulbColor(bulbIndex);
	}
	
	public SignalBlockBase(final String name) {
		super(name, Material.IRON, ImmersiveTracks.SIGNALS_TAB, new AdvancedBoundingBox(12, 16, 2).center());
	}
	
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((state.getValue(SignalBlockBase.FACING).getHorizontalIndex() + 2) % 4 + 1) + (state.getValue(SignalBlockBase.UPDATE) ? 0 : 4);
	}
	
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState()
				.withProperty(SignalBlockBase.FACING, EnumFacing.getHorizontal((meta - 3) % 4))
				.withProperty(SignalBlockBase.UPDATE, meta > 3);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SignalBlockBase.FACING, SignalBlockBase.UPDATE);
	}
	
	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
		if (!worldIn.isRemote) {
			final EnumFacing direction = placer.getHorizontalFacing().getOpposite();
			final IBlockState blockState = this.getDefaultState().withProperty(SignalBlockBase.FACING, direction);
			
			worldIn.setBlockState(pos, blockState);
		}
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
		return this.getRotatedBoundingBox(state.getValue(SignalBlockBase.FACING));
	}
	
	@Override
	public boolean isValidSource(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getBlock() == ModBlocks.SIGNAL_CONTROLLER;
	}
}
