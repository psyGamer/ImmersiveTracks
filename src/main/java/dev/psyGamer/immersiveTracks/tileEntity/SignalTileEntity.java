package dev.psyGamer.immersiveTracks.tileEntity;

import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.world.World;

import dev.psyGamer.immersiveTracks.block.SignalBlock;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.util.linking.LinkableBlockEntityTarget;

/**
 * TileEntity for all signal blocks. <br> <br>
 *
 * @author psyGamer
 * @version 1.0
 * @see SignalBlock SignalBlockBase
 * @since 1.0
 */
public class SignalTileEntity extends LinkableBlockEntityTarget {
	
	@Override
	public ItemStack onPick() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean isValidSource(final World world, final Vec3i pos) {
		return world.isBlock(pos, BlockRegistry.SIGNAL_CONTROLLER);
	}
	
	@Override
	public String getTranslationKey() {
		return "tile.signal.name";
	}
}
