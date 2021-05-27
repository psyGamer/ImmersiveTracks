package dev.psyGamer.immersiveTracks.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class copied from a client side package to make it also available to the server. <br> <br>
 *
 * @author Mojang
 * @version 1.0
 * @see com.mojang.realmsclient.util.Pair Mojang's Pair
 * @since 1.0
 */
public class Pair <A, B> {
	private final A first;
	private final B second;
	
	protected Pair(final A first, final B second) {
		this.first = first;
		this.second = second;
	}
	
	public static <A, B> Pair<A, B> of(final A a, final B b) {
		return new Pair<>(a, b);
	}
	
	@SafeVarargs
	public static <A, B> Pair<A, List<B>> ofList(final A a, final B... b) {
		return new Pair<>(a, Arrays.stream(b).collect(Collectors.toList()));
	}
	
	public A first() {
		return this.first;
	}
	
	public B second() {
		return this.second;
	}
	
	@Override
	public String toString() {
		return String.format("(%s | %s)", this.first, this.second);
	}
}
