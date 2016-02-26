package meta.simplifi.core.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import meta.simplifi.core.BR;
import meta.simplifi.core.R;
import meta.simplifi.core.viewmodel.BaseViewModel;

/**
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/25/2016.
 */
public class BindingRecyclerAdapter<T extends BaseViewModel>
        extends RecyclerView.Adapter<BindingRecyclerAdapter.BindingViewHolder>
        implements View.OnClickListener {

    public static final String TAG = BindingRecyclerAdapter.class.getSimpleName();

    private final Object mLock = new Object();
    protected ObservableArrayList<T> mViewModels;
    protected OnItemClickListener mItemClickListener;

    public BindingRecyclerAdapter() {
        mViewModels = new ObservableArrayList<>();
    }

    public BindingRecyclerAdapter(ObservableArrayList<T> viewModels) {
        mViewModels = viewModels;
    }

    public final T getItem(int position) {
        return mViewModels.get(position);
    }

    public final int getItemPosition(T viewModel) {
        return mViewModels.indexOf(viewModel);
    }

    public final void addOnListChangedCallback(ObservableList.OnListChangedCallback callback) {
        mViewModels.addOnListChangedCallback(callback);
    }

    public final void removeOnListChangedCallback(ObservableList.OnListChangedCallback callback) {
        mViewModels.removeOnListChangedCallback(callback);
    }

    /**
     * Adds the specified item at the end of the array.
     *
     * @param item The item to add at the end of the array.
     */
    public final void add(T item) {
        synchronized (mLock) {
            mViewModels.add(item);
            notifyItemInserted(mViewModels.size() - 1);
        }
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param items The Collection to add at the end of the array.
     */
    public final void addAll(Collection<T> items) {
        synchronized (mLock) {
            mViewModels.addAll(items);
            notifyItemRangeChanged(mViewModels.size() - items.size(), items.size());
        }
    }

    /**
     * Adds the specified item at the end of the array.
     *
     * @param item The item to add at the end of the array.
     */
    public final void add(int position, T item) {
        synchronized (mLock) {
            mViewModels.add(position, item);
            notifyItemInserted(position);
        }
    }

    /**
     * Removes the specified item from the array.
     *
     * @param item The item to remove.
     */
    public final void remove(T item) {
        synchronized (mLock) {
            final int position = getItemPosition(item);
            mViewModels.remove(item);
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes the specified position
     *
     * @param position
     */
    public final void remove(int position) {
        synchronized (mLock) {
            mViewModels.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes all items in the array.
     */
    public final void clear() {
        synchronized (mLock) {
            mViewModels.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mViewModels.get(position).getLayoutId();
    }

    @Override
    public final int getItemCount() {
        return mViewModels.size();
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        setAdapterAsClickListener(holder, holder.itemView);
        BaseViewModel viewModel = mViewModels.get(position);
        holder.binding.setVariable(BR.viewModel, viewModel);
        holder.binding.executePendingBindings();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, @LayoutRes int viewType) {
        View view = inflateView(parent, viewType);
        return new BindingViewHolder(view);
    }

    protected View inflateView(ViewGroup parent, @LayoutRes int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    public final void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public final boolean hasOnItemClickListener() {
        return mItemClickListener != null;
    }

    protected final void setAdapterAsClickListener(@NonNull BindingViewHolder holder, @NonNull View... views) {
        for (View view : views) {
            if (view != null) {
                view.setTag(R.id.tag_key_view_holder, holder);
                view.setOnClickListener(this);
            }
        }
    }

    protected final ObservableArrayList<T> getViewModels() {
        return mViewModels;
    }

    protected final OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag(R.id.tag_key_view_holder);
        if (tag == null) {
            Log.w(TAG, "The clicked view doesn't have a tag! The click event was ignored!");
            return;
        }

        int position = ((BindingViewHolder) tag).getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            mItemClickListener.onItemClick(v, position);
        }
    }

    /**
     *
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    /**
     * @param <T>
     */
    public static class BindingViewHolder<T extends ViewDataBinding>
            extends RecyclerView.ViewHolder {

        public final T binding;

        /**
         * @param itemView
         */
        public BindingViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
