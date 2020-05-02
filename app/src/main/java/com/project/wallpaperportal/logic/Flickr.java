package com.project.wallpaperportal.logic;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.project.wallpaperportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flickr extends Fragment {
    private Bitmap image;
    private TextView failedView;
    private ImageView imageView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String api_key = "84847469a610a5f8c906b1ef3e4321b5";
    private String keyword = null;
//    private
    private String getClusterPhotos;
    private String getPhotoSizes;
    private  String test = "https://www.flickr.com/services/rest/?method=flickr.test.echo&name=value" +
            "&api_key="+api_key+"&format=json&nojsoncallback=1";
    public static Flickr newInstance(int index) {
        Flickr fragment = new Flickr();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //refer to https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.flickr_tab, container, false);
        imageView = root.findViewById(R.id.flickr_image_view);
        imageView.setVisibility(View.GONE);
        failedView = root.findViewById(R.id.failedView);
        failedView.setVisibility(View.GONE);
        FloatingActionButton searchButton = root.findViewById(R.id.search_button);
        TextInputEditText searchBar = root.findViewById(R.id.search_bar);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = searchBar.getText().toString();
                keyword = key;
                System.out.println(keyword);
                callFlickr(key);
            }
        });
        return root;
    }
    private void callFlickr (String keyword) {
        failedView.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String getCluster = "https://www.flickr.com/services/rest/?method=flickr.tags." +
                "getClusters&api_key="+api_key+"&tag="+keyword+"&format=json&nojsoncallback=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getCluster,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getTagCluster(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println(error);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
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
    private void getTagCluster(JSONObject response) throws JSONException {
        if (response.getString("stat").equals("fail")) {
            failedView.setVisibility(View.VISIBLE);
        }
        JSONObject clusters = response.getJSONObject("clusters");
        JSONArray clustersJSONArray = clusters.getJSONArray("cluster");
        List<String> clusterTags = new ArrayList<String>();
        int clustersLength = clustersJSONArray.length();
        if (clustersLength >= 25) {
            clustersLength = 25;
        }
        for (int i = 0; i < clustersLength; i++) {
            JSONObject temp = clustersJSONArray.getJSONObject(i);
            int total = temp.getInt("total");
            if (total >= 10) {
                total = 10;
            }
            JSONArray tagArray = temp.getJSONArray("tag");
            for(int j = 0; j < total; j++) {
                JSONObject tagArrayJSONObject = tagArray.getJSONObject(j);
                String tag = tagArrayJSONObject.getString("_content");
                clusterTags.add(tag);
            }
        }
        System.out.println(clusterTags.size());
        String[] tags = getClusterPhotoOptions(clusterTags);

    }
    private String[] getClusterPhotoOptions (List<String> tags) {
        String[] tagOptions = new String[3];
        //get any 3 tags from the given List of String.
        int randomIndex;
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
             randomIndex = r.nextInt(tags.size());
            tagOptions[i] = tags.get(randomIndex);
        }
        return  tagOptions;
    }
}
