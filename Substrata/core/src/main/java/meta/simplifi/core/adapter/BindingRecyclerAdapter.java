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
 * This {@link RecyclerView.Adapter} works out of the box by requiring only that the items placed
 * inside extend {@link BaseViewModel} and that for each view-model, a corresponding
 * {@link ViewDataBinding} with a viewModel variable defined. If these conditions are met the adapter
 * will work automatically with any number of view types.
 * This class will create an array for you if none is provided and modifying the it can be done
 * through the provided interfaces. This will also automate the adapter notification process when
 * changes to the data set occur. The array in this adapter is observable and changes to it can be
 * listened for through the provided registration method.
 * <p/>
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/25/2016.
 */
public class BindingRecyclerAdapter<T extends BaseViewModel>
        extends RecyclerView.Adapter<BindingRecyclerAdapter.BindingViewHolder>
        implements View.OnClickListener {

    public static final String TAG = BindingRecyclerAdapter.class.getSimpleName();

    /**
     * Lock used to modify the content of {@link #mItems}. Any write operation
     * performed on the array should be synchronized on this lock.
     */
    private final Object mLock = new Object();

    /**
     * Contains the list of view-models that contain the data of to be rendered by this
     * RecyclerView.Adapter. The this list is referred to as "the array" in the documentation.
     */
    protected ObservableArrayList<T> mItems;
    protected OnItemClickListener mItemClickListener;

    public BindingRecyclerAdapter() {
        mItems = new ObservableArrayList<>();
    }

    public BindingRecyclerAdapter(ObservableArrayList<T> items) {
        mItems = items;
    }

    /**
     * Returns the item at the specified position in the array.
     */
    public final T getItem(int position) {
        return mItems.get(position);
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public final int getItemPosition(T item) {
        return mItems.indexOf(item);
    }

    public final void addOnListChangedCallback(ObservableList.OnListChangedCallback callback) {
        mItems.addOnListChangedCallback(callback);
    }

    public final void removeOnListChangedCallback(ObservableList.OnListChangedCallback callback) {
        mItems.removeOnListChangedCallback(callback);
    }

    /**
     * Adds the specified item at the end of the array.
     *
     * @param item The item to add at the end of the array.
     */
    public final void add(T item) {
        synchronized (mLock) {
            mItems.add(item);
            notifyItemInserted(mItems.size() - 1);
        }
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param items The Collection to add at the end of the array.
     */
    public final void addAll(Collection<T> items) {
        synchronized (mLock) {
            mItems.addAll(items);
            notifyItemRangeChanged(mItems.size() - items.size(), items.size());
        }
    }

    /**
     * Adds the specified item at the end of the array.
     *
     * @param item The item to add at the end of the array.
     */
    public final void add(int position, T item) {
        synchronized (mLock) {
            mItems.add(position, item);
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
            mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes the item at the specified position
     *
     * @param position Index of the item to remove.
     */
    public final void remove(int position) {
        synchronized (mLock) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes all items in the array.
     */
    public final void clear() {
        synchronized (mLock) {
            mItems.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * For this adapter implementation, returns the layout ID for the item at the specified position.
     *
     * @param position The position of the item in the adapter.
     * @return The layout resource ID for the item at the specified position.
     */
    @LayoutRes
    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getLayoutId();
    }

    @Override
    public final int getItemCount() {
        return mItems.size();
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        setAdapterAsClickListener(holder, holder.itemView);
        BaseViewModel viewModel = mItems.get(position);
        holder.binding.setVariable(BR.viewModel, viewModel);
        holder.binding.executePendingBindings();
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, @LayoutRes int viewType) {
        View view = inflateView(parent, viewType);
        return new BindingViewHolder(view);
    }

    /**
     * @param parent
     * @param layoutRes
     * @return
     */
    protected View inflateView(ViewGroup parent, @LayoutRes int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    /**
     * @param listener
     */
    public final void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * @return
     */
    public final boolean hasOnItemClickListener() {
        return mItemClickListener != null;
    }

    /**
     * @param holder
     * @param views
     */
    protected final void setAdapterAsClickListener(@NonNull BindingViewHolder holder, @NonNull View... views) {
        for (View view : views) {
            if (view != null) {
                view.setTag(R.id.tag_key_view_holder, holder);
                view.setOnClickListener(this);
            }
        }
    }

    /**
     * @return
     */
    protected final ObservableArrayList<T> getItems() {
        return mItems;
    }

    /**
     * @return
     */
    protected final OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    /**
     * @param v
     */
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
