package org.javaculator.antlr4.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple cache with rollback functionality.
 * <p>
 * This class maintains a primary cache and a snapshot cache. It allows you to store key/value pairs,
 * take a snapshot of the current state, and later rollback the cache to that snapshot.
 * </p>
 */
public class RollbackCache {
    private final Map<String, BigDecimal> cache = new HashMap<>();
    private final Map<String, BigDecimal> snapshot = new HashMap<>();

    /**
     * Clears the current cache.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Rolls back the current cache to the state of the last snapshot.
     * <p>
     * This method clears the current cache, restores the snapshot into the cache,
     * and then clears the snapshot.
     * </p>
     */
    public void rollback() {
        cache.clear();
        cache.putAll(snapshot);
        snapshot.clear();
    }

    /**
     * Takes a snapshot of the current cache state.
     * <p>
     * This method clears any existing snapshot and stores all current cache entries into the snapshot.
     * </p>
     */
    public void takeSnapshot() {
        snapshot.clear();
        snapshot.putAll(cache);
    }

    /**
     * Retrieves the value associated with the given key from the cache.
     *
     * @param key the key to look up.
     * @return the {@link BigDecimal} value associated with the key, or {@code null} if the key is not present.
     */
    public BigDecimal get(String key) {
        return cache.get(key);
    }

    /**
     * Stores the given value under the specified key in the cache and returns the value.
     *
     * @param key   the key under which to store the value.
     * @param value the value to store.
     * @return the value that was stored.
     */
    public BigDecimal putAndGetCurrent(String key, BigDecimal value) {
        cache.put(key, value);
        return value;
    }

    /**
     * Stores the given value under the specified key in the cache and returns the previous value.
     *
     * @param key   the key under which to store the new value.
     * @param value the new value to store.
     * @return the previous {@link BigDecimal} value associated with the key, or {@code null} if there was none.
     */
    public BigDecimal putAndGetPrevious(String key, BigDecimal value) {
        return cache.put(key, value);
    }

    /**
     * Returns a string representation of the current cache.
     *
     * @return a string representation of the cache's contents.
     */
    @Override
    public String toString() {
        return cache.toString();
    }
}

