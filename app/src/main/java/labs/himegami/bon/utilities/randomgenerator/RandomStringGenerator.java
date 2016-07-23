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
public class RandomStringGenerator {

    //region STRING GENERATORS
    public static StringBuilder alphaNumeric(int length) {
        Random generator = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        while(i < length) {
            char temp = (char) (generator.nextInt(92)+32);

            if(Character.isLetterOrDigit(temp)) {
                stringBuilder.append(temp);
                ++i;
            }
        }

        return stringBuilder;
    }

    public static StringBuilder numeric(int length) {
        Random generator = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        while(i < length) {
            char temp = (char) (generator.nextInt(92)+32);

            if(Character.isDigit(temp)) {
                stringBuilder.append(temp);
                ++i;
            }
        }

        return stringBuilder;
    }

    public static StringBuilder letter(int length) {
        Random generator = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        while(i < length) {
            char temp = (char) (generator.nextInt(92)+32);

            if(Character.isLetter(temp)) {
                stringBuilder.append(temp);
                ++i;
            }
        }

        return stringBuilder;
    }
    //endregion

}
