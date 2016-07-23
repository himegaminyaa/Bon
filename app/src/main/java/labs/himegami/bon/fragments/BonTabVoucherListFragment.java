package labs.himegami.bon.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;

import com.github.nkzawa.emitter.Emitter;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import labs.himegami.bon.R;
import labs.himegami.bon.adapter.base.BaseRecyclerViewAdapter;
import labs.himegami.bon.adapter.BonVoucherListAdapter;
import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.fragments.base.BaseSwipeListFragment;
import labs.himegami.bon.models.BonVoucherModel;
import labs.himegami.bon.providers.DataProvider;
import labs.himegami.bon.trackers.BonAddressTracker;
import labs.himegami.bon.utilities.debugging.Logger;
import labs.himegami.bon.utilities.dialogs.BaseDialogUtilities;

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
public class BonTabVoucherListFragment extends BaseSwipeListFragment<BonAddressTracker, BonVoucherModel> {

    private List<Integer> itemsClaimed;

    //region SETUP
    @Override
    protected void setupEmitterListener() {
        getMainActivity().setSocketListener(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String status = data.getString(BonDataConstants.IO_JSON_STATUS);
                    if (status.equals(BonDataConstants.IO_JSON_STATUS_VALUE)) {
                        doOnPurchase();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void processOnCreate(@Nullable Bundle savedInstanceState) {
        super.processOnCreate(savedInstanceState);
        itemsClaimed = new ArrayList<>();
    }

    @Override
    public void doOnAfterViewsUI() {
        super.doOnAfterViewsUI();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected BaseRecyclerViewAdapter<BonAddressTracker, BonVoucherModel> createListAdapter(Context context, ArrayList<BonVoucherModel> itemList) {
        return new BonVoucherListAdapter(this, itemList);
    }
    //endregion

    //region SERVICE CALL
    @Override
    protected ArrayList<BonVoucherModel> getItems(ArrayList<BonVoucherModel> itemList) {
        DataProvider.initHardCodedVouchers(getContext());
        return DataProvider.hardCodedVouchers;
    }
    //endregion

    //region ON CLICK
    @Override
    public void doOnItemLongClick(View view, BonVoucherModel model) {
        if (!model.isDisabled()) {
            Switch mSwitch = (Switch) view.findViewById(R.id.mSwitch);
            mSwitch.setChecked(!mSwitch.isChecked());
        }
    }
    //endregion

    //region VOUCHER ACTIVATION
    public void activateVoucher(BonVoucherModel model, boolean activate) {
        if (activate) {
            itemsClaimed.add(getAdapter().getIndexOf(model));
            getMainActivity().getSocket().emit(BonDataConstants.IO_EMIT_ACTIVATION, "");
            activateVoucherWS();
        } else {
            deactivateVoucherWS();
        }
    }

    @Background
    protected void activateVoucherWS() {
        Logger.d(getClass(), "Activating voucher...");
        activateVoucherUI();
    }

    @UiThread
    protected void activateVoucherUI() {
        Logger.d(getClass(), "Voucher activated");
    }

    @Background
    protected void deactivateVoucherWS() {
        Logger.d(getClass(), "Deactivating voucher...");
        deactivateVoucherUI();
    }

    @UiThread
    protected void deactivateVoucherUI() {
        Logger.d(getClass(), "Voucher deactivated");
    }

    @UiThread(delay = 5000)
    protected void doOnPurchase() {
        if (getView() != null) {
            if (getAdapter().getItem(itemsClaimed.get(0)).isChecked()) {
                BaseDialogUtilities.showSnackbar(getView(),
                        String.format("Voucher %s has been claimed!",
                                getAdapter().getItem(itemsClaimed.get(0)).getCd()));
                getAdapter().getItem(itemsClaimed.get(0)).setDisabled(true);
                getAdapter().notifyDataSetChanged();
            }
            itemsClaimed.remove(0);
        }
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    public int getFragmentTag() {
        return 0;
    }
    //endregion

}
