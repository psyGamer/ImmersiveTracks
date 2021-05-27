package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.blocks.ModelBlockBase;
import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase;
import dev.psyGamer.immersiveTracks.blocks.signal.SignalControllerBlock;
import dev.psyGamer.immersiveTracks.util.model.AdvancedBoundingBox;
import dev.psyGamer.immersiveTracks.util.model.IModelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class BlockRegistry {
	
	private static final List<Block> blocks = new ArrayList<>();
	
	public static final Block SIGNAL = new SignalBlockBase("signal");
	public static final Block SIGNAL_POLE = new ModelBlockBase("signal_pole", Material.IRON, ImmersiveTracks.SIGNALS_TAB,
			new AdvancedBoundingBox(2, 16, 2).center()
	);
	public static final Block SIGNAL_CONTROLLER = new SignalControllerBlock();
	
	public static void registerBlock(final Block block) {
		BlockRegistry.blocks.add(block);
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlockRegistry.blocks.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void registerBlockModels(final ModelRegistryEvent event) {
		for (final Block block : BlockRegistry.blocks) {
			if (block instanceof IModelRegistry) {
				((IModelRegistry) block).registerModel();
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockColors() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> SignalBlockBase.getBulbColor(worldIn, pos, tintIndex - 10), BlockRegistry.SIGNAL);
	}
}
