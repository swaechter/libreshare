package ch.swaechter.libreshare.web.utils.string;

/**
 * Provide several string helper methods.
 *
 * @author Simon Wächter
 */
public class StringUtils {

    /**
     * Private constructor to protect the utility class.
     */
    private StringUtils() {
    }

    /**
     * Check if the string is null/empty/blank.
     *
     * @param value String to check
     * @return Status of the check
     */
    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
