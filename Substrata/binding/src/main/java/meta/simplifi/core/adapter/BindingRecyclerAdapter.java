package meta.simplifi.core.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;

import meta.simplifi.core.R;
import meta.simplifi.core.adapter.viewholder.BindingViewHolder;
import meta.simplifi.core.viewmodel.BindingViewModel;

/**
 * This {@link RecyclerView.Adapter} works out of the box by requiring only that the items placed
 * inside extend {@link BindingViewModel} and that for each view-model, a corresponding
 * {@link ViewDataBinding} with a viewModel variable be defined. If these conditions are met the adapter
 * will work automatically with any number of view types.
 * This class will create an array for you if none is provided and modifying the it can be done
 * through the provided interfaces. This will also automate the adapter notification process when
 * changes to the data set occur. The array in this adapter is observable and changes to it can be
 * listened for through the provided registration method.
 * <p/>
 * Created by I. Gogolev, M. Silva.
 */
public class BindingRecyclerAdapter<T extends BindingViewModel, VH extends BindingViewHolder>
        extends RecyclerView.Adapter<VH>
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
    private final ArrayList<T> mItems;
    protected OnItemClickListener mItemClickListener;
    private Class<VH> mViewHolderClass;

    /**
     * Creates an instance of this adapter and initializes the array.
     */
    public BindingRecyclerAdapter(Class<VH> viewHolderClass) {
        mViewHolderClass = viewHolderClass;
        mItems = new ArrayList<>();
    }

    /**
     * Creates an instance of this adapter and creates and uses the passed-in list of items
     * as the array.
     *
     * @param items The items to bind to the adapter.
     */
    public BindingRecyclerAdapter(Class<VH> viewHolderClass, ArrayList<T> items) {
        mViewHolderClass = viewHolderClass;
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
     * Binds the view-model to the view-holder. Searches for a setViewModel method
     * in the holder's binding. If none is present, the data will not be bound to the holder.
     * If your holder has numerous variables, you should override this method for your specific
     * variables and subsequently call super. Additional click listener registration, such as for
     * nested controls should be done here.
     *
     * @param holder   The view holder containing the {@link ViewDataBinding}
     * @param position The position of the item in the array.
     */
    @Override
    public void onBindViewHolder(VH holder, int position) {
        setAdapterAsClickListener(holder, holder.itemView);
        BindingViewModel viewModel = mItems.get(position);
        holder.binding.setVariable(viewModel.getVariableId(), viewModel);
        holder.binding.executePendingBindings();
    }

    /**
     * Inflates the view specified by the {@link BindingViewModel}'s layout id and creates a
     * {@link BindingViewHolder}.
     *
     * @param parent   The parent view group to attach the inflated view to.
     * @param viewType The layout id of the view to be inflated and placed in
     *                 the{@link BindingViewHolder}
     * @return The view-holder containing the inflated view.
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    @Override
    public VH onCreateViewHolder(ViewGroup parent, @LayoutRes int viewType) {
        View view = inflateView(parent, viewType);
        try {
            Constructor<VH> constructor = mViewHolderClass.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException e) {
            throw new InvalidParameterException();
        } catch (IllegalAccessException e) {
            throw new InvalidParameterException();
        } catch (InstantiationException e) {
            throw new InvalidParameterException();
        } catch (InvocationTargetException e) {
            throw new InvalidParameterException();
        }
    }

    /**
     * A convenience method for inflating views.
     *
     * @param parent    The parent view group to attach the inflated view to.
     * @param layoutRes The layout id of the view to be inflated
     * @return The inflated view.
     */
    protected View inflateView(@NonNull ViewGroup parent, @LayoutRes int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    /**
     * Sets the item click listener on this adapter. Only one may be registered per adapter.
     * Callbacks will be sent to the listener when items inside of the recycler/adapter are clicked.
     * If the item in the adapter has nested controls, registering here will not be sufficient.
     * onBindViewHolder should be overridden and setAdapterAsClickListener should be called for all
     * nested controls.
     *
     * @param listener The listener to be called when an entry in the recycler is clicked.
     */
    public final void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * Checks for the presence of an itemClickListener.
     *
     * @return {@code false} if there is no {@link OnItemClickListener} registered.
     */
    public final boolean hasOnItemClickListener() {
        return mItemClickListener != null;
    }

    /**
     * Convenience method setting this instance of the adapter as the click listener for any view
     * that is passed in.
     *
     * @param holder The view-holder containing the views.
     * @param views  The views to register listeners on.
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
     * Convenience method returning the items contained in this adapter.
     *
     * @return The array contained within this adapter.
     */
    protected final ArrayList<T> getItems() {
        return mItems;
    }

    /**
     * @param v The clicked view.
     */
    @Override
    public void onClick(View v) {
        Object tag = v.getTag(R.id.tag_key_view_holder);
        if (tag == null) {
            Log.w(TAG, "The clicked view doesn't have a tag! The click event was ignored!");
            return;
        }

        int position = ((BindingViewHolder) tag).getAdapterPosition();
        if (position != RecyclerView.NO_POSITION && mItemClickListener != null) {
            mItemClickListener.onItemClick(v, position);
        }
    }

    /**
     * Interface definition for a callback to be invoked when a view in the {@link RecyclerView}
     * is clicked.
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}

