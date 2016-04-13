package com.instaclonegram.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.instaclonegram.MainActivity;
import com.instaclonegram.R;
import java.util.Map;
import com.facebook.FacebookSdk;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by lamine on 12/04/2016.
 */
public class FragmentLogin extends Fragment {
    private CallbackManager callbackManager;
    private AccessTokenTracker mFacebookAccessTokenTracker;


    public FragmentLogin() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText et_email = (EditText)rootView.findViewById(R.id.et_email);
        final EditText et_pwd = (EditText)rootView.findViewById(R.id.et_pwd);
        Firebase.setAndroidContext(getActivity());
        FacebookSdk.sdkInitialize(this.getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        mFacebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("FACEBOOKCURRENT", "Facebook.AccessTokenTracker.OnCurrentAccessTokenChanged");
                onFacebookAccessTokenChange(currentAccessToken);
            }
        };



        LoginButton btn_login_facebook = (LoginButton)rootView.findViewById(R.id.login_button_fb);
        btn_login_facebook.setReadPermissions("user_friends");
        btn_login_facebook.setReadPermissions("email");
        // If using in a fragment
        btn_login_facebook.setFragment(this);
        btn_login_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken at = loginResult.getAccessToken();
                onFacebookAccessTokenChange(at);
                Log.d("Facebook", "onsuccess");
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("Facebook", "oncancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getContext(), "Sorry an error occured", Toast.LENGTH_LONG).show();
            }
        });


        final Firebase ref = new Firebase("https://instaclonegram.firebaseio.com/");

        Button btn_login = (Button)rootView.findViewById(R.id.button_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_email.getText() != null && et_pwd.getText() != null) {
                    ref.authWithPassword(et_email.getText().toString(), et_pwd.getText().toString(), new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                            Intent myIntent = new Intent(getActivity(), MainActivity.class);
                            myIntent.putExtra("process", "loginSimple");
                            myIntent.putExtra("email", et_email.getText().toString()); //Optional parameters
                            getActivity().startActivity(myIntent);
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            Toast.makeText(getActivity().getApplicationContext(), "There was a problem. Try again.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        TextView tv_signup = (TextView)rootView.findViewById(R.id.tv_signup_login);
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frame_content, new FragmentRegister(ref)).commit();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void onFacebookAccessTokenChange(AccessToken token) {
        Firebase ref = new Firebase("https://instaclonegram.firebaseio.com/");
        if (token != null) {
            //Log.d("FACEBOOK TOKEN", token.toString());
            ref.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    // The Facebook user is now authenticated with your Firebase app
                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                    myIntent.putExtra("process", "loginFacebook");
                    myIntent.putExtra("email", String.valueOf(authData.getProviderData().get("email"))); //Optional parameters
                    myIntent.putExtra("picture", String.valueOf(authData.getProviderData().get("profileImageURL"))); //Optional parameters
                    myIntent.putExtra("name", String.valueOf(authData.getProviderData().get("displayName")));
                    myIntent.putExtra("username", String.valueOf(authData.getProviderData().get("displayName")).replaceAll(" ",""));
                    getActivity().startActivity(myIntent);
                }
                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error
                }
            });
        } else {
    /* Logged out of Facebook so do a logout from the Firebase app */
            ref.unauth();
            //Log.d("ERRORFACEBOOK", token.toString());
        }

    }
}