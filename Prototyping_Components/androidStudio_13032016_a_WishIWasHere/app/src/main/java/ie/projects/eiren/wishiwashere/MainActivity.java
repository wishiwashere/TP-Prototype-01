package ie.projects.eiren.wishiwashere;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.*;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "huoLN2BllLtOzezay2ei07bzo";
    private static final String TWITTER_SECRET = "	k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb ";

    private TwitterLoginButton loginButton;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        status = (TextView) findViewById(R.id.status);
        status.setText("Status: Ready");

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                Log.d("TWITTER", "Result Successfull 1");
                // The TwitterSession is also available through:
                //Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = twitterSessionResult.data;

                //TwitterSession session = Twitter.getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                //String extraMesg.makeText(getApplicationContext(), msg).show();
                //float twitterUserSessionId = session.getUserId();
                // extraMesg.makeText(getApplicationContext(), msg, twitterUserSessionId.LENGTH_LONG).show();
                Log.d(msg, "You are logged in");
                Log.d("TWITTER", "Result Successfull 2");
                String output = "Status: " +
                        "Your login was successful " +
                        twitterSessionResult.data.getUserName() +
                        "\nAuth Token Received: " +
                        twitterSessionResult.data.getAuthToken().token;
                Log.d("TWITTER", "Result Successfull 3");
                status.setText(output);
                Log.d("TWITTER", "Result Successfull 4");
            }

            @Override
            public void failure(TwitterException exception) {
                Log.v("TwitterKit", "Login with Twitter failure", exception);
                Log.d("TwitterKit", "Login with Twitter failre", exception);
                status.setText("Status: Login Failed");
            }
        });
    }
}
