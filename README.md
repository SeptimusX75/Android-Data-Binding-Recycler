# Android-Data-Binding-Recycler

Library that intends to simplify implementation of the ViewHolder pattern
when using a RecyclerView by leveraging Android's DataBinding library.

# Usage
*For a working implementation, check out the example under Substrata/app*

1. Include the library in your project.

2. Create a layout for your RecyclerView item making sure to specify viewModel as your variable.
        
        <!-- list_item_practice.xml -->
        <layout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:tools="http://schemas.android.com/tools">
        <data>
                <variable
                        name="viewModel"
                        type="meta.simplifi.substrata.viewmodel.PracticeViewModel" />
        </data>
        <TextView
                android:id="@+id/userName"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text=@{viewModel.name}"/>
        </layout>
        
3. Extend the `BindingRecyclerFragment` class.

        public class PracticeFragment extends BindingRecyclerFragment {
        }

4. Implement `createBindingAdapter`. You can use either the default constructor for the 
adapter or pre-populate it. 
    
        @Override
        protected BindingRecyclerAdapter<PracticeViewModel> createBindingAdapter() {
            return new BindingRecyclerAdapter();
        }
        
        or
                
        @Override
        protected BindingRecyclerAdapter<PracticeViewModel> createBindingAdapter() {
            ObservableArrayList<PracticeViewModel> list = new ObservableArrayList<>();
                return new BindingRecyclerAdapter(list);
        }
        
5. Create a view-model by extending `BaseViewModel`, implementing `getLayoutID` and specifying your layout ID.
        
        public class PracticeViewModel extends BaseViewModel {
                @Override
                public int getLayoutId(){
                        return R.layout.list_item_practice;
                }
        }

6. Add `BaseViewModel`s to the `BindingRecyclerAdapter`.

        User user = new User();
        user.name = "User 1";
        getAdapter().add(new PracticeViewModel(user));

7. Add your instance of `BindingRecyclerFragment` to an `Activity`.

8. Profit.
