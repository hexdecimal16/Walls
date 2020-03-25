package com.dhairytripathi.walls.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dhairytripathi.walls.Adapter.PixbayAdapter;
import com.dhairytripathi.walls.Model.Pixbay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceAPI {
    private static  final String  baseUrl = "https://wall.alphacoders.com/api2.0/get.php?auth=e02565c48ea6d26731d0742efda71d95&";
    private static final String GET = "https://pixabay.com/api/?key=15336001-7271597ddcdc7a1c22d248c5e";

    public static void getImages(final Context context, String q, int page, boolean editorsChoice, String order, String color, String category, String orientation, String safesearch, final ArrayList<Pixbay> pixbays, final PixbayAdapter adapter) {
        String url = GET  + "&image_type=photo&orientation=" + orientation + "&q=" + q + "&page=" + page + "&editors_choice="
                + editorsChoice + "&order=" + order + "&colors=" + color + "&category=" + category + "&safesearch=true" ;
        Log.d("URL", url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hits = response.getJSONArray("hits");
                            for(int i = 0; i < hits.length()  ; i++) {
                                JSONObject hit = hits.getJSONObject(i);
                                String id = hit.getString("id");
                                String tags = hit.getString("tags");
                                String previewURL = hit.getString("previewURL");
                                String previewWidth = hit.getString("previewWidth");
                                String previewHeight = hit.getString("previewHeight");
                                String webformatURL = hit.getString("webformatURL");
                                String webformatWidth = hit.getString("webformatWidth");
                                String webformatHeight = hit.getString("webformatHeight");
                                String largeImageURL  = hit.getString("largeImageURL");
                                String imageURL = hit.getString("largeImageURL");
                                String imageWidth = hit.getString("imageWidth");
                                String imageHeight = hit.getString("imageHeight");
                                String imageSize = hit.getString("imageSize");
                                String views = hit.getString("views");
                                String downloads = hit.getString("downloads");
                                String favorites = hit.getString("favorites");
                                String likes = hit.getString("likes");
                                String comments = hit.getString("comments");
                                String user_id = hit.getString("user_id");
                                String user = hit.getString("user");
                                String userImageURL = hit.getString("userImageURL");
                                pixbays.add(new Pixbay(id, tags, previewURL, previewWidth, previewHeight, webformatURL,webformatWidth, webformatHeight, largeImageURL, null, imageURL,imageWidth,imageHeight,imageSize,views,downloads,favorites,comments, likes,user_id,user,userImageURL));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(request);
    }
}
