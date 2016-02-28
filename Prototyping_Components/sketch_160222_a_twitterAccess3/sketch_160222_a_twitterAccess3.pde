import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.*;
import twitter4j.conf.*;
import twitter4j.json.*;
import twitter4j.management.*;
import twitter4j.util.*;
import twitter4j.util.function.*;

import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import twitter4j.conf.Configuration;

import ketai.ui.*; 
 
import java.io.BufferedReader;
import java.io.IOException;
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
String authLink;

void setup() {
  try {
    loginToTwitter();
  } 
  catch(Exception e) {
    println(e);
  }
}

//---------------------------------twitterLogin--------------------------------
void loginToTwitter(){
  String testStatus="Hello from twitter4j";
 
      ConfigurationBuilder cb = new ConfigurationBuilder();
         
         
       //the following is set without accesstoken- desktop client
      //Setting our own consumer key and secret key
      cb.setDebugEnabled(true);
      cb.setOAuthConsumerKey("");
      cb.setOAuthConsumerSecret("");
   
      try {
          TwitterFactory tf = new TwitterFactory(cb.build());
          Twitter twitter = tf.getInstance();
           
          try {
              System.out.println("-----");
 
              // get request token.
              // this will throw IllegalStateException if access token is already available
              // this is oob, desktop client version
              RequestToken requestToken = twitter.getOAuthRequestToken(); 
 
               //Printing out the tokens
              System.out.println("Got request token.");
              System.out.println("Request token: " + requestToken.getToken());
              System.out.println("Request token secret: " + requestToken.getTokenSecret());
 
              System.out.println("|-----");
               
              AccessToken accessToken = null;
 
              BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
              
              // If no token yet, get the authorization url, go to it
              while (null == accessToken) {
                  System.out.println("Open the following URL and grant access to your account:");
                  System.out.println(requestToken.getAuthorizationURL());
                  authLink = requestToken.getAuthorizationURL();
                  link(authLink);
                  System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                 //Storring the pin ?
                  String pin = br.readLine();
                    if (keyCode == ENTER){
                     myText = pin; 
                    }
                  try {
                      if (pin.length() > 0) {
                          accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                      } else {
                          accessToken = twitter.getOAuthAccessToken(requestToken);
                      }
                  } catch (TwitterException te) {
                      if (401 == te.getStatusCode()) {
                          System.out.println("Unable to get the access token.");
                      } else {
                          te.printStackTrace();
                      }
                  }
              }
              System.out.println("Got access token.");
              System.out.println("Access token: " + accessToken.getToken());
              System.out.println("Access token secret: " + accessToken.getTokenSecret());
               
          } catch (IllegalStateException ie) {
              // access token is already available, or consumer key/secret is not set.
              if (!twitter.getAuthorization().isEnabled()) {
                  System.out.println("OAuth consumer key/secret is not set.");
                  System.exit(-1);
              }
          }
          Status status = twitter.updateStatus(testStatus);
 
         System.out.println("Successfully updated the status to [" + status.getText() + "].");
 
         System.out.println("ready exit");
           
          System.exit(0);
      } catch (TwitterException te) {
          te.printStackTrace();
          System.out.println("Failed to get timeline: " + te.getMessage());
          System.exit(-1);
      } catch (IOException ioe) {
          ioe.printStackTrace();
          System.out.println("Failed to read the system input.");
          System.exit(-1);
      }
  }

//---------------------------------twitterLogin--------------------------------
void draw() {
 /* background(0);
  */
  fill(255);
  textSize(width * 0.1);
  textAlign(CENTER, CENTER);
  text(myText, width/2, height * 0.2);
}

//Turning on the keyboard
void mousePressed()
{ 
  println("twitter initialization works");
  //tweet();
  KetaiKeyboard.toggle(this);
}

//For typing in the pin
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

/*
void tweet() {
  try {
    Status status = twitter.updateStatus("Testing sending a tweet from Wish I Was Here app ! :)");
    System.out.println("Status updated to [" + status.getText() + "].");
  }
  catch (TwitterException te)
  {
    System.out.println("Error: "+ te.getMessage());
  }
}*/


/*void loginToTwitter() throws Exception {
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
}*/
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