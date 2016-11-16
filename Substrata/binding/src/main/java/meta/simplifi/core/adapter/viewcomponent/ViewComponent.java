package meta.simplifi.core.adapter.viewcomponent;

import android.view.View;

import meta.simplifi.core.adapter.model.Model;

/**
 * Created by M. Silva on 11/9/16.
 */
public abstract class ViewComponent<T extends Model> {

    private T mModel;
    private View mView;

    public ViewComponent(T model, View view) {
        mModel = model;
        mView = view;
    }

    protected void setModel(T model) {
        mModel = model;
    }

    public T getModel() {
        return mModel;
    }

    public View getView() {
        return mView;
    }
}
