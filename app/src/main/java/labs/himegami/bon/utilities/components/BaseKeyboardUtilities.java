package labs.himegami.bon.utilities.components;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Copyright (C) 2015 Solutions Exchange, Inc. All Rights Reserved.
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * April 15, 2016
 */
public class BaseKeyboardUtilities {

    /**
     * @param context
     * @param view
     * For activities:  BaseKeyboardUtilities.hideKeyboardFrom(this, getWindow().getDecorView().getRootView());
     * For fragments:   BaseKeyboardUtilities.hideKeyboardFrom(getContext, getView());
     */
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
