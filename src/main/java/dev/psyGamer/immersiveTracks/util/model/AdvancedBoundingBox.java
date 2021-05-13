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
		return this.flip(axis == EnumFacing.Axis.X, axis == EnumFacing.Axis.Y, axis == EnumFacing.Axis.Z);
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public AdvancedBoundingBox flip(final boolean xAxis, final boolean yAxis, final boolean zAxis) {
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
	private AdvancedBoundingBox setValues(final double minX, final double minY, final double minZ,
										  final double maxX, final double maxY, final double maxZ) {
		if (this.immuatable) {
			return new AdvancedBoundingBox(
					minX, minY, minZ,
					maxX, maxY, maxZ,
					true
			);
		}
		
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		
		return this;
	}
	
	/**
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	private AdvancedBoundingBox setValues(final AdvancedBoundingBox values) {
		if (this.immuatable) {
			return values;
		}
		
		this.minX = values.minX;
		this.minY = values.minY;
		this.minZ = values.minZ;
		this.maxX = values.maxX;
		this.maxY = values.maxY;
		this.maxZ = values.maxZ;
		
		return this;
	}
}
