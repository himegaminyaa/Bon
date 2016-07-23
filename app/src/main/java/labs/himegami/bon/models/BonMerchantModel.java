package labs.himegami.bon.models;

import android.os.Parcel;

import com.estimote.sdk.Beacon;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.UUID;

import labs.himegami.bon.models.base.BaseResponseModel;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 19, 2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BonMerchantModel extends BaseResponseModel {

    private byte[] merchantImg;
    private String merchantImgLink;
    private String merchantName;
    private String merchantDesc;
    private String merchantId;
    private float distance;

    private Beacon beacon;

    public BonMerchantModel() {

    }

    //region PARCELABLE
    protected BonMerchantModel(Parcel in) {
        super(in);
        in.readByteArray(merchantImg);
        merchantImgLink = in.readString();
        merchantName = in.readString();
        merchantDesc = in.readString();
        merchantId = in.readString();
        distance = in.readFloat();
        beacon = in.readParcelable(Beacon.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(beacon, flags);
        dest.writeByteArray(merchantImg);
        dest.writeString(merchantImgLink);
        dest.writeString(merchantName);
        dest.writeString(merchantId);
        dest.writeFloat(distance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BonMerchantModel> CREATOR = new Creator<BonMerchantModel>() {
        @Override
        public BonMerchantModel createFromParcel(Parcel in) {
            return new BonMerchantModel(in);
        }

        @Override
        public BonMerchantModel[] newArray(int size) {
            return new BonMerchantModel[size];
        }
    };
    //endregion

    public static boolean compareBeacons(BonMerchantModel x, BonMerchantModel y) {
        if (!x.getBeacon().getProximityUUID().equals(y.getBeacon().getProximityUUID())) {
            return false;
        } else if (x.getBeacon().getMajor() != y.getBeacon().getMajor()) {
            return false;
        } else if (x.getBeacon().getMinor() != y.getBeacon().getMinor()) {
            return false;
        } else {
            return true;
        }
    }

    //region GETTERS AND SETTERS
    public byte[] getMerchantImg() {
        return merchantImg;
    }

    public void setMerchantImg(byte[] merchantImg) {
        this.merchantImg = merchantImg;
    }

    @JsonProperty("brandlogo")
    public String getMerchantImgLink() {
        return merchantImgLink;
    }

    public void setMerchantImgLink(String merchantImgLink) {
        this.merchantImgLink = merchantImgLink;
    }

    @JsonProperty("brand_name")
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantDesc() {
        return merchantDesc;
    }

    public void setMerchantDesc(String merchantDesc) {
        this.merchantDesc = merchantDesc;
    }

    @JsonProperty("id")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
    //endregion

}
