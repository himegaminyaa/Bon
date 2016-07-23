package labs.himegami.bon.utilities.formatting;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;

import labs.himegami.bon.R;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 8, 2016
 */
public class BaseStringUtilities {

    public static String toCamelCase(final String init) {
        if (init == null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length()==init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static Spannable getSpannableText(Context context, String message) {
        Spannable spannableString = new SpannableString(message);
        spannableString.setSpan(new ForegroundColorSpan(
                        context.getResources().getColor(R.color.hl_color_primary)), 0,
                spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static Spannable getSpannableText(Context context, String message, @ColorRes int colorRes) {
        Spannable spannableString = new SpannableString(message);
        spannableString.setSpan(new ForegroundColorSpan(
                        context.getResources().getColor(colorRes)), 0,
                spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getSpannableText(Context context, SpannableString spannableString, @ColorRes int colorRes) {
        spannableString.setSpan(new ForegroundColorSpan(
                        context.getResources().getColor(colorRes)), 0,
                spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getSpannableText(SpannableString spannableString, String font) {
        spannableString.setSpan(new TypefaceSpan(font), 0,
                spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
