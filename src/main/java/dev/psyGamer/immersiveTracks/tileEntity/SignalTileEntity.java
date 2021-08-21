package dev.psyGamer.immersiveTracks.tileEntity;

import cam72cam.mod.math.Vec3i;
import cam72cam.mod.world.World;
import dev.psyGamer.immersiveTracks.block.SignalBlock;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.serialization.SerializationException;
import cam72cam.mod.serialization.TagCompound;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.util.linking.LinkableBlockEntityTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * TileEntity for all signal blocks. <br> <br>
 *
 * @author psyGamer
 * @version 1.0
 * @see SignalBlock SignalBlockBase
 * @since 1.0
 */
public class SignalTileEntity extends LinkableBlockEntityTarget {
	
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
		this.markDirty();
		this.getWorld().setBlock(this.getPos(), this.getWorld().getBlock(this.getPos()));
	}
	
	@Override
	public void save(final TagCompound compound) throws SerializationException {
		compound.setInteger("bulbs", this.lightBulbs.size());
		this.lightBulbs.forEach((element, index) -> compound.setInteger("bulb" + index, element));
		
		super.save(compound);
	}
	
	@Override
	public void load(final TagCompound compound) throws SerializationException {
		super.load(compound);
		
		for (int i = 0 ; i < compound.getInteger("bulbs") ; i++) {
			this.lightBulbs.put(i, compound.getInteger("bulb" + i));
		}
	}
	
	@Override
	public ItemStack onPick() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean isValidSource(final World world, final Vec3i pos) {
		return world.isBlock(pos, BlockRegistry.SIGNAL_CONTROLLER);
	}
}
