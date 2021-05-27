package dev.psyGamer.immersiveTracks.signals;

import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlock;
import dev.psyGamer.immersiveTracks.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SignalPreset {
	
	public static class BulbStyle {
		private final String bulbName;
		private final List<Integer> validColors;
		
		private final int currentColor;
		private final boolean shouldFlash;
		private final int onTime;
		private final int offTime;
		
		public BulbStyle(final String bulbName, final List<Integer> validColors) {
			this(bulbName, validColors, false);
		}
		
		public BulbStyle(final String bulbName, final List<Integer> validColors, final boolean shouldFlash) {
			this(bulbName, validColors, shouldFlash, 20, 20);
		}
		
		public BulbStyle(final String bulbName, final List<Integer> validColors, final boolean shouldFlash, final int onTime, final int offTime) {
			this.bulbName = bulbName;
			this.validColors = validColors;
			this.shouldFlash = shouldFlash;
			this.onTime = onTime;
			this.offTime = offTime;
			
			this.currentColor = validColors.get(0);
		}
		
		public String getBulbName() {
			return this.bulbName;
		}
		
		public List<Integer> getValidColors() {
			return this.validColors;
		}
		
		public int getCurrentColor() {
			return this.currentColor;
		}
		
		public boolean isShouldFlash() {
			return this.shouldFlash;
		}
		
		public int getOnTime() {
			return this.onTime;
		}
		
		public int getOffTime() {
			return this.offTime;
		}
	}
	
	private final String name;
	private final SignalBlock signal;
	private final List<BulbStyle> bulbStyles;
	
	public static List<BulbStyle> generateDefaultBulbStyles(final SignalBlock signal) {
		final List<BulbStyle> bulbStyles = new ArrayList<>();
		
		for (final Pair<String, List<Integer>> data : signal.getBulbData()) {
			bulbStyles.add(new BulbStyle(data.first(), data.second()));
		}
		
		return bulbStyles;
	}
	
	public SignalPreset(final String name, final SignalBlock signal) {
		this.name = name;
		this.signal = signal;
		this.bulbStyles = SignalPreset.generateDefaultBulbStyles(signal);
	}
	
	public String getName() {
		return this.name;
	}
	
	public SignalBlock getSignal() {
		return this.signal;
	}
	
	public List<BulbStyle> getBulbStyles() {
		return this.bulbStyles;
	}
}
