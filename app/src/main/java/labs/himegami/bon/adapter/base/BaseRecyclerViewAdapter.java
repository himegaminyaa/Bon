package labs.himegami.bon.adapter.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import labs.himegami.bon.R;
import labs.himegami.bon.interfaces.OnItemClickListener;
import labs.himegami.bon.interfaces.OnItemLongClickListener;
import labs.himegami.bon.models.base.BaseResponseModel;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 11, 2016
 */
public class BaseRecyclerViewAdapter<A, B extends BaseResponseModel>
        extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {

    protected OnItemClickListener itemClickListener;
    protected OnItemLongClickListener itemLongClickListener;

    protected Context context;
    protected List<B> list;

    protected int lastPosition;

    public BaseRecyclerViewAdapter(Context context, List<B> list) {
        this.context = context;
        this.list = list;
        this.lastPosition = -1;
    }

    //region BIND VIEW HOLDER
    @Override
    public BaseRecyclerViewAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        int layoutRes;

        if (viewType == BaseResponseModel.TYPE_HEADER) {
            layoutRes = getHeaderLayout();
        } else if (viewType == BaseResponseModel.TYPE_TRAILER){
            layoutRes = getTrailerLayout();

        } else {
            layoutRes = getDefaultLayout();
            if (useDefaultCardLayout()) {
                layoutRes = getDefaultCardLayout();
            }
        }

        view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false);

        return new BaseRecyclerViewAdapter.BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int position) {
        B item = getItem(position);

        if (item != null) {
            processItemView(holder.getView(), item);
        }

        if (enableItemAnimation()) {
            setAnimation(holder.getView(), position);
        }
    }
    //endregion

    //region HELPER FUNCTIONS
    public void addItem(B item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
    }

    public void addItem(int position, B item) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void setItem(B model) {
        if (this.list != null) {
            list.set(list.indexOf(model), model);
            notifyItemChanged(list.indexOf(model));
        }
    }

    public void setItem(int position, B model) {
        if (this.list != null) {
            list.set(position, model);
        }
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(B item) {
        int position = list.indexOf(item);
        list.remove(item);
        notifyItemRemoved(position);
    }

    public void removeAllItems() {
        list.clear();
        notifyDataSetChanged();
    }

    public void swapList(@NonNull ArrayList<B> list) {
        if (this.list != null) {
            this.list.clear();
            this.list.addAll(list);
        } else {
            this.list = list;
        }
        notifyDataSetChanged();
    }
    //endregion

    //region PROCESS ITEM VIEW
    protected void processItemView(final View holderView, final B model) {
        if (model.isHeader()) {
            processItemHeaderView(holderView, model);
        } else if (model.isTrailer()) {
            processItemTrailerView(holderView, model);
        } else {
            processItemDefaultView(holderView, model);
        }
    }

    protected void processItemHeaderView(final View holderView, final B model) {
        TextView title = (TextView) holderView.findViewById(R.id.mListItemTitle);
        title.setText("HEADER");
    }

    protected void processItemDefaultView(final View holderView, final B model) {
        TextView title = (TextView) holderView.findViewById(R.id.mListItemTitle);
        TextView subtitle = (TextView) holderView.findViewById(R.id.mListItemSubtitle);

        title.setText(model.getCd());
        subtitle.setText(model.getDscp());
    }

    protected void processItemTrailerView(final View holderView, final B model) {
        TextView mTrailer = (TextView) holderView.findViewById(R.id.mListItemTrailer);
        mTrailer.setText(model.getDscp());
    }

    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
//            Animation animation = getListItemAnimation();
//            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    //endregion

    //region PROPERTIES
    public boolean enableItemAnimation() {
        return true;
    }

    public boolean useDefaultCardLayout() {
        return true;
    }
    //endregion

    //region GETTERS AND SETTERS
    public List<B> getListItems() {
        return list;
    }

    public void setListItems(List<B> mList) {
        this.list = mList;
    }

    public Context getContext() { return context; }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isHeader()) {
            return BaseResponseModel.TYPE_HEADER;
        } else if (getItem(position).isTrailer()) {
            return BaseResponseModel.TYPE_TRAILER;
        } else {
            return BaseResponseModel.TYPE_ITEM;
        }
    }

    public B getItem(int position) {
        if (this.list != null) {
            return list.get(position);
        }

        return null;
    }

    public int getIndexOf(B model) {
        if (this.list != null) {
            return list.indexOf(model);
        }

        return -1;
    }

    public Animation getListItemAnimation() {
        return null;
//        return AnimationUtils.loadAnimation(context, R.anim.list_item_slide_up);
    }

    public int getHeaderLayout() {
        return R.layout.list_item_header;
    }

    public int getTrailerLayout() {
        return R.layout.list_item_trailer;
    }

    public int getDefaultLayout() {
        return R.layout.list_item_default;
    }

    public int getDefaultCardLayout() {
        return R.layout.list_item_card_default;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.itemLongClickListener = mItemLongClickListener;
    }
    //endregion

    //region BASE VIEW HOLDER
    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private View view;

        public BaseViewHolder(View view) {
            super(view);
            this.view = view;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public View getView() {
            return view;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            itemLongClickListener.onItemLongClick(v, getAdapterPosition());
            return true;
        }
    }
    //endregion

}
