import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.*;
import twitter4j.conf.*;
import twitter4j.json.*;
import twitter4j.management.*;
import twitter4j.util.*;
import twitter4j.util.function.*;
import java.io.InputStreamReader;

final public static String  CALLBACK_SCHEME = "x-oauthflow-twitter";    
final public static String  CALLBACK_URL = CALLBACK_SCHEME + "://callback";

// Set before running
String ourOAuthConsumerKey = "";
String ourOAuthConsumerSecret = "";

// The factory instance is re-useable and thread safe.
Twitter twitter;
RequestToken requestToken;

void setup() {
  println("Setup Called");
  twitter = TwitterFactory.getSingleton();
  twitter.setOAuthConsumer(ourOAuthConsumerKey, ourOAuthConsumerSecret);

  try {
    tweetMe("hello");
  } 
  catch(Exception e) {
    println(e);
  }
}

void draw() {
}

void tweetMe(String text) throws Exception {
  println("TweetMe Called");
  requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
  println(requestToken);
  AccessToken accessToken = null;
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  link(requestToken.getAuthorizationURL());
  println("User has been sent to " + requestToken.getAuthorizationURL());
  while (null == accessToken) {
    println("Access token is null");
    String pin = br.readLine();
    println("PIN = " + pin);
    try {
      if (pin.length() > 0) {
        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
        println("PIN exists. Access token = " + accessToken);
      } else {
        accessToken = twitter.getOAuthAccessToken();
        println("NO PIN YET");
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
  storeAccessToken(int(twitter.verifyCredentials().getId()), accessToken);
  Status status = twitter.updateStatus(text);
  System.out.println("Successfully updated the status to [" + status.getText() + "].");
  System.exit(0);
}
private static void storeAccessToken(int useId, AccessToken accessToken) {
  //store accessToken.getToken()
  //store accessToken.getTokenSecret()
}