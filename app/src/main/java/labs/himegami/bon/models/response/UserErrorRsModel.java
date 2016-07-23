package labs.himegami.bon.models.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import labs.himegami.bon.models.base.BaseResponseModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserErrorRsModel extends BaseResponseModel {

}