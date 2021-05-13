package dev.psyGamer.immersiveTracks.util;

import javax.vecmath.Vector3d;

public class MathUtil {
	
	public static final class PolarPosition {
		public double xRotation;
		public double yRotation;
		public double radius;
		
		public PolarPosition(final double xRotation, final double yRotation) {
			this(xRotation, yRotation, 1);
		}
		
		public PolarPosition(final double xRotation, final double yRotation, final double radius) {
			this.xRotation = xRotation;
			this.yRotation = yRotation;
			this.radius = radius;
		}
	}
	
	public static PolarPosition convertCartesianToPolar(final Vector3d pos) {
		final double radius = Math.sqrt(pos.x * pos.x + pos.y * pos.y + pos.z + pos.z);
		
		return new PolarPosition(
				Math.acos(pos.z / radius),
				Math.acos(pos.x / Math.sqrt(pos.x * pos.x + pos.y * pos.y) * (pos.y < 0 ? -1 : 1)),
				
				radius
		);
	}
	
	public static Vector3d convertPolarToCartesian(final PolarPosition pos) {
		return new Vector3d(
				pos.radius * Math.sin(pos.xRotation) * Math.cos(pos.yRotation),
				pos.radius * Math.sin(pos.xRotation) * Math.sin(pos.yRotation),
				pos.radius * Math.cos(pos.xRotation)
		);
	}
}
