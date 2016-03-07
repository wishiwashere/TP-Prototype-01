import com.temboo.core.*;
import com.temboo.Library.Twitter.OAuth.*;

// Create a session using your Temboo account application details
TembooSession session = new TembooSession("zeal", "myFirstApp", "uBt8l2jzIElwle8rfaZdB30EUgsjBVZA");
String callbackId;
String oAuthTokenSecret;
String authorizationURL;
String oAuthConsumerKey = "huoLN2BllLtOzezay2ei07bzo";
String oAuthConsumerSecret = "k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb";
String userName = "";
String userId = "";


void setup() {
  // Run the InitializeOAuth Choreo function
  runInitializeOAuthChoreo();
  
  // Run the FinalizeOAuth Choreo function
  runFinalizeOAuthChoreo();
  
  textAlign(CENTER);
  text("Username: " + userName, width/2, height * 0.3);
  text("User Id: " + userId, width/2, height * 0.6);
}

void runInitializeOAuthChoreo() {
  // Create the Choreo object using your Temboo session
  InitializeOAuth initializeOAuthChoreo = new InitializeOAuth(session);

  // Set inputs
  initializeOAuthChoreo.setConsumerKey(oAuthConsumerKey);
  initializeOAuthChoreo.setConsumerSecret(oAuthConsumerSecret);

  // Run the Choreo and store the results
  InitializeOAuthResultSet initializeOAuthResults = initializeOAuthChoreo.run();
  
  // Print results
  println(initializeOAuthResults.getAuthorizationURL());
  println(initializeOAuthResults.getCallbackID());
  println(initializeOAuthResults.getOAuthTokenSecret());
  
  authorizationURL = initializeOAuthResults.getAuthorizationURL();
  callbackId = initializeOAuthResults.getCallbackID();
  oAuthTokenSecret = initializeOAuthResults.getOAuthTokenSecret();
  
  link(authorizationURL);
}

void runFinalizeOAuthChoreo() {
  // Create the Choreo object using your Temboo session
  FinalizeOAuth finalizeOAuthChoreo = new FinalizeOAuth(session);

  // Set inputs
  finalizeOAuthChoreo.setOAuthTokenSecret(oAuthTokenSecret);
  finalizeOAuthChoreo.setConsumerKey(oAuthConsumerKey);
  finalizeOAuthChoreo.setCallbackID(callbackId);
  finalizeOAuthChoreo.setConsumerSecret(oAuthConsumerSecret);

  // Run the Choreo and store the results
  FinalizeOAuthResultSet finalizeOAuthResults = finalizeOAuthChoreo.run();
  
  // Print results
  println(finalizeOAuthResults.getAccessToken());
  println(finalizeOAuthResults.getAccessTokenSecret());
  println(finalizeOAuthResults.getErrorMessage());
  println(finalizeOAuthResults.getScreenName());
  println(finalizeOAuthResults.getUserID());
  
  userName = finalizeOAuthResults.getScreenName();
  userId = finalizeOAuthResults.getUserID();

}