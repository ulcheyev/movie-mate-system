package cz.cvut.moviemate.commonlib.util;

public class StringUtil {

    private StringUtil() {}

    public static String toLowerCaseAndReplaceSpace(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return input.toLowerCase().replace(" ", "-");
    }
}
