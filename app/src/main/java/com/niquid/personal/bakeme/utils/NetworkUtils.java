package com.niquid.personal.bakeme.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import timber.log.Timber;

public class NetworkUtils {

    private static final String RECEPIES_PATH = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final URL RECEPIES_URL = getURL(RECEPIES_PATH);

    @SuppressWarnings("SameParameterValue")
    private static URL getURL(String path){
        URL url = null;

        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            Timber.e("Couldn't create url");
        }

        return url;
    }

    public static String getRecepiesJSON(){
        String recepies = null;

        try {
            HttpURLConnection connection = (HttpURLConnection) RECEPIES_URL.openConnection();
            InputStream input = connection.getInputStream();

            recepies = IOUtils.toString(input, StandardCharsets.UTF_8);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recepies;
    }

}
