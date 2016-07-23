package labs.himegami.bon.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

import labs.himegami.bon.adapter.BonMenuListAdapter;
import labs.himegami.bon.adapter.base.BaseRecyclerViewAdapter;
import labs.himegami.bon.fragments.base.BaseSwipeListFragment;
import labs.himegami.bon.models.BonMenuModel;
import labs.himegami.bon.providers.DataProvider;
import labs.himegami.bon.trackers.BonAddressTracker;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 13, 2016
 */
@EFragment
public class BonTabMenuListFragment extends BaseSwipeListFragment<BonAddressTracker, BonMenuModel> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected BaseRecyclerViewAdapter<BonAddressTracker, BonMenuModel> createListAdapter(Context context, ArrayList<BonMenuModel> itemList) {
        return new BonMenuListAdapter(getContext(), itemList);
    }

    @Override
    protected ArrayList<BonMenuModel> getItems(ArrayList<BonMenuModel> itemList) {
        DataProvider.initHardCodedMenu();
        return DataProvider.hardCodedMenu;
    }

    @Override
    public int getFragmentTag() {
        return 0;
    }

}