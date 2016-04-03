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

public class TwitterLoginActivity extends Activity {

    private TwitterLoginButton loginButton;
    private Button cancelLoginButton;

    public static Boolean twitterLoggedIn = false;

    public static String twitterUserUsername;
    public static long twitterUserUserId;
    public static String twitterUserAccessToken;
    public static String twitterUserSecretToken;

    // Fabric.io Setup
    private static final String TWITTER_KEY = "huoLN2BllLtOzezay2ei07bzo";
    private static final String TWITTER_SECRET = "k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main View
        setContentView(R.layout.activity_twitter_login);

        cancelLoginButton = (Button) findViewById(R.id.cancel_login_button);
        cancelLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });

        // Fabric.io - Twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new com.twitter.sdk.android.Twitter(authConfig));

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;

                Log.d("Twitter Login", "Username: " + session.getUserName());
                Log.d("Twitter Login", "User Id: #" + session.getUserId());
                Log.d("Twitter Login", "Access Token: " + session.getAuthToken().token);
                Log.d("Twitter Login", "Secret Token: " + session.getAuthToken().secret);

                twitterUserUsername = session.getUserName();
                twitterUserUserId = session.getUserId();
                twitterUserAccessToken = session.getAuthToken().token;
                twitterUserSecretToken = session.getAuthToken().secret;

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
        //loginButton.performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Twitter Login", "On Resume - twitterLoggedIn = " + twitterLoggedIn);
        if(twitterLoggedIn) {
            goToMainActivity();
        }
    }

    protected void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
