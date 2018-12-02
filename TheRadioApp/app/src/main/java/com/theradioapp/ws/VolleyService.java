package com.theradioapp.ws;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.theradioapp.Utils.Utility;
import com.theradioapp.ws.api.data.VolleyMultipartRequest;
import com.theradioapp.ws.controllers.NetworkController;
import com.theradioapp.ws.interfaces.VolleyResponseListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bhavesh on 14-09-2017.
 */

public class VolleyService {

    private static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        if (sb.length() > 0) {
            sb.insert(0, "?");
        }
        return sb.toString();
    }

    private static final String TAG = "Response";

    public static <T> void GetMethod(final String url, final Class<T> modelClass, final HashMap<String, String> param,
                                     final VolleyResponseListener listener) {

        String finalUrl = url;
        if (param != null && param.size() != 0) {
            finalUrl = url + urlEncodeUTF8(param);
        }
//        Log.e(TAG, "URL: " + finalUrl);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                finalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e(TAG, "onResponse: " + response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        T responseObject = gson.fromJson(response, modelClass);
                        listener.onResponse(responseObject, url, true, null);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onResponse(null, url, false, error);

                    }
                });

        // Access the RequestQueue through singleton class.
        NetworkController.getInstance().addToRequestQueue(stringRequest);
    }

    public static <T> void PostMethod(final String url, final Class<T> modelClass,
                                      final HashMap<String, String> headers, final VolleyResponseListener listener) {

//        Log.e(TAG, "URL: " + url);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Log.e(TAG, "onResponse: " + response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        T responseObject = gson.fromJson(response, modelClass);
                        listener.onResponse(responseObject, url, true, null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onResponse(null, url, false, error);
                    }
                })

        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return headers;
            }


        };

        // Access the RequestQueue through singleton class.
        NetworkController.getInstance().addToRequestQueue(stringRequest);
    }

    public static <T> void PostMethodWithString(final String requestBody, final String url, final Class<T> modelClass,
                                                final VolleyResponseListener listener) {

//        Log.e(TAG, "URL: " + url);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, "onResponse: " + response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        try {
                            JSONObject jobj = new JSONObject(response);
                            T responseObject = gson.fromJson(jobj.toString(), modelClass);
                            listener.onResponse(responseObject, url, true, null);
                        } catch (JSONException e) {
                            Log.e(TAG, "onResponse: CALLEDDD::::");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onResponse(null, url, false, error);
                    }
                }) {


            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }


        };

        // Access the RequestQueue through singleton class.
        NetworkController.getInstance().addToRequestQueue(stringRequest);
    }

    public static <T> void PostMethodWithJSON(final String url, final Class<T> modelClass,
                                              final JSONObject jsonObject,
                                              final VolleyResponseListener listener) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, "onResponse::: " + response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        T responseObject = gson.fromJson(response.toString(), modelClass);
                        listener.onResponse(responseObject, url, true, null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "ERROR::: " + error.toString());
                        listener.onResponse(null, url, false, error);
                    }
                }) {

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return headers;
//            }


        };

        // Access the RequestQueue through singleton class.
        NetworkController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static <T> void PostMethodForImageUpload(final String url, final Class<T> modelClass,
                                                    final HashMap<String, String> headers,
                                                    final VolleyResponseListener listener,
                                                    final Map<String, VolleyMultipartRequest.DataPart> params) {

//        Log.e(TAG, "URL: " + url);
        // Initialize a new StringRequest
        VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(
                Request.Method.POST,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
//                        Log.e(TAG, "onResponse: " + resultResponse);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        T responseObject = gson.fromJson(resultResponse, modelClass);
                        listener.onResponse(responseObject, url, true, null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onResponse(null, url, false, error);
                    }
                })

        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return headers;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {

//                params.put("user_profile", new DataPart("file_cover.jpg", arr, "image/jpeg"));
                return params;
            }


        };

        // Access the RequestQueue through singleton class.
        NetworkController.getInstance().addToRequestQueue(stringRequest);
    }

    public static <T> void PostMethodWithHeaders(final String url, final Class<T> modelClass,
                                                 final HashMap<String, String> params, final HashMap<String, String> headers,
                                                 final VolleyResponseListener listener) {

//        Log.e(TAG, "URL: " + url);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e(TAG, "onResponse: " + response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        T responseObject = gson.fromJson(response, modelClass);
                        listener.onResponse(responseObject, url, true, null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onResponse(null, url, false, error);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }

        };

        // Access the RequestQueue through singleton class.
        NetworkController.getInstance().addToRequestQueue(stringRequest);
    }
}
