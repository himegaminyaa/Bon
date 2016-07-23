package labs.himegami.bon.models;

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
public class BonEstablishmentModel extends BaseResponseModel {

    private String location;
    private BonMerchantModel merchantModel;
    private String greeting;

    public BonEstablishmentModel() {

    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("partner")
    public BonMerchantModel getMerchantModel() {
        return merchantModel;
    }

    public void setMerchantModel(BonMerchantModel merchantModel) {
        this.merchantModel = merchantModel;
    }

    @JsonProperty("greeting")
    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
