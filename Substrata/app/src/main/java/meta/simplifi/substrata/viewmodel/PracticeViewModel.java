package meta.simplifi.substrata.viewmodel;

import android.databinding.Bindable;

import meta.simplifi.core.viewmodel.BindingViewModel;
import meta.simplifi.substrata.BR;
import meta.simplifi.substrata.R;
import meta.simplifi.substrata.model.User;

/**
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/26/2016.
 */
public class PracticeViewModel extends BindingViewModel {

    private User mUser;

    public PracticeViewModel(User user) {
        setUser(user);
    }

    @Bindable
    public String getUserName() {
        return mUser.name;
    }

    @Bindable
    public int getImageRes() {
        return R.mipmap.ic_launcher;
    }

    public void setUser(User user) {
        mUser = user;
        notifyPropertyChanged(BR.userName);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_practice;
    }

    @Override
    public int getVariableId() {
        return BR.viewModel;
    }
}
