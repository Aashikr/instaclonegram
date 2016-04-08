package com.instaclonegram.fragments;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.instaclonegram.R;
import com.instaclonegram.adapters.GridViewAdapter;
import com.instaclonegram.library.URLSpanNoUnderline;
import com.instaclonegram.models.Photo;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lamine on 29/03/2016.
 */
public class FragmentProfile extends Fragment {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    public FragmentProfile() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        CircleImageView profile = (CircleImageView)rootView.findViewById(R.id.imgview_profile);
        profile.setImageResource(R.drawable.kevinsys);
        initializeViews(rootView);


        gridView = (GridView) rootView.findViewById(R.id.profile_gridView);
        DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        int screen_width = displayMetrics.widthPixels;    //width of the device screen
        //int screen_height = displayMetrics.heightPixels;   //height of device screen

        int view_width = screen_width/3;   //width for imageview
        //int view_height = screen_height/3;   //height for imageview

        //ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView);

        gridAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, getData(), view_width);
        gridView.setAdapter(gridAdapter);
        return rootView;
    }

    private ArrayList<Photo> getData() {
        final ArrayList<Photo> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new Photo(bitmap));
            Log.d("bitmap", imgs.toString());
        }
        return imageItems;
    }

    public static void removeUnderlines(Spannable p_Text) {
        URLSpan[] spans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);

        for(URLSpan span:spans) {
            int start = p_Text.getSpanStart(span);
            int end = p_Text.getSpanEnd(span);
            p_Text.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            p_Text.setSpan(span, start, end, 0);
        }
    }

    private void initializeViews(View rootView) {
        TextView tv_profile_username = (TextView)rootView.findViewById(R.id.tv_profile_username);
        TextView tv_profile_description = (TextView)rootView.findViewById(R.id.tv_profile_description);
        TextView tv_profile_link = (TextView)rootView.findViewById(R.id.tv_profile_link);
        TextView tv_following = (TextView)rootView.findViewById(R.id.tv_following);
        TextView tv_followers = (TextView)rootView.findViewById(R.id.tv_followers);
        TextView tv_posts = (TextView)rootView.findViewById(R.id.tv_posts);
        TextView tv_following_cnt = (TextView)rootView.findViewById(R.id.tv_following_cnt);
        TextView tv_followers_cnt = (TextView)rootView.findViewById(R.id.tv_followers_cnt);
        TextView tv_posts_cnt = (TextView)rootView.findViewById(R.id.tv_posts_cnt);

        Button editprofile = (Button)rootView.findViewById(R.id.button_editprofile);
        editprofile.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.darkgreycolor));
        tv_profile_username.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.darkgreycolor1));
        tv_profile_description.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.darkgreycolor1));
        tv_profile_link.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.linkbluecolor));
        tv_following.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.greycolor1));
        tv_followers.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.greycolor1));
        tv_posts.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.greycolor1));
        tv_following_cnt.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.darkgreycolor1));
        tv_followers_cnt.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.darkgreycolor1));
        tv_posts_cnt.setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.darkgreycolor1));

        removeUnderlines((Spannable) tv_profile_link.getText());
    }

}