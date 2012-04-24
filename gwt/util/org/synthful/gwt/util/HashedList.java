package org.synthful.gwt.util;

import java.util.ArrayList;
import java.util.HashMap;

public class HashedList<K, V>
extends ArrayList<V> {

	static public class IllegalAddOperation
	extends Exception{

		public IllegalAddOperation(String message) {
			super(message);
		}
	}
	
	@Override
	public boolean add(V value)
	{
		return false;
	}
	
	public HashedList<K, V> put(K key, V value){
		this.valueHash.put(key, value);
		super.add(value);
		return this;
	}
	
	public HashMap<K, V> getValueHash() {
		return valueHash;
	}

	public void setValueHash(HashMap<K, V> valueHash) {
		this.valueHash = valueHash;
	}

	private HashMap<K,V> valueHash;
}
