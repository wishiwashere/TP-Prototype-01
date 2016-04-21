package processing.test.wish_i_was_here;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

// This class contains the main activity, which contains the Processing sketch for this app
public class MainActivity extends Activity {
    // Creating a Toast object, which will be used to generate a pop up to display to the user if there is no
    // green detected in the image i.e. if the user is not standing in front of a green background, to prompt
    // them to reframe their image
    public static Toast greenScreenWarning;

    // Creating an static constant int, which will be used to request permissions
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

        // requests permissions that are needed for the application. It outines that a camera
        // and writing to external storage permissions are needed. These permission are stored in
        // a string object so they can be passed into the on onRequestPermissionsResult method to
        // see what whether the user has given permission
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

        // Initialising the green screen warning Toast popup to contain the relevant text, and last for the
        // longer length of time. This pop up will appear if there is not enough green detected in the
        // camera live view screen
        greenScreenWarning = Toast.makeText(getBaseContext(), "To see yourself in this location please stand in front of a green background", Toast.LENGTH_LONG);

        // Setting the positioning of the green screen warning popup to be centered on screen
        greenScreenWarning.setGravity(Gravity.CENTER, 0, 0);

    }

    // A method that is called once the user has responded to the permissions pop-up. It accesses the
    // REQUEST_MULTIPLE_PERMISSIONS and reads does the specified permissions the application is looking for
    // and whether or not the user has granted or denied the requesting permissions. This is necessary for
    // Android 6.0 and marshmallow devices

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("Main Activity", "Main Activity Permission request result received");
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission has been granted and the application can move forward and use the necessary
                    //functionality
                    Log.d("Main Activity", "Main Activity Permission was granted from device");
                } else {
                    // Permission has been denied, the user is not allowing the application the access
                    // the camera or write to storage
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
