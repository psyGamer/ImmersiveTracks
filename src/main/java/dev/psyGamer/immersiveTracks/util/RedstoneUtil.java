
package dev.psyGamer.immersiveTracks.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class RedstoneUtil {
	
	public static Map<EnumFacing, Boolean> fromWhereIsBlockPowered(World world, BlockPos pos) {
		Map<EnumFacing, Boolean> poweredSides = new HashMap() {{
			put(EnumFacing.NORTH, false);
			put(EnumFacing.SOUTH, false);
			put(EnumFacing.EAST, false);
			put(EnumFacing.WEST, false);
		}};
		
		for (EnumFacing direction : poweredSides.keySet()) {
			if (world.getRedstonePower(pos.offset(direction), direction) > 0) {
				poweredSides.put(direction, true);
				continue;
			}
			
			IBlockState blockState = world.getBlockState(pos.offset(direction));
			
			if (	   world.getBlockState(pos).getBlock().canConnectRedstone(blockState, world, pos, direction)
					&& blockState.getBlock() == Blocks.REDSTONE_WIRE
					&& blockState.getValue(Blocks.REDSTONE_WIRE.POWER) > 0) {
				poweredSides.put(direction, true);
			}
		}
		
		return poweredSides;
	}
}
