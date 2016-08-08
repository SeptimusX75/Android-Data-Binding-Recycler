package meta.simplifi.core.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import meta.simplifi.core.adapter.BindingRecyclerAdapter;

/**
 * Base class for creating fragments with RecyclerViews using data binding.
 * Will create a fragment with a RecyclerView and attach the specified BindingRecyclerAdapter
 * to the RecyclerView. Additionally, this fragment registers itself as a listener for
 * item click events within the RecyclerView for your convenience. To handle these events,
 * override OnItemClicked.
 * <p/>
 * Created by M. Silva on 2/26/2016.
 */
public abstract class BindingRecyclerFragment<T extends BindingRecyclerAdapter>
        extends Fragment
        implements BindingRecyclerAdapter.OnItemClickListener {

    private RecyclerView.LayoutManager mLayoutManager;
    private T mAdapter;

    /**
     * Creates an instance of the adapter to set on the RecyclerView.
     *
     * @return The adapter to set on the RecyclerView.
     */
    @NonNull
    protected abstract T createBindingAdapter();

    /**
     * @return The RecyclerView contained in this Fragment.
     */
    protected abstract RecyclerView getRecyclerView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = createBindingAdapter();
        if (!mAdapter.hasOnItemClickListener()) mAdapter.setOnItemClickListener(this);

        mLayoutManager = onSetLayoutManager();
        getRecyclerView().setLayoutManager(mLayoutManager);

        getRecyclerView().setAdapter(mAdapter);
    }

    /**
     * @return The adapter attached to the RecyclerView
     */
    @NonNull
    protected T getAdapter() {
        return mAdapter;
    }

    /**
     * @return The LayoutManager used by the RecyclerView.
     */
    @NonNull
    protected RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    /**
     * Allows setting of a custom layout manager.
     * Default implementation uses a LinearLayoutManager.
     *
     * @return The LayoutManager set on the RecyclerView.
     */
    @NonNull
    protected RecyclerView.LayoutManager onSetLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
