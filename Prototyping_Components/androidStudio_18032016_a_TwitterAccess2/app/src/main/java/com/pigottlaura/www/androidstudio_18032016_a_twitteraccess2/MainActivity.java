package com.pigottlaura.www.androidstudio_18032016_a_twitteraccess2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.*;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    public static String userAccessToken;
    public static String userSecretToken;

    private TwitterLoginButton loginButton;
    
    private static final String TWITTER_KEY = "huoLN2BllLtOzezay2ei07bzo";
    private static final String TWITTER_SECRET = "k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;

                String msg = "@" + session.getUserName() + " successfully logged in! (#" + session.getUserId() + ")";

                Log.d("Twit Success - ", msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                userAccessToken = session.getAuthToken().token.toString();
                userSecretToken = session.getAuthToken().secret.toString();

                Log.d("Twit Access Token - ", userAccessToken);
                Log.d("Twit Access Secret - ", userSecretToken);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
