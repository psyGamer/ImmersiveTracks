package dev.psyGamer.immersiveTracks.gui;

import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GUIHandler implements IGuiHandler {
	
	/**
	 * Returns a Server side Container to be displayed to the user.
	 *
	 * @param ID     The Gui ID Number
	 * @param player The player viewing the Gui
	 * @param world  The current world
	 * @param x      X Position
	 * @param y      Y Position
	 * @param z      Z Position
	 * @return A GuiScreen/Container to be displayed to the user, null if none.
	 */
	@Nullable
	@Override
	public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		return null;
	}
	
	/**
	 * Returns a Container to be displayed to the user. On the client side, this
	 * needs to return a instance of GuiScreen On the server side, this needs to
	 * return a instance of Container
	 *
	 * @param ID     The Gui ID Number
	 * @param player The player viewing the Gui
	 * @param world  The current world
	 * @param x      X Position
	 * @param y      Y Position
	 * @param z      Z Position
	 * @return A GuiScreen/Container to be displayed to the user, null if none.
	 */
	@Nullable
	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		return new SignalControllerGUI((SignalControllerTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
	}
}
