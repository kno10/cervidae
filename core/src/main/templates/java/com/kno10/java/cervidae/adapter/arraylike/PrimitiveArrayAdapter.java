package com.kno10.java.cervidae.adapter.arraylike;

import java.util.Arrays;

/**
 * Class to control an {@code ${atype}[]} array.
 * 
 * @author Erich Schubert
 */
// ${NOTE}
public class ${Type}ArrayAdapter implements ArrayReadAdapter<${atype}[], ${Type}>, ArrayWriteAdapter<${atype}[], ${Type}>, ArraySortAdapter<${atype}[]>, ArrayAllocationAdapter<${atype}[]> {
	/**
	 * Adapter for ${type} arrays.
	 */
	public static ${Type}ArrayAdapter STATIC = new ${Type}ArrayAdapter();
	
	/**
	 * Constructor.
	 * 
	 * Use the static instance {@link #STATIC} to avoid object allocations!
	 */
	public ${Type}ArrayAdapter() {
		// Nothing to do.
	}
	
  @Override
  public ${Type} get(${atype}[] data, int pos) {
    return data[pos];
  }

  @Override
  public void set(${atype}[] data, int pos, ${Type} val) {
    data[pos] = val;
  }

	@Override
	public int length(${atype}[] data) {
		return data.length;
	}

	@Override
	public void swap(${atype}[] data, int i, int j) {
		${atype} tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

	@Override
	public boolean greaterThan(${atype}[] data, int i, int j) {
		return data[i] > data[j];
	}

	@Override
	public boolean equals(${atype}[] data, int i, int j) {
		return data[i] == data[j];
	}

	@Override
  public ${atype}[] newArray(int capacity) {
    return new ${atype}[capacity];
  }

  @Override
  public ${atype}[] ensureCapacity(${atype}[] existing, int capacity) {
    return Arrays.copyOf(existing, capacity);
  }
}