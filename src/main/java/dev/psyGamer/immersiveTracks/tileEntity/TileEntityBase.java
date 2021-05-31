package dev.psyGamer.immersiveTracks.tileEntity;

import dev.psyGamer.immersiveTracks.registry.TileEntiyRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A base class for all TileEntities. <br>
 * It implements all needed methods. <br>
 * Note: You still need to register it in {@link TileEntiyRegistry TileEntities} <br> <br>
 *
 * @author psyGamer
 * @version 1.0
 * @see TileEntity
 * @since 1.0
 */
public class TileEntityBase extends TileEntity {
	
	@Override
	public boolean shouldRefresh(final World world, final BlockPos pos, final IBlockState oldState, final IBlockState newState /* Not newSate */) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(super.getUpdateTag());
	}
	
	@Override
	public void handleUpdateTag(final NBTTagCompound tag) {
		this.readFromNBT(tag);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(), 0, this.getUpdateTag());
	}
	
	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		this.handleUpdateTag(pkt.getNbtCompound());
	}
	
	@Override
	public NBTTagCompound getTileData() {
		return this.writeToNBT(new NBTTagCompound());
	}
}
