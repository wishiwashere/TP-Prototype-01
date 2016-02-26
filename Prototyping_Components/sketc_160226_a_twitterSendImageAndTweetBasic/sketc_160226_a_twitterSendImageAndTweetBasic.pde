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


String directory = "";

String myText = "";
int tweetLength = 0;


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
  // sending a tweet that will take in two things, this is done so I can attach text and 
  // an image when tweeting
  sendTweet(file, myText);
}


//Sending a tweet with text and an image
void sendTweet(File file, String myText) {
  try {
    // Finding out the length of the myText so that it can be checked before sending to see 
    // does it meet with the critera for twitter
    tweetLength = myText.length(); 
    
    // Testing to see if the messages character length is less than 126, if it is the message 
    // should send but if its not it will be sent onto the esle statement. Twitter has a 140
    // character limit but as I want to attach a # tag of WishIWasHere, the character limit
    // must be shorten in order for the message to be able to be sent on twitter
    if(tweetLength < 126){
      
     //creating a new status that holds the text message that will be sent onto twitter 
     StatusUpdate status = new StatusUpdate(myText + " #WishIWasHere");
     
     //Giving the new status a media file aka an image
     status.setMedia(file);
     
     //Updating the status that will be sent on twitter so that it contains not only the next 
     // but the media file also
     twitter.updateStatus(status);
      
      // Creating a rectangle to make a display box that tells the user if their tweet was sent
      // And it will display the message "Successfully sent", this is to show that the tweet has 
      // in fact be sent out on twitter
      rect(30, 20, 150, 150, 7);
      String s = "Successfully sent :)";
      fill(80);
      text(s, 40, 30, 70, 80);
    }
    else{
      // If the tweet is not, it is because the message the user wrote exceeded the 126 character limit
      // it is has, a display will be shown telling the user the message was not sent
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