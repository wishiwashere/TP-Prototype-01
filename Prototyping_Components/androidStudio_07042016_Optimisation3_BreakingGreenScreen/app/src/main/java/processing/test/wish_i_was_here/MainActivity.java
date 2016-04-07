package processing.test.wish_i_was_here;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new Sketch();
        fragmentManager.beginTransaction()
                .replace(R.id.processingSketch, fragment)
                .commit();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Main Activity", "resumed");

        Log.d("Main Activity", "Main Activity resumed. twitterLogged in = " + TwitterLoginActivity.twitterLoggedIn);
        /*
        Log.d("Main Activity", "Twitter username = " + TwitterLoginActivity.twitterUserUsername);
        Log.d("Main Activity", "userid = " + TwitterLoginActivity.twitterUserUserId);
        Log.d("Main Activity", "Twitter access token = " + TwitterLoginActivity.twitterUserAccessToken);
        Log.d("Main Activity", "Twitter secret token = " + TwitterLoginActivity.twitterUserSecretToken);
        */
    }
}
