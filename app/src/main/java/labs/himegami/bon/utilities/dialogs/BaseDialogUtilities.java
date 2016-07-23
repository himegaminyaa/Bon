package labs.himegami.bon.utilities.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.androidannotations.annotations.UiThread;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.base.BaseActivity;
import labs.himegami.bon.activities.base.BaseMainActivity;
import labs.himegami.bon.constants.BaseDataConstants;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 22, 2016
 */
public class BaseDialogUtilities {

    protected BaseMainActivity mActivity;
    protected ProgressDialog progressDialog = null;
    public static AlertDialog alertDialog;

    //region CONSTRUCTORS
    public BaseDialogUtilities() {

    }

    public BaseDialogUtilities(BaseMainActivity mActivity) {
        this.mActivity = mActivity;
        this.progressDialog = new ProgressDialog(mActivity, R.drawable.dialog_progress_background);
    }
    //endregion

    //region ALERT DIALOG
    public static void showConnectionErrorDialogOnLogin(final BaseActivity mActivity, int errorMessage) {
        BaseAlertBuilder builder = new BaseAlertBuilder(mActivity);
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(mActivity.getResources().getString(errorMessage));
        builder.showPositiveButton();

        alertDialog = builder.create();
        alertDialog.show();

        BaseDataConstants.HAS_ALREADY_SHOWN_ERROR = true;
    }

    public static void showConnectionErrorDialogOnLogin(final BaseActivity mActivity, String errorMessage) {
        BaseAlertBuilder builder = new BaseAlertBuilder(mActivity);
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(errorMessage);
        builder.showPositiveButton();

        alertDialog = builder.create();
        alertDialog.show();

        BaseDataConstants.HAS_ALREADY_SHOWN_ERROR = true;
    }

    public static void showConnectionErrorDialog(final BaseActivity mActivity) {
        BaseAlertBuilder builder = new BaseAlertBuilder(mActivity) {
            @Override
            public void doOnPositive() {
                mActivity.logOutUI();
            }
        };
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(mActivity.getResources().getString(R.string.error_no_internet_connection));
        builder.showPositiveButton();

        alertDialog = builder.create();
        alertDialog.show();

        BaseDataConstants.HAS_ALREADY_SHOWN_ERROR = true;
    }

    public static void showConnectionErrorDialog(final BaseMainActivity mActivity, final String message) {
        BaseAlertBuilder builder = new BaseAlertBuilder(mActivity) {
            @Override
            public void doOnPositive() {
                mActivity.logOutUI();
            }
        };
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.showPositiveButton();

        alertDialog = builder.create();
        if (!mActivity.isFinishing())
        alertDialog.show();

        BaseDataConstants.HAS_ALREADY_SHOWN_ERROR = true;
    }

    public static void showErrorDialog(final BaseActivity mActivity, final String title, final String message) {
        BaseAlertBuilder builder = new BaseAlertBuilder(mActivity) {
            @Override
            public void doOnPositive() {
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.showPositiveButton();

        alertDialog = builder.create();
        if (!mActivity.isFinishing())
        alertDialog.show();

        BaseDataConstants.HAS_ALREADY_SHOWN_ERROR = true;
    }

    public static void showPushNotifDialog(Context context, final String title, final String message) {
        BaseAlertBuilder builder = new BaseAlertBuilder(context) {
            @Override
            public void doOnPositive() {
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.showPositiveButton();

        alertDialog = builder.create();
        alertDialog.show();
    }
    //endregion

    //region PROGRESS DIALOG
    private static ProgressDialog dialog = null;

    public static void showProgressDialog(Activity mActivity, String title, String message, boolean showDialog) {
        if (showDialog) {
            dialog = new ProgressDialog(mActivity);
            dialog.setTitle(title);
            dialog.setMessage(message);
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    public static void showProgressDialog(Activity mActivity, String message, boolean showDialog) {
        if (showDialog) {
            dialog = new ProgressDialog(mActivity);
            dialog.setMessage(message);
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    public static void showProgressDialog(Activity mActivity, boolean showDialog) {
        if (showDialog) {
            dialog = new ProgressDialog(mActivity);
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    public void showProgressDialog(boolean showDialog) {
        if (showDialog) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }
    //endregion

    //region SNACK BAR
    public static void showSnackbar(Activity mActivity, String message, Boolean closeable) {
        final Snackbar snack = Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        if(closeable) {
            snack.setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snack.dismiss();
                }
            });
        }
        snack.show();
    }

    public static void showSnackbar(BaseMainActivity mActivity, String message, Boolean closeable) {
        final Snackbar snack = Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        if(closeable) {
            snack.setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snack.dismiss();
                }
            });
        }
        snack.show();
    }

    public static void showSnackbar(View target, String message, Boolean closeable) {
        final Snackbar snack = Snackbar.make(target, message, Snackbar.LENGTH_LONG);
        if(closeable) {
            snack.setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snack.dismiss();
                }
            });
        }
        snack.show();
    }

    public static void showSnackbar(@NonNull View view, @NonNull String message) {
        showSnackbar(view, message, Snackbar.LENGTH_LONG, 0, null, null);
    }

    public static void showSnackbar(@NonNull View view, @NonNull String message, int length) {
        showSnackbar(view, message, length, 0);
    }

    public static void showSnackbar(@NonNull View view, @NonNull String message, int length, int colorId) {
        showSnackbar(view, message, length, colorId, null, null);
    }

    public static void showSnackbar(@NonNull View view, @NonNull String message, int length, int colorId, String action, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message,
                length);
        snackbar.setAction(action, listener);

        if (colorId != 0) {
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(colorId);
        }

        snackbar.show();
    }
    //endregion

}
