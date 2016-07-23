package labs.himegami.bon.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.EActivity;

import java.lang.reflect.Field;
import java.util.List;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.base.BaseDrawerActivity;
import labs.himegami.bon.application.BonApplication;
import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.fragments.base.BaseFragment;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.providers.DataProvider;
import labs.himegami.bon.service.BonRestService;
import labs.himegami.bon.trackers.BonAddressTracker;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 10, 2016
 */
@EActivity
public class BonMainActivity extends BaseDrawerActivity<BonAddressTracker, BaseResponseModel> {

    protected BeaconManager beaconManager;
    protected Region region;

    //region SETUP
    @Override
    protected void doOnPostCreate(@Nullable Bundle savedInstanceState) {
        super.doOnPostCreate(savedInstanceState);
        setupBeaconManager();
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

    protected void setupBeaconManager() {
        beaconManager = ((BonApplication)getApplication()).getBeaconManager();
        region = new Region(DataProvider.establishment.getMerchantModel().getMerchantName(),
                DataProvider.establishment.getMerchantModel().getBeacon().getProximityUUID(),
                DataProvider.establishment.getMerchantModel().getBeacon().getMajor(),
                DataProvider.establishment.getMerchantModel().getBeacon().getMinor());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(region);
            }
        });
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotif(getString(R.string.app_name),
                        String.format("Welcome back to %s!",
                                DataProvider.establishment.getMerchantModel().getMerchantName()));
            }

            @Override
            public void onExitedRegion(Region region) {
                showNotif(getString(R.string.app_name),
                        String.format("You're outside of %s's area.",
                                DataProvider.establishment.getMerchantModel().getMerchantName()));
            }
        });
    }

    protected void showNotif(String title, String message) {
        Intent notifyIntent = new Intent(this, getClass());
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
        } else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .getNotification();
        }
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(172969, notification);
    }
    //endregion

    //region NAVIGATION
    @Override
    protected void navigateToHome() {
        switchFragments(getHomeFragment());
    }

    @Override
    protected void doOnNavigatedItem(int menuId) {
        if (menuId == R.id.mNavWallet) {
            switchFragments(getAddressTracker().getWalletFragment());
        }
    }

    @Override
    protected void doOnBackStackEmpty() {
        finish();
    }
    //endregion

    //region LOGOUT
    @Override
    public void logOut() {
        Intent intent = new Intent(this, getLogOutActivity());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        finish();
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    protected int getBaseLayout() {
        return R.layout.activity_drawer_bon;
    }

    public BonRestService getRestService() {
        return ((BonApplication)getApplication()).restService;
    }

    @Override
    public String getSocketAddress() {
        return BonDataConstants.IO_ADDRESS;
    }

    @Override
    public Class getLogOutActivity() {
        return getAddressTracker().getLoginActivity();
    }

    @Override
    public BaseFragment getHomeFragment() {
        return getAddressTracker().getMainFragment();
    }

    @Override
    protected int getLeftMenu() {
        return R.menu.bon_menu;
    }
    //endregion

}
