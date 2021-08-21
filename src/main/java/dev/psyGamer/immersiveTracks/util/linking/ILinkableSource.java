package dev.psyGamer.immersiveTracks.util.linking;

import cam72cam.mod.math.Vec3i;
import cam72cam.mod.world.World;

public interface ILinkableSource {
	
	void onLink(World world, ILinkableTarget target);
	
	boolean isValidTarget(World world, Vec3i pos);
}
