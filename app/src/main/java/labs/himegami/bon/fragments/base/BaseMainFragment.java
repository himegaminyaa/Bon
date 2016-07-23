package labs.himegami.bon.fragments.base;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EFragment;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.base.BaseMainActivity;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.trackers.base.BaseAddressTracker;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 9, 2016
 */
@EFragment
public abstract class BaseMainFragment<A extends BaseAddressTracker, B extends BaseResponseModel>
        extends BaseFragment<A, B> implements ActionMode.Callback{

    @Override
    public void doOnAfterViewsUI() {
        if (!showSubtitle()) {
            updateActionBarSubtitle(null);
        }
    }

    @AfterInject
    public void setInterceptors() {
    }

    //region ACTION BAR UPDATERS
    protected void updateActionBarTitle(String title) {
        if (getMainActivity() != null) {
            getMainActivity().updateActionBarTitle(title);
        }
    }

    protected void updateActionBarTitle(int titleId) {
        if (getMainActivity() != null) {
            getMainActivity().updateActionBarTitle(titleId);
        }
    }

    protected void updateActionBarSubtitle(String subtitle) {
        if (getMainActivity() != null) {
            getMainActivity().updateActionBarSubtitle(subtitle);
        }
    }

    protected void updateActionBarSubtitle(int subtitleId) {
        if (getMainActivity() != null) {
            getMainActivity().updateActionBarSubtitle(subtitleId);
        }
    }
    //endregion

    //region ACTION MODE
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
    //endregion

    //region PROPERTIES
    public boolean showSubtitle() {
        return true;
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_main_base;
    }

    protected BaseMainActivity<A, ?> getMainActivity() {
        if (getActivity() instanceof BaseMainActivity) {
            return (BaseMainActivity) getActivity();
        } else {
            return null;
        }
    }
    //endregion

}
