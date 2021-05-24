package dev.psyGamer.immersiveTracks.util;

/**
 * A utility class copied from a client side package to make it also available to the server. <br> <br>
 *
 * @author Mojang
 * @version 1.0
 * @since 1.0
 * @see com.mojang.realmsclient.util.Pair Mojang's Pair
 */
public class Pair<A, B> {
	private final A first;
	private final B second;
	
	protected Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}
	
	public static <A, B> Pair<A, B> of(A a, B b) {
		return new Pair<>(a, b);
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
