package dev.psyGamer.immersiveTracks.util.linking;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.world.World;

public abstract class LinkableBlockEntityTarget extends BlockEntity {
	
	public abstract boolean isValidSource(World world, Vec3i pos);
}
