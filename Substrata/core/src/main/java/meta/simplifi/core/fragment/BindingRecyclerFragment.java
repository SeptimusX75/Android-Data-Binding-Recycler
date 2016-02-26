package meta.simplifi.core.fragment;

import android.os.Bundle;
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
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/26/2016.
 */
public abstract class BindingRecyclerFragment<T extends BindingRecyclerAdapter>
        extends Fragment
        implements BindingRecyclerAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private T mAdapter;

    @NonNull
    protected abstract T createBindingAdapter();

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater
                .inflate(R.layout.layout_recycler_view, container, false);
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

    @NonNull
    protected T getAdapter() {
        return mAdapter;
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @NonNull
    protected RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @NonNull
    protected RecyclerView.LayoutManager onSetLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return layoutManager;
    }
}
