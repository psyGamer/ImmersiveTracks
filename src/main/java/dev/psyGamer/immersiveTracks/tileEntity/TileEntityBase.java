package dev.psyGamer.immersiveTracks.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {
	
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
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
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
