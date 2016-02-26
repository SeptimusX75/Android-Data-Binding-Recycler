package meta.simplifi.core.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.LayoutRes;

/**
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/25/2016.
 */
public abstract class BaseViewModel extends BaseObservable {
    @LayoutRes
    public abstract int getLayoutId();
}
