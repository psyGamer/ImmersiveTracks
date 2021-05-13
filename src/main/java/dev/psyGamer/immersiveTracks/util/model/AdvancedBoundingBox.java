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
	
	private double minX;
	private double minY;
	private double minZ;
	private double maxX;
	private double maxY;
	private double maxZ;
	
	private EnumFacing defaultDirection = EnumFacing.NORTH;
	private EnumFacing upwardRotationBase = EnumFacing.NORTH;
	
	private EnumFacing downwardRotationBase = EnumFacing.NORTH;
	
	private final boolean immuatable;
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox(final double minX, final double minY, final double minZ,
							   final double maxX, final double maxY, final double maxZ) {
		this(minX, minY, minZ, maxX, maxY, maxZ, false);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox(final double minX, final double minY, final double minZ,
							   final double maxX, final double maxY, final double maxZ,
							   final boolean immutable) {
		
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		
		this.immuatable = immutable;
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
		
		this.immuatable = immutable;
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox flip(final EnumFacing.Axis axis) {
		System.out.println("flip axis: " + axis);
		return this.flip(axis == EnumFacing.Axis.X, axis == EnumFacing.Axis.Y, axis == EnumFacing.Axis.Z);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox rotate(final EnumFacing facing) {
		System.out.println("rot: " + facing);
		switch (facing) {
			default:
			case NORTH:
				return this;
			case SOUTH:
				return this.flip(true, false, true);
			case EAST:
				return this.rotateY(1);
			case WEST:
				return this.rotateY(1).flip(true, false, true);
			case UP:
				switch (this.upwardRotationBase) {
					default:
					case NORTH:
						return this.rotateX(1);
					case SOUTH:
						return this.rotateX(1).flip(true, false, true);
					case EAST:
						return this.rotateX(1).rotateY(1);
					case WEST:
						return this.rotateX(1).rotateY(1).flip(true, false, true);
				}
			case DOWN:
				switch (this.downwardRotationBase) {
					default:
					case NORTH:
						return this.rotateX(3);
					case SOUTH:
						return this.rotateX(3).flip(true, false, true);
					case EAST:
						return this.rotateX(3).rotateY(3);
					case WEST:
						return this.rotateX(3).rotateY(3).flip(true, false, true);
				}
		}
	}
	
	public AdvancedBoundingBox rotateX(final int times) {
		System.out.println("rotX: " + times);
		AdvancedBoundingBox boundingBox = this;
		
		for (int i = 0 ; i < times ; i++) {
			boundingBox = this.setValues(
					this.minX, -this.minZ + 16, this.minY,
					this.maxX, -this.maxZ + 16, this.maxY
			);
		}
		
		return boundingBox;
	}
	
	public AdvancedBoundingBox rotateY(final int times) {
		System.out.println("rotY: " + times);
		AdvancedBoundingBox boundingBox = this;
		
		for (int i = 0 ; i < times ; i++) {
			boundingBox = this.setValues(
					-this.minY + 16, this.minY, this.minX,
					-this.maxY + 16, this.maxY, this.maxX
			);
		}
		
		return boundingBox;
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox flip(final boolean xAxis, final boolean yAxis, final boolean zAxis) {
		System.out.println("flip bool");
		return this.setValues(
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
		return this.setValues(
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
		return this.setValues(
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
	public AdvancedBoundingBox copy() {
		return this.copy(this.immuatable);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox copy(final boolean immutable) {
		return new AdvancedBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AxisAlignedBB getBoundingBox() {
		System.out.println("getting: " + this);
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
	public AdvancedBoundingBox setValues(final double minX, final double minY, final double minZ,
										 final double maxX, final double maxY, final double maxZ) {
		if (this.immuatable) {
			System.out.println("Immutable update");
			System.out.println("Before: " + this);
			final AdvancedBoundingBox boundingBox = new AdvancedBoundingBox(
					minX, minY, minZ,
					maxX, maxY, maxZ,
					true
			);
			System.out.println("After: " + boundingBox);
			return boundingBox;
		}
		System.out.println("Before: " + this);
		
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		
		System.out.println("After: " + this);
		
		return this;
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox setValues(final AdvancedBoundingBox values) {
		if (this.immuatable) {
			System.out.println("Immutable update");
			System.out.println("Before: " + this);
			System.out.println("After: " + values);
			
			return values;
		}
		
		System.out.println("Before: " + this);
		
		this.minX = values.minX;
		this.minY = values.minY;
		this.minZ = values.minZ;
		this.maxX = values.maxX;
		this.maxY = values.maxY;
		this.maxZ = values.maxZ;
		
		System.out.println("After: " + this);
		
		return this;
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public void setDefaultDirection(final EnumFacing defaultDirection) {
		if (defaultDirection == EnumFacing.DOWN || defaultDirection == EnumFacing.UP) {
			throw new IllegalArgumentException("Default direction must be on the horizontal plane (NORTH, EAST, SOUTH, WEST)");
		} // TODO Add support for also UP and DOWN
		
		this.defaultDirection = defaultDirection;
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
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public boolean isImmuatable() {
		return this.immuatable;
	}
	
	@Override
	public String toString() {
		return String.format("AdvancedBoundingBox[%s, %s, %s -> %s, %s, %s | Immutable: %b]", this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ, this.immuatable);
	}
}
