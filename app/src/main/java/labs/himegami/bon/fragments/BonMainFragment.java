package labs.himegami.bon.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import labs.himegami.bon.R;
import labs.himegami.bon.adapter.base.BaseFragmentPageAdapter;
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
 * July 12, 2016
 */
@EFragment
public class BonMainFragment extends BaseMainFragment<BonAddressTracker, BaseResponseModel>
        implements ViewPager.OnPageChangeListener {

    @ViewById
    protected ViewPager mViewPager;

    @ViewById
    protected TabLayout mTabs;

    protected BaseFragmentPageAdapter pageAdapter;
    protected BonTabVoucherListFragment voucherFragment;
    protected BonTabMenuListFragment menuFragment;
    protected BonTabChatFragment chatFragment;

    private static final int tabSize = 3;

    protected int[] tabIcons = {
            R.drawable.ic_bon_vouchers_black_24dp,
            R.drawable.ic_bon_food_black_24dp,
            R.drawable.ic_bon_chat_black_24dp
    };

    protected int[] selTabIcons = {
            R.drawable.ic_bon_vouchers_white_24dp,
            R.drawable.ic_bon_food_white_24dp,
            R.drawable.ic_bon_chat_white_24dp
    };

    //region SETUP
    @Override
    public void doOnAfterViewsUI() {
        super.doOnAfterViewsUI();
        setupAdapter();
        setupTabs();
        setupViewPager();
        setupTabIcons();
    }

    protected void setupAdapter() {
        pageAdapter = new BaseFragmentPageAdapter(getChildFragmentManager());
    }

    protected void setupTabs() {
        voucherFragment = getAddressTracker().getTabVoucherFragment();
        menuFragment = getAddressTracker().getTabMenuFragment();
        chatFragment = getAddressTracker().getTabChatFragment();

        pageAdapter.addFragment(voucherFragment, getString(R.string.label_tab_voucher));
        pageAdapter.addFragment(menuFragment, getString(R.string.label_tab_menu));
        pageAdapter.addFragment(chatFragment, getString(R.string.label_tab_chat));
    }

    protected void setupViewPager() {
        mViewPager.setAdapter(pageAdapter);
        mTabs.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }

    protected void setupTabIcons() {
        for (int i = 0; i < tabSize; i++) {
            TabLayout.Tab tab = mTabs.getTabAt(i);
            if (tab != null) {
                if(i == 0) {
                    tab.setIcon(selTabIcons[i]);
                    tab.setCustomView(getCustomTab(tab, true));
                }
                else {
                    tab.setIcon(tabIcons[i]);
                    tab.setCustomView(getCustomTab(tab, false));
                }
            }
        }
    }
    //endregion

    //region CUSTOM TAB MANAGEMENT
    public View getCustomTab(TabLayout.Tab tab, boolean isSelected) {
        ViewGroup customTab = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.bon_main_tab, null);
        TextView customText = (TextView) customTab.findViewById(R.id.mText);
        ImageView customImage = (ImageView) customTab.findViewById(R.id.mIcon);
        customText.setText(tab.getText());
        customText.setTextColor(isSelected ? getResources().getColor(R.color.bon_white) :
                getResources().getColor(R.color.bon_black));
        customImage.setImageDrawable(tab.getIcon());
        return customTab;
    }

    protected void updateCustomTab(TabLayout.Tab tab, boolean isSelected) {
        TextView customText = (TextView) tab.getCustomView().findViewById(R.id.mText);
        ImageView customImage = (ImageView) tab.getCustomView().findViewById(R.id.mIcon);
        customText.setText(tab.getText());
        customText.setTextColor(isSelected ? getResources().getColor(R.color.bon_white) :
                getResources().getColor(R.color.bon_black));
        customImage.setImageDrawable(tab.getIcon());
    }
    //endregion

    //region ON PAGE CHANGE LISTENERS
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < tabSize; i++) {
            TabLayout.Tab tab = mTabs.getTabAt(i);
            if (tab != null) {
                if(i == position) {
                    tab.setIcon(selTabIcons[i]);
                    updateCustomTab(tab, true);
                }
                else {
                    tab.setIcon(tabIcons[i]);
                    updateCustomTab(tab, false);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_main_bon;
    }

    @Override
    public int getFragmentTag() {
        return R.string.tag_fragment_home;
    }
    //endregion

}
