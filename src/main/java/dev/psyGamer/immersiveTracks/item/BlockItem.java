package dev.psyGamer.immersiveTracks.item;

import cam72cam.mod.block.BlockType;
import cam72cam.mod.entity.Player;
import cam72cam.mod.entity.boundingbox.DefaultBoundingBox;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.resource.Identifier;
import cam72cam.mod.sound.Audio;
import cam72cam.mod.sound.ISound;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.block.BlockTypeBase;
import dev.psyGamer.immersiveTracks.block.BlockTypeEntityBase;
import dev.psyGamer.immersiveTracks.registry.CreativeTabRegistry;

import java.util.Collections;
import java.util.List;

public class BlockItem extends CustomItem {
	
	private final BlockType block;
	
	public BlockItem(final BlockType block) {
		super(ImmersiveTracks.MODID, "item_" + block.id.getPath()); // We need to add a prefix to avoid a crash (UMC Issue #60)
		
		this.block = block;
	}
	
	@Override
	public ClickResult onClickBlock(final Player player, final World world, final Vec3i pos, final Player.Hand hand, final Facing facing, final Vec3d inBlockPos) {
		final Vec3i target = world.isReplaceable(pos) ? pos : pos.offset(facing);
		final ItemStack heldItemStack = player.getHeldItem(hand);
		
		if (DefaultBoundingBox.BLOCK.offset(new Vec3d(target)).intersects(player.getBounds()))
			return ClickResult.PASS;
		
		world.setBlock(target, this.block);
		heldItemStack.shrink(1);
		
		if (this.block instanceof BlockTypeBase)
			((BlockTypeBase) this.block).onPlace(player, world, pos, hand, facing);
		else if (this.block instanceof BlockTypeEntityBase)
			((BlockTypeEntityBase) this.block).onPlace(player, world, pos, hand, facing);
		
		if (world.isServer)
			return ClickResult.ACCEPTED;
		
		final ISound placeSound;
		
		if (this.block instanceof BlockTypeBase)
			placeSound = ((BlockTypeBase) this.block).getPlaceSound();
		else if (this.block instanceof BlockTypeEntityBase)
			placeSound = ((BlockTypeEntityBase) this.block).getPlaceSound();
		else
			placeSound = Audio.newSound(new Identifier("sounds/dig/stone3.ogg"), false, 15, 1);
		
		placeSound.play(new Vec3d(pos));
		
		return ClickResult.ACCEPTED;
	}
	
	@Override
	public List<CreativeTab> getCreativeTabs() {
		return Collections.singletonList(CreativeTabRegistry.SIGNALS_TAB);
	}
}
