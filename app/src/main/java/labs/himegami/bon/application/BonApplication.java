package labs.himegami.bon.application;

import android.app.Application;
import android.content.Context;

import com.estimote.sdk.BeaconManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.rest.RestService;

import java.util.UUID;

import labs.himegami.bon.R;
import labs.himegami.bon.service.BonRestService;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 14, 2016
 */
@EApplication
public class BonApplication extends Application {

    protected BeaconManager beaconManager;

    @RestService
    public static BonRestService restService;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFacebook();
        setupEstimote();
        setupCalligraphy();
    }

    protected void setupFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    protected void setupEstimote() {
        beaconManager = new BeaconManager(getApplicationContext());
    }

    protected void setupCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public BeaconManager getBeaconManager() {
        return beaconManager;
    }

}
