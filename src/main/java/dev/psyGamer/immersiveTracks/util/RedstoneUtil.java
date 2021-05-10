
package dev.psyGamer.immersiveTracks.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;

import java.util.HashMap;
import java.util.Map;

public class RedstoneUtil {
	
	public static Map<EnumFacing, Boolean> getPoweredSides(World world, BlockPos pos) {
		Map<EnumFacing, Boolean> poweredSides = new HashMap<>();
		
		getPowerOnSides(world, pos).forEach((direction, power) -> poweredSides.put(direction, power > 0));
		
		return poweredSides;
	}
	
	public static Map<EnumFacing, Integer> getPowerOnSides(World world, BlockPos pos) {
		Map<EnumFacing, Integer> sidePower = new HashMap();
		
		for (EnumFacing direction : EnumFacing.values()) {
			sidePower.put(direction, world.getRedstonePower(pos.offset(direction), direction));
			
			if (sidePower.get(direction) > 0 || direction == EnumFacing.UP || direction == EnumFacing.DOWN)
				continue;
			
			IBlockState blockState = world.getBlockState(pos);
			IBlockState offsetBlockState = world.getBlockState(pos.offset(direction));
			
			if (	blockState.getBlock().canConnectRedstone(offsetBlockState, world, pos, direction)
				 && offsetBlockState.getBlock() == Blocks.REDSTONE_WIRE) {
				
				sidePower.put(direction, offsetBlockState.getValue(Blocks.REDSTONE_WIRE.POWER));
			}
		}
		
		return sidePower;
	}
	
	public static boolean isBlockPowered(World world, BlockPos pos) {
		return getPoweredSides(world, pos).values().contains(true);
	}
}
