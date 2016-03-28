package com.pigottlaura.www.androidstudio_28032016_a_instagramlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button mInstagramSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInWithInstagram();
        /*
        mInstagramSignInButton = (Button)findViewById(R.id.instagram_sign_in_button);
        mInstagramSignInButton.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithInstagram();
            }
        });
        */
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && data.getScheme().equals("sociallogin")) {
            Log.d("INSTAGRAM", "Data received - " + data.getDataString());
            /*
            final String accessToken = data.getFragment().replaceFirst("access_token=", "");
            if (accessToken != null) {
                Log.d("INSTAGRAM", "Access token = " + accessToken);
            } else {
                Log.d("INSTAGRAM", "No Access Token received");
            }
            */
        } else {
            Log.d("INSTAGRAM", "No data received");
        }
    }

}
