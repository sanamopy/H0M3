package com.sanamo.h0M3.api.chat;

public final class ChatFormat {

    // Prevent instantiation
    private ChatFormat() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String format(String prefix, String message) {
        if (prefix == null) {
            prefix = "";
        }
        if (message == null) {
            message = "";
        }
        return ColorUtil.translate(prefix + " " + message);
    }

    public static String error(String message) {
        return format("&8&l[&4&lH0M3&8&l]", "&r&c" + message);
    }

    public static String info(String message) {
        return format("&8&l[&4&lH0M3&8&l]", "&r&7" + message);
    }

    public static String success(String message) {
        return format("&8&l[&a&lH0M3&8&l]", "&r&a" + message);
    }

    public static String warning(String message) {
        return format("&8&l[&e&lH0M3&8&l]", "&r&e" + message);
    }
}
