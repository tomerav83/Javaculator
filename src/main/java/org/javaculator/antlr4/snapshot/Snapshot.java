package org.javaculator.antlr4.snapshot;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Snapshot {
    private static final Map<String, BigDecimal> VARIABLE_STATE = new HashMap<>();
    private static final Map<String, BigDecimal> SNAPSHOT = new HashMap<>();

    public void clear() {
        VARIABLE_STATE.clear();
    }

    public Snapshot rollback() {
        VARIABLE_STATE.clear();
        VARIABLE_STATE.putAll(SNAPSHOT);
        SNAPSHOT.clear();
        return this;
    }

    public void take() {
        SNAPSHOT.clear();
        SNAPSHOT.putAll(VARIABLE_STATE);
    }

    public BigDecimal get(String key) {
        return VARIABLE_STATE.get(key);
    }

    public BigDecimal putAndGetCurrent(String key, BigDecimal value) {
        VARIABLE_STATE.put(key, value);
        return value;
    }

    public BigDecimal putAndGetPrevious(String key, BigDecimal value) {
        return VARIABLE_STATE.put(key, value);
    }

    @Override
    public String toString() {
        return VARIABLE_STATE.toString();
    }
}
