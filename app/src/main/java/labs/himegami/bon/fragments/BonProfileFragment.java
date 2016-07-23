package labs.himegami.bon.fragments;

import org.androidannotations.annotations.EFragment;

import labs.himegami.bon.R;
import labs.himegami.bon.fragments.base.BaseMainFragment;
import labs.himegami.bon.models.BonUserModel;
import labs.himegami.bon.trackers.BonAddressTracker;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 16, 2016
 */
@EFragment
public class BonProfileFragment extends BaseMainFragment<BonAddressTracker, BonUserModel> {

    @Override
    public int getFragmentTag() {
        return R.string.tag_fragment_profile;
    }

}
