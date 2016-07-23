package labs.himegami.bon.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import labs.himegami.bon.R;
import labs.himegami.bon.activities.BonPostLoginScanActivity;
import labs.himegami.bon.activities.BonPreLoginScanActivity;
import labs.himegami.bon.adapter.BonEstablishmentAdapter;
import labs.himegami.bon.adapter.base.BaseRecyclerViewAdapter;
import labs.himegami.bon.constants.BonDataConstants;
import labs.himegami.bon.fragments.base.BaseListFragment;
import labs.himegami.bon.models.BonEstablishmentModel;
import labs.himegami.bon.models.BonMerchantModel;
import labs.himegami.bon.models.response.BaseErrorHandler;
import labs.himegami.bon.models.response.FormParamValueMap;
import labs.himegami.bon.providers.DataProvider;
import labs.himegami.bon.service.interceptors.UserRequestInterceptor;
import labs.himegami.bon.trackers.BonAddressTracker;
import labs.himegami.bon.utilities.debugging.Logger;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 20, 2016
 */
@EFragment
public class BonMerchantListFragment extends BaseListFragment<BonAddressTracker, BonEstablishmentModel> {

    //region VARIABLES
    @Bean
    public BaseErrorHandler errorHandler;

    protected ImageLoader imageLoader;
    protected BeaconManager beaconManager;
    protected Region region;

    protected boolean hasLoaded;
    //endregion

