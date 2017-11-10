package com.niquid.personal.bakeme.backgroud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;

import com.niquid.personal.bakeme.utils.NetworkUtils;

import timber.log.Timber;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;


public class FetchRecipeTask implements LoaderManager.LoaderCallbacks<String> {

    private static final int FETCH_ONLINE_RECIPES = 101;

    private final Context context;
    private LoaderManager manager;
    private OnTaskFinished onTaskFinished;
    private String recipes;

    public FetchRecipeTask(Context context, LoaderManager manager, OnTaskFinished onTaskFinished) {
        this.context = context;
        this.manager = manager;
        this.onTaskFinished = onTaskFinished;
    }

    public void fetchRecipes(){
        Loader loader = manager.getLoader(FETCH_ONLINE_RECIPES);
        if(loader == null)
            manager.initLoader(FETCH_ONLINE_RECIPES, null, this);
        else
            manager.restartLoader(FETCH_ONLINE_RECIPES, null, this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(context) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                recipes = preferences.getString(RECIPE_KEY, "");
                if(recipes.equals("")) {
                    Timber.i("Fetching recipes online");
                    forceLoad();
                }
                else {
                    Timber.i("No need to fetch recipes");
                    deliverResult(recipes);
                }
            }

            @Override
            public String loadInBackground() {
                recipes = NetworkUtils.getRecepiesJSON();
                return recipes;
            }


        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        onTaskFinished.onRecipesFetched(data, context);
    }


    @Override
    public void onLoaderReset(Loader<String> loader) {
        if(loader != null)
            manager.restartLoader(FETCH_ONLINE_RECIPES, null, this);

    }

    public interface OnTaskFinished{
        void onRecipesFetched(String json, Context context);
    }
}
