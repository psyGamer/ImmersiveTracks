package dev.psyGamer.immersiveTracks.item;

import cam72cam.mod.block.BlockType;
import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;
import dev.psyGamer.immersiveTracks.ImmersiveTracks;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BlockItem extends CustomItem {
	
	private final BlockType block;
	private final CreativeTab tab;
	
	public BlockItem(final BlockType block, final CreativeTab tab) {
		super(ImmersiveTracks.MODID, "item_" + block.id.getPath()); // We need to add a prefix to avoid a crash (UMC Issue #60)
		
		this.block = block;
		this.tab = tab;
	}
	
	@Override
	public ClickResult onClickBlock(final Player player, final World world, final Vec3i pos, final Player.Hand hand, final Facing facing, final Vec3d inBlockPos) {
		final Optional<Vec3i> target = getPlacingPostion(world, player, pos, facing);
		
		if (!target.isPresent())
			return ClickResult.REJECTED;
		
		world.setBlock(target.get(), this.block);
		
		return ClickResult.ACCEPTED;
	}
	
	@Override
	public List<CreativeTab> getCreativeTabs() {
		return Collections.singletonList(this.tab);
	}
	
	private static Optional<Vec3i> getPlacingPostion(final World world, final Player player, final Vec3i pos, final Facing facing) {
		final Vec3i target = world.isReplaceable(pos) ? pos : pos.offset(facing);
		
		if (isStandingInBlock(player.getBlockPosition().subtract(target)))
			return Optional.empty();
		
		if (world.isAir(target) || world.isReplaceable(target))
			return Optional.of(target);
		
		return Optional.empty();
	}
	
	private static boolean isStandingInBlock(final Vec3i pos) {
		return pos.x == 0 && pos.z == 0 && (pos.y == 0 || pos.y == -1);
	}
}
