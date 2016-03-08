import ketai.ui.*; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

String testStatus = "Hello from twitter4j";

ConfigurationBuilder cb = new ConfigurationBuilder();
Twitter twitter;

String tAccessToken;
String tAccessSecretToken;
String aToken;

String myText = "";
String textWarning = "";
String authLink;

void setup() {
  fullScreen();
  try {
    loginToTwitter();
  } 
  catch(Exception e) {
    println(e);
  }
}

public void loginToTwitter() throws Exception {

  //Setting our own consumer key and secret key
  cb.setDebugEnabled(true);
  cb.setOAuthConsumerKey("lZOXrnspUJC0HSAze5vX4ZLvb");
  cb.setOAuthConsumerSecret("rAQ6wDlXPSoAdsix37aeUXJaCRM6DYBXmx0O4Y7KCLlKjOL3ea");

  // The factory instance is re-useable and thread safe.
  TwitterFactory tf = new TwitterFactory(cb.build());
  Twitter twitter = tf.getInstance();

  //sending a request
  RequestToken requestToken = twitter.getOAuthRequestToken("oob");
  AccessToken accessToken = null;
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  while (null == accessToken) {
    System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
    System.out.println("Open the following URL and grant access to your account:");
    System.out.println(requestToken.getAuthorizationURL());
    link(requestToken.getAuthorizationURL());
    String pin = br.readLine();
    try {
      println("trying- " + frameCount);
      if (pin.length() > 0) {
        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
        println(accessToken);
      } else {
        accessToken = twitter.getOAuthAccessToken();
      }
    } 
    catch (TwitterException te) {
      if (401 == te.getStatusCode()) {
        System.out.println("Unable to get the access token.");
      } else {
        te.printStackTrace();
      }
    }
  }
  //persist to the accessToken for future reference.
  //storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
  Status status = twitter.updateStatus("Testing Twitter OAuth");
  System.out.println("Successfully updated the status to [" + status.getText() + "].");
  System.exit(0);
}
private static void storeAccessToken(int useId, AccessToken accessToken) {
  //store accessToken.getToken()
  //store accessToken.getTokenSecret()
}