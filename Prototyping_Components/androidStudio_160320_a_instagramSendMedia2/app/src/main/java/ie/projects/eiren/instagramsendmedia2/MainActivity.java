package ie.projects.eiren.instagramsendmedia2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInstagramSignInButton = (Button)findViewById(R.id.instagram_sign_in_button);
        mInstagramSignInButton.setOnClickListener(new OnClickListener() {
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
                .appendQueryParameter("client_id", "\n" + "68fb309036d446a2bada8e179d5122b4")
                .appendQueryParameter("redirect_uri", "socialMediaLogin://redirect")
                .appendQueryParameter("response_type", "token");
        final Intent browser = new Intent(Intent.ACTION_VIEW, uriBuilder.build());
        startActivity(browser);
    }

    private void checkForInstagramData() {
        final Uri data = this.getIntent().getData();
        if(data != null && data.getScheme().equals("sociallogin") && data.getFragment() != null) {
            final String accessToken = data.getFragment().replaceFirst("access_token=", "");
            if (accessToken != null) {
                // handleSignInResult(...);
            } else {
                // handleSignInResult(...);
            }
        }
    }

}
