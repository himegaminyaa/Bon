package labs.himegami.bon.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.List;

import labs.himegami.bon.R;
import labs.himegami.bon.adapter.base.BaseRecyclerViewAdapter;
import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.fragments.BonTabVoucherListFragment;
import labs.himegami.bon.models.BonVoucherModel;
import labs.himegami.bon.trackers.BonAddressTracker;
import labs.himegami.bon.utilities.animations.ViewAnimationUtilities;
import labs.himegami.bon.utilities.components.BaseDecoder;
import labs.himegami.bon.utilities.formatting.BaseFormatUtilities;
import labs.himegami.bon.utilities.formatting.BaseTimeConverters;
import labs.himegami.bon.utilities.randomgenerator.RandomStringGenerator;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 15, 2016
 */
public class BonVoucherListAdapter extends BaseRecyclerViewAdapter<BonAddressTracker, BonVoucherModel> {

    BonTabVoucherListFragment voucherFragment;

    public BonVoucherListAdapter(Context context, List<BonVoucherModel> list) {
        super(context, list);
    }

    public BonVoucherListAdapter(BonTabVoucherListFragment voucherFragment, List<BonVoucherModel> list) {
        super(voucherFragment.getContext(), list);
        this.voucherFragment = voucherFragment;
    }

    //region PROCESS VIEW
    @Override
    protected void processItemDefaultView(View holderView, final BonVoucherModel model) {
        final AppCompatTextView mTimer = (AppCompatTextView) holderView.findViewById(R.id.mTimer);
        final Switch mSwitch = (Switch) holderView.findViewById(R.id.mSwitch);
        final View mTintSelect = holderView.findViewById(R.id.mTintSelect);
        final View mTimerPanel = holderView.findViewById(R.id.mTimerPanel);
        final View mDisablePanel = holderView.findViewById(R.id.mDisablePanel);

        AppCompatTextView mCode = (AppCompatTextView) holderView.findViewById(R.id.mCode);
        AppCompatTextView mName = (AppCompatTextView) holderView.findViewById(R.id.mName);
        AppCompatTextView mPrice = (AppCompatTextView) holderView.findViewById(R.id.mPrice);
        AppCompatTextView mPromo = (AppCompatTextView) holderView.findViewById(R.id.mPromo);
        AppCompatTextView mPromoCd = (AppCompatTextView) holderView.findViewById(R.id.mPromoCd);
        AppCompatImageView mBackground = (AppCompatImageView) holderView.findViewById(R.id.mBackground);

        View mPanelLeft = holderView.findViewById(R.id.mPanelLeft);

        if (model.getCd() == null) {
            model.setCd(RandomStringGenerator.letter(16).toString());
        }

        mCode.setText(model.getCd());
        mName.setText(model.getVoucherName());
        mPrice.setText(String.format("%s %s",
                BaseFormatUtilities.convertDecimalToString(model.getVoucherPrice()),
                model.getVoucherCurrency()));
        mPromo.setText(model.getVoucherPromo());
        mPromoCd.setText(model.getVoucherPromoCd());

        if (model.getVoucherType() != null) {
            int color = getContext().getResources().getColor(model.getVoucherType().getColor());
            mPromo.setTextColor(color);
            mPanelLeft.setBackgroundColor(color);
            mTintSelect.setBackgroundColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mSwitch.setThumbTintList(ColorStateList.valueOf(color));
            }
        }

        if (model.getVoucherImage() != null) {
            mBackground.setImageBitmap(BaseDecoder.decodeByteArray(model.getVoucherImage()));
        }

        final CountDownTimer timer = new CountDownTimer(model.getCountDownTime(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                model.setCountDownTime(millisUntilFinished);
                mTimer.setText(BaseTimeConverters.convertToTime(millisUntilFinished));
                if (millisUntilFinished < 60000) {
                    mTimer.setTextColor(Color.RED);
                } else {
                    mTimer.setTextColor(getContext().getResources().getColor(R.color.hl_white));
                }
            }

            @Override
            public void onFinish() {
                mSwitch.setChecked(false);
            }
        };

        if (model.isDisabled()) {
            model.setChecked(false);
            mDisablePanel.setVisibility(View.VISIBLE);
            mSwitch.setEnabled(false);
        } else {
            mDisablePanel.setVisibility(View.GONE);
            mSwitch.setEnabled(true);
        }

//        if (model.isChecked()) {
//            timer.start();
//            model.setSoftUpdate(true);
//            mSwitch.setChecked(true);
//            mTimerPanel.setVisibility(View.VISIBLE);
//            mTintSelect.setVisibility(View.VISIBLE);
//        } else {
//            timer.cancel();
//            mSwitch.setChecked(false);
//            mTimerPanel.setVisibility(View.GONE);
//            mTintSelect.setVisibility(View.GONE);
//        }

        mSwitch.setChecked(model.isChecked());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.setChecked(isChecked);
                if (isChecked) {
                    timer.start();
                    ViewAnimationUtilities.expand(mTimerPanel, ViewAnimationUtilities.VERTICAL);
                    ViewAnimationUtilities.expand(mTintSelect, ViewAnimationUtilities.HORIZONTAL);
                    voucherFragment.activateVoucher(model, true);
                } else {
                    timer.cancel();
                    ViewAnimationUtilities.collapse(mTimerPanel, ViewAnimationUtilities.VERTICAL);
                    ViewAnimationUtilities.collapse(mTintSelect, ViewAnimationUtilities.HORIZONTAL);
                    voucherFragment.activateVoucher(model, false);
                }
//                if (!model.isSoftUpdate() || !isChecked) {
//                    model.setCountDownTime(BonDataConstants.VOUCHER_DURATION);
//                    model.setChecked(isChecked);
//                    model.setSoftUpdate(false);
//                    if (isChecked) {
//                        timer.start();
//                        ViewAnimationUtilities.expand(mTimerPanel, ViewAnimationUtilities.VERTICAL);
//                        ViewAnimationUtilities.expand(mTintSelect, ViewAnimationUtilities.HORIZONTAL);
//                    } else {
//                        timer.cancel();
//                        ViewAnimationUtilities.collapse(mTimerPanel, ViewAnimationUtilities.VERTICAL);
//                        ViewAnimationUtilities.collapse(mTintSelect, ViewAnimationUtilities.HORIZONTAL);
//                    }
//                }
            }
        });
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
        return R.layout.bon_list_item_voucher;
    }
    //endregion

}
