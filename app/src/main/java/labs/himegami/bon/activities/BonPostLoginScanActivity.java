package labs.himegami.bon.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.estimote.sdk.BeaconManager;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.base.BaseActivity;
import labs.himegami.bon.activities.base.BaseMainActivity;
import labs.himegami.bon.application.BonApplication;
import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.fragments.BonMerchantListFragment;
import labs.himegami.bon.fragments.base.BaseFragment;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.providers.DataProvider;
import labs.himegami.bon.service.BonRestService;
import labs.himegami.bon.service.interceptors.UserRequestInterceptor;
import labs.himegami.bon.trackers.BonAddressTracker;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 21, 2016
 */
@EActivity
public class BonPostLoginScanActivity extends BaseMainActivity<BonAddressTracker, BaseResponseModel> {

    //region VIEWS
    @ViewById
    protected CircularImageView mBack;
    //endregion

    protected ImageLoader imageLoader;
    protected BeaconManager beaconManager;

    //region SETUP
    @Override
    protected void doOnPostCreate(@Nullable Bundle savedInstanceState) {
        super.doOnPostCreate(savedInstanceState);
        beaconManager = ((BonApplication)getApplication()).getBeaconManager();
        getSocket().emit(BonDataConstants.IO_EMIT_REACH, "");
        setupImageLoader();
    }

    @Override
    protected void setupAddressTracker() {
        setAddressTracker(new BonAddressTracker());
    }

    @Override
    public void initAuth() {
        getRestService().setRestErrorHandler(errorHandler);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void setupImageLoader() {
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
    }
    //endregion

    //region ON CLICK
    @Click
    public void mBack() {
        logOutUI();
    }
    //endregion

    //region LOGOUT
    @Override
    public void logOut() {
        logOutWS();
    }

    @Override
    public void logOutWS() {
        getRestService().logout();
    }

    @Override
    public Class getLogOutActivity() {
        return getAddressTracker().getLoginActivity();
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    protected int getBaseLayout() {
        return R.layout.activity_post_login_scan_bon;
    }

    public BonRestService getRestService() {
        return ((BonApplication)getApplication()).restService;
    }

    @Override
    public BaseFragment<BonAddressTracker, ?> getHomeFragment() {
        return getAddressTracker().getMerchantFragment();
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public BeaconManager getBeaconManager() {
        return beaconManager;
    }
    //endregion

}
