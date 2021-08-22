package dev.psyGamer.immersiveTracks.tileEntity;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.serialization.SerializationException;
import cam72cam.mod.serialization.TagCompound;

import cam72cam.mod.world.World;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.util.linking.LinkableBlockEntitySource;
import dev.psyGamer.immersiveTracks.util.linking.LinkableBlockEntityTarget;

import java.util.*;
import java.util.stream.Collectors;

public class SignalControllerTileEntity extends LinkableBlockEntitySource {
	
	private final Map<Vec3i, SignalTileEntity> connectedSignals = new HashMap();
	
	public void addSignal(final SignalTileEntity tileEntity) {
		this.connectedSignals.put(tileEntity.getPos(), tileEntity);
		
		this.getWorld().notifyNeighborsOfStateChange(
				this.getPos(), BlockRegistry.SIGNAL_CONTROLLER, false
		);
		this.markDirty();
	}
	
	public void updateSignals(final boolean active) {
		this.connectedSignals.forEach((position, signal) -> {
			if (signal == null) {
				signal = this.getWorld().getBlockEntity(position, SignalTileEntity.class);
				
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
	public void save(final TagCompound compound) throws SerializationException {
		final int[] signalPositions = new int[this.connectedSignals.size() * 3];
		
		for (int i = 0 ; i < this.connectedSignals.size() ; i++) {
			final Vec3i position = new ArrayList<>(this.connectedSignals.keySet()).get(i);
			
			signalPositions[i * 3] = position.x;
			signalPositions[i * 3 + 1] = position.y;
			signalPositions[i * 3 + 2] = position.z;
		}
		
		compound.setInteger("signalPositions", signalPositions.length);
		for (int i = 0 ; i < signalPositions.length ; i++) {
			compound.setInteger("signalPosition" + i, signalPositions[i]);
		}
		
		super.save(compound);
	}
	
	@Override
	public void load(final TagCompound compound) throws SerializationException {
		super.load(compound);
		
		for (int i = 0 ; i < compound.getInteger("signalPositions") ; i += 3) {
			final Vec3i position = new Vec3i(
					compound.getInteger("signalPostion" + (i)),
					compound.getInteger("signalPostion" + (i + 1)),
					compound.getInteger("signalPostion" + (i + 2))
			);
			
			this.connectedSignals.put(position, this.getWorld() == null ? null : this.getWorld().getBlockEntity(position, SignalTileEntity.class));
		}
	}
	
	@Override
	public ItemStack onPick() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void onLink(final World world, final LinkableBlockEntityTarget target) {
		this.addSignal((SignalTileEntity) target);
	}
	
	@Override
	public boolean isValidTarget(final World world, final Vec3i pos) {
		return world.isBlock(pos, BlockRegistry.SIGNAL);
	}
	
	@Override
	public String getTranslationKey() {
		return "tile.signal_controller.name";
	}
}
