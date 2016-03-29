package com.instaclonegram.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instaclonegram.R;

/**
 * Created by lamine on 29/03/2016.
 */
public class FragmentProfile extends Fragment {

    public FragmentProfile() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return rootView;
    }
}