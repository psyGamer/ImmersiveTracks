package dev.psyGamer.immersiveTracks.blocks.signal;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.blocks.ModelBlockBase;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

@SuppressWarnings("deprecation")
public class SignalBlockBase extends ModelBlockBase {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public static int getBulbColor(final IBlockAccess world, final BlockPos pos, final int bulbIndex) {
		if (bulbIndex < 0) {
			return 0xFFFFFF;
		}
		
		//noinspection ConstantConditions
		return ((SignalTileEntity) world.getTileEntity(pos)).getBulbColor(bulbIndex);
	}
	
	public SignalBlockBase(final String name) {
		super(name, Material.IRON, ImmersiveTracks.SIGNALS_TAB,
				new Vector3d(2, 0, 7),
				new Vector3d(13, 9, 16)
		);
	}
	
	@Override
	public int getMetaFromState(final IBlockState state) {
		return state.getValue(SignalBlockBase.FACING).getHorizontalIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState().withProperty(SignalBlockBase.FACING, EnumFacing.getHorizontal(meta));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SignalBlockBase.FACING);
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
}
