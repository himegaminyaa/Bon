package labs.himegami.bon.trackers;

import labs.himegami.bon.activities.BonLoginActivity_;
import labs.himegami.bon.activities.BonMainActivity_;
import labs.himegami.bon.activities.BonPostLoginScanActivity_;
import labs.himegami.bon.activities.BonPreLoginScanActivity_;
import labs.himegami.bon.activities.BonRegisterActivity_;
import labs.himegami.bon.fragments.BonMainFragment;
import labs.himegami.bon.fragments.BonMainFragment_;
import labs.himegami.bon.fragments.BonMerchantListFragment;
import labs.himegami.bon.fragments.BonMerchantListFragment_;
import labs.himegami.bon.fragments.BonTabChatFragment;
import labs.himegami.bon.fragments.BonTabChatFragment_;
import labs.himegami.bon.fragments.BonTabMenuListFragment;
import labs.himegami.bon.fragments.BonTabMenuListFragment_;
import labs.himegami.bon.fragments.BonTabVoucherListFragment;
import labs.himegami.bon.fragments.BonTabVoucherListFragment_;
import labs.himegami.bon.fragments.BonWalletFragment;
import labs.himegami.bon.fragments.BonWalletFragment_;
import labs.himegami.bon.trackers.base.BaseAddressTracker;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 15, 2016
 */
public class BonAddressTracker extends BaseAddressTracker {

    public BonAddressTracker() {

    }

    public Class getLoginActivity() {
        return BonLoginActivity_.class;
    }

    public Class getMainActivity() {
        return BonMainActivity_.class;
    }

    public Class getPreLoginScanActivity() {
        return BonPreLoginScanActivity_.class;
    }

    public Class getPostLoginScanActivity() {
        return BonPostLoginScanActivity_.class;
    }

    public Class getRegisterActivity() {
        return BonRegisterActivity_.class;
    }

    public BonMerchantListFragment getMerchantFragment() {
        return new BonMerchantListFragment_();
    }

    public BonMainFragment getMainFragment() {
        return new BonMainFragment_();
    }

    public BonTabChatFragment getTabChatFragment() {
        return new BonTabChatFragment_();
    }

    public BonTabMenuListFragment getTabMenuFragment() {
        return new BonTabMenuListFragment_();
    }

    public BonTabVoucherListFragment getTabVoucherFragment() {
        return new BonTabVoucherListFragment_();
    }

    public BonWalletFragment getWalletFragment() {
        return new BonWalletFragment_();
    }

}
