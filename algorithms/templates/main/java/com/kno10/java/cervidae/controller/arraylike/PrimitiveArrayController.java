package com.kno10.java.cervidae.controller.arraylike;

/**
 * Class to control an {@code #primitive#[]} array.
 * 
 * @author Erich Schubert
 */
// #NOTE#
public class #Primitive#ArrayController implements ArrayController<#primitive#[]> {
	/**
	 * Adapter for #primitive# arrays.
	 */
	public static #Primitive#ArrayController STATIC = new #Primitive#ArrayController();
	
	/**
	 * Constructor.
	 * 
	 * Use the static instance {@link #STATIC} to avoid object allocations!
	 */
	public #Primitive#ArrayController() {
		// Nothing to do.
	}

	@Override
	public int length(#primitive#[] data) {
		return data.length;
	}

	@Override
	public void swap(#primitive#[] data, int i, int j) {
		#primitive# tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

	@Override
	public boolean greaterThan(#primitive#[] data, int i, int j) {
		return data[i] > data[j];
	}

	@Override
	public boolean equals(#primitive#[] data, int i, int j) {
		return data[i] == data[j];
	}
}