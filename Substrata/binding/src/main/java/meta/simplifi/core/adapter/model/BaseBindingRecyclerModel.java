package meta.simplifi.core.adapter.model;

import android.databinding.BaseObservable;
import android.support.v4.util.SparseArrayCompat;

/**
 * Created by M. Silva on 11/10/16.
 */

public abstract class BaseBindingRecyclerModel extends BaseObservable
        implements BindingRecyclerModel {
    private SparseArrayCompat<Object> mBindingMap = new SparseArrayCompat<>();

    protected final void putVariableMapping(int variableId, Object value) {
        mBindingMap.put(variableId, value);
    }

    @Override
    public SparseArrayCompat<Object> getVariableMapping() {
        return mBindingMap;
    }

    @Override
    public Class<? extends RecyclerModel> getRecyclerModelClass() {
        return this.getClass();
    }
}
