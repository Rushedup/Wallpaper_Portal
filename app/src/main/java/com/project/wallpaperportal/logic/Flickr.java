package com.project.wallpaperportal.logic;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.wallpaperportal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Flickr extends Fragment {
    private Bitmap image;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String api_key = "84847469a610a5f8c906b1ef3e4321b5";
    private String getClusterUrl;
    private String getClusterPhotos;
    private String getPhotoSizes;
    private  String test = "https://www.flickr.com/services/rest/?method=flickr.test.echo&name=value&api_key=84847469a610a5f8c906b1ef3e4321b5&format=json";
    public static Flickr newInstance(int index) {
        Flickr fragment = new Flickr();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        System.out.println("flickr on create");
        super.onCreate(savedInstanceState);
        System.out.println("flickr on create");
    }
    //refer to https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("flickr on create view");
        View root = inflater.inflate(R.layout.flickr_tab, container, false);
        callFlickr(test);
        return root;
    }
//    private void callFlickr (String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try{
//                            String print = response.getString("api_key");
//                            System.out.println(print);
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error){
//                        System.out.println(error);
//                    }
//                }
//        );
//        requestQueue.add(jsonObjectRequest);
//    }
    private  void callFlickr(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);
                        JSONObject workableResponse = parseResponse(response);
                        String apiKey = null;
                        try {
                            JSONObject workAround = workableResponse.getJSONObject("api_key");
                            apiKey = workAround.getString("_content");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(apiKey);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    /**
     * Instead of creating functions like these again for the button, just call it from the flickr class
     * @param result the image to be set
     */
    private void setWallapaper(Bitmap result) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        try {
            wallpaperManager.setBitmap(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void buttonHandler(Button button) {
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWallapaper(image);
            }
        });
    }
    private JSONObject parseResponse(String response) {
        char[] temp = new  char[response.length() - 15];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = response.charAt(i + 14);
        }
        String toReturn = new String(temp);
        try {
            JSONObject object = new JSONObject(toReturn);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
