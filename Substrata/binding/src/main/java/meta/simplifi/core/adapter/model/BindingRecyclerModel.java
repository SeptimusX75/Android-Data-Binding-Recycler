package meta.simplifi.core.adapter.model;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by M. Silva on 11/10/16.
 */

public interface BindingRecyclerModel extends RecyclerModel {
    SparseArrayCompat<Object> getVariableMapping();
}
