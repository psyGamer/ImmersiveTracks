package dev.psyGamer.immersiveTracks.gui;

import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlock;
import dev.psyGamer.immersiveTracks.signals.SignalPreset;
import dev.psyGamer.immersiveTracks.tileEntity.SignalControllerTileEntity;
import dev.psyGamer.immersiveTracks.tileEntity.SignalTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignalControllerGUI extends GuiScreen {
	
	private final SignalControllerTileEntity signalController;
	
	private SignalBlock currentSignal;
	private SignalPreset currentPreset;
	private final Map<SignalBlock, String> signalBlockNames = new HashMap<>();
	
	public SignalControllerGUI(final SignalControllerTileEntity signalController) {
		this.signalController = signalController;
		
		for (final SignalTileEntity connectedSignal : signalController.getConnectedSignals()) {
			final Block block = connectedSignal.getBlockType();
			
			if (block instanceof SignalBlock) {
				this.currentSignal = (SignalBlock) block;
				this.signalBlockNames.put((SignalBlock) block, block.getLocalizedName());
			}
		}
		
		if (this.currentSignal == null) {
			return;
		}
		
		if (!SignalPreset.getSignalPresets().get(this.currentSignal).isEmpty()) {
			this.currentPreset = SignalPreset.getSignalPresets().get(this.currentSignal).get(0);
		}
	}
	
	private GuiButtonExt signalButton;
	private GuiButtonExt createPresetButton;
	private GuiButtonExt deletePresetButton;
	private GuiTextField presetNameField;
	
	private final List<GuiButtonExt> signalPresetButtons = new ArrayList<>();
	
	private final List<GuiButtonExt> presetSignalColorButtons = new ArrayList<>();
	private final List<GuiCheckBox> presetShouldFlashButtons = new ArrayList<>();
	private final List<GuiTextField> presetsOnTimeFields = new ArrayList<>();
	private final List<GuiTextField> presetsOffTimeFields = new ArrayList<>();
	
	@Override
	public void initGui() {
		if (this.currentSignal == null) {
			return;
		}
		
		this.signalButton = new GuiButtonExt(-3, 200, 300, this.signalBlockNames.get(this.currentSignal));
		this.createPresetButton = new GuiButtonExt(-2, 100, 200, "+");
		
		this.buttonList.add(this.signalButton);
		this.buttonList.add(this.createPresetButton);
		
		if (this.currentPreset == null) {
			System.out.println("Fuck you, es ist null");
			return;
		}
		
		this.deletePresetButton = new GuiButtonExt(-1, 0, 0, TextFormatting.RED + "Delete Preset");
		
		this.updatePresetList();
		this.updatePresetScreen();
		
		this.buttonList.add(this.deletePresetButton);
	}
	
	@Override
	protected void actionPerformed(final GuiButton button) {
		if (!button.enabled) {
			return;
		}
		
		if (button.id == this.signalButton.id) {
			this.nextSignal();
		} else if (button.id == this.createPresetButton.id) {
			this.addNewPreset();
		} else if (button.id == this.deletePresetButton.id) {
			this.removePreset();
		} else if (button.id >= 1 && button.id <= SignalPreset.getSignalPresets().get(this.currentSignal).size()) {
			this.currentPreset = SignalPreset.getSignalPresets().get(this.currentSignal).get(button.id - 1);
			
			this.updatePresetList();
			this.updatePresetScreen();
		} else if (button.id > SignalPreset.getSignalPresets().get(this.currentSignal).size()) {
			if (button instanceof GuiButtonExt) {
				this.nextSignalColor(button);
			} else if (button instanceof GuiCheckBox) {
				this.updateToggle((GuiCheckBox) button);
			}
		}
	}
	
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		this.drawDefaultBackground();
		
		if (this.presetNameField != null) {
			this.presetNameField.drawTextBox();
		}
		
		this.presetShouldFlashButtons.forEach(button -> {
			button.drawButton(this.mc, mouseX, mouseY, partialTicks);
		});
		this.presetsOnTimeFields.forEach(GuiTextField::drawTextBox);
		this.presetsOffTimeFields.forEach(GuiTextField::drawTextBox);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
//		this.box.mousePressed(this.mc, mouseX, mouseY);
		
		if (this.presetNameField != null) {
			this.presetNameField.mouseClicked(mouseX, mouseY, mouseButton);
		}
		
		this.presetsOnTimeFields.forEach(field -> field.mouseClicked(mouseX, mouseY, mouseButton));
		this.presetsOffTimeFields.forEach(field -> field.mouseClicked(mouseX, mouseY, mouseButton));
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (!Character.isDigit(typedChar) &&
				keyCode != Keyboard.KEY_LEFT &&
				keyCode != Keyboard.KEY_RIGHT &&
				keyCode != Keyboard.KEY_LCONTROL &&
				keyCode != Keyboard.KEY_RCONTROL &&
				keyCode != Keyboard.KEY_LSHIFT &&
				keyCode != Keyboard.KEY_RSHIFT &&
				keyCode != Keyboard.KEY_DELETE &&
				keyCode != Keyboard.KEY_BACK) {
			
			super.keyTyped(typedChar, keyCode);
			return;
		}
		
		if (this.presetNameField != null) {
			this.presetNameField.textboxKeyTyped(typedChar, keyCode);
			
			if (this.presetNameField.getText().isEmpty()) {
				this.currentPreset.name = TextFormatting.GRAY + "< No name >";
			} else {
				this.currentPreset.name = this.presetNameField.getText();
			}
			
			this.updatePresetList();
		}
		
		this.presetsOnTimeFields.forEach(field -> {
			field.textboxKeyTyped(typedChar, keyCode);
			
			if (field.isFocused()) {
				this.updateOnTime(field);
			}
		});
		this.presetsOffTimeFields.forEach(field -> {
			field.textboxKeyTyped(typedChar, keyCode);
			
			if (field.isFocused()) {
				this.updateOffTime(field);
			}
		});
		
		super.keyTyped(typedChar, keyCode);
	}
	
	private void addNewPreset() {
		new SignalPreset("New Preset", this.currentSignal);
	}
	
	private void removePreset() {
		final int currentIndex = SignalPreset.getSignalPresets().get(this.currentSignal).indexOf(this.currentPreset);
		final int nextIndex = currentIndex == 0 ? 0 : currentIndex - 1;
		
		SignalPreset.getSignalPresets().get(this.currentSignal).remove(this.currentPreset);
		
		if (SignalPreset.getSignalPresets().get(this.currentSignal).isEmpty()) {
			this.currentPreset = null;
			
			return;
		}
		
		this.currentPreset = SignalPreset.getSignalPresets().get(this.currentSignal).get(nextIndex);
		
		this.updatePresetList();
		this.updatePresetScreen();
	}
	
	private void nextSignal() {
		final int nextIndex = (new ArrayList<>(this.signalBlockNames.keySet()).indexOf(this.currentSignal) + 1) % this.signalBlockNames.size();
		
		this.currentSignal = new ArrayList<>(this.signalBlockNames.keySet()).get(nextIndex);
		this.signalButton.displayString = this.signalBlockNames.get(this.currentSignal);
	}
	
	private void nextSignalColor(final GuiButton button) {
		final SignalPreset.BulbStyle style = this.getBulbStyle(button.id);
		final int nextColor = style.validColors.get((style.validColors.indexOf(style.currentColor) + 1) % style.validColors.size());
		final String nextColorName = this.currentSignal.getColorName(nextColor);
		
		style.currentColor = nextColor;
		button.displayString = nextColorName;
	}
	
	private void updateToggle(final GuiCheckBox checkBox) {
		this.getBulbStyle(checkBox.id).shouldFlash = checkBox.isChecked();
		
		final int index = this.presetShouldFlashButtons.indexOf(checkBox);
		
		this.presetsOnTimeFields.get(index).setEnabled(checkBox.isChecked());
		this.presetsOffTimeFields.get(index).setEnabled(checkBox.isChecked());
	}
	
	private void updateOnTime(final GuiTextField textField) {
		try {
			this.getBulbStyle(textField.getId()).onTime = Math.max(1, Integer.parseInt(textField.getText()));
		} catch (final NumberFormatException ex) {
			this.getBulbStyle(textField.getId()).onTime = 1;
		}
	}
	
	private void updateOffTime(final GuiTextField textField) {
		try {
			this.getBulbStyle(textField.getId()).offTime = Math.max(1, Integer.parseInt(textField.getText()));
		} catch (final NumberFormatException ex) {
			this.getBulbStyle(textField.getId()).offTime = 1;
		}
	}
	
	private void updatePresetList() {
		this.buttonList.removeAll(this.signalPresetButtons);
		this.signalPresetButtons.clear();
		
		for (int i = 0 ; i < SignalPreset.getSignalPresets().get(this.currentSignal).size() ; i++) {
			final GuiButtonExt btn = new GuiButtonExt(i + 1, 0, i * 30 + 20, SignalPreset.getSignalPresets().get(this.currentSignal).get(i).name);
			if (SignalPreset.getSignalPresets().get(this.currentSignal).indexOf(this.currentPreset) == i) {
				btn.enabled = false;
			}
			
			this.signalPresetButtons.add(btn);
		}
		
		this.buttonList.addAll(this.signalPresetButtons);
	}
	
	private void updatePresetScreen() {
		this.buttonList.removeAll(this.presetShouldFlashButtons);
		this.buttonList.removeAll(this.presetSignalColorButtons);
		
		final int xOffset = 150;
		final int yOffset = 100;
		final int yLineOffset = 30;
		
		for (int i = 0 ; i < this.currentPreset.getBulbStyles().size() ; i++) {
			final int yPos = i * yLineOffset + yOffset;
			this.presetSignalColorButtons.add(new GuiButtonExt(i * 4 + SignalPreset.getSignalPresets().get(this.currentSignal).size() + 1, xOffset, yPos, 50, 20, ""));
			this.presetShouldFlashButtons.add(new GuiCheckBox(i * 4 + SignalPreset.getSignalPresets().get(this.currentSignal).size() + 2, xOffset + 55, yPos + 4, "Flashing", false));
			this.presetsOnTimeFields.add(new GuiTextField(i * 4 + SignalPreset.getSignalPresets().get(this.currentSignal).size() + 3, this.fontRenderer, xOffset + 115, yPos + 2, 40, 16));
			this.presetsOffTimeFields.add(new GuiTextField(i * 4 + SignalPreset.getSignalPresets().get(this.currentSignal).size() + 4, this.fontRenderer, xOffset + 165, yPos + 2, 40, 16));
		}
		
		this.presetSignalColorButtons.forEach(button -> button.displayString = this.currentSignal.getColorName(this.getBulbStyle(button.id).currentColor));
		this.presetShouldFlashButtons.forEach(checkBox -> checkBox.setIsChecked(this.getBulbStyle(checkBox.id).shouldFlash));
		
		this.presetsOnTimeFields.forEach(field -> {
			final SignalPreset.BulbStyle style = this.getBulbStyle(field.getId());
			
			field.setEnabled(style.shouldFlash);
			field.setText(String.valueOf(style.onTime));
		});
		this.presetsOffTimeFields.forEach(field -> {
			final SignalPreset.BulbStyle style = this.getBulbStyle(field.getId());
			
			field.setEnabled(style.shouldFlash);
			field.setText(String.valueOf(style.offTime));
			
		});
		
		this.presetNameField = new GuiTextField(0, this.fontRenderer, 300, 10, 100, 16);
		this.presetNameField.setText(this.currentPreset.name);
		
		this.buttonList.addAll(this.presetSignalColorButtons);
		this.buttonList.addAll(this.presetShouldFlashButtons);
	}
	
	private SignalPreset.BulbStyle getBulbStyle(final int id) {
		return this.currentPreset.getBulbStyles().get((int) Math.floor((id - SignalPreset.getSignalPresets().get(this.currentSignal).size() - 1) / 4f));
	}
}
