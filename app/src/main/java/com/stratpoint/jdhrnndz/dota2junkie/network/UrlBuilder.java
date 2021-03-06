package com.stratpoint.jdhrnndz.dota2junkie.network;

import android.content.Context;
import android.content.res.Resources;

import com.stratpoint.jdhrnndz.dota2junkie.R;

import java.util.HashMap;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/5/16
 * Description: Utility class for building request urls
 */
public class UrlBuilder {
    public static String buildGenericUrl(Context context, int endpoint, HashMap<String, String> args) {
        Resources resources = context.getResources();

        StringBuilder url = new StringBuilder();
        // Start with the api endpoint provided
        url.append(resources.getString(endpoint));
        // Add api key
        url.append("?key=");
        url.append(resources.getString(R.string.api_key));
        // Parse args
        for (String key: args.keySet()) {
            url.append('&');
            url.append(key);
            url.append('=');
            url.append(args.get(key));
        }

        return url.toString();
    }

    public static String buildHeroImageUrl(Context context, String heroName) {
        return context.getString(R.string.get_hero_images) +
                heroName +
                context.getString(R.string.hero_image_suffix);
    }

    public static String buildItemImageUrlTemplate(Context context) {
        return context.getString(R.string.get_item_images) +
                "%s" +
                context.getString(R.string.item_image_suffix);
    }
}