    //region SETUP
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof  BonPreLoginScanActivity) {
            imageLoader = getPreLoginScanActivity().getImageLoader();
        } else {
            imageLoader = getPostLoginScanActivity().getImageLoader();
        }
    }

    @Override
    protected void setupAddressTracker() {
        addressTracker = getBaseActivity().getAddressTracker();
    }

    @Override
    public void setInterceptors() {
        if (getPostLoginScanActivity() != null) {
            UserRequestInterceptor interceptor =
                    new UserRequestInterceptor(getPostLoginScanActivity(), DataProvider.bonUser);
            RestTemplate template = getPostLoginScanActivity().getRestService().getRestTemplate();

            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
            interceptors.add(interceptor);

            template.setInterceptors(interceptors);
            getPostLoginScanActivity().getRestService().setRestTemplate(template);
        }
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected BaseRecyclerViewAdapter<BonAddressTracker, BonEstablishmentModel> createListAdapter(Context context, ArrayList<BonEstablishmentModel> itemList) {
        return new BonEstablishmentAdapter(this, itemList);
    }
    //endregion

    //region LIFE CYCLE
    @Override
    public void onResume() {
        hasLoaded = false;
        super.onResume();
        callWebservice();
    }

    @Override
    public void onPause() {
        beaconManager.stopRanging(region);
        beaconManager.disconnect();
        super.onPause();
    }
    //endregion

    //region SERVICE CALL
    @Override
    public void callWebservice() {
        onPreExecute();
        region = new Region("Region", UUID.fromString(BonDataConstants.BON_UUID), null, null);
        if (getPreLoginScanActivity() != null) {
            beaconManager = getPreLoginScanActivity().getBeaconManager();
        } else {
            beaconManager = getPostLoginScanActivity().getBeaconManager();
        }
        beaconManager.setForegroundScanPeriod(1000, 5000);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    itemList = new ArrayList<>();
                    for (Beacon beacon : list) {
                        itemList.add(getEstablishmentModel(beacon));
                    }
                    if (!hasLoaded) {
                        onExecution();
                        Logger.d(getClass(), getString(R.string.debug_message_web_service_stopped));
                    } else {
                        getEstablishmentsWS(itemList);
                    }
                }
            }
        });
    }

    protected BonEstablishmentModel getEstablishmentModel(Beacon beacon) {
        BonEstablishmentModel model = new BonEstablishmentModel();
        if (model.getMerchantModel() == null) {
            model.setMerchantModel(new BonMerchantModel());
        }
        model.getMerchantModel().setBeacon(beacon);
        return model;
    }

    @Override
    protected ArrayList<BonEstablishmentModel> getItems(ArrayList<BonEstablishmentModel> itemList) {
        return this.itemList;
    }

    @Override
    public void doOnPostExecute() {
        super.doOnPostExecute();
        getEstablishmentsWS(getAdapterList());
        hasLoaded = true;
    }

    @Background
    protected void getEstablishmentsWS(ArrayList<BonEstablishmentModel> list) {
        Logger.d(getClass(), "Getting establishments");
        if (list != null) {
            if (list.size() > 0) {
                List<BonEstablishmentModel> elist = new ArrayList<>();
                for (BonEstablishmentModel establishment : list) {
                    ResponseEntity<BonEstablishmentModel> establishmentEntity;

//                    FormParamValueMap values = new FormParamValueMap();
//                    values.add("uuid", establishment.getMerchantModel().getBeacon().getProximityUUID().toString());
//                    values.add("major", String.valueOf(establishment.getMerchantModel().getBeacon().getMajor()));
//                    values.add("minor", String.valueOf(establishment.getMerchantModel().getBeacon().getMinor()));

                    if (getPreLoginScanActivity() != null) {
//                        establishmentEntity = getPreLoginScanActivity().getRestService().getEstablishment(values);
                        establishmentEntity = getPreLoginScanActivity().getRestService().
                                getEstablishment(establishment.getMerchantModel().getBeacon().getProximityUUID().toString(),
                                        String.valueOf(establishment.getMerchantModel().getBeacon().getMajor()),
                                        String.valueOf(establishment.getMerchantModel().getBeacon().getMinor()));
                    } else {
//                        establishmentEntity = getPostLoginScanActivity().getRestService().getEstablishment(values);
                        establishmentEntity = getPostLoginScanActivity().getRestService().
                                getEstablishment(establishment.getMerchantModel().getBeacon().getProximityUUID().toString(),
                                        String.valueOf(establishment.getMerchantModel().getBeacon().getMajor()),
                                        String.valueOf(establishment.getMerchantModel().getBeacon().getMinor()));
                    }

                    if (establishmentEntity != null) {
                        BonEstablishmentModel model = establishmentEntity.getBody();
                        elist.add(establishmentEntity.getBody());
                        model.getMerchantModel().setBeacon(list.get(elist.indexOf(model)).getMerchantModel().getBeacon());
                    }
                }
                getEstablishmentsUI(elist);

//                ResponseEntity<ArrayList> establishmentsEntity;
//
//                if (getPreLoginScanActivity() != null) {
//                    establishmentsEntity = getPreLoginScanActivity().getRestService().getEstablishments(getParsedAddress(list));
//                } else {
//                    establishmentsEntity = getPostLoginScanActivity().getRestService().getEstablishments(getParsedAddress(list));
//                }
//
//                if (establishmentsEntity != null) {
//                    ArrayList<BonEstablishmentModel> elist = new ObjectMapper().convertValue(establishmentsEntity.getBody(),
//                            new TypeReference<List<BonEstablishmentModel>>() { });
//                    getEstablishmentsUI(elist);
//                } else {
//                    getEstablishmentsUI(list);
//                }
            }
        } else {
            getEstablishmentsUI(list);
        }
    }

    @UiThread
    protected void getEstablishmentsUI(List<BonEstablishmentModel> list) {
        getAdapter().setListItems(list);
        getAdapter().notifyDataSetChanged();
        Logger.d(getClass(), String.format("Retrieved %d establishments", list.size()));
    }

    protected String getParsedAddress(ArrayList<BonEstablishmentModel> list) {
        StringBuilder builder = new StringBuilder("?beacons=[");
        for (BonEstablishmentModel establishment : list) {
            builder.append(String.format("{\"uuid\":\"%s\",\"major\":\"%s\",\"minor\":\"%s\"}",
                    establishment.getMerchantModel().getBeacon().getProximityUUID().toString().toUpperCase(),
                    String.valueOf(establishment.getMerchantModel().getBeacon().getMajor()),
                            String.valueOf(establishment.getMerchantModel().getBeacon().getMinor())));
            if (list.indexOf(establishment) < list.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }
    //endregion

    //region ON CLICK
    @Override
    public void doOnItemClick(View view, BonEstablishmentModel model) {
        if (getActivity() instanceof BonPostLoginScanActivity) {
            DataProvider.establishment = model;

            getPostLoginScanActivity().getSocket().emit(BonDataConstants.IO_EMIT_IMPRESSION, "");
            Intent intent = new Intent(getActivity(), getAddressTracker().getMainActivity());
            startActivity(intent);
//            getActivity().finish();
        } else if (getActivity() instanceof BonPreLoginScanActivity) {
            // do something
        }
    }
    //endregion

    //region PROPERTIES
    @Override
    public boolean shouldCallWebService() {
        return false;
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    public int getFragmentTag() {
        return R.string.tag_fragment_merchant;
    }

    public BonPreLoginScanActivity getPreLoginScanActivity() {
        if (getActivity() instanceof BonPreLoginScanActivity) {
            return (BonPreLoginScanActivity)getActivity();
        } else {
            return null;
        }
    }

    public BonPostLoginScanActivity getPostLoginScanActivity() {
        if (getActivity() instanceof BonPostLoginScanActivity) {
            return (BonPostLoginScanActivity)getActivity();
        } else {
            return null;
        }
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    //endregion

}
