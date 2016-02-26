package meta.simplifi.substrata;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by SeptimusX75 (msilva28.dev@gmail.com) on 2/26/2016.
 */
public class BindingAdapters {
    @BindingAdapter("bind:image")
    public static void setImage(ImageView imageView, int imageRes) {
        imageView.setImageResource(imageRes);
    }
}
