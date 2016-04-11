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
        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.smallsf, null);
        int photo_height = d.getIntrinsicHeight();
        int photo_width = d.getIntrinsicWidth();
        DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        int screen_width = displayMetrics.widthPixels;
        int new_photo_height = (screen_width * photo_height) / photo_width;
        ListView lv = (ListView)rootView.findViewById(R.id.feed_listView);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.attachToListView(lv);

        ArrayList<Photo> al = new ArrayList<>();

        al.add(0, new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 0, "kevin", 0, "20m", screen_width, new_photo_height));
        al.add(1, new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 1, "kevin", 0, "20m", screen_width, new_photo_height));
        al.add(2, new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 2, "kevin", 0, "20m", screen_width, new_photo_height));
        al.add(3, new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 3, "kevin", 0, "20m", screen_width, new_photo_height));
        al.add(4, new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 4, "kevin", 0, "20m", screen_width, new_photo_height));
        al.add(5, new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 5, "kevin", 0, "20m", screen_width, new_photo_height));
        al.add(6, new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 6, "kevin", 0, "20m", screen_width, new_photo_height));
        al.add(7, new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 7, "kevin", 0, "20m", screen_width, new_photo_height));

        for (Photo p : al) {
            Log.d("NUMBER", Integer.toString(p.getId()));
        }


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
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byte_arr = stream.toByteArray();
        // Encode Image to String
        encodedString = Base64.encodeToString(byte_arr, 0);
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
/*
                ImageLoader imageLoader = ImageLoader.getInstance();
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).build();
                imageLoader.init(config);
                DisplayImageOptions options = new DisplayImageOptions.Builder().build();*/
                firebase.child("image").child("kevin").setValue(converted);

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
