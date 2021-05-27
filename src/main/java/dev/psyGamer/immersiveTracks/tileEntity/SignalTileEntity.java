package dev.psyGamer.immersiveTracks.tileEntity;

import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

/**
 * TileEntity for all signal blocks. <br> <br>
 *
 * @author psyGamer
 * @version 1.0
 * @see dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase SignalBlockBase
 * @since 1.0
 */
public class SignalTileEntity extends TileEntityBase {
	
	private final Map<Integer, Integer> lightBulbs = new HashMap<>();
	
	/**
	 * Sets the bulb color. <br>
	 * Note: Will cause an block update. <br> <br>
	 *
	 * @param bulbIndex The index of the bulb.
	 * @param bulbColor The color of the bulb in hex. (e.g. 0x00FF00)
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public void setBulbColor(final int bulbIndex, final int bulbColor) {
		this.lightBulbs.put(bulbIndex, bulbColor);
	}
	
	/**
	 * Gets the bulb color. <br> <br>
	 *
	 * @param bulbIndex The index of the bulb.
	 * @return The color of the bulb.
	 * @author psyGamer
	 * @version 1.0
	 * @since 1.0
	 */
	public int getBulbColor(final int bulbIndex) {
		if (!this.lightBulbs.containsKey(bulbIndex)) {
			return 0x00000;
		}
		
		return this.lightBulbs.get(bulbIndex);
	}
	
	public void markForUpdate() {
		final IBlockState oldState = this.world.getBlockState(this.pos);
		final IBlockState newState = oldState.withProperty(SignalBlockBase.UPDATE,
				!oldState.getValue(SignalBlockBase.UPDATE));
		
		System.out.println(oldState);
		System.out.println(newState);
		
		this.markDirty();
		this.world.setBlockState(this.pos, newState);
		this.world.scheduleUpdate(this.pos, BlockRegistry.SIGNAL, 10);
	}
	
	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
		compound.setInteger("bulbs", this.lightBulbs.size());
		
		this.lightBulbs.forEach((index, color) -> {
			compound.setInteger("bulb_" + index, color);
		});
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int bulbs = compound.getInteger("bulbs");
		
		for (int i = 0 ; i < bulbs ; i++) {
			this.lightBulbs.put(i, compound.getInteger("bulb_" + i));
		}
	}
}
