package com.stratpoint.jdhrnndz.dota2junkie.network;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TextInputEditText;

import com.stratpoint.jdhrnndz.dota2junkie.R;

import java.util.HashMap;

/**
 * Created by johndeniellehernandez on 8/5/16.
 */
public class UrlBuilder {
    public static String buildUrl(Context context, int endpoint, HashMap<String, String> args) {
        Resources resources = context.getResources();

        StringBuilder url = new StringBuilder();
        // Start with the api endpoint provided
        url.append(resources.getString(endpoint));
        // Add api key
        url.append("?key=");
        url.append(resources.getString(R.string.api_key));
        // Parse args
        for (String key: args.keySet()) {
            url.append("&" + key + "=");
            url.append(args.get(key));
        }

        return url.toString();
    }
}
