import android.os.Environment;
import android.content.*;

import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.*;
import twitter4j.conf.*;
import twitter4j.json.*;
import twitter4j.management.*;
import twitter4j.util.*;
import twitter4j.util.function.*;

Twitter twitter;

int appWidth, appHeight;
int orientationZ = -90;
int camNum;
int cameraScale = -1;
String directory = "";

PImage currentImage;


void setup(){
  size(displayWidth, displayHeight);
  
  appWidth = displayWidth;
  appHeight = displayHeight;
  imageMode(CENTER);
  directory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES  + "/WishIWasHereApp/";  
  background(255);
  
  /*----------------------------------New------------------------------------*/
  
  ConfigurationBuilder cb = new ConfigurationBuilder();
  cb.setOAuthConsumerKey("");
  cb.setOAuthConsumerSecret("");
  cb.setOAuthAccessToken("");
  cb.setOAuthAccessTokenSecret(""); 
    
  TwitterFactory tf = new TwitterFactory(cb.build());
  twitter = tf.getInstance();
}

void draw(){
  pushMatrix();
  translate(displayWidth/2, displayHeight/2);
  popMatrix();
}

public boolean isExternalStorageWritable() {
  String state = Environment.getExternalStorageState();
  if (Environment.MEDIA_MOUNTED.equals(state)) {
    println("Writable: " + state);
    return true;
  }
  println("Not Writable: " + state);
  return false;
}

void mousePressed()
{ 
  // Saving the current frame of the ketaiCamera and assigning it a name, and incrementing 
  // number to ensure images are not overwritten and to allow for multiple images
  // Also assigning an image format so the frame is saved as a jpeg to the users phone and 
  // can be seen in their gallery under a folder title of Wish I Was Here App
  
  isExternalStorageWritable();
  
  PImage img = createImage(66, 66, RGB);
  img.loadPixels();
  for (int i = 0; i < img.pixels.length; i++) {
    img.pixels[i] = color(0, 90, 102); 
  }
  img.updatePixels();
  currentImage = img;
  image(currentImage, 17, 17);
  
  File thePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
  println("thePath is " + thePath.toString());
            
  try {
    
    if (!currentImage.save(directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg")){
      println("Failed to save image");
    }
    
    println("Saved to = " + directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg");
  }catch(Exception e){
    println("Got an exception : " + e);
  }

 /* ----------------------- New -------------------- */
 println("twitter initialization works");
 tweet();
}


void tweet(){
 try{
   Status status = twitter.updateStatus("Testing sending a tweet from processing on an android app");
   System.out.println("Status updated to [" + status.getText() + "].");
 }
 catch (TwitterException te)
 {
  System.out.println("Error: "+ te.getMessage()); 
 }
}