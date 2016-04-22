package processing.test.wish_i_was_here;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

// This class contains the main activity, which contains the Processing sketch for this app
public class MainActivity extends Activity {

    final int REQUEST_MULTIPLE_PERMISSIONS = 124;

    // Overriding this activity's onCreate() method, so that the Proccesing sketch can
    // be brought in as a fragment, and displayed when this activity is running
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting the content view of this activity to the activity_main.xml layout file
        // so that the sizing and positioning values can be applied, as well as the frame
        // layout for the fragment which will contain the Processing sketch
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_MULTIPLE_PERMISSIONS);

        // Using the Android fragment manager, which will be used to add the Sketch fragment
        // to this activity below
        FragmentManager fragmentManager = getFragmentManager();

        // Creating a new fragment, and initialising it to be a new instance of the Sketch
        // class, which contains the main code for the Processing portion of this app
        Fragment fragment = new Sketch();

        // Using the fragment manager, created above, to add the Processing sketch fragment
        // to this activity by replacing the current FrameLayout element (as declared in the
        // activity_main.xml layout file) with the fragment declared above.
        fragmentManager.beginTransaction()
                .replace(R.id.processingSketch, fragment)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("Main Activity", "Main Activity Permission request result received");
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Main Activity", "Main Activity Permission was granted from device");
                } else {
                    // Permission denied
                    // functionality that depends on this permission.
                    Log.d("Main Activity", "Main Activity Permission was denied from device");
                }
                return;
            }
        }
    }

    // Overriding this activity's onResume() method, so that when the user returns to the app
    // after authenticating their login with twitter, their details will be logged out to the
    // logcat (for TESTING purposes)
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Main Activity", "resumed");
        Log.d("Main Activity", "Main Activity resumed. twitterLogged in = " + TwitterLoginActivity.twitterLoggedIn);

        // Checking if Twitter has been logged in, before trying to access the user's credentials
        if(TwitterLoginActivity.twitterLoggedIn) {
            Log.d("Main Activity", "Twitter username = " + TwitterLoginActivity.twitterUserUsername);
            Log.d("Main Activity", "userid = " + TwitterLoginActivity.twitterUserUserId);
            Log.d("Main Activity", "Twitter access token = " + TwitterLoginActivity.twitterUserAccessToken);
            Log.d("Main Activity", "Twitter secret token = " + TwitterLoginActivity.twitterUserSecretToken);
        }
    }
}
