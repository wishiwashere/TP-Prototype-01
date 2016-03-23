package com.pigottlaura.www.wishiwashere;


import java.io.File;

import processing.core.*;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class Sketch extends PApplet {

    PImage loadedImage;
    String ourOAuthConsumerKey = "huoLN2BllLtOzezay2ei07bzo";
    String ourOAuthConsumerSecret = "k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb";
    String ourOAuthAccessToken = "4833019853-PzhGbWL0lulwsER62Ly7VY7P5WQcJT52j0MSIzI";
    String ourOAuthAccessTokenSecret = "TazSQHl662mp6GIJkzlWRI5LkjOEnQZ4ifof7V3X3t30C";

    // Setting up the configuration for tweeting from an account on Twitter
    ConfigurationBuilder cb = new ConfigurationBuilder();

    // Using the twitter class for tweeting, so later I can call on the twitter factory, which in turn
    // creates a new instance of the configuration builder
    Twitter twitter;

    String twitterImageFilename = "twitterImage.jpg";

    File twitterImage;

    @Override
    public void settings() {
        size(1000, 1000, P2D);
        fullScreen();
        println("Processing settings function called");
    }

    @Override
    public void setup() {
        background(255);
        println("Processing setup function called");

        cb.setOAuthConsumerKey(ourOAuthConsumerKey);
        cb.setOAuthConsumerSecret(ourOAuthConsumerSecret);
        cb.setOAuthAccessToken(ourOAuthAccessToken);
        cb.setOAuthAccessTokenSecret(ourOAuthAccessTokenSecret);

        twitter = new TwitterFactory(cb.build()).getInstance();

        fill(0);
        ellipse(200, 200, 100, 100);


        File dir = getActivity().getFilesDir();
        println("File - getActivity().getFilesDir() = " + dir);
        println("File - sketchPath = " + sketchPath);
    }

    @Override
    public void draw() {
        if(frameCount == 1){
            saveFrame("testImage.jpg");
            saveFrame(twitterImageFilename);
            println(sketchPath);
            sendTweet();
        }
        background(255);

        if(frameCount % 20 == 0) {
            loadedImage = loadImage("testImage.jpg");
            imageMode(CORNER);
            image(loadedImage, 0, 0);
        }

        fill(0);
        textSize(30);
        textAlign(CENTER, CENTER);
        text("Hello World", width/2, height/2 - 100);
    }

    public void sendTweet(){
        twitterImage = new File(sketchPath + "/" + twitterImageFilename);

        try {
            StatusUpdate status = new StatusUpdate("Testing saving image to Android Device local storage");

            status.setMedia(twitterImage);
            twitter.updateStatus(status);
        }
        catch (TwitterException te)
        {
            //If the tweet can't be sent, it will print out the reason that
            // is causing the problem
            println("Error: "+ te.getMessage());
        }
    }
}