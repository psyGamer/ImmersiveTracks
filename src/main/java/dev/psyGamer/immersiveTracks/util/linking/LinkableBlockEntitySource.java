package dev.psyGamer.immersiveTracks.util.linking;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.world.World;

public abstract class LinkableBlockEntitySource extends BlockEntity {
	
	public abstract void onLink(World world, LinkableBlockEntityTarget target);
	
	public abstract boolean isValidTarget(World world, Vec3i pos);
}
