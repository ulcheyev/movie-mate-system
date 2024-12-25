package cz.cvut.moviemate.commonlib.utils;

import java.util.Random;

public class CommonUtil {
    private static final Random random = new Random();

    private CommonUtil() {}

    public static int getRandomInt(int max) {
        return random.nextInt(max);
    }
}
