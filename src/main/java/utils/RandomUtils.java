package utils;

import java.util.Random;

public class RandomUtils {

    public static String getRandomWord(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char tmp = (char) ('a' + random.nextInt('z' - 'a' + 1));
            sb.append(tmp);
        }
        return sb.toString();
    }

    public static Long getRandomPhoneNumber() {
        return (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
    }
}
