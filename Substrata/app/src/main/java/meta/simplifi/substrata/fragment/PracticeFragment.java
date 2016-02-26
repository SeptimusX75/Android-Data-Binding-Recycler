package meta.simplifi.substrata.fragment;

import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import meta.simplifi.core.adapter.BindingRecyclerAdapter;
import meta.simplifi.core.fragment.BindingRecyclerFragment;
import meta.simplifi.substrata.R;
import meta.simplifi.substrata.model.User;
import meta.simplifi.substrata.viewmodel.PracticeViewModel;

/**
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/26/2016.
 */
public class PracticeFragment
        extends BindingRecyclerFragment<BindingRecyclerAdapter<PracticeViewModel>> {
    @NonNull
    @Override
    protected BindingRecyclerAdapter<PracticeViewModel> createBindingAdapter() {
        ObservableArrayList<PracticeViewModel> list =
                new ObservableArrayList<>();
        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.name = "User " + (i + 1);
            list.add(new PracticeViewModel(user));
        }

        return new BindingRecyclerAdapter<>(list);
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
