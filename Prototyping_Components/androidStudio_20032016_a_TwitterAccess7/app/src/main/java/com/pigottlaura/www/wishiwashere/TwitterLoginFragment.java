package com.pigottlaura.www.wishiwashere;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class TwitterLoginFragment extends Fragment {

    private TwitterLoginButton loginButton;

    public static Boolean twitterLoggedIn = false;

    // Fabric.io Setup
    private static final String TWITTER_KEY = "huoLN2BllLtOzezay2ei07bzo";
    private static final String TWITTER_SECRET = "k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fabric.io - Twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getActivity(), new com.twitter.sdk.android.Twitter(authConfig));

        loginButton = (TwitterLoginButton) getActivity().findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;

                Log.d("Twitter Login", "Username: " + session.getUserName());
                Log.d("Twitter Login", "User Id: #" + session.getUserId());
                Log.d("Twitter Login", "Access Token: " + session.getAuthToken().token);
                Log.d("Twitter Login", "Secret Token: " + session.getAuthToken().secret);

                MainActivity.twitterUserUsername = session.getUserName();
                MainActivity.twitterUserUserId = session.getUserId();
                MainActivity.twitterUserAccessToken = session.getAuthToken().token;
                MainActivity.twitterUserSecretToken = session.getAuthToken().secret;

                twitterLoggedIn = true;
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("Twitter Login", "Failed to login with Twitter", exception);
            }
        });

        // Triggering a click on the login button as soon as this activity is created
        // so that the user never actually sees this activity's screen, and is instead
        // taken directly to the Twitter login page
        loginButton.performClick();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
