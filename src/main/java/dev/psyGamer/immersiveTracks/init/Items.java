package dev.psyGamer.immersiveTracks.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Items {
	private static final List<Item> items = new ArrayList<>();
	
	public static void addBlock(Block block) {
		Items.addItem(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	public static void addItem(Item item) {
		items.add(item);
	}
	public static Item[] getItems() {
		return items.toArray(new Item[0]);
	}
}
