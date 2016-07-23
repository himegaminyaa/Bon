package labs.himegami.bon.service.interceptors.base;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 22, 2016
 */
public abstract class BaseRequestInterceptor implements ClientHttpRequestInterceptor {

    //region INTERCEPT REQUEST
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        if (shouldInterpretRequest()) {
            interpretRequest(request, body, execution);
        }
        return interceptRequest(request, body, execution);
    }

    protected ClientHttpResponse interceptRequest(HttpRequest request, byte[] body,
                                                  ClientHttpRequestExecution execution) throws IOException {
        return execution.execute(request, body);
    }
    //endregion

    //region INTERPRET REQUEST
    protected void interpretRequest(HttpRequest request, byte[] body,
                                    ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        if (response.getBody() != null) {
            getStringFromInputStream(response.getBody());
        }
    }

    protected String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
    //endregion

    //region PROPERTY
    protected boolean shouldInterpretRequest() {
        return false;
    }
    //endregion

}
