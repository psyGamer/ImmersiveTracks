package dev.psyGamer.immersiveTracks.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

import net.minecraft.tileentity.TileEntity;

/**
 * A base class for all TileEntities. <br>
 * It implements all needed methods. <br>
 * Note: You still need to register it in {@link dev.psyGamer.immersiveTracks.init.TileEntities TileEntities} <br> <br>
 *
 * @author psyGamer
 * @version 1.0
 * @since 1.0
 * @see TileEntity
 */
public class TileEntityBase extends TileEntity {
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(super.getUpdateTag());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	@Override
	public NBTTagCompound getTileData() {
		return this.writeToNBT(new NBTTagCompound());
	}
}
