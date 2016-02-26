import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.*;
import twitter4j.conf.*;
import twitter4j.json.*;
import twitter4j.management.*;
import twitter4j.util.*;
import twitter4j.util.function.*;
import ketai.ui.*;

ConfigurationBuilder cb = new ConfigurationBuilder();
Twitter twitter;
File file = new File("");



String myText = "Trying to see if my word limitor for twitter is working, Hello World :) ";
int tweetLength = 0;


String directory = "";

void setup() {

  size(200,200);
  background(0);
  
  cb.setOAuthConsumerKey("");
  cb.setOAuthConsumerSecret("");
  cb.setOAuthAccessToken("");
  cb.setOAuthAccessTokenSecret("");

  twitter = new TwitterFactory(cb.build()).getInstance();
}

void draw() {
}


void mousePressed()
{ 
  sendTweet(file, myText);
}

void sendTweet(File file, String myText) {
  try {
    tweetLength = myText.length(); 
    if(tweetLength < 126){
     StatusUpdate status = new StatusUpdate(myText + "#WishIWasHere");
     status.setMedia(file);
     twitter.updateStatus(status);

    // System.out.println("Status updated to [" + status.getText() + "].");
      
      rect(30, 20, 150, 150, 7);
      String s = "Successfully sent :)";
      fill(80);
      text(s, 40, 30, 70, 80);
    }
    else{
      rect(30, 20, 150, 150, 7);
      String s = "Sorry but your message was too long :(";
      fill(80);
      text(s, 40, 30, 70, 80);
    }
  }
  catch (TwitterException te)
  {
    System.out.println("Error: "+ te.getMessage());
  }
}