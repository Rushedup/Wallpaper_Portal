package com.project.wallpaperportal.logic;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.project.wallpaperportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NASA extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static NASA newInstance(int index) {
        NASA fragment = new NASA();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        System.out.println("nasa on create");
        super.onCreate(savedInstanceState);
        System.out.println("nasa on create");
    }
    //refer to https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("nasa on create view");
        View root = inflater.inflate(R.layout.nasa_tab, container, false);
//        root.findViewById(R.id.nasa_image_info);
        TextView textView = root.findViewById(R.id.nasa_image_info);
        textView.setMovementMethod(new ScrollingMovementMethod());
        ImageView imageView = root.findViewById(R.id.apod_image_view);
        volleyRequest(textView, imageView);
        return root;
    }
    private void volleyRequest(TextView textView, ImageView imageView) {
//        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://api.nasa.gov/planetary/apod?api_key=zxgod3DcImapspEBaEBvIdC8dpv4Y1V8BO9L5KU8";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Get the JSON array
                            String explanatiion = response.getString("explanation");
                            textView.setText(explanatiion);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        System.out.println("DID NOT WORK!");
                    }
                }
        );
        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
//    private void setText(TextView textView, String response) {
//        String[] explanation = response.split("explanation");
//        explanation = explanation[1].split("\"", 4);
////                        explanation = explanation[3].split("\"", 3);
//        // Display the first 500 characters of the response string.
//        textView.setText(explanation[2]);
//    }
//    private String getImageURL (String response) {
//        String[] splitCheck = response.split("media_type");
//        if (splitCheck[])
//    }

}
