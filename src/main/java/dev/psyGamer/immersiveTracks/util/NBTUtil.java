package dev.psyGamer.immersiveTracks.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class NBTUtil {
	
	public static void setBlockPosition(final NBTTagCompound tag, final String key, final BlockPos value) {
		tag.setIntArray(key, new int[] { value.getX(), value.getY(), value.getZ() });
	}
	
	public static BlockPos getBlockPosition(final NBTTagCompound tag, final String key) {
		return new BlockPos(tag.getIntArray(key)[0], tag.getIntArray(key)[1], tag.getIntArray(key)[2]);
	}
}
