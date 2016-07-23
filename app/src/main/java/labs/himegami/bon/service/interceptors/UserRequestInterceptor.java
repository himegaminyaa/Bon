package labs.himegami.bon.service.interceptors;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import labs.himegami.bon.activities.base.BaseMainActivity;
import labs.himegami.bon.models.BonUserModel;
import labs.himegami.bon.providers.DataProvider;
import labs.himegami.bon.service.interceptors.base.BaseRequestInterceptor;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 22, 2016
 */
public class UserRequestInterceptor extends BaseRequestInterceptor {

    private BaseMainActivity activity;
    private BonUserModel userModel;

    public UserRequestInterceptor(BaseMainActivity activity, BonUserModel bonUserModel) {
        this.activity = activity;
        this.userModel = bonUserModel;
    }

    @Override
    protected ClientHttpResponse interceptRequest(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set("Authorization: ", "Token " + userModel.getAuthToken());
        return super.interceptRequest(request, body, execution);
    }

    @Override
    protected boolean shouldInterpretRequest() {
        return false;
    }
}
