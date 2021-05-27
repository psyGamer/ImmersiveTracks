package dev.psyGamer.immersiveTracks.registry;

import dev.psyGamer.immersiveTracks.items.LinkerItem;
import dev.psyGamer.immersiveTracks.util.model.IModelRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemRegistry {
	private static final List<Item> items = new ArrayList<>();
	
	public static final Item LINKER = new LinkerItem("linker");
	
	public static void registerItem(final Item item) {
		ItemRegistry.items.add(item);
	}
	
	public static void registerItemBlock(final Block block) {
		ItemRegistry.registerItem(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemRegistry.items.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void registerItemModels(final ModelRegistryEvent event) {
		for (final Item item : ItemRegistry.items) {
			if (item instanceof IModelRegistry) {
				((IModelRegistry) item).registerModel();
			}
		}
	}
}
