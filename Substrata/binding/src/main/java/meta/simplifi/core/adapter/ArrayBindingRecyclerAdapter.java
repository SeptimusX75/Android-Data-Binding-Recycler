package meta.simplifi.core.adapter;

import java.util.ArrayList;

import meta.simplifi.core.adapter.viewholder.BindingViewHolder;
import meta.simplifi.core.viewmodel.BindingViewModel;

/**
 * Created by M. Silva on 8/4/16.
 */
public class ArrayBindingRecyclerAdapter<T extends BindingViewModel> extends BindingRecyclerAdapter<T, BindingViewHolder> {
    public ArrayBindingRecyclerAdapter() {
        super(BindingViewHolder.class);
    }

    public ArrayBindingRecyclerAdapter(ArrayList<T> items) {
        super(BindingViewHolder.class, items);
    }
}
