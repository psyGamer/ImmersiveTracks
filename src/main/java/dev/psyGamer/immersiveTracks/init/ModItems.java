package dev.psyGamer.immersiveTracks.init;

import dev.psyGamer.immersiveTracks.items.LinkerItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
	private static final List<Item> items = new ArrayList<>();
	
	public static void addBlock(final Block block) {
		ModItems.addItem(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	
	public static void addItem(final Item item) {
		ModItems.items.add(item);
	}
	
	public static Item[] getItems() {
		return ModItems.items.toArray(new Item[0]);
	}
	
	public static final Item LINKER = new LinkerItem("linker");
}
