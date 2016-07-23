package labs.himegami.bon.utilities.randomgenerator;

import java.util.Random;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 12, 2016
 */
public class RandomIntGenerator {

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return min + rand.nextInt((max - min) + 1);
    }

}
