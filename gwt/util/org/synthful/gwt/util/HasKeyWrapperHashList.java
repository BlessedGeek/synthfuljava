package org.synthful.gwt.util;

public class HasKeyWrapperHashList<K, V, W extends HasKeyWrapper<K,V>>
extends HashList<K, V> {
	
	HasKeyWrapperHashList<K, V, W> add(W hasKeyWrapper){
		this.put(hasKeyWrapper.getKey(), hasKeyWrapper.getWrappedVaue());
		return this;
	}
}
