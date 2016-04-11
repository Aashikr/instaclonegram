package com.instaclonegram.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lamine on 09/04/2016.
 */

public class FeedListViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Photo> data = new ArrayList<>();
    private Firebase firebase;

    public FeedListViewAdapter(Context context, int layoutResourceId, ArrayList data, Firebase firebase) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.firebase = firebase;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        Log.d("Viewing", Integer.toString(data.get(position).getId()));
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.photo = data.get(position);
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

            final ViewHolder finalHolder = holder;

/*            firebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Long like = (Long) snapshot.child("photo").child(Integer.toString(finalHolder.photo.getId())).child("like").getValue();
                    finalHolder.photo.setLike(like.intValue());
                    finalHolder.like_cnt.setText(Integer.toString(like.intValue()));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    //finalHolder.like_cnt.setText("0");
                }
            });
*/
            holder.like_button.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    firebase.child("photo").child(Integer.toString(data.get(position).getId())).child("like").setValue(finalHolder.photo.getLike() + 1);
                    finalHolder.photo.setLike(finalHolder.photo.getLike() + 1);
                    Log.d("LIKED ThIS PICTURE : ", Integer.toString(data.get(position).getId()));
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    firebase.child("photo").child(Integer.toString(data.get(position).getId())).child("like").setValue(finalHolder.photo.getLike() - 1);
                    finalHolder.photo.setLike(finalHolder.photo.getLike() - 1);
                    Log.d("UNLIKED ThIS PICTURE : ", Integer.toString(data.get(position).getId()));
                }
            });
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.image.setImageBitmap(holder.photo.getPhoto());
        holder.image.setMinimumWidth(holder.photo.getWidth());
        holder.image.setMinimumHeight(holder.photo.getHeight());

        return row;
    }

    static class ViewHolder {
        CircleImageView profile_pic;
        Photo photo;
        TextView username;
        TextView timestamp;
        ImageView image;
        LikeButton like_button;
        TextView like_cnt;
        TextView like_tv;
        int id;
    }
}
