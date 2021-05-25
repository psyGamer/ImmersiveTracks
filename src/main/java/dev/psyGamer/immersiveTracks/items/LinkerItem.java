package dev.psyGamer.immersiveTracks.items;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.util.NBTUtil;
import dev.psyGamer.immersiveTracks.util.linking.ILinkableSource;
import dev.psyGamer.immersiveTracks.util.linking.ILinkableTarget;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class LinkerItem extends ItemBase {
	
	public LinkerItem(final String name) {
		super(name, ImmersiveTracks.SIGNALS_TAB);
		
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
		if (world.isRemote && player.getHeldItem(hand).getItem() == this) {
			final Block block = world.getBlockState(pos).getBlock();
			
			NBTTagCompound tag = player.getHeldItem(hand).getTagCompound();
			
			if (tag == null) {
				tag = new NBTTagCompound();
			}
			
			if (block instanceof ILinkableSource) {
				NBTUtil.setBlockPosition(tag, "source", pos);
				tag.setInteger("sourceWorldID", world.provider.getDimension());
				LinkerItem.sendMessageInActionBar(player, "§7Linking " + block.getLocalizedName() + "...");
			}
			if (block instanceof ILinkableTarget && tag.hasKey("source")) {
				if (tag.getInteger("sourceWorldID") != world.provider.getDimension()) {
					LinkerItem.sendMessageInActionBar(player, "§cYou can't link over dimensions");
					
					return EnumActionResult.SUCCESS;
				}
				
				final ILinkableSource source = LinkerItem.getSource(world, NBTUtil.getBlockPosition(tag, "source"));
				final ILinkableTarget target = LinkerItem.getTarget(world, pos);
				
				if (source == null || target == null) {
					LinkerItem.sendMessageInActionBar(player, "§cAn error occurred, please try again");
					
					if (player.getHeldItem(hand).getItem() == this) {
						player.getHeldItem(hand).setTagCompound(null);
					}
					
					return EnumActionResult.SUCCESS;
				}
				
				if (!(source.isValidTarget(world, pos) || target.isValidSource(world, NBTUtil.getBlockPosition(tag, "source")))) {
					LinkerItem.sendMessageInActionBar(player, "§cCan't link " + ((Block) source).getLocalizedName() + " with " + ((Block) target).getLocalizedName());
					
					return EnumActionResult.SUCCESS;
				}
				
				source.onLinkAsSource(world, pos);
				target.onLinkAsTarget(world, NBTUtil.getBlockPosition(tag, "source"));
				
				LinkerItem.sendMessageInActionBar(player, "§aSuccessfully linked " + ((Block) source).getLocalizedName() + " with " + ((Block) target).getLocalizedName());
				
				return EnumActionResult.SUCCESS;
			}
			
			LinkerItem.sendMessageInActionBar(player, "§cYou must link the source first");
			
			if (!(block instanceof ILinkableSource || block instanceof ILinkableTarget)) {
				return EnumActionResult.FAIL;
			}
		}
		
		return EnumActionResult.SUCCESS;
	}
	
	private static void sendMessageInActionBar(final EntityPlayer player, final String message) {
		player.sendStatusMessage(new TextComponentString(message), true);
	}
	
	private static ILinkableSource getSource(final World world, final BlockPos pos) {
		if (!world.isBlockLoaded(pos)) {
			return null;
		}
		
		final Block sourceBlock = world.getBlockState(pos).getBlock();
		
		if (!(sourceBlock instanceof ILinkableSource)) {
			return null;
		}
		
		return (ILinkableSource) sourceBlock;
	}
	
	private static ILinkableTarget getTarget(final World world, final BlockPos pos) {
		if (!world.isBlockLoaded(pos)) {
			return null;
		}
		
		final Block targetBlock = world.getBlockState(pos).getBlock();
		
		if (!(targetBlock instanceof ILinkableTarget)) {
			return null;
		}
		
		return (ILinkableTarget) targetBlock;
	}
}