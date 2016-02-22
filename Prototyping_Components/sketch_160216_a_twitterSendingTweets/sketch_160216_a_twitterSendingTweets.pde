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

String myText = "";

void setup() {

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
  tweet();
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