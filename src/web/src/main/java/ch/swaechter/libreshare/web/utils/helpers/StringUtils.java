package ch.swaechter.libreshare.web.utils.helpers;

public class StringUtils {

    // Private constructor
    private StringUtils() {
    }

    public static boolean isNonEmptyString(String value) {
        return value != null && !value.isBlank();
    }
}
