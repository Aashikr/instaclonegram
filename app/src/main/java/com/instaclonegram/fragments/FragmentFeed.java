package com.instaclonegram.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.instaclonegram.R;
import com.instaclonegram.adapters.FeedListViewAdapter;
import com.instaclonegram.models.Photo;
import com.like.LikeButton;
import com.melnykov.fab.FloatingActionButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lamine on 29/03/2016.
 */
public class FragmentFeed extends Fragment {

    String encodedString;
    String imgPath, fileName;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMG = 1;
    private static String username = "kevin";

    Firebase firebase;
    public FragmentFeed() {

    }

    public FragmentFeed(Firebase firebase) {
        this.firebase = firebase;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        //initialize_views(rootView);


        DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        final int screen_width = displayMetrics.widthPixels;
        final ListView lv = (ListView)rootView.findViewById(R.id.feed_listView);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.attachToListView(lv);

        final ArrayList<Photo> al = new ArrayList<>();

        Firebase ref = new Firebase("https://instaclonegram.firebaseio.com/images");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Map<String,Object> image_map = (Map<String, Object>) dataSnapshot.child("kevin").getValue();
                    for (Map.Entry<String, Object> entry : image_map.entrySet()) {
                        String photo_str = String.valueOf(entry.getValue());
                        byte[] decodedString = Base64.decode(photo_str, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        if (decodedByte == null) {
                            Log.d("decodedbyte", "null");
                        }
                        int photo_height = decodedByte.getHeight();
                        int photo_width = decodedByte.getWidth();
                        final int new_photo_height = (screen_width * photo_height) / photo_width;
                        Photo photo = new Photo(decodedByte, 7, entry.getKey(), 0, "20m", screen_width, new_photo_height);
                        al.add(photo);
                    }
                }
                FeedListViewAdapter flva = new FeedListViewAdapter(getContext(), R.layout.photo_item, al, firebase);
                lv.setAdapter(flva);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery(rootView);
                //firebase.child("image").child(bmp.get).setValue(imageFile);
            }
        });

        FeedListViewAdapter flva = new FeedListViewAdapter(getContext(), R.layout.photo_item, al, firebase);
        lv.setAdapter(flva);
        return rootView;
    }


    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    public String convertImage()
    {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        bitmap = BitmapFactory.decodeFile(imgPath,
                options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Must compress the Image to reduce image size to make upload easy
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byte_arr = stream.toByteArray();
        // Encode Image to String
        encodedString = Base64.encodeToString(byte_arr, 0);
        Log.d("SIZE IMAGE!!!!!", Integer.toString(encodedString.length()));
        return encodedString;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                /* ENCODED */

                String converted = convertImage();
                firebase.child("images").child(username).child(fileName.replace(".", "o")).setValue(converted);

            } else {
                Toast.makeText(this.getActivity().getApplicationContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this.getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            Log.d("exception", e.toString());
        }

    }


}
