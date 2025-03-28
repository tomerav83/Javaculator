package org.javaculator.antlr4.snapshot;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Snapshot {
    private static final Map<String, BigDecimal> VARS = new HashMap<>();

    public void clear() {
        VARS.clear();
    }

    public BigDecimal get(String key) {
        return VARS.get(key);
    }

    public BigDecimal putAndGetCurrent(String key, BigDecimal value) {
        VARS.put(key, value);
        return value;
    }

    public BigDecimal putAndGetPrevious(String key, BigDecimal value) {
        return VARS.put(key, value);
    }

    public boolean isMissingOrNull(String key) {
        return !(VARS.containsKey(key) && VARS.get(key) != null);
    }

    @Override
    public String toString() {
        return VARS.toString();
    }
}
