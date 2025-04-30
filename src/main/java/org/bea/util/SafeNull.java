package org.bea.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.Supplier;

/**
 * Утилитный класс для работы с объектами с возможными null - значениями
 */
public class SafeNull {

    /**
     * Принимает лямбду с объектом любой вложенности,
     * отдаст значение или null
     */
    @Nullable
    public static <T> T getOrNull(@NonNull Supplier<T> s) {
        try {
            return s.get();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
