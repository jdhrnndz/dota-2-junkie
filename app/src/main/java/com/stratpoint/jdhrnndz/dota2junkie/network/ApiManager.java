package com.stratpoint.jdhrnndz.dota2junkie.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchDetails;
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchHistory;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/4/16
 * Description:
 */
public class ApiManager {
    public static final int STRING_RESPONSE_TYPE = 9;
    public static final int MATCH_HISTORY_RESPONSE_TYPE = 6;
    public static final int MATCH_DETAILS_RESPONSE_TYPE = 3;

    public static void fetchUserInfo(Context context, String url, final DotaApiResponseListener responseListener) {
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        // StringRequest is used instead of a GSONRequest like in the docs because the
        // result will be passed to another activity
        StringRequest playerSummaryRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseListener.onReceiveResponse(200, response, ApiManager.STRING_RESPONSE_TYPE);
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

    public static void fetchMatchHistory(Context context, String url, final DotaApiResponseListener responseListener) {
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        GsonRequest<MatchHistory> matchHistoryRequest = new GsonRequest<>(url, MatchHistory.class, null,
                new Response.Listener<MatchHistory>() {
                    @Override
                    public void onResponse(MatchHistory response) {
                        responseListener.onReceiveResponse(200, response, ApiManager.MATCH_HISTORY_RESPONSE_TYPE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onReceiveErrorResponse(400, error);
                    }
                }
        );

        queue.add(matchHistoryRequest);
    }

    public static void fetchMatchDetails(Context context, String url, final DotaApiResponseListener responseListener) {
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        GsonRequest matchDetailsRequest = new GsonRequest<>(url, MatchDetails.class, null,
                new Response.Listener<MatchDetails>() {
                    @Override
                    public void onResponse(MatchDetails response) {
                        responseListener.onReceiveResponse(200, response.getResult(), ApiManager.MATCH_DETAILS_RESPONSE_TYPE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onReceiveErrorResponse(400, error);
                    }
                }
        );

        queue.add(matchDetailsRequest);
    }
}
