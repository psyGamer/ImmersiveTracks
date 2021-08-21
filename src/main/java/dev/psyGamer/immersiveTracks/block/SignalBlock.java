package dev.psyGamer.immersiveTracks.block;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.block.BlockTypeEntity;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.registry.BlockRegistry;
import dev.psyGamer.immersiveTracks.signals.SignalPreset;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import dev.psyGamer.immersiveTracks.util.Pair;
import dev.psyGamer.immersiveTracks.util.linking.ILinkableTarget;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.*;

public class SignalBlock extends BlockTypeEntity implements ILinkableTarget {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool UPDATE = PropertyBool.create("update"); // TODO don't
	
	private static final List<SignalBlock> SIGNAL_BLOCKS = new ArrayList<>();
	private final Map<String, List<Integer>> bulbColors = new HashMap<>();
	private final Map<Integer, String> colorNames = new HashMap<>();
	
	public static int getTintColor(final IBlockAccess world, final BlockPos pos, final int bulbIndex) {
		if (bulbIndex < 0 || !(world.getTileEntity(pos) instanceof SignalTileEntity)) {
			return 0xFFFFFF;
		}
		
		//noinspection ConstantConditions
		return ((SignalTileEntity) world.getTileEntity(pos)).getBulbColor(bulbIndex);
	}
	
	public static List<SignalBlock> getSignalBlocks() {
		return Collections.unmodifiableList(SignalBlock.SIGNAL_BLOCKS);
	}
	
	public Map<String, List<Integer>> getBulbColors() {
		return Collections.unmodifiableMap(this.bulbColors);
	}
	
	public String getColorName(final int color) {
		return this.colorNames.get(color);
	}
	
	@SuppressWarnings("unchecked")
	public SignalBlock(final String name, final Pair<String, Map<Integer, String>>... bulbData) {
//		super(name, Material.IRON, CreativeTabRegistry.SIGNALS_TAB, new AdvancedBoundingBox(12, 16, 2).center());
		super(ImmersiveTracks.MODID, name);
		
		if (bulbData == null) {
			throw new IllegalArgumentException("Bulb data may not be null");
		}
		
		for (final Pair<String, Map<Integer, String>> data : bulbData) {
			if (data.first().isEmpty()) {
				throw new IllegalArgumentException("Bulb data may not contain an empty string");
			}
			if (data.second().isEmpty()) {
				throw new IllegalArgumentException("Bulb data may not contain an empty color list");
			}
			
			this.bulbColors.put(data.first(), new ArrayList<>(data.second().keySet()));
			this.colorNames.putAll(data.second());
		}
		
		SignalBlock.SIGNAL_BLOCKS.add(this);
		
		new SignalPreset("Default", this);
	}
	
	@Override
	public boolean isValidSource(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getBlock() == BlockRegistry.SIGNAL_CONTROLLER;
	}
	
	@Override
	protected BlockEntity constructBlockEntity() {
		return new SignalTileEntity();
	}
}
