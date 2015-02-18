package com.ralitski.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NonNullMap<K, V> implements Map<K, V> {
	
	private Map<K, V> mapImplementation;
	private Generator<V> valueGenerator;
	
	public NonNullMap(Generator<V> valueGenerator) {
		this(new HashMap<K, V>(), valueGenerator);
	}
	
	public NonNullMap(Map<K, V> mapImplementation, Generator<V> valueGenerator) {
		this.mapImplementation = mapImplementation;
		this.valueGenerator = valueGenerator;
	}

	@Override
	public void clear() {
		mapImplementation.clear();
	}

	@Override
	public boolean containsKey(Object o) {
		return mapImplementation.containsKey(o);
	}

	@Override
	public boolean containsValue(Object o) {
		return mapImplementation.containsKey(o);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return mapImplementation.entrySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object o) {
		V v = mapImplementation.get(o);
		if(v == null) {
			mapImplementation.put((K)o, v = valueGenerator.next());
		}
		return v;
	}

	@Override
	public boolean isEmpty() {
		return mapImplementation.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return mapImplementation.keySet();
	}

	@Override
	public V put(K key, V value) {
		return mapImplementation.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		mapImplementation.putAll(m);
	}

	@Override
	public V remove(Object o) {
		return mapImplementation.remove(o);
	}

	@Override
	public int size() {
		return mapImplementation.size();
	}

	@Override
	public Collection<V> values() {
		return mapImplementation.values();
	}

}
