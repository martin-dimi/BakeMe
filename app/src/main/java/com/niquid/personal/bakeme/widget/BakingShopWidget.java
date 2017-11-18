package com.niquid.personal.bakeme.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.MainActivity;
import com.niquid.personal.bakeme.models.Ingredient;

import org.parceler.Parcels;

import java.util.List;

import static com.niquid.personal.bakeme.utils.RecipeUtils.INGREDIENTS_KEY;

/**
 * Implementation of App Widget functionality.
 */
public class BakingShopWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId, List<Ingredient> ingredients) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_app);

        Intent startActivity = new Intent(context, MainActivity.class);
        PendingIntent startActivityPending = PendingIntent.getActivity(context, 0, startActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_header, startActivityPending);

        Intent intent = new Intent(context, BakingWidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(INGREDIENTS_KEY, Parcels.wrap(ingredients));
        intent.putExtra(INGREDIENTS_KEY, bundle);
        views.setRemoteAdapter(R.id.widget_shopping_list, intent);
        views.setEmptyView(R.id.widget_shopping_list, R.id.empty_view);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Widget doesn't change periodically, only when user wants
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, List<Ingredient> ingredients){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

