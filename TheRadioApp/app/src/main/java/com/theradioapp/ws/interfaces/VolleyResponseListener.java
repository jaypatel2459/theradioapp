package com.theradioapp.ws.interfaces;

import com.android.volley.VolleyError;

/**
 * Created by Bhavesh on 14-09-2017.
 */

public interface VolleyResponseListener {
    void onResponse(Object response, String url, boolean isSuccess, VolleyError error);
}
