package labs.himegami.bon.models.base;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 7, 2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseModel implements Parcelable {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_TRAILER = 2;

    private String id;
    private String cd;
    private String dscp;
    private String errorMsg;

    private boolean header;
    private boolean trailer;
    private boolean softUpdate;

    public BaseResponseModel() {

    }

    //region PARCELABLE
    protected BaseResponseModel(Parcel in) {
        id = in.readString();
        cd = in.readString();
        dscp = in.readString();
        errorMsg = in.readString();
        header = in.readByte() != 0;
        trailer = in.readByte() != 0;
        softUpdate = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cd);
        dest.writeString(dscp);
        dest.writeString(errorMsg);
        dest.writeByte((byte) (header ? 1 : 0));
        dest.writeByte((byte) (trailer ? 1 : 0));
        dest.writeByte((byte) (softUpdate ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseResponseModel> CREATOR = new Creator<BaseResponseModel>() {
        @Override
        public BaseResponseModel createFromParcel(Parcel in) {
            return new BaseResponseModel(in);
        }

        @Override
        public BaseResponseModel[] newArray(int size) {
            return new BaseResponseModel[size];
        }
    };
    //endregion

    //region GETTERS AND SETTERS
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getDscp() {
        return dscp;
    }

    public void setDscp(String dscp) {
        this.dscp = dscp;
    }

    @JsonProperty("errorMsg")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isHeader() {
        return header;
    }

    public void setIsHeader(boolean header) {
        this.header = header;
    }

    public boolean isTrailer() {
        return trailer;
    }

    public void setIsTrailer(boolean trailer) {
        this.trailer = trailer;
    }

    public boolean isSoftUpdate() {
        return softUpdate;
    }

    public void setSoftUpdate(boolean softUpdate) {
        this.softUpdate = softUpdate;
    }
    //endregion

}
