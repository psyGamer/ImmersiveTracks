package dev.psyGamer.immersiveTracks.blocks;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.util.IModelRegistry;
import dev.psyGamer.immersiveTracks.init.Items;
import dev.psyGamer.immersiveTracks.init.Blocks;

import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * A base class for all Blocks. <br>
 * Implements all needed methods and registers itself. <br> <br>
 *
 * @author psyGamer
 * @version 1.0
 * @since 1.0
 * @see Block
 */
public class BlockBase extends Block implements IModelRegistry {
	
	public BlockBase(String name, Material material, CreativeTabs tab) {
		super(material);
		
		setRegistryName(name);
		setUnlocalizedName(ImmersiveTracks.MODID + "." + name);
		
		setCreativeTab(tab);
		
		Blocks.addBlock(this); // <br> <br>
		Items .addBlock(this);
	}
	
	@Override
	public void registerModel() {
		ImmersiveTracks.getProxy().registerModel(Item.getItemFromBlock(this), 0);
	}
}
