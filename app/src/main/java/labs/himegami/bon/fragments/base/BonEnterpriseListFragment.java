package labs.himegami.bon.fragments.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

import labs.himegami.bon.adapter.base.BaseRecyclerViewAdapter;
import labs.himegami.bon.models.BonMerchantModel;
import labs.himegami.bon.trackers.BonAddressTracker;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 19, 2016
 */
@EFragment
public class BonEnterpriseListFragment extends BaseSwipeListFragment<BonAddressTracker, BonMerchantModel> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return null;
    }

    @Override
    protected BaseRecyclerViewAdapter<BonAddressTracker, BonMerchantModel> createListAdapter(Context context, ArrayList<BonMerchantModel> itemList) {
        return null;
    }

    @Override
    protected ArrayList<BonMerchantModel> getItems(ArrayList<BonMerchantModel> itemList) {
        return null;
    }

    @Override
    public int getFragmentTag() {
        return 0;
    }
}
