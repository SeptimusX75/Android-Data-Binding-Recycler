package meta.simplifi.core.adapter.viewcomponent;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;

import meta.simplifi.core.adapter.model.BaseBindingRecyclerModel;
import meta.simplifi.core.adapter.viewcomponent.RecyclerViewComponent;

/**
 * Created by M. Silva on 11/10/16.
 */

public class BindingRecyclerViewComponent<M extends BaseBindingRecyclerModel, B extends ViewDataBinding>
        extends RecyclerViewComponent<M> {

    private B mBinding;

    public BindingRecyclerViewComponent(M model, View view) {
        super(model, view);
        mBinding = DataBindingUtil.bind(view);
    }

    protected final B getBinding() {
        return mBinding;
    }

    @Override
    public void onBind(M model, int position) {
        super.onBind(model, position);
        B binding = getBinding();
        SparseArrayCompat<Object> mapping = model.getVariableMapping();
        for (int i = 0; i < mapping.size(); i++) {
            int varId = mapping.keyAt(i);
            Object value = mapping.get(varId);
            binding.setVariable(varId, value);
        }
        binding.executePendingBindings();
    }
}
