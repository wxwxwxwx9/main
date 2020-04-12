package seedu.diary.commons.util;

/**
 * Helper functions for handling boolean(s).
 */
public class BooleanUtil {

    public static final String INVALID_BOOLEAN = "Please use a valid boolean!";

    /**
     * Returns true if the {@code str} is a valid boolean.
     *
     * @param str
     */
    public static boolean isValidBoolean(String str) {
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }

}
