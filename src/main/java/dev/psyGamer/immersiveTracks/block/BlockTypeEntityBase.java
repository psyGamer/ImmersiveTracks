package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockTypeEntity;
import cam72cam.mod.entity.Player;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.resource.Identifier;
import cam72cam.mod.sound.Audio;
import cam72cam.mod.sound.ISound;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;
import dev.psyGamer.immersiveTracks.ImmersiveTracks;

public abstract class BlockTypeEntityBase extends BlockTypeEntity {
	
	public BlockTypeEntityBase(final String name) {
		super(ImmersiveTracks.MODID, name);
	}
	
	public void onPlace(final Player player, final World world, final Vec3i pos, final Player.Hand hand, final Facing facing) {
	}
	
	public ISound getPlaceSound() {
		return Audio.newSound(new Identifier("sounds/dig/stone3.ogg"), false, 15, 1);
	}
}
