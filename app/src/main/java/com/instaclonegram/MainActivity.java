package com.instaclonegram;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.instaclonegram.fragments.FragmentExplore;
import com.instaclonegram.fragments.FragmentFeed;
import com.instaclonegram.fragments.FragmentProfile;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class MainActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle bundle) {
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://instaclonegram.firebaseio.com/");
        this.disableLearningPattern();
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.instagramblue)));
        ab.setElevation(0);
        ab.setTitle("Instagram");
        MaterialAccount account = new MaterialAccount(this.getResources(),"Kevin Systrom","kevin@instagram.com", R.drawable.kevinsys, R.drawable.smallsf);
        MaterialSection section1 = newSection("Profile", new FragmentProfile(myFirebaseRef));
        MaterialSection section2 = newSection("Feed", new FragmentFeed(myFirebaseRef));
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

    /*
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
    }*/

}
