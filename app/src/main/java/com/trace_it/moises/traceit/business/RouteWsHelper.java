package com.trace_it.moises.traceit.business;

import android.os.AsyncTask;
import android.util.Log;

import com.trace_it.moises.traceit.domain.Coordinate;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kenneth on 23/06/2015.
 */
public class RouteWsHelper extends AsyncTask<ArrayList<Coordinate>, String, String> {


    @Override
    protected String doInBackground(ArrayList<Coordinate>... params) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("");
        post.setHeader("content-type", "application/json");
        try {
            JSONArray jsonArray=new JSONArray(params[0]);
            JSONObject object=new JSONObject();
            for(Coordinate coordinate:params[0]){
                object.put("X",coordinate.getX());
                object.put("Y",coordinate.getY());
            }
            StringEntity entity=new StringEntity(jsonArray.toString());
            post.setEntity(entity);
            HttpResponse response=client.execute(post);
        } catch (Exception ex) {
            Log.e("route ex" ,ex.getMessage());
        }
        return null;
    }
}
