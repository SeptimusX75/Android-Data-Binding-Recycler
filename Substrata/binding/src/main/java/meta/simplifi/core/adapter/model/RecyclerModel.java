package meta.simplifi.core.adapter.model;

import meta.simplifi.core.adapter.viewcomponent.RecyclerViewComponent;

/**
 * Created by M. Silva on 11/9/16.
 */

public interface RecyclerModel extends Model {
    Class<? extends RecyclerViewComponent> getViewComponentClass();

    Class<? extends RecyclerModel> getRecyclerModelClass();
}
