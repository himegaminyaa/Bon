package labs.himegami.bon.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.estimote.sdk.SystemRequirementsChecker;
import com.facebook.FacebookSdk;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.base.BaseLoginActivity;
import labs.himegami.bon.application.BonApplication;
import labs.himegami.bon.constants.BaseDataConstants;
import labs.himegami.bon.models.BonUserModel;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.models.response.FormParamValueMap;
import labs.himegami.bon.providers.DataProvider;
import labs.himegami.bon.service.BonRestService;
import labs.himegami.bon.service.interceptors.LoginRequestInterceptor;
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
 * July 16, 2016
 */
@EActivity
public class BonLoginActivity extends BaseLoginActivity<BonAddressTracker, BaseResponseModel> {

    //region VIEWS
    @ViewById
    protected CardView mRegisterC;

    @ViewById
    protected CircularImageView mScan;
    //endregion

    //region VARIABLES
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

    protected void setAuthInterceptors() {
        LoginRequestInterceptor interceptor = new LoginRequestInterceptor();
        RestTemplate template = getRestService().getRestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(interceptor);

        template.setInterceptors(interceptors);
        getRestService().setRestTemplate(template);
    }
    //endregion

    //region LIFE CYCLE
    @Override
    protected void onResume() {
        mScan.setVisibility(View.VISIBLE);
        mUsername.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.VISIBLE);
        mUsernameLayout.setVisibility(View.VISIBLE);
        mPasswordLayout.setVisibility(View.VISIBLE);
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        if (BaseDataConstants.HAS_ALREADY_SHOWN_ERROR) {
            BaseDataConstants.HAS_ALREADY_SHOWN_ERROR = false;
        }
    }

    @Override
    protected void onPause() {
        mScan.setVisibility(View.GONE);
        super.onPause();
    }
    //endregion

    //region ON CLICK
    @Click
    public void mRegisterC() {
        mRegister();
    }

    @Click
    public void mScan() {
        Intent intent = new Intent(this, getAddressTracker().getPreLoginScanActivity());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //endregion

    //region SERVICE CALL
    @Override
    public void mLogin() {
        setAuthInterceptors();
        super.mLogin();
    }

    @Override
    protected void loginWS() {
        FormParamValueMap values = new FormParamValueMap();
        values.add("password", mPassword.getText().toString());
        values.add("username", mUsername.getText().toString());

        try {
            ResponseEntity<BonUserModel> userEntity = getRestService().login(values);
            if (userEntity.getBody() != null) {
                DataProvider.bonUser = userEntity.getBody();
                if (DataProvider.bonUser.getAuthToken() != null) {
                    loginUI();
                } else {
                    showErrorDialog("Login Failed",
                            "Please try again or supply a different set of credentials.");
                }
            } else {
                showErrorDialog("Login Failed",
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

    @Override
    protected void registerUI() {
        mUsername.setVisibility(View.GONE);
        mPassword.setVisibility(View.GONE);
        mUsernameLayout.setVisibility(View.GONE);
        mPasswordLayout.setVisibility(View.GONE);

        Intent intent = new Intent(this, getAddressTracker().getRegisterActivity());
        intent.putExtras(bundle);
        startActivity(intent);
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
    @Override
    protected boolean loginIsValid() {
        Boolean fieldsAreValid = true;
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
        return R.layout.activity_login_bon;
    }

    public BonRestService getRestService() {
        return ((BonApplication)getApplication()).restService;
    }

    @Override
    protected Class getNextActivity() {
        return getAddressTracker().getPostLoginScanActivity();
    }
    //endregion

}
