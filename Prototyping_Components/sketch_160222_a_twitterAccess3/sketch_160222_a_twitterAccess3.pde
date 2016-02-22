import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.*;
import twitter4j.conf.*;
import twitter4j.json.*;
import twitter4j.management.*;
import twitter4j.util.*;
import twitter4j.util.function.*;
import java.io.InputStreamReader;

String username = "";
String password = "";

//import ketai.ui.*;

ConfigurationBuilder cb = new ConfigurationBuilder();
Twitter twitter;
//Query queryForTwitter;

String tAccessToken;
String tAccessSecretToken;
String aToken;

String myText = "";
String textWarning = "";


void setup() {
  try {
    loginToTwitter();
  } 
  catch(Exception e) {
    println(e);
  }
}

void loginToTwitter() throws Exception {
  //twitter = new TwitterFactory(cb.build()).getInstance();

  // The factory instance is re-useable and thread safe.
  //twitter = TwitterFactory.getSingleton();
  twitter = new TwitterFactory().getInstance();
  twitter.setOAuthConsumer(username, password);
  //RequestToken requestToken = new RequestToken(getOauthToken(), getOauthTokenSecret());
  
  RequestToken requestToken = new RequestToken(tAccessToken, tAccessSecretToken);
  twitter.setOAuthConsumer(username, password);
  AccessToken accessToken = twitter.getOAuthAccessToken(requestToken);
  accessToken = new RequestToken(password);
  storeAccessToken(accessToken);
  
  
  
  //AccessToken accessToken = null;
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  while (null == accessToken) {
    System.out.println("Open the following URL and grant access to your account:");
    System.out.println(requestToken.getAuthorizationURL());
    System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
    String pin = br.readLine();
    
    try {
      if (pin.length() > 0) {
        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
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
  storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
  Status status = twitter.updateStatus("hello");
  System.out.println("Successfully updated the status to [" + status.getText() + "].");
  System.exit(0);

  //fullScreen();
  size(200, 200);
}

void storeAccessToken(long useId, AccessToken accessToken) {
  accessToken.getToken();
  accessToken.getTokenSecret();
}
/*
 public static void main(String args[]) throws Exception{
 // The factory instance is re-useable and thread safe.
 Twitter twitter = TwitterFactory.getSingleton();
 twitter.setOAuthConsumer("[consumer key]", "[consumer secret]");
 RequestToken requestToken = twitter.getOAuthRequestToken();
 AccessToken accessToken = null;
 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 while (null == accessToken) {
 System.out.println("Open the following URL and grant access to your account:");
 System.out.println(requestToken.getAuthorizationURL());
 System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
 String pin = br.readLine();
 try{
 if(pin.length() > 0){
 accessToken = twitter.getOAuthAccessToken(requestToken, pin);
 }else{
 accessToken = twitter.getOAuthAccessToken();
 }
 } catch (TwitterException te) {
 if(401 == te.getStatusCode()){
 System.out.println("Unable to get the access token.");
 }else{
 te.printStackTrace();
 }
 }
 }
 //persist to the accessToken for future reference.
 storeAccessToken(twitter.verifyCredentials().getId() , accessToken);   
 
 
 Status status = twitter.updateStatus(args[0]);
 System.out.println("Successfully updated the status to [" + status.getText() + "].");
 System.exit(0);
 }
 private static void storeAccessToken(int useId, AccessToken accessToken){
 tAccessToken = accessToken.getToken();
 tAccessSecretToken = accessToken.getTokenSecret();
 }
/*
 private static AccessToken loadAccessToken(int useId){
 String token =  tAccessToken; // load from a persistent store
 String tokenSecret = tAccessSecretToken; // load from a persistent store
 return new AccessToken(token, tokenSecret);
 }*/


void draw() {
  background(0);
  String passwordStars = "";

  for (int i = 0; i < myText.length(); i++) {
    int lastLetter = myText.length() - 1;
    if (i == lastLetter) {
      passwordStars += myText.charAt(lastLetter);
    } else {
      passwordStars += "*";
    }
  }

  fill(255);
  textSize(width * 0.1);
  textAlign(CENTER, CENTER);
  text(myText, width/2, height * 0.2);
  text(passwordStars, width/2, height * 0.5);
}

void mousePressed()
{ 
  println("twitter initialization works");
  tweet();
  //KetaiKeyboard.toggle(this);
}

void keyPressed() {
  if (key == CODED) {
    if (keyCode == 67)
    {
      if (myText.length() > 0) {
        println("BACKSPACE");
        myText = myText.substring(0, myText.length() - 1);
      }
    }
  } else {
    myText += key;
  }
  println(myText);
}

void tweet() {
  try {
    Status status = twitter.updateStatus("Testing sending a tweet from Wish I Was Here app ! :)");
    System.out.println("Status updated to [" + status.getText() + "].");
  }
  catch (TwitterException te)
  {
    System.out.println("Error: "+ te.getMessage());
  }
}