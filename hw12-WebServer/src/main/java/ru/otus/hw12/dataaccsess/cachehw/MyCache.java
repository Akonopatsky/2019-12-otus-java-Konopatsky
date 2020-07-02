package ru.otus.hw12.dataaccsess.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
  private final Map<K, V> cache = new WeakHashMap<>();
  private final List<HwListener<K,V>> listeners = new ArrayList<>();

  @Override
  public void put(K key, V value) {
    cache.put(key, value);
    event(key, value, "put");
  }

  @Override
  public void remove(K key) {
    V value = cache.get(key);
    cache.remove(key);
    event(key, value, "remove");
  }

  @Override
  public V get(K key) {
    return cache.get(key);
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    listeners.remove(listener);
  }

  private void event(K key, V value, String action) {
    listeners.forEach(listeners -> listeners.notify(key, value, action));
  }
}
