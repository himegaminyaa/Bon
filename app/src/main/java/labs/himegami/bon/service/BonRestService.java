package labs.himegami.bon.service;

import com.estimote.sdk.repackaged.retrofit_v1_9_0.retrofit.http.Query;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.models.BeaconWsModel;
import labs.himegami.bon.models.BonEstablishmentModel;
import labs.himegami.bon.models.BonUserModel;
import labs.himegami.bon.models.response.FormParamValueMap;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 16, 2016
 */
@Rest(rootUrl = BonDataConstants.BON_ROOT_URL, converters = {
                FormHttpMessageConverter.class,
                MappingJacksonHttpMessageConverter.class})
public interface BonRestService extends RestClientErrorHandling {

    RestTemplate getRestTemplate();
    void setRestTemplate(RestTemplate restTemplate);

    String getRootUrl();
    void setRootUrl(String rootUrl);

    @Post("register/")
    ResponseEntity<BonUserModel> register(FormParamValueMap values);

    @Post("auth/login/")
    ResponseEntity<BonUserModel> login(FormParamValueMap values);

    @Post("auth/logout/")
    ResponseEntity<BonUserModel> logout();

    @Get("establishments/{cheat}")
    @Accept(MediaType.APPLICATION_JSON)
    ResponseEntity<ArrayList> getEstablishments(String cheat);

    @Get("establishments/?uuid={uuid}&major={major}&minor={minor}")
    @Accept(MediaType.APPLICATION_JSON)
    ResponseEntity<BonEstablishmentModel> getEstablishment(String uuid, String major, String minor);

    /**
     *  Regular Register
     *  {
     *      first_name
     *      last_name
     *      username
     *      email
     *      password
     *  }
     *
     *  Regular Login
     *  {
     *      username = [email]
     *      password
     *  }
     *
     *  FB Register
     *  {
     *      first_name
     *      last_name
     *      email
     *      fb_id
     *  }
     *
     *  FB Login
     *  {
     *      email
     *      fb_id
     *  }
     */
}
