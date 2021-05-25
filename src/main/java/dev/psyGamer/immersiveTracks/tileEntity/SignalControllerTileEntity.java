package dev.psyGamer.immersiveTracks.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

public class SignalControllerTileEntity extends TileEntityBase {
	
	private final HashMap<BlockPos, SignalTileEntity> connectedSignals = new HashMap<>();
	
	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
		final int[] signalPositions = new int[this.connectedSignals.size() * 3];
		
		for (int i = 0 ; i < this.connectedSignals.size() ; i++) {
			final BlockPos position = new ArrayList<>(this.connectedSignals.keySet()).get(i);
			
			signalPositions[i] = position.getX();
			signalPositions[i + 1] = position.getY();
			signalPositions[i + 2] = position.getZ();
		}
		
		compound.setIntArray("signalPositions", signalPositions);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		this.connectedSignals.clear();
		
		final int[] signalPositions = compound.getIntArray("signalPositions");
		
		for (int i = 0 ; i < signalPositions.length ; i += 3) {
			final BlockPos pos = new BlockPos(
					signalPositions[i],
					signalPositions[i + 1],
					signalPositions[i + 2]
			);
			
			if (this.getSignal(pos) != null) {
				this.connectedSignals.put(pos, this.getSignal(pos));
			}
		}
	}
	
	private SignalTileEntity getSignal(final BlockPos pos) {
		final TileEntity tileEntity = this.world.getTileEntity(pos);
		
		if (tileEntity instanceof SignalTileEntity) {
			return (SignalTileEntity) tileEntity;
		}
		
		return null;
	}
}
