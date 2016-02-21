import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.*;
import twitter4j.conf.*;
import twitter4j.json.*;
import twitter4j.management.*;
import twitter4j.util.*;
import twitter4j.util.function.*;

Twitter twitter;

void setup(){
  
  ConfigurationBuilder cb = new ConfigurationBuilder();
  cb.setOAuthConsumerKey("");
  cb.setOAuthConsumerSecret("");
  cb.setOAuthAccessToken("");
  cb.setOAuthAccessTokenSecret("");
  
  
  TwitterFactory tf = new TwitterFactory(cb.build());
  twitter = tf.getInstance();
}

void draw(){
  
}

void mousePressed(){
 println("twitter initialization works"); 
}