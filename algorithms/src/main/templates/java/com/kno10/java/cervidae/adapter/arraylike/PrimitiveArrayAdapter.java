package com.kno10.java.cervidae.adapter.arraylike;

/**
 * Class to control an {@code #primitive#[]} array.
 * 
 * @author Erich Schubert
 */
// #NOTE#
public class #Primitive#ArrayAdapter implements ArrayReadAdapter<#primitive#[], #Primitive#>, ArrayWriteAdapter<#primitive#[], #Primitive#>, ArraySortAdapter<#primitive#[]> {
	/**
	 * Adapter for #primitive# arrays.
	 */
	public static #Primitive#ArrayAdapter STATIC = new #Primitive#ArrayAdapter();
	
	/**
	 * Constructor.
	 * 
	 * Use the static instance {@link #STATIC} to avoid object allocations!
	 */
	public #Primitive#ArrayAdapter() {
		// Nothing to do.
	}
	
  @Override
  public #Primitive# get(#primitive#[] data, int pos) {
    return data[pos];
  }

  @Override
  public void set(#primitive#[] data, int pos, #Primitive# val) {
    data[pos] = val;
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