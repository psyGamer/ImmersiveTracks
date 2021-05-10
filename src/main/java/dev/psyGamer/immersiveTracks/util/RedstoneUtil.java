
package dev.psyGamer.immersiveTracks.util;

import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements several utility methods for working with Redstone. <br> <br>
 *
 * @author psyGamer
 * @version 1.0
 * @since 1.0
 */
public class RedstoneUtil {
	
	/**
	 * Returns from which sides a block is powered. <br> <br>
	 *
	 * @param world The world of the block which should be checked.
	 * @param pos The position of the block which should be checked.
	 * @return A Map of EnumFacing linking to weather it is powered. <br>
	 *         <code>null</code> if the block is unloaded.
	 *
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public static Map<EnumFacing, Boolean> getPoweredSides(World world, BlockPos pos) {
		if (!world.isBlockLoaded(pos))
			return null;
		
		final Map<EnumFacing, Boolean> poweredSides = new HashMap<>();
		
		getPowerOnSides(world, pos).forEach((direction, power) -> poweredSides.put(direction, power > 0));
		
		return poweredSides;
	}
	
	/**
	 * Returns the redstone power of each side. <br> <br>
	 *
	 * @param world The world of the block which should be checked.
	 * @param pos The position of the block which should be checked.
	 * @return A Map of EnumFacing linking to the power level. <br>
	 *         <code>null</code> if the block is unloaded.
	 *
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
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
	
	/**
	 * Returns from which sides a block is powered. <br> <br>
	 *
	 * @param world The world of the block which should be checked.
	 * @param pos The position of the block which should be checked.
	 * @return A boolean weather the block is powered or not. <br>
	 *         <code>false</code> if the block is unloaded.
	 *
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public static boolean isBlockPowered(World world, BlockPos pos) {
		if(!world.isBlockLoaded(pos))
			return false;
		
		return getPoweredSides(world, pos).values().contains(true);
	}
}
