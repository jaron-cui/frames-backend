package web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DoubleMap<K, V> implements Map<K, V> {
  private final Map<K, V> values;
  private final Map<V, K> keys;

  public DoubleMap() {
    this.values = new HashMap<>();
    this.keys = new HashMap<>();
  }

  @Override
  public int size() {
    return this.values.size();
  }

  @Override
  public boolean isEmpty() {
    return this.values.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return this.values.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return this.keys.containsKey(value);
  }

  @Override
  public V get(Object key) {
    return this.values.get(key);
  }

  public K getKey(Object value) {
    return this.keys.get(value);
  }

  @Override
  public V put(K key, V value) {
    if (this.values.containsKey(key)) {
      this.keys.remove(this.values.get(key));
    }
    this.values.put(key, value);
    this.keys.put(value, key);
    return value;
  }

  @Override
  public V remove(Object key) {
    V value = this.values.remove(key);
    this.keys.remove(value);
    return value;
  }

  public K removeValue(Object value) {
    K key = this.keys.remove(value);
    this.values.remove(key);
    return key;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    for (K key : m.keySet()) {
      this.put(key, m.get(key));
    }
  }

  @Override
  public void clear() {
    this.values.clear();
    this.keys.clear();
  }

  @Override
  public Set<K> keySet() {
    return this.values.keySet();
  }

  @Override
  public Collection<V> values() {
    return this.values.values();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return this.values.entrySet();
  }
}
