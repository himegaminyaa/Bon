package labs.himegami.bon.models.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

import labs.himegami.bon.models.base.BaseResponseModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRsModel extends BaseResponseModel {

	private List<UserErrorRsModel> userErrorRsList;

	//region GETTERS AND SETTERS
	public List<UserErrorRsModel> getUserErrorRsList() {
		return userErrorRsList;
	}

	public void setUserErrorRsList(List<UserErrorRsModel> userErrorRsList) {
		this.userErrorRsList = userErrorRsList;
	}
	//endregion

}