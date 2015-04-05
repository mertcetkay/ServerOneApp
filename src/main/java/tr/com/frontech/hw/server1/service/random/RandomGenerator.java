package tr.com.frontech.hw.server1.service.random;


import java.util.Random;

/**
 * RandomGenerator utility class definition.
 * Created by Mert on 8.2.2015.
 */
public final class RandomGenerator {

    /**
     * set of symbols
     */
    private static char[] symbols;

    /**
     *  random number generator
     */
    private static final Random random = new Random();

    /**
     * character buffer to form string
     */
    private static char[] buffer;

    /**
     * static block to initialize symbols
     */
    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch )
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    /**
     * prevents instance creation
     */
    public RandomGenerator(){
        throw new UnsupportedOperationException();
    }

    /**
     * generates random long
     * @return random long
     */
    public static Long getRandomLong(){
        return random.nextLong();
    }

    /**
     * generates random string
     * @return random string
     */
    public static String getRandomString(int length){
        if (length < 1)
            throw new IllegalArgumentException("length < 1: " + length);
        buffer = new char[length];
        for (int idx = 0; idx < buffer.length; ++idx)
            buffer[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buffer);
    }

    /**
     * main method - test purposes
     */
    public static void main(String...args){
        System.out.println(RandomGenerator.getRandomLong());
        System.out.println();
        System.out.println(RandomGenerator.getRandomString(20));
    }
}
