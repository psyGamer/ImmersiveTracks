package dev.psyGamer.immersiveTracks.tileEntity;

import dev.psyGamer.immersiveTracks.blocks.SignalBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class SignalTileEntity extends TileEntity {
	
	private boolean active = false;
	
	public boolean isActive() {
		return active;
	}
	
	public void toggleActive() {
		active = !active;
		
		markDirty();
//		shouldRefresh(world, getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()));
	}
	
	public float getYRotation() {
		IBlockState state = world.getBlockState(getPos());
		
		if (!(state.getBlock() instanceof SignalBlock)) {
			return 0;
		}
		
		switch(state.getValue(SignalBlock.FACING)) {
			case EAST:
				return 270;
			case SOUTH:
				return 180;
			case WEST:
				return 90;
			case NORTH:
			default:
				return 0;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("active", active);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		active = compound.getBoolean("active");
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
