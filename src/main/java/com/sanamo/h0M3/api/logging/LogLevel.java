/**
 * <h1>LogLevel - Logging Level Enumeration</h1>
 * <p>
 * The LogLevel enum defines the different levels of logging available in
 * SanamoCore's logging system. These levels follow standard logging conventions
 * and allow for fine-grained control over what gets logged.
 * </p>
 *
 * @author Karter Sanamo
 * @version 1.0-SNAPSHOT
 * @since 1.0.0
 */
package com.sanamo.h0M3.api.logging;

public enum LogLevel {
    DEBUG(0, "DEBUG"),
    INFO(1, "INFO"),
    WARNING(2, "WARN"),
    ERROR(3, "ERROR"),
    SEVERE(4, "SEVERE"),
    NONE(5, "NONE");

    private final int priority;
    private final String displayName;

    LogLevel(int priority, String displayName) {
        this.priority = priority;
        this.displayName = displayName;
    }

    public static LogLevel fromString(String level) {
        if (level == null) {
            return INFO;
        }

        try {
            return valueOf(level.toUpperCase());
        } catch (IllegalArgumentException e) {
            return INFO;
        }
    }

    public int getPriority() {
        return priority;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean shouldLog(LogLevel level) {
        if (this == NONE) {
            return false;
        }
        return level.priority < this.priority;
    }
}