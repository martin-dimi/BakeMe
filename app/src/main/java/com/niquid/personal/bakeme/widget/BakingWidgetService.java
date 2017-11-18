package com.niquid.personal.bakeme.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Ingredient;

import org.parceler.Parcels;

import java.util.List;

import static com.niquid.personal.bakeme.utils.RecipeUtils.INGREDIENTS_KEY;

public class BakingWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ShoppingListRemoteViewFactory(this.getApplicationContext(), intent);
    }
}

class ShoppingListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private List<Ingredient> mIngredients;
    private final Intent intent;

    ShoppingListRemoteViewFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        this.intent = intent;
        if(intent != null) {
            Bundle bundle = intent.getBundleExtra(INGREDIENTS_KEY);
            mIngredients = Parcels.unwrap(bundle.getParcelable(INGREDIENTS_KEY));
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(intent != null) {
            Bundle bundle = intent.getBundleExtra(INGREDIENTS_KEY);
            mIngredients = Parcels.unwrap(bundle.getParcelable(INGREDIENTS_KEY));
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients != null) return mIngredients.size();
        else return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        try {
            RemoteViews ingredientView = new RemoteViews(mContext.getPackageName(), R.layout.widget_shopping_list_item);

            ingredientView.setTextViewText(R.id.widget_ingredient_item_number, String.valueOf(i + 1));
            ingredientView.setTextViewText(R.id.widget_ingredient_item, mIngredients.get(i).getIngredient());

            return ingredientView;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
