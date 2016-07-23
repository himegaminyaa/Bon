package labs.himegami.bon.activities.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import labs.himegami.bon.R;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.trackers.base.BaseAddressTracker;
import labs.himegami.bon.utilities.components.BaseKeyboardUtilities;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 7, 2016
 */
public abstract class BaseDrawerActivity<A extends BaseAddressTracker, B extends BaseResponseModel>
        extends BaseMainActivity<A, B> {

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mLeftNav;
    protected NavigationView mRightNav;

    protected MenuItem selectedItem;
    protected boolean isDrawerOpen;
    protected boolean isBackPressed;
    protected boolean menuIsBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (haveBaseDrawerLayout()) {
            mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
            mLeftNav = (NavigationView) findViewById(R.id.mLeftNav);
            mRightNav = (NavigationView) findViewById(R.id.mRightNav);
            setupDrawerContent();
            setupSideBars();
        }
    }

    //region MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BaseKeyboardUtilities.hideKeyboardFrom(this, getWindow().getDecorView().getRootView());
                if (menuIsBackButton) {
                    menuIsBackButton = false;
                    doWhenMenuIsBackButton();
                    onBackPressed();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doWhenMenuIsBackButton() {
    }
    //endregion

    //region SETUP
    protected void setupDrawerContent() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                doOnDrawerClosed();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                doOnDrawerOpened();
            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);
    }

    protected void setupSideBars() {
        mLeftNav.inflateMenu(getLeftMenu());
        if (mLeftNav != null) {
            lockNavView(mLeftNav, lockLeftDrawer());
            mLeftNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    selectedItem = menuItem;
                    return true;
                }
            });
        }
        mRightNav.inflateMenu(getRightMenu());
        if (mRightNav != null) {
            lockNavView(mRightNav, lockRightDrawer());
            mRightNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    selectedItem = menuItem;
                    return true;
                }
            });
        }
    }

    public void lockNavView(NavigationView navView, boolean lock) {
        if (lock) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, navView);
            closeDrawers();
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, navView);
        }
    }
    //endregion

    //region DRAWER ACTIONS
    protected void doOnDrawerClosed() {
        isDrawerOpen = false;
        BaseKeyboardUtilities.hideKeyboardFrom(this, getWindow().getDecorView().getRootView());
        if (selectedItem != null) {
            selectNavigateItem(selectedItem);
        }
        if (isBackPressed) {
            isBackPressed = false;
            onBackPressed();
        }
    }

    protected void doOnDrawerOpened() {
        isDrawerOpen = true;
        selectedItem = null;
    }

    public void closeDrawers() {
        mDrawerLayout.closeDrawers();
    }
    //endregion

    //region NAVIGATION
    protected void selectNavigateItem(MenuItem menuItem) {
        if (R.id.mNavHome == menuItem.getItemId()) {
            navigateToHome();
        } else if (R.id.mNavLogout == menuItem.getItemId()) {
            logOut();
        } else {
            doOnNavigatedItem(menuItem.getItemId());
        }
    }

    protected abstract void navigateToHome();

    protected abstract void doOnNavigatedItem(int menuId);
    //endregion

    //region PROPERTIES
    protected boolean haveBaseDrawerLayout() {
        return true;
    }

    protected boolean lockLeftDrawer() {
        return false;
    }

    protected boolean lockRightDrawer() {
        return true;
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    protected int getBaseLayout() {
        return R.layout.activity_drawer_base;
    }

    protected int getLeftMenu() {
        return R.menu.left_nav_menu;
    }

    protected int getRightMenu() {
        return R.menu.right_nav_menu;
    }
    //endregion

}
