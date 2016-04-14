package processing.test.wish_i_was_here;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

// This class contains the login activity for authorising a user's Twitter account using Fabric.io
public class TwitterLoginActivity extends Activity {

    // Creating two buttons, one for redirecting the user the the Twitter login page,
    // and one for allowing a user to proceed to the MainActivity without logging in
    // to Twitter
    private TwitterLoginButton loginButton;
    private Button cancelLoginButton;

    // Creating public static variables, to store the data relating to the Twitter login,
    // so that it can be accessed from any class within this app
    public static Boolean twitterLoggedIn = false;
    public static String twitterUserUsername;
    public static long twitterUserUserId;
    public static String twitterUserAccessToken;
    public static String twitterUserSecretToken;

    // Creating two static variables to store our app's Twitter Key, and Twitter Secret key
    // which will be used to create our authorisation configuration once this class
    // has been created.
    private static final String TWITTER_KEY = "huoLN2BllLtOzezay2ei07bzo";
    private static final String TWITTER_SECRET = "k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb";

    // Overriding this activity's onCreate() method, so that the Twitter login can be set up,
    // using Fabric.io, and the relevant buttons and text displayed when this activity is running
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creating a new authorisation configuration for Twitter, using our app's Twitter
        // key and secret key credentials, as specified above
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        // Configuring the Fabric.io kit, using the TwitterAuthConfig configuration we
        // created above.
        Fabric.with(this, new com.twitter.sdk.android.Twitter(authConfig));

        // Setting the content view of this activity to the activity_twitter_login.xml layout file
        // so that the sizing and positioning values can be applied to the buttons, and the
        // relevant text views added to the screen
        setContentView(R.layout.activity_twitter_login);

        // Initialising the loginButton to be a TwitterLoginButton, based on the dimensions specified
        // in the activity_twitter_login.xml layout file
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        // Using the setCallback() method to define what will happen when a Twitter authorisation callback
        // is passed to the loginButton
        loginButton.setCallback(new Callback<TwitterSession>() {

            // Overriding this callback's success method, passing in the result to be processed
            @Override
            public void success(Result<TwitterSession> result) {

                // Creating a TwitterSession object, to store the data contained in the result of the call
                // to authorise the login
                TwitterSession session = result.data;

                // Initialising the static variables of this class, so that the user's credentials
                // can be accessed from all other classes in this app
                twitterUserUsername = session.getUserName();
                twitterUserUserId = session.getUserId();
                twitterUserAccessToken = session.getAuthToken().token;
                twitterUserSecretToken = session.getAuthToken().secret;

                // Logging out each of the user's credentials (for TESTING purposes)
                Log.d("Twitter Login", "Username: " + twitterUserUsername);
                Log.d("Twitter Login", "User Id: #" + twitterUserUserId);
                Log.d("Twitter Login", "Access Token: " + twitterUserAccessToken);
                Log.d("Twitter Login", "Secret Token: " + twitterUserSecretToken);

                // Setting the static twitterLoggedIn boolean of this class to be true, so that all
                // classes can now check if the user has logged in with Twitter
                twitterLoggedIn = true;
            }

            // Overriding this callback's failure method, passing in the exception which occurred
            @Override
            public void failure(TwitterException exception) {
                // Logging out the exception to the logcat (for TESTING purposes)
                Log.d("Twitter Login", "Failed to login with Twitter", exception);
            }
        });

        // Triggering a click on the login button as soon as this activity is created
        // so that the user never actually sees this activity's screen, and is instead
        // taken directly to the Twitter login page. (Not currently used)
        //loginButton.performClick();

        // Initialising the cancelLoginButton to be a Button, based on the dimensions specified
        // in the activity_twitter_login.xml layout file
        cancelLoginButton = (Button) findViewById(R.id.cancel_login_button);

        // Setting up a click event listener on this button
        cancelLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling this class's goToMainActivity() method, to switch to the main Processing app
                goToMainActivity();
            }
        });
    }

    // Overriding this activity's onActivityResult() method, so that we can pass it to
    // the loginButton's callback, to parse the user data from the result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Passing the activity result to the loginButton
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    // Overriding this activity's onResume() method, so we can check if the login has
    // been completed
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Twitter Login", "On Resume - twitterLoggedIn = " + twitterLoggedIn);

        // Checking if the twitter login has been successful before returning the user to the main
        // Processing app. If the log in was not successful, the user would just be brought back to
        // the TwitterLoginActivity screen, and could choose to try again, or continue without Twitter
        if(twitterLoggedIn) {
            // Calling this class's goToMainActivity() method, to switch to the main Processing app
            goToMainActivity();
        }
    }

    protected void goToMainActivity(){
        // Creating a new intent, to take the user from this Activity, to the MainActivity,
        // where the main Processing Sketch is running
        Intent intent = new Intent(this, MainActivity.class);

        // Starting the new activity
        startActivity(intent);

        // Finishing the current activity
        finish();
    }

}
