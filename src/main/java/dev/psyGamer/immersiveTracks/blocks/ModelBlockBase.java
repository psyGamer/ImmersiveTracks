package dev.psyGamer.immersiveTracks.blocks;

import dev.psyGamer.immersiveTracks.util.MathUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.vecmath.Vector3d;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class ModelBlockBase extends BlockBase {
	
	private final AxisAlignedBB boundingBox;
	private final Map<EnumFacing, AxisAlignedBB> rotatedBoundingBoxes = new HashMap<>();
	
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
	
	protected AxisAlignedBB rotateBoundingBox(final AxisAlignedBB boundingBox, final EnumFacing facing) {
		return this.rotateBoundingBox(boundingBox, facing, false);
	}
	
	protected AxisAlignedBB rotateBoundingBox(AxisAlignedBB boundingBox, final EnumFacing facing, final boolean forceRecalculation) {
		if (!forceRecalculation && this.rotatedBoundingBoxes.containsKey(facing)) {
			return this.rotatedBoundingBoxes.get(facing);
		}
		
		final double xAngle;
		final double yAngle;
		
		switch (facing) {
			default:
			case NORTH:
				xAngle = 0;
				yAngle = 0;
				break;
			case EAST:
				xAngle = 90;
				yAngle = 0;
				break;
			case SOUTH:
				xAngle = 180;
				yAngle = 0;
				break;
			case WEST:
				xAngle = 270;
				yAngle = 0;
				break;
			case UP:
				xAngle = 0;
				yAngle = 90;
				break;
			case DOWN:
				xAngle = 0;
				yAngle = 270;
				break;
		}
		
		final MathUtil.PolarPosition polarMin = MathUtil.convertCartesianToPolar(new Vector3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ));
		final MathUtil.PolarPosition polarMax = MathUtil.convertCartesianToPolar(new Vector3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ));
		
		polarMin.xRotation += xAngle;
		polarMax.xRotation += xAngle;
		polarMin.yRotation += yAngle;
		polarMax.yRotation += yAngle;
		
		final Vector3d minPos = MathUtil.convertPolarToCartesian(polarMin);
		final Vector3d maxPos = MathUtil.convertPolarToCartesian(polarMax);
		
		boundingBox = new AxisAlignedBB(minPos.x, minPos.y, minPos.z, maxPos.x, maxPos.y, maxPos.z);
		
		this.rotatedBoundingBoxes.put(facing, boundingBox);
		
		return boundingBox;
	}
	
	static Vector3d getPosOnSphere(final double rotX, final double rotY, final double r) {
		final double x = r * Math.sin(rotX) * Math.cos(rotY);
		final double y = r * Math.sin(rotX) * Math.sin(rotY);
		final double z = r * Math.cos(rotX);
		
		return new Vector3d(x, y, z);
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
		System.out.println(this.boundingBox);
		return this.boundingBox;
	}
}
