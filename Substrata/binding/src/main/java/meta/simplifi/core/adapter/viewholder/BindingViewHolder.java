package meta.simplifi.core.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Uses {@link ViewDataBinding} to create a view-holder
 *
 * @param <T> The binding type contained within.
 */
public class BindingViewHolder<T extends ViewDataBinding>
        extends RecyclerView.ViewHolder {

    public final T binding;

    /**
     * Creates an instance of this view-holder that binds the passed in view
     * to the respective binding.
     *
     * @param itemView The view to bind.
     */
    public BindingViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
