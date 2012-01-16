package org.synthful.gwt.util;

public class HasKeyHashList<K, V extends HasKey<K>>
extends HashList<K, V> {
	
	HasKeyHashList<K, V> add(V haskey){
		this.put(haskey.getKey(), haskey);
		return this;
	}

}
