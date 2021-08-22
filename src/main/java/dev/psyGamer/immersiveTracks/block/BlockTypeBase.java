package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockType;
import cam72cam.mod.entity.Player;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;
import dev.psyGamer.immersiveTracks.ImmersiveTracks;

public class BlockTypeBase extends BlockType {
	
	public BlockTypeBase(final String name) {
		super(ImmersiveTracks.MODID, name);
	}
	
	@Override
	public boolean tryBreak(final World world, final Vec3i pos, final Player player) {
		return true;
	}
	
	public void onPlace(final Player player, final World world, final Vec3i pos, final Player.Hand hand, final Facing facing) {
	}
	
	@Override
	public void onBreak(final World world, final Vec3i pos) {
	}
	
	@Override
	public boolean onClick(final World world, final Vec3i pos, final Player player, final Player.Hand hand, final Facing facing, final Vec3d hit) {
		return false;
	}
	
	@Override
	public ItemStack onPick(final World world, final Vec3i pos) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void onNeighborChange(final World world, final Vec3i pos, final Vec3i neighbor) {
	}
}
