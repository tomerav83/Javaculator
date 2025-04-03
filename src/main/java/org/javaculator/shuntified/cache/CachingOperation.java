package org.javaculator.shuntified.cache;

@FunctionalInterface
public interface CachingOperation<T1, T2, T3, R> {
    R apply(T1 var1, T2 var2, T3 var3);
}
