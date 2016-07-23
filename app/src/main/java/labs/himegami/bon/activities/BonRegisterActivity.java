package labs.himegami.bon.activities;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.base.BaseActivity;
import labs.himegami.bon.application.BonApplication;
import labs.himegami.bon.models.BonUserModel;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.models.response.FormParamValueMap;
import labs.himegami.bon.service.BonRestService;
import labs.himegami.bon.trackers.BonAddressTracker;
import labs.himegami.bon.utilities.dialogs.BaseDialogUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 19, 2016
 */
@EActivity
public class BonRegisterActivity extends BaseActivity<BonAddressTracker, BaseResponseModel> {

    //region VIEWS
    @ViewById
    protected TextInputEditText mFirstName;

    @ViewById
    protected TextInputEditText mLastName;

    @ViewById
    protected TextInputLayout mFirstNameLayout;

    @ViewById
    protected TextInputLayout mLastNameLayout;

    @ViewById
    protected TextInputEditText mUsername;

    @ViewById
    protected TextInputLayout mUsernameLayout;

    @ViewById
    protected TextInputEditText mPassword;

    @ViewById
    protected TextInputLayout mPasswordLayout;

    @ViewById
    protected CircularImageView mBack;

    @ViewById
    protected View mRegister;
    //endregion

    //region VARIABLES
    @RestService
    protected BonRestService restService;

    protected ResponseEntity<BonUserModel> responseEntity;
    //endregion

    //region SETUP
    @Override
    protected void setupAddressTracker() {
        setAddressTracker(new BonAddressTracker());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    //endregion

    //region LIFE CYCLE
    @Override
    protected void onResume() {
        mBack.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mBack.setVisibility(View.GONE);
        super.onPause();
    }
    //endregion

    //region ON CLICK
    @Click
    public void mBack() {
        onBackPressed();
    }

    @Click
    public void mRegister() {
        if (allFieldsAreValid()) {
            registerWS();
        }
    }
    //endregion

    //region SERVICE CALL
    @Background
    protected void registerWS() {
        FormParamValueMap values = new FormParamValueMap();
        values.add("first_name", mFirstName.getText().toString());
        values.add("last_name", mLastName.getText().toString());
        values.add("email", mUsername.getText().toString());
        values.add("password", mPassword.getText().toString());
        values.add("username", mUsername.getText().toString());

        try {
            ResponseEntity<BonUserModel> userEntity = getRestService().register(values);
            if (userEntity.getBody() != null) {
                BonUserModel model = userEntity.getBody();
                if (model.getEmail().equals(mUsername.getText().toString())) {
                    showErrorDialog("Congratulations!", "You are now registered to Bon!");
                    registerUI();
                } else {
                    showErrorDialog("Registration Failed",
                            "Please try again or supply a different set of credentials.");
                }
            } else {
                showErrorDialog("Registration Failed",
                        "Please try again or supply a different set of credentials.");
            }
        } catch (ResourceAccessException e) {
            onResourceAccessException(e);
        } catch (HttpServerErrorException e) {
            showErrorDialog(R.string.error_server_unavailable);
        } catch (HttpClientErrorException e) {
            showErrorDialog(R.string.error_server_unavailable);
        }
    }
    protected void onResourceAccessException(ResourceAccessException e) {
        if (e.getCause() instanceof ConnectException) {
            if (e.getCause().toString().contains(getString(R.string.os_econnrefused))) {
                showErrorDialog(R.string.error_server_unavailable);
            } else if (e.getCause().toString().contains(getString(R.string.os_enetunreach))) {
                showErrorDialog(R.string.error_no_internet_connection);
            } else if (e.getCause().toString().contains(getString(R.string.os_ehostunreach))) {
                showErrorDialog(R.string.error_server_unavailable);
            } else {
                showErrorDialog(R.string.error_connection_refused);
            }
        } else if (e.getCause() instanceof SSLHandshakeException) {
            showErrorDialog(R.string.error_ssl_handshake_exception);
        } else if (e.getCause() instanceof SocketTimeoutException) {
            showErrorDialog(R.string.error_request_timed_out);
        }
    }

    @UiThread
    protected void registerUI() {

    }

    @UiThread
    protected void showErrorDialog(String title, String message) {
        BaseDialogUtilities.showErrorDialog(this, title, message);
    }

    @UiThread
    protected void showErrorDialog(int messageID) {
        BaseDialogUtilities.showConnectionErrorDialogOnLogin(this, messageID);
    }
    //endregion

    //region VALIDATORS
    protected boolean allFieldsAreValid() {
        Boolean fieldsAreValid = true;
        fieldsAreValid = fieldChecker(fieldsAreValid, mFirstName, mFirstNameLayout, 1);
        fieldsAreValid = fieldChecker(fieldsAreValid, mLastName, mLastNameLayout, 1);
        fieldsAreValid = fieldChecker(fieldsAreValid, mUsername, mUsernameLayout, 1);
        fieldsAreValid = fieldChecker(fieldsAreValid, mPassword, mPasswordLayout, 1);
        return fieldsAreValid;
    }

    protected boolean fieldChecker(Boolean fieldsAreValid, TextView field, TextInputLayout fieldLabel, int minimumChar) {
        if (field.getText().length() > 0) {
            if (field.getText().length() >= minimumChar) {
                fieldLabel.setError(null);
                return fieldsAreValid;
            } else {
                field.requestFocus();
                fieldLabel.setError(getString(R.string.error_minimum_field).replace("{0}", Integer.toString(minimumChar)));
                return false;
            }
        } else {
            field.requestFocus();
            fieldLabel.setError(getString(R.string.error_this_field_is_required));
            return false;
        }
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    protected int getBaseLayout() {
        return R.layout.activity_register_bon;
    }

    public BonRestService getRestService() {
        return ((BonApplication)getApplication()).restService;
    }
    //endregion

}
