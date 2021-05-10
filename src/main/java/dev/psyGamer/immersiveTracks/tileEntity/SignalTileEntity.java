package dev.psyGamer.immersiveTracks.tileEntity;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class SignalTileEntity extends TileEntityBase {
	
	private final Map<Integer, Integer> lightBulbs = new HashMap<>();
	
	public void setBulbColor(int bulbIndex, int bulbColor) {
		lightBulbs.put(bulbIndex, bulbColor);
		
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
	}
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
