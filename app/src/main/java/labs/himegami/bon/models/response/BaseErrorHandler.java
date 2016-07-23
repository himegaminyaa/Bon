package labs.himegami.bon.models.response;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.BonPostLoginScanActivity;
import labs.himegami.bon.activities.BonPreLoginScanActivity;
import labs.himegami.bon.activities.base.BaseMainActivity;
import labs.himegami.bon.constants.BaseDataConstants;
import labs.himegami.bon.utilities.dialogs.BaseDialogUtilities;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 22, 2016
 */
@EBean
public class BaseErrorHandler implements RestErrorHandler {

    @RootContext
    protected BaseMainActivity activity;

    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException runtimeException) {
        boolean shouldLogout = true;
        String errorMessage = "";

        if (runtimeException instanceof HttpClientErrorException) {
            HttpClientErrorException exception = (HttpClientErrorException) runtimeException;
            doOnPreHandlingOfException();

            if (exception.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                if (activity.getErrorModel() != null) {
                    errorMessage = activity.getErrorModel().getErrorMsg();
                } else {
                    errorMessage = activity.getString(R.string.error_unauthorized_user);
                }
            } else if (exception.getStatusCode().equals(HttpStatus.BAD_GATEWAY)) {
                errorMessage = activity
                        .getString(R.string.error_request_timed_out);
            } else if (exception.getStatusCode().equals(
                    HttpStatus.SERVICE_UNAVAILABLE)) {
                errorMessage = activity.getString(R.string.error_server_unavailable);
            }
        } else if (runtimeException instanceof ResourceAccessException) {
            if (runtimeException.getCause() instanceof SocketTimeoutException) {
                errorMessage = activity
                        .getString(R.string.error_request_timed_out);
            } else if (runtimeException.getCause() instanceof ConnectException) {
                if (runtimeException
                        .getCause()
                        .toString()
                        .contains(activity.getString(R.string.os_econnrefused))) {
                    errorMessage = activity
                            .getString(R.string.error_server_unavailable);
                } else if (runtimeException.getCause().toString()
                        .contains(activity.getString(R.string.os_enetunreach))) {
                    errorMessage = activity
                            .getString(R.string.error_no_internet_connection);
                } else if (runtimeException
                        .getCause()
                        .toString()
                        .contains(activity.getString(R.string.os_ehostunreach))) {
                    errorMessage = activity
                            .getString(R.string.error_server_unavailable);
                } else {
                    errorMessage = activity.getString(R.string.error_occurred);
                }
            } else if (runtimeException.getCause() instanceof UnknownHostException) {
                errorMessage = activity.getString(R.string.error_server_unavailable);
            }
        } else {
            errorMessage = activity.getString(R.string.error_occurred);
        }

        if (errorMessage.equals("")) {
            errorMessage = activity.getString(R.string.error_occurred);
        }

        if (!BaseDataConstants.HAS_ALREADY_SHOWN_ERROR) {
            if (shouldLogout) {
                callErrorDialog(errorMessage);
            } else {
                callLoginErrorDialog(errorMessage);
            }

            BaseDataConstants.HAS_ALREADY_SHOWN_ERROR = true;
        }
    }

    protected void doOnPreHandlingOfException() {
        if (activity instanceof BonPreLoginScanActivity) {
            ((BonPreLoginScanActivity) activity).getBeaconManager().disconnect();
        } else if (activity instanceof BonPostLoginScanActivity) {
            ((BonPostLoginScanActivity) activity).getBeaconManager().disconnect();
        }
    }

    //region CALL DIALOGS
    @UiThread
    public void callErrorDialog() {
        BaseDialogUtilities.showConnectionErrorDialog(activity);
    }

    @UiThread
    public void callErrorDialog(String message) {
        BaseDialogUtilities.showConnectionErrorDialog(activity, message);
    }

    @UiThread
    public void callLoginErrorDialog(String message) {
        BaseDialogUtilities.showConnectionErrorDialogOnLogin(activity, message);
    }
    //endregion

}
