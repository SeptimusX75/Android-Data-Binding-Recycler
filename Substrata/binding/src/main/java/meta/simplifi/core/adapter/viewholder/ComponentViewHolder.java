package meta.simplifi.core.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import meta.simplifi.core.adapter.model.RecyclerModel;
import meta.simplifi.core.adapter.viewcomponent.RecyclerViewComponent;

/**
 * Created by M. Silva on 11/8/16.
 */

@SuppressWarnings("WeakerAccess")
public class ComponentViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ComponentViewHolder.class.getSimpleName();
    public RecyclerViewComponent component;

    public ComponentViewHolder(View itemView) {
        super(itemView);
        Log.d(TAG, "created");
    }

    public void onBind(RecyclerModel model) {
        //noinspection unchecked
        component.onBind(model, getAdapterPosition());
        Log.d(TAG, "onBind");
    }

    public void onDetached() {
        component.onDetachedFromWindow();
        Log.d(TAG, "onDetached");
    }

    public void onAttached() {
        component.onAttachedToWindow();
        Log.d(TAG, "onAttached");
    }

    public interface ViewHolderLifecycleCallbacks<T extends RecyclerModel> {
        void onBind(T model, int position);

        void onDetachedFromWindow();

        void onAttachedToWindow();

        void onAttachedToRecycler();

        void onDetachedFromRecycler();
    }
}
