package com.stratpoint.jdhrnndz.dota2junkie.network;

import com.android.volley.VolleyError;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/4/16
 * Description: Interface for classes that does the heavy-lifting in processing the responses. Don't
 * let the ApiManager do the processing.
 */
public interface DotaApiResponseListener {
    void onReceiveResponse(int statusCode, Object response, int type);

    void onReceiveErrorResponse(int statusCode, VolleyError error);
}
