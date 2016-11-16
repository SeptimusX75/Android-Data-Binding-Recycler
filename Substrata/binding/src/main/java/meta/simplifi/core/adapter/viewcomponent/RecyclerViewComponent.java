package meta.simplifi.core.adapter.viewcomponent;

import android.view.View;

import meta.simplifi.core.adapter.viewholder.ComponentViewHolder;
import meta.simplifi.core.adapter.model.RecyclerModel;

/**
 * Created by M. Silva on 11/9/16.
 */

public abstract class RecyclerViewComponent<T extends RecyclerModel> extends ViewComponent<T>
        implements ComponentViewHolder.ViewHolderLifecycleCallbacks<T> {

    public RecyclerViewComponent(T model, View view) {
        super(model, view);
    }

    @Override
    public void onBind(T model, int position) {
        setModel(model);
    }

    @Override
    public void onDetachedFromWindow() {

    }

    @Override
    public void onAttachedToWindow() {

    }

    @Override
    public void onAttachedToRecycler() {

    }

    @Override
    public void onDetachedFromRecycler() {

    }
}
