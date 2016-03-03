import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.*;
import twitter4j.conf.*;
import twitter4j.json.*;
import twitter4j.management.*;
import twitter4j.util.*;
import twitter4j.util.function.*;

import android.os.Environment;

ConfigurationBuilder cb = new ConfigurationBuilder();
Twitter twitter;

File file = new File("");

String myText = "Getting images from storage, or at least trying to :) ";
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
  
    directory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES  + "/WishIWasHereApp/";  
    currentImage = 
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

void keepImage() {
  // Checking if Storage is available
  if (isExternalStorageWritable()) {    
    // Trying to save out the image. Putting this code in an if statement, so that if it fails, a message will be logged
    if (currentImage.save(directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg")) {
      println("Successfully saved image to = " + directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg");
      currentScreen = "SaveShareScreenA";
    } else {
      println("Failed to save image");
    }
  }
}

Boolean isExternalStorageWritable() {
  Boolean answer = false;

  // Creating a string to store the state of the external storage
  String state = Environment.getExternalStorageState();

  // Testing the string value of the enviroment property media_mounted, against the
  // string value of the state (as declared above). If media_mounted then storage
  // is available to be written/read, and all permissions are in place
  if (Environment.MEDIA_MOUNTED.equals(state)) {
    println("External Storage is writable: " + state);
    answer = true;
  } else {
    println("External Storage is writable: " + state);
  }

  return answer;
}