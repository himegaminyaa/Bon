package labs.himegami.bon.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import labs.himegami.bon.R;
import labs.himegami.bon.fragments.base.BaseFragment;
import labs.himegami.bon.models.base.BaseResponseModel;
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
public abstract class BaseFragmentActivity<A extends BaseAddressTracker, B extends BaseResponseModel>
        extends BaseActivity<A, B> implements FragmentManager.OnBackStackChangedListener{

    //region VARIABLES
    private Bundle bundle;
    private FragmentManager fm;
    protected BaseFragment<A,?> fragment;
    //endregion

    //region SETUP
    @Override
    protected void doOnPostCreate(@Nullable Bundle savedInstanceState) {
        setupFragmentManager();
    }

    protected void setupFragmentManager() {
        fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(this);
    }
    //endregion

    //region BUNDLE HANDLING
    @Override
    protected void instanceIsNotNull(Bundle savedInstanceState) {
        if (fragment != null) {
            if (fragment.getClass() != getHomeFragment().getClass()) {
                BaseFragment<A,?> homeFragment = getHomeFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.mFragment, homeFragment, getString(R.string.tag_fragment_home)).commit();
                getSupportFragmentManager().beginTransaction().addToBackStack(getString(R.string.tag_fragment_home)).commit();
                switchFragments(fragment, bundle, true, getString(fragment.getFragmentTag()));
            } else {
                switchFragments(fragment, bundle, false, getString(fragment.getFragmentTag()));
            }
        } else {
            instanceIsNull();
        }
    }

    @Override
    protected void instanceIsNull() {
        BaseFragment<A,?> fragment = getHomeFragment();
        if (fragment.getFragmentTag() != 0) {
            switchFragments(fragment, null, false, getString(fragment.getFragmentTag()));
        } else {
            switchFragments(fragment, null, false, getString(R.string.tag_fragment_home));
        }
    }
    //endregion

    //region SWITCH FRAGMENTS
    public void switchFragments(BaseFragment<A, ?> fragment) {
        if (getCurrentFragment().getFragmentTag() != fragment.getFragmentTag()) {
            switchFragments(fragment, null, true,
                    getString(fragment.getFragmentTag()), null);
        }
    }

    public void switchFragments(BaseFragment<A, ?> fragment, boolean addBackStack) {
        switchFragments(fragment, null, addBackStack,
                getString(fragment.getFragmentTag()), null);
    }

    public void switchFragments(BaseFragment<A, ?> fragment, String fragmentTag) {
        switchFragments(fragment, null, true, fragmentTag, null);
    }

    public void switchFragments(BaseFragment<A,?> fragment,
                                boolean addBackStack, String fragmentTag) {
        switchFragments(fragment, null, addBackStack, fragmentTag, null);
    }

    public void switchFragments(BaseFragment<A,?> fragment, Bundle args, String fragmentTag) {
        switchFragments(fragment, args, true, fragmentTag,  null);
    }

    public void switchFragments(BaseFragment<A,?> fragment, Bundle args,
                                boolean addBackStack, String fragmentTag) {
        switchFragments(fragment, args, addBackStack, fragmentTag, null);
    }

    public void switchFragments(BaseFragment<A,?> fragment, Bundle args, boolean addBackStack,
                                String fragmentTag,  ArrayList<View> sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();

        if(fragment != null) {
            if (args != null) {
                fragment.setArguments(args);
            }
            ft.replace(R.id.mFragment, fragment, fragmentTag);
            if (addBackStack) {
                ft.addToBackStack(fragmentTag);
            }
        }

        ft.commit();
    }
    //endregion

    //region NAVIGATION
    @Override
    public void onBackPressed() {
        doBeforeBackStackCheck();
        if (fm.getBackStackEntryCount() == 0) {
            doOnBackStackEmpty();
        } else {
            doOnBackStackFilled();
            if (shouldBackPress()) {
                onFragmentBackPressed();
                super.onBackPressed();
            }
        }
    }
    //endregion

    //region BACK STACK
    @Override
    public void onBackStackChanged() {
        StringBuilder log = new StringBuilder("Current back stack:");
        int count = fm.getBackStackEntryCount();
        for (int i = count - 1; i >= 0; i--) {
            FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(i);
            log.append(" ");
            log.append(entry.getName());
        }
        Logger.d(getClass(), log.toString());
    }

    protected void doBeforeBackStackCheck() {

    }

    protected void doOnBackStackEmpty() {

    }

    protected void doOnBackStackFilled() {
        doOnWhichFragment();
    }

    protected void doOnWhichFragment() {

    }

    public void popAllBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for(int i=0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    protected void onFragmentBackPressed() {
        if (fragment != null) {
            fragment.doOnBackPress();
        }
    }
    //endregion

    //region PROPERTIES
    protected boolean shouldBackPress() {
        return true;
    }
    //endregion

    //region GETTERS AND SETTER
    public BaseFragment<A,?> getCurrentFragment() {
        return  (BaseFragment<A, ?>) getSupportFragmentManager().findFragmentById(R.id.mFragment);
    }

    public abstract BaseFragment<A, ?> getHomeFragment();
    //endregion

}
