package labs.himegami.bon.fragments;

import com.github.nkzawa.socketio.client.IO;

import org.androidannotations.annotations.EFragment;

import java.net.URISyntaxException;

import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.fragments.base.BaseMainFragment;
import labs.himegami.bon.models.base.BaseResponseModel;
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
public class BonTabChatFragment extends BaseMainFragment<BonAddressTracker, BaseResponseModel> {

    @Override
    public int getFragmentTag() {
        return 0;
    }

}
