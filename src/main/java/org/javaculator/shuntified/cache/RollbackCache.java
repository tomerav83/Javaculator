package org.javaculator.shuntified.cache;

import org.javaculator.shuntified.exceptions.eval.MathOperationException;
import org.javaculator.shuntified.exceptions.eval.MissingVariableEntryException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;

/**
 * A simple cache with rollback functionality.
 * <p>
 * This class maintains a primary cache and a snapshot cache. It allows you to store key/value pairs,
 * take a snapshot of the current state, and later rollback the cache to that snapshot.
 * </p>
 */
public class RollbackCache {
    private final Map<String, Double> cache = new HashMap<>();
    private final Map<String, Double> snapshot = new HashMap<>();

    public Map<String, Double> get() {
        return cache;
    }

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
    public Double get(String key) {
        return Optional.ofNullable(cache.get(key))
                .orElseThrow(() -> new MissingVariableEntryException(key));
    }

    public Double putAndGetCurrent(String key, Double rhs) {
        cache.put(key, rhs);
        return rhs;
    }

    public Double putAndGetCurrent(String key, Double rhs, DoubleBinaryOperator op) {
        return Optional.ofNullable(cache.get(key))
                .map((Double lhs) -> applyOperator(lhs, rhs, op))
                .map((Double updated) -> {
                    cache.put(key, updated);
                    return updated;
                })
                .orElseThrow(() -> new MissingVariableEntryException(key));
    }

    public Double putAndGetPrevious(String key, Double rhs, DoubleBinaryOperator op) {
        return Optional.ofNullable(cache.get(key))
                .map((Double lhs) -> cache.put(key, applyOperator(lhs, rhs, op)))
                .orElseThrow(() -> new MissingVariableEntryException(key));
    }

    private static Double applyOperator(Double  lhs, Double rhs, DoubleBinaryOperator op) {
        try {
            return op.applyAsDouble(lhs, rhs);
        } catch (Exception e) {
            throw new MathOperationException(e);
        }
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
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

