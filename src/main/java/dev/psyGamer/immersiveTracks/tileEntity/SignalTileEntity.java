package dev.psyGamer.immersiveTracks.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SignalTileEntity extends TileEntity {
	
	private final Map<Integer, Integer> lightBulbs = new HashMap<>();
	
	public void setBulbColor(int bulbIndex, int bulbColor) {
		lightBulbs.put(bulbIndex, bulbColor);
		
		markDirty();
		world.markBlockRangeForRenderUpdate(getPos(), getPos());
	}
	public int getBulbColor(int bulbIndex) {
		if (!lightBulbs.containsKey(bulbIndex))
			return 0x00000;
		
		return lightBulbs.get(bulbIndex);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("bulbs", lightBulbs.size());
		
		lightBulbs.forEach((index, color) -> {
			compound.setInteger("bulb_" + index, color);
		});
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int bulbs = compound.getInteger("bulbs");
		
		for (int i = 0; i < bulbs; i++){
			lightBulbs.put(i, compound.getInteger("bulb_" + i));
		}
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(super.getUpdateTag());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}
	
	@Nullable
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
