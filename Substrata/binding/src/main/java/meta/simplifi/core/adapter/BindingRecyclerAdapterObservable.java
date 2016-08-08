package meta.simplifi.core.adapter;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import meta.simplifi.core.adapter.viewholder.BindingViewHolder;
import meta.simplifi.core.viewmodel.BindingViewModel;

public class BindingRecyclerAdapterObservable<T extends BindingViewModel>
        extends BindingRecyclerAdapter<T, BindingViewHolder> {

    private final ObservableArrayList<T> mItems;

    public BindingRecyclerAdapterObservable() {
        super(BindingViewHolder.class, new ObservableArrayList<T>());
        mItems = (ObservableArrayList<T>) getItems();
    }

    public BindingRecyclerAdapterObservable(ObservableArrayList<T> items) {
        super(BindingViewHolder.class, items);
        mItems = (ObservableArrayList<T>) getItems();
    }

    /**
     * Allows for listening to changes on the array.
     *
     * @param callback The callback to be executed on list changes.
     */
    public final void addOnListChangedCallback(ObservableList.OnListChangedCallback callback) {
        mItems.addOnListChangedCallback(callback);
    }

    /**
     * Removes the specified callback from the array.
     *
     * @param callback The callback to remove.
     */
    public final void removeOnListChangedCallback(ObservableList.OnListChangedCallback callback) {
        mItems.removeOnListChangedCallback(callback);
    }
}
