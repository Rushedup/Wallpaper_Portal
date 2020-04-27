package com.project.wallpaperportal.logic;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NASA extends Fragment {
    private String imageUrl;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Bitmap image;
    private Target target;
    private TextView superTextView;
    private Button superButton;
    private ProgressBar progressBar;
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
        superTextView = textView;
        ImageView imageView = root.findViewById(R.id.apod_image_view);
        Button setWallpaper = root.findViewById(R.id.setWallpaper);
        superButton = setWallpaper;
        ProgressBar loading_image = root.findViewById(R.id.loading_image);
        progressBar = loading_image;
        loading_image.setVisibility(View.VISIBLE);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        setWallpaper.setVisibility(View.GONE);
        volleyRequest(textView, imageView, setWallpaper);
        return root;
    }
    private void volleyRequest(TextView textView, ImageView imageView, Button button) {
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
                        // Process the JSON
                        try{
                            // Get the JSON array
                            String explanation = response.getString("explanation");
                            String type = response.getString("media_type");
                            if (type.equals("image")) {
                                String imageURL = response.getString("hdurl");
                                imageUrl = imageURL;
                                loadFromPicasso(imageUrl, imageView);
                                textView.setText(explanation);
                            } else {
                                textView.setMovementMethod(LinkMovementMethod.getInstance());
                                textView.setText(R.string.apod_link);
                                textView.setVisibility(View.VISIBLE);
                            }
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
    private void loadFromPicasso (String url, ImageView imageView) {
            Picasso.get().load(url).into(target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    image = bitmap;
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                    if (imageView.getVisibility() == View.VISIBLE) {
                        superTextView.setVisibility(View.VISIBLE);
                        buttonHandler(superButton);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
    }
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
}
