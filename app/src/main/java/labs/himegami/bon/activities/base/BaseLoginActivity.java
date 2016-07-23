package labs.himegami.bon.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import labs.himegami.bon.R;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.trackers.base.BaseAddressTracker;

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
public class BaseLoginActivity<A extends BaseAddressTracker, B extends BaseResponseModel>
        extends BaseActivity<A, B> {

    //region VIEWS
    @ViewById
    protected AppCompatButton mLogin;

    @ViewById
    protected AppCompatButton mRegister;

    @ViewById
    protected TextInputEditText mUsername;

    @ViewById
    protected TextInputEditText mPassword;

    @ViewById
    protected TextInputLayout mUsernameLayout;

    @ViewById
    protected TextInputLayout mPasswordLayout;
    //endregion

    protected Bundle bundle;

    @Override
    protected void doOnPostCreate(@Nullable Bundle savedInstanceState) {
        super.doOnPostCreate(savedInstanceState);
        bundle = new Bundle();
    }

    //region LOGIN
    @Click
    public void mLogin() {
        if (loginIsValid()) {
            doOnLogin();
        } else {
            doOnInvalidLogin();
        }
    }

    protected void doOnInvalidLogin() {

    }

    protected boolean loginIsValid() {
        return true;
    }

    protected void doOnLogin() {
        loginWS();
    }

    @Background
    protected void loginWS() {
        loginUI();
    }

    protected void loginUI() {
        nextActivity();
    }

    protected void nextActivity() {
        Intent intent = new Intent(this, getNextActivity());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //endregion

    //region REGISTER
    @Click
    public void mRegister() {
        if (registerIsValid()) {
            doOnRegister();
        } else {
            doOnInvalidLogin();
        }
    }

    protected void doOnInvalidRegister() {

    }

    protected boolean registerIsValid() {
        return true;
    }

    protected void doOnRegister() {
        registerWS();
    }

    @Background
    protected void registerWS() {
        registerUI();
    }

    @UiThread
    protected void registerUI() {

    }
    //endregion

    protected Class getNextActivity() {
        return BaseMainActivity.class;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_login_base;
    }

}
