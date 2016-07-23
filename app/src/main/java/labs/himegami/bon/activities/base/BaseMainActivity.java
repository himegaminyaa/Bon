package labs.himegami.bon.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.net.URISyntaxException;

import labs.himegami.bon.R;
import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.models.response.BaseErrorHandler;
import labs.himegami.bon.models.response.BaseRsModel;
import labs.himegami.bon.trackers.base.BaseAddressTracker;
import labs.himegami.bon.utilities.formatting.BaseStringUtilities;

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
public abstract class BaseMainActivity<A extends BaseAddressTracker, B extends BaseResponseModel>
        extends BaseFragmentActivity<A, B> {

    @ViewById
    protected Toolbar mToolbar;

    //region VARIABLES
    @Bean
    protected BaseErrorHandler errorHandler;

    protected BaseRsModel errorModel;
    //endregion

    //region SOCKET VARIABLES
    protected Socket socket;
    {
        try {
        socket = IO.socket(getSocketAddress());
        } catch (URISyntaxException e) {
        e.printStackTrace();
        }
    }

    private Emitter.Listener socketListener;
    //endregion

    //region SETUP
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
        setupSockets();
    }

    protected void setupToolbar() {
        if(showBaseActionBar()) {
            mToolbar = (Toolbar)findViewById(R.id.mToolbar);
            setSupportActionBar(mToolbar);

            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(true);
        }
    }

    protected void setupSockets() {
        socket.connect();
    }

    @AfterInject
    abstract public void initAuth();
    //endregion

    //region ACTION BAR UPDATERS
    public void updateActionBarTitle(int titleId) {
        if(getSupportActionBar() != null) {
            if (titleId != 0) {
                SpannableString spannableString = new SpannableString(getString(titleId));
                spannableString = checkActionBarCustomizations(spannableString, true);
                getSupportActionBar().setTitle(spannableString);
            }
        }
    }

    public void updateActionBarTitle(String title) {
        if(getSupportActionBar() != null) {
            SpannableString spannableString = new SpannableString(title);
            spannableString = checkActionBarCustomizations(spannableString, true);
            getSupportActionBar().setTitle(spannableString);
        }
    }

    public void updateActionBarSubtitle(int subtitleId) {
        if(getSupportActionBar() != null) {
            if (subtitleId != 0) {
                SpannableString spannableString = new SpannableString(getString(subtitleId));
                spannableString = checkActionBarCustomizations(spannableString, false);
                getSupportActionBar().setSubtitle(spannableString);
            }
        }
    }

    public void updateActionBarSubtitle(String subtitle) {
        if(getSupportActionBar() != null) {
            SpannableString spannableString = new SpannableString(subtitle);
            spannableString = checkActionBarCustomizations(spannableString, false);
            getSupportActionBar().setSubtitle(spannableString);
        }
    }

    protected SpannableString checkActionBarCustomizations(SpannableString spannableString, boolean isTitle) {
        if (useCustomFontOnActionBar()) {
            spannableString = applyCustomFontsOnActionBar(spannableString, isTitle);
        }
        if (useCustomColorOnActionBar()) {
            spannableString = applyCustomColorsOnActionBar(spannableString, isTitle);
        }
        return spannableString;
    }

    protected SpannableString applyCustomFontsOnActionBar(SpannableString spannableString, boolean isTitle) {
        spannableString.setSpan(new TypefaceSpan(
                isTitle ? getActionBarTitleFont() : getActionBarSubtitleFont()),
                0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    protected SpannableString applyCustomColorsOnActionBar(SpannableString spannableString, boolean isTitle) {
        return BaseStringUtilities.getSpannableText(this, spannableString,
                isTitle ? getActionBarTitleColorRes() : getActionBarSubtitleColorRes());
    }
    //endregion

    //region LOG OUT
    public abstract void logOut();

    @Background
    public void logOutWS() {
        getSharedPrefEditor().clear();
        logOutUI();
    }

    @UiThread
    public void logOutUI() {
        Intent intent = new Intent(this, getLogOutActivity());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        finish();
    }

    @UiThread
    public void logOutCancelled() {

    }

    public Class getLogOutActivity() {
        return null;
    }
    //endregion

    //region PROPERTIES
    protected boolean showBaseActionBar() {
        return true;
    }

    protected boolean useCustomFontOnActionBar() {
        return false;
    }

    private boolean useCustomColorOnActionBar() {
        return false;
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    protected int getBaseLayout() {
        return R.layout.activity_main_base;
    }

    protected String getActionBarTitleFont() {
        return null;
    }

    protected String getActionBarSubtitleFont() {
        return null;
    }

    @ColorRes
    protected int getActionBarTitleColorRes() {
        return R.color.hl_color_primary;
    }

    @ColorRes
    protected int getActionBarSubtitleColorRes() {
        return R.color.hl_color_primary;
    }

    public String getSocketAddress() {
        return "";
    }

    public Socket getSocket() {
        return socket;
    }

    public Emitter.Listener getSocketListener() {
        return socketListener;
    }

    public void setSocketListener(Emitter.Listener socketListener) {
        socket.on("claimed", socketListener);
        this.socketListener = socketListener;
    }

    public BaseRsModel getErrorModel() {
        return errorModel;
    }

    public void setErrorModel(BaseRsModel errorModel) {
        this.errorModel = errorModel;
    }
    //endregion

}
