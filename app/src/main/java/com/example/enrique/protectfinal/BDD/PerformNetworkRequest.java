package com.example.enrique.protectfinal.BDD;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask {
    private String url;
    private HashMap<String, String> params;
    int requestCode;

    public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    @Override
    protected Object doInBackground(Object[] args) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == Api.CODE_POST_REQUEST) {
            Log.i("CONEXION", "doInbCK_POST");
            return requestHandler.sendPostRequest(url, params);
        }else if (requestCode == Api.CODE_GET_REQUEST) {
            Log.i("CONEXION", "doInbCK_GET");
            return requestHandler.sendGetRequest(url);
        }else if(requestCode == 3){
            return requestHandler.downloadBitmap(url);
        }
        return null;
    }
}
