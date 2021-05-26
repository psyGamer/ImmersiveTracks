package dev.psyGamer.immersiveTracks.util.linking;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILinkableSource {
	
	void onLink(World world, BlockPos source, BlockPos target);
	
	boolean isValidTarget(World world, BlockPos pos);
}
