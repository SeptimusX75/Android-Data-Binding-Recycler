# Android-Data-Binding-Recycler

Library that intends to simplify implementation of the ViewHolder pattern
when using a RecyclerView by leveraging Android's DataBinding library.

# Usage
*For a working implementation, check out the example under Substrata/app*

1. Include the library in your project.

2. Extend the `BindingRecyclerFragment` class.

        public class PracticeFragment extends BindingRecyclerFragment {
        }

3. Implement `createBindingAdapter`. You can either use the default constructor for the 
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

4. Add `BaseViewModel`s to the `BindingRecyclerAdapter`.

        User user = new User();
        user.name = "User 1";
        getAdapter().add(new PracticeViewModel(user));

5. Add your instance of `BindingRecyclerFragment` to an `Activity`.

6. Profit.