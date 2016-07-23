package labs.himegami.bon.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.estimote.sdk.Utils;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import labs.himegami.bon.R;
import labs.himegami.bon.adapter.base.BaseRecyclerViewAdapter;
import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.fragments.BonMerchantListFragment;
import labs.himegami.bon.models.BonEstablishmentModel;
import labs.himegami.bon.models.BonMerchantModel;
import labs.himegami.bon.trackers.BonAddressTracker;
import labs.himegami.bon.utilities.randomgenerator.RandomStringGenerator;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 20, 2016
 */
public class BonEstablishmentAdapter extends BaseRecyclerViewAdapter<BonAddressTracker, BonEstablishmentModel> {

    private BonMerchantListFragment merchantListFragment;

    public BonEstablishmentAdapter(BonMerchantListFragment merchantListFragment, List<BonEstablishmentModel> list) {
        super(merchantListFragment.getContext(), list);
        this.merchantListFragment = merchantListFragment;
    }

    @Override
    protected void processItemDefaultView(View holderView, BonEstablishmentModel model) {
        AppCompatTextView mName = (AppCompatTextView) holderView.findViewById(R.id.mMerchName);
        AppCompatTextView mDesc = (AppCompatTextView) holderView.findViewById(R.id.mMerchDesc);
        AppCompatTextView mDistance = (AppCompatTextView) holderView.findViewById(R.id.mMerchDistance);
        CircularImageView mIcon = (CircularImageView) holderView.findViewById(R.id.mMerchantIcon);

        mName.setText(model.getMerchantModel().getMerchantName());
        mDesc.setText(model.getLocation());
        if (model.getMerchantModel().getBeacon() != null) {
            mDistance.setText(String.format("%1.2fM", Utils.computeAccuracy(model.getMerchantModel().getBeacon())));
        }
        if (model.getMerchantModel().getMerchantImgLink() != null) {
            merchantListFragment.getImageLoader().displayImage(BonDataConstants.BON_IMG_URL + model.getMerchantModel().getMerchantImgLink(), mIcon);
        }
    }

    //region MULTIPLE REGION HANDLER
    public ArrayList<BonEstablishmentModel> updateList(ArrayList<BonEstablishmentModel> list, ArrayList<BonEstablishmentModel> scannedList) {
        MerchantComparator comparator = new MerchantComparator();

        List<BonEstablishmentModel> tempList = new ArrayList<>(list);
        List<BonEstablishmentModel> tempScannedList = new ArrayList<>(scannedList);

        Collections.sort(tempList, comparator);
        Collections.sort(tempScannedList,comparator);

        Iterator<BonEstablishmentModel> i1 = tempList.iterator();
        Iterator<BonEstablishmentModel> i2 = tempScannedList.iterator();


        while (i1.hasNext()) {
            BonEstablishmentModel iM1 = i1.next();
            while (i2.hasNext()) {
                BonEstablishmentModel iM2 = i2.next();
                if (comparator.compare(iM1, iM2) == 0) {
                    iM1 = iM2;
                    i2.remove();
                }
            }
        }

        list = copyIterator(i1);
        scannedList = copyIterator(i2);

        for (BonEstablishmentModel model : scannedList) {
            list.add(model);
        }

        Collections.sort(list, comparator);
        return list;
    }

    private class MerchantComparator implements Comparator<BonEstablishmentModel> {

        @Override
        public int compare(BonEstablishmentModel lhs, BonEstablishmentModel rhs) {
            return (BonMerchantModel.compareBeacons(
                    lhs.getMerchantModel(), rhs.getMerchantModel())) ? 0 : 1;
        }

    }

    private ArrayList<BonEstablishmentModel> copyIterator(Iterator<BonEstablishmentModel> iter) {
        ArrayList<BonEstablishmentModel> copy = new ArrayList<>();
        while (iter.hasNext()) {
            copy.add(iter.next());
        }
        return copy;
    }
    //endregion

    //region PROPERTIES
    @Override
    public boolean useDefaultCardLayout() {
        return false;
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    public int getDefaultLayout() {
        return R.layout.bon_list_item_merchant;
    }
    //endregion

}
