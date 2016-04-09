package com.instaclonegram.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instaclonegram.R;
import com.like.LikeButton;

/**
 * Created by lamine on 29/03/2016.
 */
public class FragmentFeed extends Fragment {

    public FragmentFeed() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.photo_item, container, false);
        //initialize_views(rootView);
        return rootView;
    }
    
}
