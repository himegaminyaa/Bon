package labs.himegami.bon.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.springframework.http.ResponseEntity;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.base.BaseActivity;
import labs.himegami.bon.activities.base.BaseFragmentActivity;
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
@EFragment
public abstract class BaseFragment<A extends BaseAddressTracker, B extends BaseResponseModel> extends Fragment {

    //region VARIABLES
    protected A addressTracker;
    protected ResponseEntity<B> responseEntity;
    protected View rootView;
    //endregion

    //region ON CREATE
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAddressTracker();
        processOnCreate(savedInstanceState);
    }

    protected void setupAddressTracker() {

    }

    protected void processOnCreate(@Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(getFragmentLayout(), container, false);
        rootView = processOnCreateView(rootView, inflater, container, savedInstanceState);
        return rootView;
    }

    protected View processOnCreateView(View rootView, LayoutInflater inflater,
                                       @Nullable ViewGroup container,
                                       @Nullable Bundle savedInstanceState) {
        return rootView;
    }

    @AfterViews
    public void doOnAfterViewsUI() {

    }
    //endregion

    //region LIFE CYCLE
    @Override
    public void onStart() {
        super.onStart();
        Logger.d(getClass(), getString(R.string.debug_message_fragment_started));
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(getClass(), getString(R.string.debug_message_fragment_resumed));
        doOnResume();
    }

    protected void doOnResume() {

    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(getClass(), getString(R.string.debug_message_fragment_paused));
    }

    protected void doOnPause() {
        doOnPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(getClass(), getString(R.string.debug_message_fragment_stopped));
    }
    //endregion

    //region WEB SERVICE
    @Background
    public void callWebservice() {
        onPreExecute();
        onExecution();
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
        if (getActivity() != null) {
            doOnExecution();
        }
    }

    public void doOnExecution() {
        onPostExecute();
    }

    @UiThread
    protected void onPostExecute() {
        Logger.d(getClass(), getString(R.string.debug_message_web_service_post_execute));
        if (getActivity() != null) {
            if (getView() != null) {
                doOnPostExecute();
            }
        }
    }

    public void doOnPostExecute() {
    }
    //endregion

    //region NAVIGATION
    public void doOnBackPress() {

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
    public boolean shouldReloadUponResume() {
        return true;
    }

    public boolean shouldCallWebService() {
        return true;
    }
    //endregion

    //region GETTERS AND SETTERS
    public BaseActivity<A, B> getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity)getActivity();
        } else {
            return null;
        }
    }

    public BaseFragmentActivity<A, B> getFragmentActivity() {
        if (getActivity() instanceof BaseFragmentActivity) {
            return (BaseFragmentActivity)getActivity();
        } else {
            return null;
        }
    }

    public abstract int getFragmentTag();

    public abstract int getFragmentLayout();

    public A getAddressTracker() {
        return getBaseActivity().getAddressTracker();
    }

    public ResponseEntity<B> getResponseEntity() {
        return responseEntity;
    }
    //endregion

}
