package dev.psyGamer.immersiveTracks.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.vecmath.Vector3d;

@SuppressWarnings("deprecation")
public class ModelBlockBase extends BlockBase {
	
	private final AxisAlignedBB boundingBox;
	
	public ModelBlockBase(final String name, final Material material, final CreativeTabs tab, final Vector3d posA, final Vector3d posB) {
		super(name, material, tab);
		
		this.boundingBox = posA == null || posB == null ? new AxisAlignedBB(
				0, 0, 0,
				1, 1, 1
		) : new AxisAlignedBB(
				posA.x / 16D, posA.y / 16D, posA.z / 16D,
				posB.x / 16D, posB.y / 16D, posB.z / 16D
		);
	}
	
	public ModelBlockBase(final String name, final Material material, final CreativeTabs tab) {
		this(name, material, tab, null, null);
	}
	
	@Override
	public boolean isNormalCube(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(final IBlockState state) {
		return false;
	}
	
	@Override
	public boolean causesSuffocation(final IBlockState state) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(final IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
		return this.boundingBox;
	}
}
