package com.reactmfwllibrary.requests;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by janithah on 6/8/2018.
 */

public class WLRequest extends ReactContextBaseJavaModule {
    private static final String WLREQUEST_SUCCESS = "Success";

    public WLRequest(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("GET", WLResourceRequest.GET);
        constants.put("POST", WLResourceRequest.POST);
        constants.put("PUT", WLResourceRequest.PUT);
        constants.put("DELETE", WLResourceRequest.DELETE);
        return constants;
    }


    @Override
    public String getName() {
            return "WLRequest";
    }

    private WLResourceRequest getHeaderParams(JSONObject headerParams,WLResourceRequest request) throws JSONException {
        Iterator<?> keys = headerParams.keys();
        while(keys.hasNext() ) {
            String key = (String) keys.next();
            if (headerParams.get(key) instanceof JSONObject) {
                request.addHeader(key, String.valueOf(headerParams.getJSONObject(key).toString()));
            }
            else if(headerParams.get(key) instanceof JSONArray){
                request.addHeader(key, String.valueOf(headerParams.getJSONArray(key).toString()));
            }
            else if(headerParams.get(key) instanceof String){
                request.addHeader(key, headerParams.getString(key));
            }
            else if(headerParams.get(key) instanceof Number){
                request.addHeader(key, String.valueOf(headerParams.getDouble(key)));
            }
            else if(headerParams.get(key) instanceof Boolean){
                request.addHeader(key, String.valueOf(headerParams.getBoolean(key)));
            }
        }
        return request;
    }

    private WLResourceRequest getQueryParams(JSONObject queryParams,WLResourceRequest request) throws JSONException {
        Iterator<?> keys = queryParams.keys();
        while(keys.hasNext() ) {
            String key = (String) keys.next();
            if (queryParams.get(key) instanceof JSONObject) {
                request.setQueryParameter(key, String.valueOf(queryParams.getJSONObject(key).toString()));
            }
            else if(queryParams.get(key) instanceof JSONArray){
                request.setQueryParameter(key, String.valueOf(queryParams.getJSONArray(key).toString()));
            }
            else if(queryParams.get(key) instanceof String){
                request.setQueryParameter(key, queryParams.getString(key));
            }
            else if(queryParams.get(key) instanceof Number){
                request.setQueryParameter(key, String.valueOf(queryParams.getDouble(key)));
            }
            else if(queryParams.get(key) instanceof Boolean){
                request.setQueryParameter(key, String.valueOf(queryParams.getBoolean(key)));
            }
        }
        return request;
    }

    private HashMap getFormParams(JSONObject formParams) throws JSONException {
        HashMap h_formParams = new HashMap();
        Iterator<?> keys = formParams.keys();
        while(keys.hasNext() ) {
            String key = (String) keys.next();
            if (formParams.get(key) instanceof JSONObject) {
                h_formParams.put(key, String.valueOf(formParams.getJSONObject(key).toString()));
            }
            else if(formParams.get(key) instanceof JSONArray){
                h_formParams.put(key, String.valueOf(formParams.getJSONArray(key).toString()));
            }
            else if(formParams.get(key) instanceof String){
                h_formParams.put(key, formParams.getString(key));
            }
            else if(formParams.get(key) instanceof Number){
                h_formParams.put(key, String.valueOf(formParams.getDouble(key)));
            }
            else if(formParams.get(key) instanceof Boolean){
                h_formParams.put(key, String.valueOf(formParams.getBoolean(key)));
            }
        }
        return h_formParams;
    }

    public static JSONObject convertMapToJson(ReadableMap readableMap) throws JSONException {
        JSONObject object = new JSONObject();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (readableMap.getType(key)) {
                case Null:
                    object.put(key, JSONObject.NULL);
                    break;
                case Boolean:
                    object.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    object.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    object.put(key, readableMap.getString(key));
                    break;
                case Map:
                    object.put(key, convertMapToJson(readableMap.getMap(key)));
                    break;
                case Array:
                    object.put(key, convertArrayToJson(readableMap.getArray(key)));
                    break;
            }
        }
        return object;
    }

    public static JSONArray convertArrayToJson(ReadableArray readableArray) throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < readableArray.size(); i++) {
            switch (readableArray.getType(i)) {
                case Null:
                    break;
                case Boolean:
                    array.put(readableArray.getBoolean(i));
                    break;
                case Number:
                    array.put(readableArray.getDouble(i));
                    break;
                case String:
                    array.put(readableArray.getString(i));
                    break;
                case Map:
                    array.put(convertMapToJson(readableArray.getMap(i)));
                    break;
                case Array:
                    array.put(convertArrayToJson(readableArray.getArray(i)));
                    break;
            }
        }
        return array;
    }


    @ReactMethod
    public void queryRequest(ReadableMap queryParams, ReadableMap headerParams, String urlString, String method, final Promise promise){
        try {
        WLResourceRequest request = new WLResourceRequest(URI.create(urlString),method);

            JSONObject j_headerParams = convertMapToJson(headerParams);
            JSONObject j_queryParams = convertMapToJson(queryParams);
            request = getHeaderParams(j_headerParams,request);
            request = getQueryParams(j_queryParams,request);

            request.send(new WLResponseListener() {
            @Override
            public void onSuccess(WLResponse wlResponse) {
                promise.resolve(wlResponse.getResponseText());
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                promise.resolve(wlFailResponse.getErrorMsg());
            }
        });
        } catch (Exception e) {
            promise.resolve(e.getMessage());
        }
    }


    @ReactMethod
    public void formRequest(ReadableMap formParams, ReadableMap headerParams, String urlString, String method, final Promise promise){
        try {
            WLResourceRequest request = new WLResourceRequest(URI.create(urlString),method);
            Log.e("Header ",headerParams.toString());
            Log.e("Form ",formParams.toString());
            JSONObject j_headerParams = convertMapToJson(headerParams);
            Log.e("J_Header ",j_headerParams.toString());
            JSONObject j_formParams = convertMapToJson(formParams);
            Log.e("J_Form ",j_formParams.toString());
            request = getHeaderParams(j_headerParams,request);

            HashMap h_formparams =  getFormParams(j_formParams);
            Log.e("H_Form ",h_formparams.toString());
            request.send(h_formparams,new WLResponseListener() {
                @Override
                public void onSuccess(WLResponse wlResponse) {
                    promise.resolve(wlResponse.getResponseText());
                }

                @Override
                public void onFailure(WLFailResponse wlFailResponse) {
                    promise.resolve(wlFailResponse.getErrorMsg());
                }
            });
        } catch (Exception e) {
            Log.e("Error ",e.getMessage().toString());
            promise.resolve(e.getMessage());
        }
    }

    @ReactMethod
    public void jsonRequest(ReadableMap bodyParams, ReadableMap headerParams, String urlString, String method, final Promise promise){
        try {
            WLResourceRequest request = new WLResourceRequest(URI.create(urlString),method);

            JSONObject j_headerParams = convertMapToJson(headerParams);
            JSONObject j_bodyParams = convertMapToJson(bodyParams);
            request = getHeaderParams(j_headerParams,request);


            request.send(j_bodyParams,new WLResponseListener() {
                @Override
                public void onSuccess(WLResponse wlResponse) {
                    promise.resolve(wlResponse.getResponseText());
                }

                @Override
                public void onFailure(WLFailResponse wlFailResponse) {
                    promise.resolve(wlFailResponse.getErrorMsg());
                }
            });
        } catch (Exception e) {
            promise.resolve(e.getMessage());
        }
    }

}
