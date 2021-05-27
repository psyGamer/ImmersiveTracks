package dev.psyGamer.immersiveTracks.blocks;

import dev.psyGamer.immersiveTracks.ImmersiveTracks;
import dev.psyGamer.immersiveTracks.init.BlockRegistry;
import dev.psyGamer.immersiveTracks.init.ItemRegistry;
import dev.psyGamer.immersiveTracks.util.model.IModelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * A base class for all Blocks. <br>
 * Implements all needed methods and registers itself. <br> <br>
 *
 * @author psyGamer
 * @version 1.0
 * @see Block
 * @since 1.0
 */
public class BlockBase extends Block implements IModelRegistry {
	
	public BlockBase(final String name, final Material material, final CreativeTabs tab) {
		super(material);
		
		this.setRegistryName(name);
		this.setUnlocalizedName(ImmersiveTracks.MODID + "." + name);
		
		this.setCreativeTab(tab);
		
		BlockRegistry.registerBlock(this);
		ItemRegistry.registerItemBlock(this);
	}
	
	@Override
	public void registerModel() {
		ImmersiveTracks.getProxy().registerModel(Item.getItemFromBlock(this), 0);
	}
}
