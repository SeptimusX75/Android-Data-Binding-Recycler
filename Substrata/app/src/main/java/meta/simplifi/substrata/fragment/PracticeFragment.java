package meta.simplifi.substrata.fragment;

import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import meta.simplifi.core.adapter.ArrayBindingRecyclerAdapter;
import meta.simplifi.core.adapter.BindingRecyclerAdapter;
import meta.simplifi.core.fragment.BindingRecyclerFragment;
import meta.simplifi.substrata.R;
import meta.simplifi.substrata.model.User;
import meta.simplifi.substrata.viewmodel.PracticeViewModel;

/**
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/26/2016.
 */
public class PracticeFragment
        extends BindingRecyclerFragment<ArrayBindingRecyclerAdapter<PracticeViewModel>> {

    private RecyclerView mRecyclerView;

    @NonNull
    @Override
    protected ArrayBindingRecyclerAdapter<PracticeViewModel> createBindingAdapter() {
        ObservableArrayList<PracticeViewModel> list =
                new ObservableArrayList<>();
        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.name = "User " + (i + 1);
            list.add(new PracticeViewModel(user));
        }

        return new ArrayBindingRecyclerAdapter<>(list);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = ((RecyclerView) inflater.inflate(R.layout.layout_recycler_view, container));
        return mRecyclerView;
    }

    @Override
    public void onItemClick(View v, int position) {
        PracticeViewModel item = getAdapter().getItem(position);
        switch (v.getId()) {
            case R.id.userListItem:
                Toast.makeText(getContext(), item.getUserName() + " Clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
