package meta.simplifi.core.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import meta.simplifi.core.R;
import meta.simplifi.core.adapter.BindingRecyclerAdapter;

/**
 * Base class for creating fragments with RecyclerViews using data binding.
 * Will create a fragment with a RecyclerView and attach the specified BindingRecyclerAdapter
 * to the RecyclerView. Additionally, this fragment registers itself as a listener for
 * item click events within the RecyclerView for your convenience. To handle these events,
 * override OnItemClicked.
 * <p/>
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/26/2016.
 */
public abstract class BindingRecyclerFragment<T extends BindingRecyclerAdapter>
        extends Fragment
        implements BindingRecyclerAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
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
     * Gets the layout containing the RecyclerView.
     *
     * @return The layout resource ID for the layout containing the RecyclerView.
     * Default implementation returns only a RecyclerView.
     */
    @LayoutRes
    protected int getLayoutId() {
        return R.layout.layout_recycler_view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(getLayoutId(), container, false);
        return mRecyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = createBindingAdapter();
        if (!mAdapter.hasOnItemClickListener()) mAdapter.setOnItemClickListener(this);

        mLayoutManager = onSetLayoutManager(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * @return The adapter attached to the RecyclerView
     */
    @NonNull
    protected T getAdapter() {
        return mAdapter;
    }

    /**
     * @return The RecyclerView contained in this Fragment.
     */
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
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
     * @param recyclerView The RecyclerView to set a layout manager on.
     * @return The LayoutManager set on the RecyclerView.
     */
    @NonNull
    protected RecyclerView.LayoutManager onSetLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return layoutManager;
    }
}
