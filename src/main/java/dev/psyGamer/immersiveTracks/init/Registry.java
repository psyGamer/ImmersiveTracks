package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase;
import dev.psyGamer.immersiveTracks.util.model.IModelRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class Registry {
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.getItems());
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.getBlocks());
	}
	
	@SubscribeEvent
	public static void registerModels(final ModelRegistryEvent event) {
		for (final Item item : ModItems.getItems()) {
			if (item instanceof IModelRegistry) {
				((IModelRegistry) item).registerModel();
			}
		}
		
		for (final Block block : ModBlocks.getBlocks()) {
			if (block instanceof IModelRegistry) {
				((IModelRegistry) block).registerModel();
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockColors() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> SignalBlockBase.getBulbColor(worldIn, pos, tintIndex - 10), ModBlocks.SIGNAL);
	}
}
