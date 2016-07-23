package labs.himegami.bon.utilities.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.base.BaseMainActivity;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 22, 2016
 */
public class BaseAlertBuilder extends AlertDialog.Builder
        implements DialogInterface.OnDismissListener, DialogInterface.OnCancelListener, DialogInterface.OnClickListener {

    protected BaseMainActivity mActivity;

    //region LISTENER SETUP
    final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            doOnPositive();
        }
    };

    final DialogInterface.OnClickListener neutralListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            doOnNeutral();
        }
    };

    final DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            doOnNegative();
        }
    };
    //endregion

    //region CONSTRUCTORS
    public BaseAlertBuilder(BaseMainActivity mActivity) {
        super(mActivity);
        this.mActivity = mActivity;
    }

    public BaseAlertBuilder(Context context) {
        super(context);
    }
    //endregion

    //region BUTTON SETUP
    public void showPositiveButton(CharSequence text) {
        setPositiveButton(text, positiveListener);
    }

    public void showNeutralButton(CharSequence text) {
        setNeutralButton(text, neutralListener);
    }

    public void showNegativeButton(CharSequence text) {
        setNegativeButton(text, negativeListener);
    }

    public void showPositiveButton() {
        showPositiveButton(getContext().getResources().getString(R.string.button_ok));
    }

    public void showNegativeButton() {
        showNegativeButton(getContext().getResources().getString(R.string.button_cancel));
    }
    //endregion

    //region DO ON ACTION
    public void doOnPositive() {

    }

    public void doOnNeutral() {

    }

    public void doOnNegative() {

    }
    //endregion

    //region GESTURES
    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    //endregion

}