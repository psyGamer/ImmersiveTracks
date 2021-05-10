package dev.psyGamer.immersiveTracks.tileEntity;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

/**
 * TileEntity for all signal blocks. <br> <br>
 *
 *
 * @author psyGamer
 * @version 1.0
 * @since 1.0
 * @see dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase SignalBlockBase
 */
public class SignalTileEntity extends TileEntityBase {
	
	private final Map<Integer, Integer> lightBulbs = new HashMap<>();
	
	/**
	 * Sets the bulb color. <br>
	 * Note: Will cause an block update. <br> <br>
	 *
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 * @param bulbIndex The index of the bulb.
	 * @param bulbColor The color of the bulb in hex. (e.g. 0x00FF00)
	 */
	public void setBulbColor(int bulbIndex, int bulbColor) {
		lightBulbs.put(bulbIndex, bulbColor);
		
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
	}
	/**
	 * Gets the bulb color. <br> <br>
	 *
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 * @param bulbIndex The index of the bulb.
	 * @return The color of the bulb.
	 */
	public int getBulbColor(int bulbIndex) {
		if (!lightBulbs.containsKey(bulbIndex))
			return 0x00000;
		
		return lightBulbs.get(bulbIndex);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("bulbs", lightBulbs.size());
		
		lightBulbs.forEach((index, color) -> {
			compound.setInteger("bulb_" + index, color);
		});
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int bulbs = compound.getInteger("bulbs");
		
		for (int i = 0; i < bulbs; i++){
			lightBulbs.put(i, compound.getInteger("bulb_" + i));
		}
	}
}
