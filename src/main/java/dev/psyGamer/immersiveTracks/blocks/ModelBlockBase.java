package dev.psyGamer.immersiveTracks.blocks;

import dev.psyGamer.immersiveTracks.util.model.AdvancedBoundingBox;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Map;

@SuppressWarnings("deprecation")
public class ModelBlockBase extends BlockBase {
	
	protected Map<EnumFacing, AxisAlignedBB> boundingBoxes;
	
	public ModelBlockBase(final String name, final Material material, final CreativeTabs tab, final AdvancedBoundingBox boundingBox) {
		super(name, material, tab);
		
		this.boundingBoxes = boundingBox != null
				? boundingBox.generateRotationMap()
				: new AdvancedBoundingBox(0, 0, 0, 16, 16, 16).generateRotationMap();
		
		if (boundingBox != null) {
			return;
		}
		
		for (final EnumFacing facing : EnumFacing.values()) {
			this.boundingBoxes.put(facing, new AxisAlignedBB(
					0, 0, 0,
					1, 1, 1
			));
		}
	}
	
	protected void recalculateRotationMap(final AdvancedBoundingBox boundingBox) {
		this.boundingBoxes = boundingBox.generateRotationMap();
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
		return this.boundingBoxes.get(EnumFacing.NORTH);
	}
	
	public AxisAlignedBB getRotatedBoundingBox(final EnumFacing rotation) {
		return this.boundingBoxes.get(rotation);
	}
}
