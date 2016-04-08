package com.instaclonegram.fragments;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.instaclonegram.R;
import com.instaclonegram.library.URLSpanNoUnderline;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lamine on 29/03/2016.
 */
public class FragmentProfile extends Fragment {

    public FragmentProfile() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        CircleImageView profile = (CircleImageView)rootView.findViewById(R.id.imgview_profile);
        profile.setImageResource(R.drawable.kevinsys);

        initializeViews(rootView);

        return rootView;
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