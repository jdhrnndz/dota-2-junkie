package com.stratpoint.jdhrnndz.dota2junkie.network;

import android.content.Context;
import android.support.design.widget.TextInputEditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stratpoint.jdhrnndz.dota2junkie.R;

/**
 * Created by johndeniellehernandez on 8/4/16.
 */
public class ApiManager {
    public static void fetchUserInfo(Context context, String url, final DotaApiResponseListener responseListener) {
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        // StringRequest is used instead of a GSONRequest like in the docs because the
        // result will be passed to another activity
        StringRequest playerSummaryRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseListener.onReceiveResponse(200, response, null, null);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onReceiveErrorResponse(400, error);
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(playerSummaryRequest);
    }
}
