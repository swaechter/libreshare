package ch.swaechter.libreshare.web.utils.string;

public class StringUtils {

    // Private constructor
    private StringUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
