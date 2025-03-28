package org.javaculator.antlr4.handlers.primary;

import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CsvMapConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) {
        if (source == null) {
            return Collections.emptyMap();
        }

        String str = (String) source;
        Map<String, Integer> map = new HashMap<>();
        // Expecting format: "key1:val1,key2:val2"
        String[] entries = str.split(",");
        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                int value = Integer.parseInt(keyValue[1].trim());
                map.put(key, value);
            } else {
                throw new IllegalArgumentException("Entry is not in key:value format: " + entry);
            }
        }

        return map;
    }
}
