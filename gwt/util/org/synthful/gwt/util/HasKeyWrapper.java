package org.synthful.gwt.util;

public interface HasKeyWrapper< K, V>
extends HasKey<K>{
	V getWrappedVaue();
	void setWrappedValue(V v);
}