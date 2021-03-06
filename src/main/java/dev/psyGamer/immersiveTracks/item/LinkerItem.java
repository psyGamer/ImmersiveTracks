package dev.psyGamer.immersiveTracks.item;

import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.serialization.TagCompound;
import cam72cam.mod.text.PlayerMessage;
import cam72cam.mod.text.TextColor;
import cam72cam.mod.text.TextUtil;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.ModConfig;
import dev.psyGamer.immersiveTracks.registry.CreativeTabRegistry;
import dev.psyGamer.immersiveTracks.util.linking.LinkableBlockEntitySource;
import dev.psyGamer.immersiveTracks.util.linking.LinkableBlockEntityTarget;

import java.util.Collections;
import java.util.List;

public class LinkerItem extends CustomItem {
	
	public LinkerItem(final String name) {
		super(ImmersiveTracks.MODID, name);
	}
	
	@Override
	public ClickResult onClickBlock(final Player player, final World world, final Vec3i pos, final Player.Hand hand, final Facing facing, final Vec3d inBlockPos) {
		if (world.isServer) {
			if (player.isCrouching()) {
				LinkerItem.sendMessage(player, TextColor.GRAY + "Stopped linking");
				
				player.getHeldItem(hand).setTagCompound(null);
			}
			
			TagCompound tag = player.getHeldItem(hand).getTagCompound();
			
			if (tag == null) {
				tag = new TagCompound();
			}
			
			final LinkableBlockEntityTarget targetTileEntity = world.getBlockEntity(pos, LinkableBlockEntityTarget.class);
			final LinkableBlockEntitySource sourceTileEntity = world.getBlockEntity(pos, LinkableBlockEntitySource.class);
			
			final String targetName = targetTileEntity == null ? "" : TextUtil.translate(targetTileEntity.getTranslationKey());
			final String sourceName = sourceTileEntity == null ? "" : TextUtil.translate(sourceTileEntity.getTranslationKey());
			
			if (targetTileEntity != null) {
				tag.setVec3i("source", pos);
				tag.setInteger("sourceWorldID", world.getId());
				
				LinkerItem.sendMessage(player, TextColor.GRAY + "Linking " + sourceName + "...");
			} else if (sourceTileEntity != null && tag.hasKey("source")) {
				if (tag.getInteger("sourceWorldID") != world.getId()) {
					LinkerItem.sendMessage(player, TextColor.RED + "You can't link over dimensions");
					
					return ClickResult.ACCEPTED;
				}
				
				final Vec3i sourcePos = tag.getVec3i("source");
				
				final LinkableBlockEntitySource source = world.getBlockEntity(sourcePos, LinkableBlockEntitySource.class);
				final LinkableBlockEntityTarget target = world.getBlockEntity(pos, LinkableBlockEntityTarget.class);
				
				if (source == null || target == null) {
					LinkerItem.sendMessage(player, TextColor.RED + "An error occurred, please try again");
					
					if (player.getHeldItem(hand).is(this)) {
						player.getHeldItem(hand).setTagCompound(null);
					}
					
					return ClickResult.ACCEPTED;
				}
				
				if (!(source.isValidTarget(world, pos) || target.isValidSource(world, sourcePos))) {
					LinkerItem.sendMessage(player, TextColor.RED + "Can't link " + sourceName + " with " + targetName);
					
					return ClickResult.ACCEPTED;
				}
				
				if (LinkerItem.getDistance(sourcePos, pos) > ModConfig.maxLinkingDistance) {
					LinkerItem.sendMessage(player, TextColor.RED + "Blocks are too far apart (Maximum linking distance: " + ModConfig.maxLinkingDistance + ")");
					
					return ClickResult.ACCEPTED;
				}
				
				source.onLink(world, target);
				
				LinkerItem.sendMessage(player, TextColor.GREEN + "Successfully linked " + targetName + " with " + sourceName);
				
				return ClickResult.ACCEPTED;
			} else if (sourceTileEntity != null) {
				LinkerItem.sendMessage(player, TextColor.RED + "You must link the source first");
			}
			
			if (targetTileEntity == null && sourceTileEntity == null) {
				return ClickResult.PASS;
			}
			
			player.getHeldItem(hand).setTagCompound(tag);
		}
		
		return ClickResult.ACCEPTED;
	}
	
	@Override
	public int getStackSize() {
		return 1;
	}
	
	@Override
	public List<CreativeTab> getCreativeTabs() {
		return Collections.singletonList(CreativeTabRegistry.SIGNALS_TAB);
	}
	
	private static double getDistance(final Vec3i from, final Vec3i to) {
		return Math.sqrt(
				(from.x - to.x) * (from.x - to.x) +
						(from.y - to.y) * (from.y - to.y) +
						(from.z - to.z) * (from.z - to.z)
		);
	}
	
	private static void sendMessage(final Player player, final String message) {
		player.sendMessage(PlayerMessage.direct(message)); // UMC doesn't support action bar messages
	}
}
