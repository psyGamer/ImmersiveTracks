package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.block.BlockTypeEntity;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.world.World;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;

import net.minecraft.block.properties.PropertyBool;

public class SignalControllerBlock extends BlockTypeEntity {
	
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public SignalControllerBlock() {
		super(ImmersiveTracks.MODID, "signal_controller");
	}
	
	@Override
	protected BlockEntity constructBlockEntity() {
		return new SignalControllerTileEntity();
	}
}
