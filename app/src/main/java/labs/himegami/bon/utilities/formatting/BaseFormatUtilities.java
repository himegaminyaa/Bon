package labs.himegami.bon.utilities.formatting;

import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 16, 2016
 */
public class BaseFormatUtilities  {

    public static String convertDecimalToString(@NonNull BigDecimal decimal) {
        if (decimal == null) {
            decimal  = new BigDecimal(0.00);
        }

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        return nf.format(decimal);
    }

    public static BigDecimal convertStringToDecimal(@NonNull String decimalString){
        if (!decimalString.isEmpty()) {
            return new BigDecimal(decimalString);
        } else {
            return null;
        }
    }

    public static Integer convertStringToInt(@NonNull String intString) {
        return Integer.parseInt(intString);
    }

    public static String toCamelCase(String s){
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts){
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString;
    }

    public static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }

    public static List<String> convertBigDecimalListToStringList(List<BigDecimal> decimals) {
        List<String> strings = new ArrayList<>();
        for(BigDecimal decimal : decimals){
            strings.add(convertDecimalToString(decimal));
        }
        return strings;
    }

    public static List<BigDecimal> convertStringListToBigDecimalList(List<String> strings) {
        List<BigDecimal> decimals = new ArrayList<>();
        for(String string : strings) {
            decimals.add(convertStringToDecimal(string));
        }
        return decimals;
    }

}