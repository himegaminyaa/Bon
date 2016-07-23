package labs.himegami.bon.models;

import android.os.Parcel;

import java.math.BigDecimal;

import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.enums.VoucherTypeEnum;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.utilities.formatting.BaseFormatUtilities;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 15, 2016
 */
public class BonVoucherModel extends BaseResponseModel {

    private String voucherPromo;
    private String voucherPromoCd;
    private String voucherName;
    private String voucherCurrency;
    private BigDecimal voucherPrice;
    private VoucherTypeEnum voucherType;
    private byte[] voucherImage;
    private boolean isChecked;
    private boolean isDisabled;
    private long countDownTime = BonDataConstants.VOUCHER_DURATION;

    public BonVoucherModel() {

    }

    //region PARCELABLE
    protected BonVoucherModel(Parcel in) {
        super(in);
        voucherPromo = in.readString();
        voucherPromoCd = in.readString();
        voucherName = in.readString();
        voucherCurrency = in.readString();
        voucherPrice = BaseFormatUtilities.convertStringToDecimal(in.readString());
        voucherType = VoucherTypeEnum.findVoucherTypeUsingKey(in.readInt());
        in.readByteArray(voucherImage);
        isChecked = Boolean.getBoolean(in.readString());
        isDisabled = Boolean.getBoolean(in.readString());
        countDownTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(voucherPromo);
        dest.writeString(voucherPromoCd);
        dest.writeString(voucherName);
        dest.writeString(voucherCurrency);
        dest.writeString(BaseFormatUtilities.convertDecimalToString(voucherPrice));
        dest.writeInt(voucherType.getKey());
        dest.writeByteArray(voucherImage);
        dest.writeString(Boolean.toString(isChecked));
        dest.writeString(Boolean.toString(isDisabled));
        dest.writeLong(countDownTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseResponseModel> CREATOR = new Creator<BaseResponseModel>() {
        @Override
        public BaseResponseModel createFromParcel(Parcel in) {
            return new BonVoucherModel(in);
        }

        @Override
        public BaseResponseModel[] newArray(int size) {
            return new BaseResponseModel[size];
        }
    };
    //endregion

    //region GETTERS AND SETTERS
    public String getVoucherPromo() {
        return voucherPromo;
    }

    public void setVoucherPromo(String voucherPromo) {
        this.voucherPromo = voucherPromo;
    }

    public String getVoucherPromoCd() {
        return voucherPromoCd;
    }

    public void setVoucherPromoCd(String voucherPromoCd) {
        this.voucherPromoCd = voucherPromoCd;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getVoucherCurrency() {
        return voucherCurrency;
    }

    public void setVoucherCurrency(String voucherCurrency) {
        this.voucherCurrency = voucherCurrency;
    }

    public BigDecimal getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(BigDecimal voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public byte[] getVoucherImage() {
        return voucherImage;
    }

    public void setVoucherType(VoucherTypeEnum voucherType) {
        this.voucherType = voucherType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setVoucherImage(byte[] voucherImage) {
        this.voucherImage = voucherImage;
    }

    public VoucherTypeEnum getVoucherType() {
        return voucherType;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public long getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(long countDownTime) {
        this.countDownTime = countDownTime;
    }
    //endregion


}
