package meta.simplifi.core.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.AnyRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

/**
 * View-model that maintains a 1:1 relationship with its corresponding generated binding.
 * Said binding's will preferably contain only one variable, a viewModel variable, that will
 * handle all of the presentation logic.
 * <p/>
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/25/2016.
 */
public abstract class BaseViewModel extends BaseObservable {

    /**
     * Gets the layout ID associated with this view-model.
     *
     * @return The layout ID of the layout with the corresponding binding.
     */
    @LayoutRes
    public abstract int getLayoutId();

    @AnyRes
    public abstract int getVariableId();
}
