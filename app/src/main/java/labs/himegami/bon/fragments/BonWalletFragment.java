package labs.himegami.bon.fragments;

import android.text.SpannableString;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import labs.himegami.bon.R;
import labs.himegami.bon.fragments.base.BaseMainFragment;
import labs.himegami.bon.trackers.BonAddressTracker;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.utilities.formatting.BaseStringUtilities;

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
public class BonWalletFragment extends BaseMainFragment<BonAddressTracker, BaseResponseModel> {

    @ViewById
    protected TextView mDue;
    @ViewById
    protected TextView mCredit;

    @Override
    public void doOnAfterViewsUI() {
        super.doOnAfterViewsUI();
        setCustomFonts();
    }

    protected void setCustomFonts() {
//        SpannableString spannableString = new SpannableString(mDueCurrency.getText().toString());
//        mDueCurrency.setText(BaseStringUtilities.getSpannableText(spannableString, "lily_script_reg.tff"));
//        mCreditCurrency.setText(BaseStringUtilities.getSpannableText(spannableString, "lily_script_reg.tff"));
    }

    //region GETTERS AND SETTERS
    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_wallet_bon;
    }

    @Override
    public int getFragmentTag() {
        return R.string.tag_fragment_wallet;
    }
    //endregion

}
