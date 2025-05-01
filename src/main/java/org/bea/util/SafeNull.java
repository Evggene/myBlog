package org.bea.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.Supplier;

public class SafeNull {

    @Nullable
    public static <T> T getOrNull(@NonNull Supplier<T> s) {
        try {
            return s.get();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
