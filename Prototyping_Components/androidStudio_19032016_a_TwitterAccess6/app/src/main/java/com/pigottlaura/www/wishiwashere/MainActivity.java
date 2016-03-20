package com.pigottlaura.www.wishiwashere;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    public static Boolean twitterLoggedIn = false;

    public static String twitterUserUsername;
    public static long twitterUserUserId;
    public static String twitterUserAccessToken;
    public static String twitterUserSecretToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting the XML file which will determine the layout of this
        // activity i.e. make the sketch appear full screen etc.
        setContentView(R.layout.activity_main);

        // Adding the Processing Sketch as a new Fragment (as in Processing
        // 3.0, PApplet extends Fragment)
        FragmentManager fragmentManager = getFragmentManager();

        Fragment processing = new Sketch();
        fragmentManager.beginTransaction()
                .replace(R.id.processingSketch, processing)
                .commit();

        Log.d("Main Activity", "twitterLoggedIn = " + twitterLoggedIn);
        if(twitterLoggedIn == false) {
            Fragment twitter = new TwitterLoginFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.twitter_login_fragment, twitter)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Main Activity", "Twitter onActivityResult() received");

        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getFragmentManager().findFragmentById(R.id.twitter_login_fragment);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
