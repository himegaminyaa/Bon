package labs.himegami.bon.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;

import labs.himegami.bon.R;
import labs.himegami.bon.adapter.base.BaseRecyclerViewAdapter;
import labs.himegami.bon.dividers.BaseDividerItemDecoration;
import labs.himegami.bon.interfaces.OnItemClickListener;
import labs.himegami.bon.interfaces.OnItemLongClickListener;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.trackers.base.BaseAddressTracker;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 11, 2016
 */
@EFragment
public abstract class BaseListFragment<A extends BaseAddressTracker, B extends BaseResponseModel>
        extends BaseMainFragment<A, B> implements OnItemClickListener, OnItemLongClickListener {

    //region VIEWS
    @ViewById
    protected RecyclerView mRecyclerView;

    @ViewById
    protected View mLoadingPanel;

    @ViewById
    protected ProgressBar mProgressBar;

    @ViewById
    protected TextView mLoadingMessage;
    //endregion

    //region VARIABLES
    protected boolean isLoading;
    protected boolean hasLoaded;
    protected ArrayList<B> itemList;
    protected ArrayList<B> loadedList;

    protected int positionOfItemClicked;
    protected int currentIndex;

    private BaseRecyclerViewAdapter<A, B> listAdapter;
    private ObjectMapper objectMapper;
    //endregion

    //region SETUP
    @Override
    protected void processOnCreate(@Nullable Bundle savedInstanceState) {
        super.processOnCreate(savedInstanceState);
        setupEmitterListener();

        isLoading = false;
        hasLoaded = false;

        objectMapper = new ObjectMapper();
        positionOfItemClicked = 0;
        currentIndex = 0;

        if (loadedList == null) {
            loadedList = new ArrayList<>();
        }

        if (!shouldReloadUponResume()) {
            if (shouldCallWebService()) {
                callWebservice();
            }
        }
    }

    @Override
    public void doOnAfterViewsUI() {
        super.doOnAfterViewsUI();
        if (mRecyclerView.getLayoutManager() != null) {
            mRecyclerView.setLayoutManager(getLayoutManager(getContext()));
        }
        if (shouldReloadUponResume()) {
            if (shouldCallWebService()) {
                callWebservice();
            }
        }
    }

    protected void setupEmitterListener() {

    }
    //endregion

    //region SERVICE CALL
    @Override
    public void doOnPreExecute() {
        super.doOnPreExecute();
        isLoading = true;
        checkIfLoading();
    }

    @Override
    public void doOnExecution() {
        ArrayList<B> temp = new ArrayList<>();
        itemList = getItems(temp);
        itemList = processItems(itemList);
        hasLoaded = true;
        onPostExecute();
    }

    protected ArrayList<B> processItems(ArrayList<B> itemList) {
        return itemList;
    }

    @Override
    public void doOnPostExecute() {
        super.doOnPostExecute();
        isLoading = false;
        loadItemsToAdapter();
        checkIfLoading();
    }

    protected void loadItemsToAdapter() {
        listAdapter = createListAdapter(getContext(), itemList);
        listAdapter.setOnItemClickListener(this);
        listAdapter.setOnItemLongClickListener(this);

        mRecyclerView.setItemViewCacheSize(itemList.size()); // noob
        mRecyclerView.setLayoutManager(getLayoutManager(getContext()));
        mRecyclerView.setAdapter(listAdapter);

        if (showDivider()) {
            mRecyclerView.
                    addItemDecoration(new BaseDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        }
    }

    public void checkIfLoading() {
        if (isLoading) {
            mRecyclerView.setVisibility(View.GONE);
            mLoadingPanel.setVisibility(View.VISIBLE);
            mLoadingMessage.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            if (itemList != null) {
                if (itemList.size() > 0) {
                    mLoadingPanel.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    doAfterLoading();
                }
            } else {
                doAfterLoading();
            }
        }
    }

    protected void doAfterLoading() {
        if (hasLoaded) {
            mProgressBar.setVisibility(View.GONE);
            mLoadingMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void doOnPause() {
        super.doOnPause();
        if (!shouldReloadUponResume()) {
            if (getAdapter() != null) {
                loadedList = (ArrayList<B>) getAdapter().getListItems();
            }
        }
    }

    @Override
    protected void doOnResume() {
        super.doOnResume();
        if (!loadedList.isEmpty()) {
            setRecentlyLoadedItems(loadedList);
            doOnPostExecute();
            setRecyclerViewToPosition();
        }
    }
    //endregion

    //region ON CLICK
    @Override
    public void onItemClick(View view, int position) {
        positionOfItemClicked = position;
        if (getActivity() != null) {
            if (getAdapter() != null) {
                doOnItemClick(view, (B) getAdapter().getListItems().get(position));
            }
        }
    }

    public void doOnItemClick(View view, B model) {

    }

    @Override
    public void onItemLongClick(View view, int position) {
        positionOfItemClicked = position;
        if (getActivity() != null) {
            if (getAdapter() != null) {
                doOnItemLongClick(view, (B) getAdapter().getListItems().get(position));
            }
        }
    }

    public void doOnItemLongClick(View view, B model) {

    }
    //endregion

    //region PROPERTIES
    protected boolean showDivider() {
        return false;
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_list_base;
    }

    protected abstract RecyclerView.LayoutManager getLayoutManager(Context context);

    protected abstract BaseRecyclerViewAdapter<A, B> createListAdapter(Context context, ArrayList<B> itemList);

    protected abstract ArrayList<B> getItems(ArrayList<B> itemList);

    public BaseRecyclerViewAdapter<A, B> getAdapter() {
        return listAdapter;
    }

    protected ArrayList<B> getAdapterList() {
        return (ArrayList<B>) getAdapter().getListItems();
    }

    protected ArrayList<B> getLoadedList() {
        return loadedList;
    }

    protected ArrayList<B> getList() {
        return itemList;
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public int getItemPosition() {
        return positionOfItemClicked;
    }

    public void setRecentlyLoadedItems(ArrayList<B> itemList) {
        this.itemList = itemList;
    }

    public void setRecyclerViewToPosition() {
        mRecyclerView.clearFocus();
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(positionOfItemClicked);
            }
        });
    }
    //endregion

}
