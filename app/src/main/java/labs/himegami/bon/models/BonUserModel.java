package labs.himegami.bon.models;

import android.os.Parcel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

import labs.himegami.bon.models.base.BaseResponseModel;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class BonUserModel extends BaseResponseModel {

    private String authToken;
    private String firstName;
    private String middleName;
    private String surName;
    private String email;
    private String mobileNumber;
    private BigDecimal bonCredits;
    private BigDecimal bonDebits;
    private BigDecimal totalBonCredits;
    private BigDecimal totalBonDebits;
    private byte[] profilePhoto;
    private Date memberSince;
    private int vouchersUsed;

    public BonUserModel() {

    }

    //region PARCELABLE
    protected BonUserModel(Parcel in) {
        super(in);
        authToken = in.readString();
        firstName = in.readString();
        middleName = in.readString();
        surName = in.readString();
        email = in.readString();
        mobileNumber = in.readString();
        bonCredits = BaseFormatUtilities.convertStringToDecimal(in.readString());
        bonDebits = BaseFormatUtilities.convertStringToDecimal(in.readString());
        totalBonCredits = BaseFormatUtilities.convertStringToDecimal(in.readString());
        totalBonDebits = BaseFormatUtilities.convertStringToDecimal(in.readString());
        in.readByteArray(profilePhoto);
        memberSince = new Date(in.readLong());
        vouchersUsed = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(authToken);
        dest.writeString(firstName);
        dest.writeString(middleName);
        dest.writeString(surName);
        dest.writeString(email);
        dest.writeString(mobileNumber);
        dest.writeString(BaseFormatUtilities.convertDecimalToString(bonCredits));
        dest.writeString(BaseFormatUtilities.convertDecimalToString(bonDebits));
        dest.writeString(BaseFormatUtilities.convertDecimalToString(totalBonCredits));
        dest.writeString(BaseFormatUtilities.convertDecimalToString(totalBonDebits));
        dest.writeByteArray(profilePhoto);
        dest.writeLong(memberSince.getTime());
        dest.writeInt(vouchersUsed);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BonUserModel> CREATOR = new Creator<BonUserModel>() {
        @Override
        public BonUserModel createFromParcel(Parcel in) {
            return new BonUserModel(in);
        }

        @Override
        public BonUserModel[] newArray(int size) {
            return new BonUserModel[size];
        }
    };
    //endregion

    //region GETTERS AND SETTERS
    @JsonProperty("auth_token")
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @JsonProperty("last_name")
    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public BigDecimal getBonCredits() {
        return bonCredits;
    }

    public void setBonCredits(BigDecimal bonCredits) {
        this.bonCredits = bonCredits;
    }

    public BigDecimal getBonDebits() {
        return bonDebits;
    }

    public void setBonDebits(BigDecimal bonDebits) {
        this.bonDebits = bonDebits;
    }

    public BigDecimal getTotalBonCredits() {
        return totalBonCredits;
    }

    public void setTotalBonCredits(BigDecimal totalBonCredits) {
        this.totalBonCredits = totalBonCredits;
    }

    public BigDecimal getTotalBonDebits() {
        return totalBonDebits;
    }

    public void setTotalBonDebits(BigDecimal totalBonDebits) {
        this.totalBonDebits = totalBonDebits;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Date getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(Date memberSince) {
        this.memberSince = memberSince;
    }

    public int getVouchersUsed() {
        return vouchersUsed;
    }

    public void setVouchersUsed(int vouchersUsed) {
        this.vouchersUsed = vouchersUsed;
    }
    //endregion

}
