package labs.himegami.bon.models;

import com.estimote.sdk.Beacon;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import labs.himegami.bon.models.base.BaseResponseModel;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 23, 2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeaconWsModel extends BaseResponseModel {

    public String UUID;
    public String major;
    public String minor;

    public BeaconWsModel() {

    }

    public BeaconWsModel(Beacon beacon) {
        this.UUID = beacon.getProximityUUID().toString();
        this.major = String.valueOf(beacon.getMajor());
        this.minor = String.valueOf(beacon.getMinor());
    }

    //region GETTERS AND SETTERS
    @JsonProperty("uuid")
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    @JsonProperty("major")
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @JsonProperty("minor")
    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }
    //endregion

}
