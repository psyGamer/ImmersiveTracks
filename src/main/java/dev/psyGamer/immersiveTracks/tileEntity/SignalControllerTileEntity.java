package dev.psyGamer.immersiveTracks.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class SignalControllerTileEntity extends TileEntityBase {
	
	private final Map<BlockPos, SignalTileEntity> connectedSignals = new HashMap();
	
	public void addSignal(final SignalTileEntity tileEntity) {
		this.connectedSignals.put(tileEntity.getPos(), tileEntity);
		this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), Constants.BlockFlags.DEFAULT);
		this.markDirty();
	}
	
	public void updateSignals(final boolean active) {
		this.connectedSignals.forEach((position, signal) -> {
			if (signal == null) {
				signal = (SignalTileEntity) this.world.getTileEntity(position);
				
				this.connectedSignals.put(position, signal);
				
				if (signal == null) {
					return;
				}
			}
			
			signal.setBulbColor(0, active ? 0x222222 : 0xEE0000);
			signal.setBulbColor(1, active ? 0x00EE00 : 0x222222);
			
			signal.markForUpdate();
		});
	}
	
	public List<SignalTileEntity> getConnectedSignals() {
		return this.connectedSignals.values().stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
	
	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
		final int[] signalPositions = new int[this.connectedSignals.size() * 3];
		
		for (int i = 0 ; i < this.connectedSignals.size() ; i++) {
			final BlockPos position = new ArrayList<>(this.connectedSignals.keySet()).get(i);
			signalPositions[i * 3] = position.getX();
			signalPositions[i * 3 + 1] = position.getY();
			signalPositions[i * 3 + 2] = position.getZ();
		}
		
		compound.setIntArray("signalPositions", signalPositions);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int[] signalPositions = compound.getIntArray("signalPositions");
		
		for (int i = 0 ; i < signalPositions.length ; i += 3) {
			final BlockPos position = new BlockPos(
					signalPositions[i],
					signalPositions[i + 1],
					signalPositions[i + 2]
			);
			
			this.connectedSignals.put(position, this.world == null ? null : (SignalTileEntity) this.world.getTileEntity(position));
		}
	}
}
