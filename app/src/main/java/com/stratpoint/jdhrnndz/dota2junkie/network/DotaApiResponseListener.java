package com.stratpoint.jdhrnndz.dota2junkie.network;

import com.android.volley.VolleyError;
import com.stratpoint.jdhrnndz.dota2junkie.MatchHistory;

/**
 * Created by johndeniellehernandez on 8/4/16.
 */
public interface DotaApiResponseListener {
    void onReceiveStringResponse(int statusCode, String response);

    void onReceiveMatchHistoryResponse(int statusCode, MatchHistory response);

    void onReceiveErrorResponse(int statusCode, VolleyError error);
}
