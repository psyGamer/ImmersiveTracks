package dev.psyGamer.immersiveTracks.blocks;

import dev.psyGamer.immersiveTracks.Main;
import dev.psyGamer.immersiveTracks.init.Blocks;
import dev.psyGamer.immersiveTracks.init.Items;
import dev.psyGamer.immersiveTracks.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {
	
	public BlockBase(String name, Material material, CreativeTabs tab) {
		super(material);
		
		setRegistryName(name);
		setUnlocalizedName(name);
		
		setCreativeTab(tab);
		
		Blocks.addBlock(this);
		//Items.addBlock(this);
		Items.addItem(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModel() {
		Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
	}
}
