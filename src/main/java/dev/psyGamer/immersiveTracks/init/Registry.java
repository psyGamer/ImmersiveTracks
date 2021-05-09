package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;

import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlockBase;
import dev.psyGamer.immersiveTracks.util.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.block.Block;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class Registry {
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		System.out.println("ITEMS");
		event.getRegistry().registerAll(Items.getItems());
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		System.out.println("BLOCKS");
		event.getRegistry().registerAll(Blocks.getBlocks());
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		System.out.println("MODELS");
		
		for (Item item : Items.getItems()) {
			if (item instanceof IHasModel) {
				((IHasModel) item).registerModel();
			}
		}
		
		for (Block block : Blocks.getBlocks()) {
			if (block instanceof IHasModel) {
				((IHasModel) block).registerModel();
			}
		}
	}
	
	@SubscribeEvent
	public static void registerTextures(TextureStitchEvent.Pre event) {
		if (!event.getMap().getBasePath().equals("textures")) {
			return;
		}
		
		event.getMap().registerSprite(new ResourceLocation(ImmersiveTracks.MODID, "blocks/bulb"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockColors() {
		System.out.println("BLOCK COLORS");
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> SignalBlockBase.getBulbColor(worldIn, pos, tintIndex - 10), Blocks.SIGNAL);
	}
}
