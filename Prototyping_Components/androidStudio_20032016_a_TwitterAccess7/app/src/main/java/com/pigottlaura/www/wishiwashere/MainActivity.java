package com.pigottlaura.www.wishiwashere;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    public static Boolean twitterLoggedIn = false;

    public static String twitterUserUsername;
    public static long twitterUserUserId;
    public static String twitterUserAccessToken;
    public static String twitterUserSecretToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();

        Fragment twitter = new TwitterLoginFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.twitterLoginFragment, twitter)
                .commit();

        Fragment processing = new Sketch();
        fragmentManager.beginTransaction()
                .replace(R.id.processingSketch, processing)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Main Activity", "Activity resumed");
        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getFragmentManager().findFragmentById(R.id.twitterLoginFragment);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
