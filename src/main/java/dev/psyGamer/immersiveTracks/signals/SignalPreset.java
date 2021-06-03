package dev.psyGamer.immersiveTracks.signals;

import dev.psyGamer.immersiveTracks.blocks.signal.SignalBlock;
import dev.psyGamer.immersiveTracks.util.Pair;

import java.util.*;

public class SignalPreset {
	
	public static class BulbStyle {
		public final String bulbName;
		public final List<Integer> validColors;
		
		public int currentColor;
		public boolean shouldFlash;
		public int onTime;
		public int offTime;
		
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
	}
	
	public String name;
	private final SignalBlock signal;
	private final List<BulbStyle> bulbStyles;
	
	public static Map<SignalBlock, List<SignalPreset>> signalPresets = new HashMap<>();
	
	public static Map<SignalBlock, List<SignalPreset>> getSignalPresets() {
		return Collections.unmodifiableMap(SignalPreset.signalPresets);
	}
	
	public static List<BulbStyle> generateDefaultBulbStyles(final SignalBlock signal) {
		final List<BulbStyle> bulbStyles = new ArrayList<>();
		
		for (final Pair<String, List<Integer>> color : Pair.mapToPairList(signal.getBulbColors())) {
			bulbStyles.add(new BulbStyle(color.first(), color.second()));
		}
		
		return bulbStyles;
	}
	
	public SignalPreset(final String name, final SignalBlock signal) {
		this.name = name;
		this.signal = signal;
		this.bulbStyles = SignalPreset.generateDefaultBulbStyles(signal);
		
		if (!SignalPreset.signalPresets.containsKey(signal)) {
			SignalPreset.signalPresets.put(signal, new ArrayList<>());
		}
		
		SignalPreset.signalPresets.get(signal).add(this);
	}
	
	public SignalBlock getSignal() {
		return this.signal;
	}
	
	public List<BulbStyle> getBulbStyles() {
		return this.bulbStyles;
	}
}
