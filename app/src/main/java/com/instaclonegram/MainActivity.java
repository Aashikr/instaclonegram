package com.instaclonegram;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.instaclonegram.fragments.FragmentExplore;
import com.instaclonegram.fragments.FragmentFeed;
import com.instaclonegram.fragments.FragmentLogin;
import com.instaclonegram.fragments.FragmentProfile;
import com.instaclonegram.library.GetBitmapAsyncTask;
import com.instaclonegram.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class MainActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle bundle) {
        //FragmentManager fm = this.getSupportFragmentManager();
        // Ajoutez le fragment

        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://instaclonegram.firebaseio.com/");
        this.disableLearningPattern();
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.instagramblue)));
        ab.setElevation(0);
        ab.setTitle("Instagram");

        Intent intent = getIntent();
        String process = intent.getStringExtra("process");
        final String name = intent.getStringExtra("name");
        final String username = intent.getStringExtra("username");
        final String email = intent.getStringExtra("email");
        final String picture = intent.getStringExtra("picture");



        User user = new User(picture, username, name, "I am "+name+" and I love building stuff!", "www.laminekechache.com", email, "public", 0, 0, 0);
        Bitmap bit = null;
        try {
            bit = new GetBitmapAsyncTask().execute(picture).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (process.contentEquals("loginFacebook") || process.contentEquals("registerSimple")) {
            Firebase ref = new Firebase("https://instaclonegram.firebaseio.com/users");
            Map<String, String> map = new HashMap<String, String>();
            map.put("picture", picture);
            map.put("username", username);
            map.put("name", name);
            map.put("description", "I am "+name+" and I love building stuff!");
            map.put("link", "www.laminekechache.com");
            map.put("email", email);
            map.put("privacy", "public");
            map.put("followers", "0");
            map.put("following", "0");
            map.put("posts", "0");
            ref.child(email.replaceAll(".", "")).push().setValue(map);
        }
        else if (process.contentEquals("loginSimple")) // Have to get back data from database using 'email'
        {

        }

        MaterialAccount account = null;
        account = new MaterialAccount(this.getResources(),name, email, bit, R.drawable.smallsf);
        MaterialSection section1 = newSection("Profile", new FragmentProfile(myFirebaseRef, user));
        MaterialSection section2 = newSection("Feed", new FragmentFeed(myFirebaseRef, user));
        MaterialSection section3 = newSection("Explore", new FragmentExplore());
        addAccount(account);
        addSection(section2);
        addSection(section1);
        addSection(section3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
