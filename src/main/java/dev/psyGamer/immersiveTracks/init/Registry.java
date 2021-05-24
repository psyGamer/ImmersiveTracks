package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.util.IModelRegistry;
import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase;

import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;

@Mod.EventBusSubscriber
public class Registry {
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(Items.getItems());
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(Blocks.getBlocks());
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (Item item : Items.getItems()) {
			if (item instanceof IModelRegistry) {
				((IModelRegistry) item).registerModel();
			}
		}
		
		for (Block block : Blocks.getBlocks()) {
			if (block instanceof IModelRegistry) {
				((IModelRegistry) block).registerModel();
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockColors() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> SignalBlockBase.getBulbColor(worldIn, pos, tintIndex - 10), Blocks.SIGNAL);
	}
}
