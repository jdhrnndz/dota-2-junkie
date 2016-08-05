package com.stratpoint.jdhrnndz.dota2junkie.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stratpoint.jdhrnndz.dota2junkie.MatchHistory;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.MatchesFragment;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.TabFragment;

import java.util.ArrayList;

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
                        responseListener.onReceiveStringResponse(200, response);
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
                        ArrayList<Long> matchIds = new ArrayList<>();
                        int len = response.getResult().getMatches().length;
                        MatchHistory.Match[] matches = response.getResult().getMatches();

                        for(int i=0; i<len; i++) {
                            matchIds.add(matches[i].getId());
                        }

                        ((MatchesFragment) TabFragment.MATCHES.getFragment()).setMatchIds(matchIds);

                        responseListener.onReceiveMatchHistoryResponse(200, response);
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
}
