package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.block.BlockTypeEntity;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import dev.psyGamer.immersiveTracks.util.linking.ILinkableSource;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignalControllerBlock extends BlockTypeEntity implements ILinkableSource {
	
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public SignalControllerBlock() {
		super(ImmersiveTracks.MODID, "signal_controller");
	}
	
	@Override
	public void onLink(final World world, final BlockPos source, final BlockPos target) {
		final SignalTileEntity signalTileEntity = (SignalTileEntity) world.getTileEntity(target);
		final SignalControllerTileEntity controllerTileEntity = (SignalControllerTileEntity) world.getTileEntity(source);
		
		controllerTileEntity.addSignal(signalTileEntity);
	}
	
	@Override
	public boolean isValidTarget(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getBlock() == BlockRegistry.SIGNAL;
	}
	
	@Override
	protected BlockEntity constructBlockEntity() {
		return new SignalControllerTileEntity();
	}
}
