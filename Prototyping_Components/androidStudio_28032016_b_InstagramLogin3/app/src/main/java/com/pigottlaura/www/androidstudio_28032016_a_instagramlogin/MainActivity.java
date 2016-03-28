package com.pigottlaura.www.androidstudio_28032016_a_instagramlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    Button InstagramLoginButton;
    Boolean instagramLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("INSTAGRAM", "About to check if instagram logged in - " + instagramLoggedIn);

        checkForInstagramData();

        InstagramLoginButton = (Button)findViewById(R.id.instagram_login_button);
        InstagramLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithInstagram();
            }
        });
    }

    private void signInWithInstagram() {
        final Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.instagram.com")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", "68fb309036d446a2bada8e179d5122b4")
                .appendQueryParameter("redirect_uri", "sociallogin://redirect")
                .appendQueryParameter("response_type", "token");
        final Intent browser = new Intent(Intent.ACTION_VIEW, uriBuilder.build());
        startActivity(browser);
    }

    private void checkForInstagramData() {
        final Uri data = this.getIntent().getData();
        if(data != null && data.getScheme().equals("sociallogin") && data.getFragment() != null) {
            Log.d("INSTAGRAM", "Data received");
            instagramLoggedIn = true;
            final String accessToken = data.getFragment().replaceFirst("access_token=", "");
            // Can then request user data by sending request to https://api.instagram.com/v1/users/self/?access_token=accessToken
            /* Response will be like
            {
                "access_token": "fb2e77d.47a0479900504cb3ab4a1f626d174d2d",
                "user": {
                    "id": "1574083",
                    "username": "snoopdogg",
                    "full_name": "Snoop Dogg",
                    "profile_picture": "..."
                }
            }
            */
            if (accessToken != null) {
                Log.d("INSTAGRAM", "Access token received - " + accessToken);
                Log.d("INSTAGRAM", "Data = " + data);
            } else {
                Log.d("INSTAGRAM", "No Access token available");
            }
        }
    }

}
