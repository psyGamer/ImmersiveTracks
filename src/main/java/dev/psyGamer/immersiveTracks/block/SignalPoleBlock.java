package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockType;
import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;

public class SignalPoleBlock extends BlockType {
	
	public SignalPoleBlock(final String name) {
		super(ImmersiveTracks.MODID, name);
	}
	
	@Override
	public boolean tryBreak(final World world, final Vec3i pos, final Player player) {
		return false;
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
