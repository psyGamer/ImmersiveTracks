package dev.psyGamer.immersiveTracks.util.model;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

import javax.vecmath.Vector3d;

/**
 * @author psyGamer
 * @version 1.0
 * @since 1.0
 */
public class AdvancedBoundingBox {
	
	private final double minX;
	private final double minY;
	private final double minZ;
	private final double maxX;
	private final double maxY;
	private final double maxZ;
	
	private EnumFacing upwardRotationBase = EnumFacing.NORTH;
	private EnumFacing downwardRotationBase = EnumFacing.NORTH;
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox(final double minX, final double minY, final double minZ,
							   final double maxX, final double maxY, final double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox(final Vector3d min, final Vector3d max) {
		this(min, max, false);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox(final Vector3d min, final Vector3d max,
							   final boolean immutable) {
		this.minX = min.x;
		this.minY = min.y;
		this.minZ = min.z;
		this.maxX = max.x;
		this.maxY = max.y;
		this.maxZ = max.z;
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox flip(final EnumFacing.Axis axis) {
		return this.flip(axis == EnumFacing.Axis.X, axis == EnumFacing.Axis.Y, axis == EnumFacing.Axis.Z);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox rotate(final EnumFacing facing) {
		switch (facing) {
			default:
			case NORTH:
				return this;
			case SOUTH:
				return this.flip(true, false, true);
			case EAST:
				return this.rotatePositiveY();
			case WEST:
				return this.rotatePositiveY().flip(true, false, true);
			case UP:
				switch (this.upwardRotationBase) {
					default:
					case NORTH:
						return this.rotatePositiveX();
					case SOUTH:
						return this.rotatePositiveX().flip(true, false, true);
					case EAST:
						return this.rotatePositiveX().rotatePositiveY();
					case WEST:
						return this.rotatePositiveX().rotatePositiveY().flip(true, false, true);
				}
			case DOWN:
				switch (this.downwardRotationBase) {
					default:
					case NORTH:
						return this.rotateNegativeX();
					case SOUTH:
						return this.rotateNegativeX().flip(true, false, true);
					case EAST:
						return this.rotateNegativeX().rotatePositiveY();
					case WEST:
						return this.rotateNegativeX().rotatePositiveY().flip(true, false, true);
				}
		}
	}
	
	public AdvancedBoundingBox rotatePositiveX() {
		return new AdvancedBoundingBox(
				this.minX, -this.minZ + 16, this.minY,
				this.maxX, -this.maxZ + 16, this.maxY
		);
	}
	
	public AdvancedBoundingBox rotateNegativeX() {
		return new AdvancedBoundingBox(
				this.minX, this.minZ, -this.minY + 16,
				this.maxX, this.maxZ, -this.maxY + 16
		);
	}
	
	public AdvancedBoundingBox rotatePositiveY() {
		return new AdvancedBoundingBox(
				-this.minZ + 16, this.minY, this.minX,
				-this.maxZ + 16, this.maxY, this.maxX
		);
	}
	
	public AdvancedBoundingBox rotateNegativeY() {
		return new AdvancedBoundingBox(
				this.minZ, this.minY, -this.minX + 16,
				this.maxZ, this.maxY, -this.maxX + 16
		);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox flip(final boolean xAxis, final boolean yAxis, final boolean zAxis) {
		return new AdvancedBoundingBox(
				xAxis ? -this.minX + 16 : this.minX, yAxis ? -this.minY + 16 : this.minY, zAxis ? -this.minZ + 16 : this.minZ,
				xAxis ? -this.maxX + 16 : this.maxX, yAxis ? -this.maxY + 16 : this.maxY, zAxis ? -this.maxZ + 16 : this.maxZ
		);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox center(final boolean x, final boolean y, final boolean z) {
		final double centerX = Math.abs(this.maxX - this.minX);
		final double centerY = Math.abs(this.maxY - this.minY);
		final double centerZ = Math.abs(this.maxZ - this.minZ);
		
		return this.offset(
				x ? 8 - centerX : 0,
				y ? 8 - centerY : 0,
				z ? 8 - centerZ : 0
		);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox offset(final double x, final double y, final double z) {
		return new AdvancedBoundingBox(
				this.minX + x, this.minY + y, this.minZ + z,
				this.maxX + x, this.maxY + y, this.maxZ + z
		);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox grow(final double x, final double y, final double z) {
		return new AdvancedBoundingBox(
				this.minX - x, this.minY - y, this.minZ - z,
				this.maxX + x, this.maxY + y, this.maxZ + z
		);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox grow(final double value) {
		return this.grow(value, value, value);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox shrink(final double x, final double y, final double z) {
		return this.grow(-x, -y, -z);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox shrink(final double value) {
		return this.shrink(value, value, value);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AxisAlignedBB getBoundingBox() {
		return new AxisAlignedBB(
				this.minX / 16, this.minY / 16, this.minZ / 16,
				this.maxX / 16, this.maxY / 16, this.maxZ / 16
		);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public void setUpwardRotationBase(final EnumFacing upwardRotationBase) {
		if (upwardRotationBase == EnumFacing.DOWN || upwardRotationBase == EnumFacing.UP) {
			throw new IllegalArgumentException("Upward rotation base must be on the horizontal plane (NORTH, EAST, SOUTH, WEST)");
		}
		
		this.upwardRotationBase = upwardRotationBase;
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public void setDownwardRotationBase(final EnumFacing downwardRotationBase) {
		if (downwardRotationBase == EnumFacing.DOWN || downwardRotationBase == EnumFacing.UP) {
			throw new IllegalArgumentException("Downward rotation base must be on the horizontal plane (NORTH, EAST, SOUTH, WEST)");
		}
		
		this.downwardRotationBase = downwardRotationBase;
	}
	
	@Override
	public String toString() {
		return String.format("AdvancedBoundingBox[%s, %s, %s -> %s, %s, %s]", this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
	}
}
