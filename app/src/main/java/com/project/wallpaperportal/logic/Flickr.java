package com.project.wallpaperportal.logic;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.project.wallpaperportal.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class Flickr extends Fragment {
    private Bitmap image;
    private TextView failedView;
    private ImageView imageView;
    private Button setWallpaperButton;
    private TextView imageTitle;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String api_key = "84847469a610a5f8c906b1ef3e4321b5";
    private String keyword;
    /**
     * Target to load picasso images into.
     */
    private Target target;
    private Button shuffleButton;
    private ProgressBar progressBar;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.flickr_tab, container, false);
        imageView = root.findViewById(R.id.flickr_image_view);
        failedView = root.findViewById(R.id.failedView);
        setWallpaperButton = root.findViewById(R.id.setWallpaper);
        shuffleButton = root.findViewById(R.id.shuffle_button);
        progressBar = root.findViewById(R.id.progressBar);
        FloatingActionButton searchButton = root.findViewById(R.id.search_button);
        TextInputEditText searchBar = root.findViewById(R.id.search_bar);
        imageTitle = root.findViewById(R.id.imageInfo);
        imageTitle.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        failedView.setVisibility(View.GONE);
        setWallpaperButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        searchButton.setOnClickListener(v -> {
            searchBar.onEditorAction(EditorInfo.IME_ACTION_DONE);
            shuffleButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            imageTitle.setVisibility(View.GONE);
            String key = Objects.requireNonNull(searchBar.getText()).toString();
            keyword = key;
            callFlickr(key);
            System.out.println(keyword);
        });
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                imageTitle.setVisibility(View.GONE);
                searchBar.onEditorAction(EditorInfo.IME_ACTION_GO);
                shuffleButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String key = Objects.requireNonNull(searchBar.getText()).toString();
                keyword = key;
                callFlickr(key);
                return true;
            }
            return false;
        });
        shuffleButton.setVisibility(View.GONE);
        shuffleButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            shuffleButton.setVisibility(View.GONE);
            imageTitle.setVisibility(View.GONE);
            callFlickr(keyword);
        });
        return root;
    }

    private void callFlickr(String keyword) {
        imageView.setVisibility(View.GONE);
        setWallpaperButton.setVisibility(View.GONE);
        failedView.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String getCluster = "https://www.flickr.com/services/rest/?method=flickr.tags." +
                "getClusters&api_key="+api_key+"&tag="+keyword+"&format=json&nojsoncallback=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getCluster,
                null,
                response -> {
                    try {
                        getTagCluster(response, keyword);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace
        );
        requestQueue.add(jsonObjectRequest);
    }
    /**
     * Instead of creating functions like these again for the button, just call it from the flickr class
     * @param result the image to be set
     */
    private void setWallpaper(Bitmap result) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        try {
            wallpaperManager.setBitmap(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void buttonHandler(Button button) {
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(v -> setWallpaper(image));
    }

    private void getTagCluster(JSONObject response, String tag) throws JSONException {
        if (response.getString("stat").equals("fail")) {
            failedView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        JSONObject clusters = response.getJSONObject("clusters");
        JSONArray clustersJSONArray = clusters.getJSONArray("cluster");
        List<String> clusterTags = new ArrayList<>();
        int clustersLength = clustersJSONArray.length();
//        if (clustersLength >= 25) {
//            clustersLength = 25;
//        }
        for (int i = 0; i < clustersLength; i++) {
            JSONObject temp = clustersJSONArray.getJSONObject(i);
            int total = temp.getInt("total");
//            if (total >= 10) {
//                total = 10;
//            }
            JSONArray tagArray = temp.getJSONArray("tag");
            for(int j = 0; j < total; j++) {
                JSONObject tagArrayJSONObject = tagArray.getJSONObject(j);
                String currentTag = tagArrayJSONObject.getString("_content");
                clusterTags.add(currentTag);
            }
        }
        String[] tags = getClusterPhotoOptions(clusterTags);
        getRandomPhotoID(tags, tag);
    }

    private String[] getClusterPhotoOptions (List<String> tags) {
        String[] tagOptions = new String[3];
        //get any 3 tags from the given List of String.
        int randomIndex;
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
             randomIndex = r.nextInt(tags.size());
            tagOptions[i] = tags.get(randomIndex);
            System.out.println(tagOptions[i]);
        }
        return tagOptions;
    }

    private void getRandomPhotoID(String[] tags, String originalTag) {
        String getClusterPhotoUrl = "https://www.flickr.com/services/rest/?method=flickr.tags.getClusterPhotos&api_key="
                +api_key+"&tag="+originalTag+"&cluster_id="+tags[0]+"-"+tags[1]+"-"+tags[2]+"&format=json&nojsoncallback=1";
        System.out.println(getClusterPhotoUrl);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getClusterPhotoUrl,
                null,
                response -> {
                    try {
                        JSONObject jsonObject = response.getJSONObject("photos");
                        JSONArray photoIDs = jsonObject.getJSONArray("photo");
                        JSONArray publicPhotoIDs = new JSONArray();
                        for (int i = 0; i < photoIDs.length(); i++) {
                            JSONObject temp = photoIDs.getJSONObject(i);
                            int isPublic = temp.getInt("ispublic");
                            if (isPublic == 1) {
                                publicPhotoIDs.put(temp);
                            }
                        }
                        String[] pictureInfo = returnRandomID(publicPhotoIDs);
                        System.out.println(pictureInfo[0]);
                        getPhotoUrl(pictureInfo[0]);
                        imageTitle.setText(pictureInfo[1]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace
        );
        requestQueue.add(jsonObjectRequest);
    }

    private String[] returnRandomID (JSONArray publicPhotoID) throws JSONException {
        Random r = new Random();
        int randomIndex = r.nextInt(publicPhotoID.length());
        String[] photoIDAndTitle = new String[3];
        photoIDAndTitle[0] = publicPhotoID.getJSONObject(randomIndex).getString("id");
        photoIDAndTitle[1] = publicPhotoID.getJSONObject(randomIndex).getString("title");
        photoIDAndTitle[2] = publicPhotoID.getJSONObject(randomIndex).getString("username");
        return photoIDAndTitle;
    }

    private void getPhotoUrl(String photoId) {
        String getSizeUrl = "https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&" +
                "api_key="+api_key+"&photo_id="+photoId+"&format=json&nojsoncallback=1";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getSizeUrl,
                null,
                response -> {
                    try {
                        JSONObject jsonObject = response.getJSONObject("sizes");
                        JSONArray sizes = jsonObject.getJSONArray("size");
                        JSONObject photo = sizes.getJSONObject(sizes.length() - 1);
                        String url = photo.getString("source");
                        System.out.println(url);
                        loadImage(url, imageView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void loadImage (String imageUrl, ImageView imageViewToBeLoaded) {
        Picasso.get().load(imageUrl).into(target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                image = bitmap;
                imageViewToBeLoaded.setImageBitmap(bitmap);
                imageViewToBeLoaded.setVisibility(View.VISIBLE);
                if(imageViewToBeLoaded.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                    buttonHandler(setWallpaperButton);
                    shuffleButton.setVisibility(View.VISIBLE);
//                    imageTitle.setVisibility(View.VISIBLE);
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
}
