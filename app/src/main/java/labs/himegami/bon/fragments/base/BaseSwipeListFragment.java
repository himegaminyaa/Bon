package labs.himegami.bon.fragments.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import labs.himegami.bon.R;
import labs.himegami.bon.models.base.BaseResponseModel;
import labs.himegami.bon.trackers.base.BaseAddressTracker;
import labs.himegami.bon.utilities.debugging.Logger;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 12, 2016
 */
@EFragment
public abstract class BaseSwipeListFragment<A extends BaseAddressTracker, B extends BaseResponseModel>
        extends BaseListFragment<A, B> implements SwipeRefreshLayout.OnRefreshListener, View.OnTouchListener{

    //region VIEWS
    @ViewById
    protected SwipeRefreshLayout mSwipeRefresh;
    //endregion

    //region VARIABLES
    protected boolean isLoading;
    protected boolean isRefreshing;
    //endregion

    @Override
    public void doOnAfterViewsUI() {
        super.doOnAfterViewsUI();
        setupSwipeRefreshGesture();
        setupRecyclerViewGesture();
    }

    //region LISTENERS
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v instanceof SwipeRefreshLayout) {
            onTouchSwipeRefresh(v, event);
        } else if (v instanceof RecyclerView) {
            onTouchRecyclerView(v, event);
        }
        return false;
    }

    protected void onTouchSwipeRefresh(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!isRefreshing) {
                }
                break;

            case MotionEvent.ACTION_UP:
                if (!isRefreshing) {
                    try {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, 500);
                    } catch (NullPointerException e) {
                        Logger.e(getClass(), e.getMessage());
                    }
                }
                break;
        }
    }

    protected void onTouchRecyclerView(View v, MotionEvent event) {
        mSwipeRefresh.setEnabled(true);
    }
    //endregion

    //region GESTURES
    protected void setupSwipeRefreshGesture() {
        if (!monoSwipeRefresh()) {
            mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.RED, Color.YELLOW);
        } else {
            mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.hl_color_900));
        }

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setOnTouchListener(this);
    }

    protected void setupRecyclerViewGesture() {
        initRecyclerViewTransitions();
        mRecyclerView.setOnTouchListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // LOAD MORE ITEMS
                if (dy > 0) {
                    if (!isLoading) {
                        // This one works but has restrictions
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            if (!mRecyclerView.canScrollVertically(dy)) {
                                doOnReachingBottom();
                            }
                        }
                    }
                }
            }
        });
    }

    public void initRecyclerViewTransitions() {

    }

    protected void doOnReachingBottom() {
    }
    //endregion

    //region SERVICE CALL
    @Override
    public void doOnPreExecute() {
        if (!isRefreshing) {
            if (getView() != null) {
                super.doOnPreExecute();
            }
        }
    }

    @Override
    public void doOnPostExecute() {
        mSwipeRefresh.setRefreshing(isRefreshing = false);
        super.doOnPostExecute();
    }
    //endregion

    //region ON REFRESH
    @Override
    public void onRefresh() {
        doOnRefresh();
    }

    protected void doOnRefresh() {
        currentIndex = 0;
        positionOfItemClicked = 0;
        isRefreshing = true;
        hasLoaded = false;
        if (getAdapter() != null) {
            getAdapter().removeAllItems();
        }
        callWebservice();
    }
    //endregion

    //region PROPERTIES
    protected boolean monoSwipeRefresh() {
        return true;
    }
    //endregion

    //region GETTERS AND SETTERS
    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_list_swipe_base;
    }
    //endregion

}
