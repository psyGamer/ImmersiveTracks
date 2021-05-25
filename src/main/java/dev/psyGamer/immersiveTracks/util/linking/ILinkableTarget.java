package dev.psyGamer.immersiveTracks.util.linking;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILinkableTarget {
	
	void onLinkAsTarget(World world, BlockPos source);
	
	boolean isValidSource(World world, BlockPos pos);
}
