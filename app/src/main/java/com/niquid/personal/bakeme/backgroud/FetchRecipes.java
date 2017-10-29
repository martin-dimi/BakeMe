package com.niquid.personal.bakeme.backgroud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.niquid.personal.bakeme.utils.NetworkUtils;

import timber.log.Timber;


public class FetchRecipes implements LoaderManager.LoaderCallbacks<String> {

    private static final String RECIPES_JSON = "recipes";
    private static final int FETCH_ONLINE_RECIPES = 101;

    private final Context context;
    private LoaderManager manager;
    private OnTaskFinished onTaskFinished;
    private String recipes;

    public FetchRecipes(Context context, LoaderManager manager, OnTaskFinished onTaskFinished) {
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
                recipes = preferences.getString(RECIPES_JSON, "");
                if(recipes.equals(""))
                    forceLoad();
                else {
                    Timber.i("No need to fetch recipes");
                    deliverResult(recipes);
                }
            }

            @Override
            public String loadInBackground() {
                Timber.i("Fetching recipes online");
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
