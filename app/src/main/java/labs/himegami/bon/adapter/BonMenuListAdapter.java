package labs.himegami.bon.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import labs.himegami.bon.R;
import labs.himegami.bon.adapter.base.BaseRecyclerViewAdapter;
import labs.himegami.bon.models.BonMenuModel;
import labs.himegami.bon.trackers.BonAddressTracker;
import labs.himegami.bon.utilities.formatting.BaseFormatUtilities;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 16, 2016
 */
public class BonMenuListAdapter extends BaseRecyclerViewAdapter<BonAddressTracker, BonMenuModel> {

    public BonMenuListAdapter(Context context, List<BonMenuModel> list) {
        super(context, list);
    }

    @Override
    protected void processItemDefaultView(View holderView, BonMenuModel model) {
        LinearLayout mContainer = (LinearLayout) holderView.findViewById(R.id.mContainer);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.bon_list_item_menu_item, null);
        AppCompatTextView itemName = (AppCompatTextView) view.findViewById(R.id.mItemName);
        AppCompatTextView itemPrice = (AppCompatTextView) view.findViewById(R.id.mItemPrice);
        AppCompatTextView itemDesc = (AppCompatTextView) view.findViewById(R.id.mItemDesc);

        itemName.setText(model.getName());
        itemPrice.setText(BaseFormatUtilities.convertDecimalToString(model.getPrice()));
        itemDesc.setText(model.getDesc());
        mContainer.addView(view);
    }

    @Override
    protected void processItemHeaderView(View holderView, BonMenuModel model) {
        AppCompatTextView mTitle = (AppCompatTextView) holderView.findViewById(R.id.mItemTitle);
        mTitle.setText(model.getTitle());
    }

    @Override
    public int getDefaultLayout() {
        return R.layout.bon_list_item_menu;
    }

    @Override
    public int getHeaderLayout() {
        return R.layout.bon_list_item_menu_header;
    }

    @Override
    public boolean useDefaultCardLayout() {
        return false;
    }
}
