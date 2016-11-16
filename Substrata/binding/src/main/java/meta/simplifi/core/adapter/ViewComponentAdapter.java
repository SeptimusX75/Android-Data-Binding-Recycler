package meta.simplifi.core.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import meta.simplifi.core.adapter.viewholder.ComponentViewHolder;
import meta.simplifi.core.adapter.model.RecyclerModel;
import meta.simplifi.core.adapter.viewcomponent.RecyclerViewComponent;

/**
 * Created by M. Silva on 11/9/16.
 */

public class ViewComponentAdapter<T extends RecyclerModel>
        extends RecyclerView.Adapter<ComponentViewHolder> {

    private final Object mLock = new Object();
    private ArrayList<T> mModels = new ArrayList<>();

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ComponentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        T model = mModels.get(position);

        if (holder.component == null) {
            holder.component = createViewComponent(model, holder.itemView);
        }

        holder.onBind(model);
    }

    @Override
    @LayoutRes
    public int getItemViewType(int position) {
        return mModels.get(position).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    @Override
    public void onViewRecycled(ComponentViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(ComponentViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(ComponentViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onAttached();
    }

    @Override
    public void onViewDetachedFromWindow(ComponentViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onDetached();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    private <RVC extends RecyclerViewComponent> RVC createViewComponent(T model, View view) {
        try {
            Constructor<?> constructor =
                    model.getViewComponentClass().getConstructor(model.getRecyclerModelClass(), View.class);
            return (RVC) constructor.newInstance(model, view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the item at the specified position in the array.
     */
    public final T getItem(int position) {
        return mModels.get(position);
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public final int getItemPosition(T item) {
        return mModels.indexOf(item);
    }

    /**
     * Adds the specified item at the end of the array.
     *
     * @param item The item to add at the end of the array.
     */
    public void add(T item) {
        synchronized (mLock) {
            mModels.add(item);
            notifyItemInserted(mModels.size() - 1);
        }
    }

    /**
     * Adds the specified item at the end of the array.
     *
     * @param item The item to add at the end of the array.
     */
    public final void add(int position, T item) {
        synchronized (mLock) {
            mModels.add(position, item);
            notifyItemInserted(position);
        }
    }

    /**
     * Replaces the item at the specified position.
     *
     * @param index the position of the item to replace
     * @param item  The item to write into the specified position.
     */
    public final void set(int index, T item) {
        synchronized (mLock) {
            mModels.set(index, item);
            notifyItemChanged(index);
        }
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param items The Collection to add at the end of the array.
     */
    public final void addAll(Collection<T> items) {
        synchronized (mLock) {
            mModels.addAll(items);
            notifyItemRangeChanged(mModels.size() - items.size(), items.size());
        }
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param items The Collection to add at the end of the array.
     */
    public final void addAll(int index, Collection<T> items) {
        synchronized (mLock) {
            mModels.addAll(index, items);
            notifyItemRangeChanged(index, items.size());
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
            mModels.remove(item);
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
            mModels.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes all items in the array.
     */
    public final void clear() {
        synchronized (mLock) {
            mModels.clear();
            notifyDataSetChanged();
        }
    }
}
