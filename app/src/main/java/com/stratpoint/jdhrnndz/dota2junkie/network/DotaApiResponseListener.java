package com.stratpoint.jdhrnndz.dota2junkie.network;

import com.android.volley.VolleyError;

/**
 * Created by johndeniellehernandez on 8/4/16.
 */
public interface DotaApiResponseListener {
    void onReceiveResponse(int statusCode, Object response, int type);

    void onReceiveErrorResponse(int statusCode, VolleyError error);
}
