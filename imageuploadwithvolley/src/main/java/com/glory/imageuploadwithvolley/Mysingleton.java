package com.glory.imageuploadwithvolley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingleton {
    private static Mysingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mcContext;

    private Mysingleton(Context context){
        mcContext = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(mcContext.getApplicationContext());
        return requestQueue;
    }

    public static synchronized Mysingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new Mysingleton(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }

}
