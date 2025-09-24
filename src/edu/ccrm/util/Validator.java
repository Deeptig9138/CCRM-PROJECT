package edu.ccrm.util;

import java.util.Objects;

public final class Validator {
    private Validator() {}

    public static void requireNonNull(Object o, String name) {
        if (Objects.isNull(o)) throw new IllegalArgumentException(name + " cannot be null");
    }

    public static void requireNonEmpty(String s, String name) {
        if (s == null || s.trim().isEmpty()) throw new IllegalArgumentException(name + " cannot be empty");
    }
}