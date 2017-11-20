package com.niquid.personal.bakeme.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.MainActivity;
import com.niquid.personal.bakeme.models.Ingredient;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingShopWidget extends AppWidgetProvider {

    public static List<Ingredient> ingredients;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId, List<Ingredient> ingredients) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_app);

        Intent startActivity = new Intent(context, MainActivity.class);
        PendingIntent startActivityPending = PendingIntent.getActivity(context, 0, startActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_header, startActivityPending);

        BakingShopWidget.ingredients = ingredients;

        Intent intent = new Intent(context, BakingWidgetService.class);
        views.setRemoteAdapter(R.id.widget_shopping_list, intent);
        views.setEmptyView(R.id.widget_shopping_list, R.id.empty_view);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_shopping_list);

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

