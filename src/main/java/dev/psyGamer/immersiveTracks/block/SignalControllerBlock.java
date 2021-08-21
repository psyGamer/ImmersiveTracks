package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.block.BlockTypeEntity;

import cam72cam.mod.math.Vec3i;
import cam72cam.mod.world.World;
import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import dev.psyGamer.immersiveTracks.util.linking.ILinkableSource;

import net.minecraft.block.properties.PropertyBool;

public class SignalControllerBlock extends BlockTypeEntity implements ILinkableSource {
	
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public SignalControllerBlock() {
		super(ImmersiveTracks.MODID, "signal_controller");
	}
	
	@Override
	public void onLink(final World world, final Vec3i source, final Vec3i target) {
		final SignalTileEntity signalTileEntity = world.getBlockEntity(target, SignalTileEntity.class);
		final SignalControllerTileEntity controllerTileEntity = world.getBlockEntity(source, SignalControllerTileEntity.class);
		
		controllerTileEntity.addSignal(signalTileEntity);
	}
	
	@Override
	public boolean isValidTarget(final World world, final Vec3i pos) {
		return world.isBlock(pos, BlockRegistry.SIGNAL);
	}
	
	@Override
	protected BlockEntity constructBlockEntity() {
		return new SignalControllerTileEntity();
	}
}
