package com.instaclonegram.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.instaclonegram.R;
import com.instaclonegram.models.Photo;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lamine on 09/04/2016.
 */

public class FeedListViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Photo> data = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private Firebase firebase;
    private int new_photo_height;


    public FeedListViewAdapter(Context context, int layoutResourceId, ArrayList data, ArrayList ids, Firebase firebase) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.ids = ids;
        this.firebase = firebase;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        final Photo photo = data.get(position);
        final String str = ids.get(position);
        Log.d("CURRENT GETVIEW ID", str);
        final Firebase currentRef = firebase.child("images").child(str);
        final Map<String, Object> likemap = new HashMap<>();
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        final int screen_width = displayMetrics.widthPixels;
        new_photo_height = (screen_width * photo.getHeight()) / photo.getWidth();

        Log.d("Viewing", Integer.toString(data.get(position).getId()));
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.profile_pic = (CircleImageView)row.findViewById(R.id.feed_profile_imgview);
            holder.username = (TextView) row.findViewById(R.id.feed_tv_username);
            holder.username.setTextColor(ContextCompat.getColor(getContext(), R.color.instagramblue));
            holder.timestamp = (TextView)row.findViewById(R.id.feed_tv_timestamp);
            holder.timestamp.setTextColor(ContextCompat.getColor(getContext(), R.color.greycolor1));
            holder.image = (ImageView) row.findViewById(R.id.feed_imageView);
            holder.like_button = (LikeButton)row.findViewById(R.id.feed_heart_button);
            holder.like_cnt = (TextView)row.findViewById(R.id.feed_tv_likes_cnt);
            holder.like_cnt.setTextColor(ContextCompat.getColor(getContext(), R.color.instagramblue));

            holder.like_tv = (TextView)row.findViewById(R.id.feed_tv_likes);
            holder.like_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.instagramblue));

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        final ViewHolder finalHolder = holder;
        currentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               finalHolder.like_cnt.setText(String.valueOf(snapshot.child("like").getValue()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                finalHolder.like_cnt.setText("0");
            }
        });

        holder.like_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                    likemap.put("like", photo.getLike() + 1);
                    photo.setLike(photo.getLike() + 1);
                    currentRef.updateChildren(likemap);
                //Log.d("CURRENT ID", ids.get(position));
                //finalHolder.like_cnt.setText(String.valueOf(photo.getLike()));
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likemap.put("like", photo.getLike() - 1);
                photo.setLike(photo.getLike() - 1);
                currentRef.updateChildren(likemap);
            }
        });


        Bitmap bitmap = base64ToBitmap(data.get(position));
        holder.image.setImageBitmap(bitmap);
        holder.image.setMinimumWidth(screen_width);
        holder.image.setMinimumHeight(new_photo_height);

        return row;
    }

    static class ViewHolder {
        CircleImageView profile_pic;
        TextView username;
        TextView timestamp;
        ImageView image;
        LikeButton like_button;
        TextView like_cnt;
        TextView like_tv;
    }

    private static Bitmap base64ToBitmap(Photo current) {
        byte[] decodedString = Base64.decode(current.getPhoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}
