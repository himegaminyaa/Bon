package labs.himegami.bon.models;

import android.os.Parcel;

import java.math.BigDecimal;
import java.util.List;

import labs.himegami.bon.models.base.BaseResponseModel;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 16, 2016
 */
public class BonMenuModel extends BaseResponseModel {

    private String title;
    private String name;
    private BigDecimal price;
    private String priceString;
    private String desc;

    public BonMenuModel() {

    }

    //region PARCELABLE
    protected BonMenuModel(Parcel in) {
        super(in);
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseResponseModel> CREATOR = new Creator<BaseResponseModel>() {
        @Override
        public BaseResponseModel createFromParcel(Parcel in) {
            return new BonMenuModel(in);
        }

        @Override
        public BaseResponseModel[] newArray(int size) {
            return new BaseResponseModel[size];
        }
    };
    //endregion

    //region GETTERS AND SETTERS
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    //endregion

}
