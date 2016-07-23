package labs.himegami.bon.activities.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.springframework.http.ResponseEntity;

import labs.himegami.bon.R;
import labs.himegami.bon.constants.BaseDataConstants;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.models.response.FormParamValueMap;
import labs.himegami.bon.trackers.base.BaseAddressTracker;
import labs.himegami.bon.utilities.debugging.Logger;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 7, 2016
 */
@EActivity
public abstract class BaseActivity<A extends BaseAddressTracker, B extends BaseResponseModel>
        extends AppCompatActivity {

    private A addressTracker;
    private ResponseEntity<B> responseEntity;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedPrefEditor;

    private String screenTitle;
    private String deviceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        doPreOnCreate();
        requestFullScreen();
        super.onCreate(savedInstanceState);
        doOnPostCreate(savedInstanceState);
        setContentView(getBaseLayout());
        setupProperties();
        setupAddressTracker();
        handleSavedInstanceState(savedInstanceState);
    }

    //region SETUP
    @AfterViews
    public void doOnAfterViews() {

    }

    protected void doPreOnCreate() {

    }

    protected void doOnPostCreate(@Nullable Bundle savedInstanceState) {

    }

    protected void requestFullScreen() {
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (Build.VERSION.SDK_INT < 16) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

    protected void setupAddressTracker() {
        // A addressTracker = new A();
    }

    protected void setupProperties() {
        sharedPref = getApplicationContext().
                getSharedPreferences(BaseDataConstants.CONFIGURATION_PROPERTIES, MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();
        sharedPrefEditor.apply();
    }

    protected void handleSavedInstanceState(@Nullable Bundle savedInstanceState) {
        Logger.d(getClass(), getString(R.string.debug_message_handle_saved_instance));
        savedInstanceState = this.getIntent().getExtras();
        if (savedInstanceState != null) {
            Logger.d(getClass(), getString(R.string.debug_message_handled_saved_instance_is_not_null));
            instanceIsNotNull(savedInstanceState);
        } else {
            Logger.d(getClass(), getString(R.string.debug_message_handled_saved_instance_is_null));
            instanceIsNull();
        }
    }

    protected void instanceIsNotNull(Bundle  savedInstanceState) {

    }

    protected void instanceIsNull() {

    }
    //endregion

    //region LIFE CYCLE
    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(getClass(), getString(R.string.debug_message_activity_started));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(getClass(), getString(R.string.debug_message_activity_resumed));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(getClass(), getString(R.string.debug_message_activity_paused));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(getClass(), getString(R.string.debug_message_activity_stopped));
    }
    //endregion

    //region WEB SERVICE
    @Background
    public void callWebservice() {
        onPreExecute();
        onExecution();
        onPostExecute();
        Logger.d(getClass(), getString(R.string.debug_message_web_service_stopped));
    }

    @UiThread
    protected void onPreExecute() {
        Logger.d(getClass(), getString(R.string.debug_message_web_service_started));
        Logger.d(getClass(), getString(R.string.debug_message_web_service_pre_execute));
        doOnPreExecute();
    }

    public void doOnPreExecute() {

    }

    @Background
    protected void onExecution() {
        Logger.d(getClass(), getString(R.string.debug_message_web_service_call_execution));
        doOnExecution();
    }

    public void doOnExecution() {
    }

    @UiThread
    protected void onPostExecute() {
        Logger.d(getClass(), getString(R.string.debug_message_web_service_post_execute));
        doOnPostExecute();
    }

    public void doOnPostExecute() {
    }
    //endregion

    //region RESPONSE CALL
    protected FormParamValueMap getMappedValues() {
        return null;
    }

    protected ResponseEntity<B> getReponse() {
        return null;
    }

    protected ResponseEntity<B> getReponse(FormParamValueMap values) {
        return null;
    }
    //endregion

    //region PROPERTIES
    protected boolean isFullScreen() {
        return false;
    }
    //endregion

    //region GETTERS AND SETTERS
    @LayoutRes
    protected abstract int getBaseLayout();

    public A getAddressTracker() {
        return addressTracker;
    }

    public void setAddressTracker(A addressTracker) {
        this.addressTracker = addressTracker;
    }

    public ResponseEntity<B> getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity<B> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public SharedPreferences.Editor getSharedPrefEditor() {
        return sharedPrefEditor;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }
    //endregion

}
