package com.instaclonegram.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.instaclonegram.R;
import com.instaclonegram.adapters.FeedListViewAdapter;
import com.instaclonegram.models.Photo;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lamine on 29/03/2016.
 */
public class FragmentFeed extends Fragment {

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

        /*Photo a = new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 0, "kevin", 0, "20m", screen_width, new_photo_height);
        Photo b = new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 1, "kevin", 0, "20m", screen_width, new_photo_height);
        Photo c = new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 2, "kevin", 0, "20m", screen_width, new_photo_height);
        Photo x = new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 3, "kevin", 0, "20m", screen_width, new_photo_height);
        Photo e = new Photo((BitmapFactory.decodeResource(getResources(), R.drawable.smallsf)), 4, "kevin", 0, "20m", screen_width, new_photo_height);
        firebase.child("photo").child(Integer.toString(a.getId()));
        firebase.setValue(a);
        firebase.child("photo").child(Integer.toString(b.getId())).setValue(b);
        firebase.child("photo").child(Integer.toString(c.getId())).setValue(c);
        firebase.child("photo").child(Integer.toString(x.getId())).setValue(x);
        firebase.child("photo").child(Integer.toString(e.getId())).setValue(e);*/

   /*     final ArrayList<Photo> ax = new ArrayList<>();
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Photo photo = postSnapshot.getValue(Photo.class);
                    ax.add(photo);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        FeedListViewAdapter flva = new FeedListViewAdapter(getContext(), R.layout.photo_item, al, firebase);
        lv.setAdapter(flva);
        return rootView;
    }

}
