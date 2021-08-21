package dev.psyGamer.immersiveTracks.util.linking;

import cam72cam.mod.math.Vec3i;
import cam72cam.mod.world.World;

public interface ILinkableTarget {
	
	boolean isValidSource(World world, Vec3i pos);
}
