package com.pigottlaura.www.wishiwashere;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import processing.core.*;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class Sketch extends PApplet {

    String filename;
    File directory;

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

        directory = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES  + "/WishIWasHereApp/");
        if(!directory.isDirectory()){
            directory.mkdirs();
            println("New directory created - " + directory);
         }

        fill(0);
        ellipse(200, 200, 100, 100);


        File dir = getActivity().getFilesDir();
        println("File - getActivity().getFilesDir() = " + dir);
        println("File - sketchPath = " + sketchPath);
    }

    @Override
    public void draw() {
        if(frameCount == 1){
            filename = "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg";
            String mediaPath = directory + filename;

            saveFrame(mediaPath);

            String mediaType = "image/*";

            createInstagramIntent(mediaType, mediaPath);
        }

        fill(0);
        textSize(30);
        textAlign(CENTER, CENTER);
        text("Hello World", width / 2, height / 2 - 100);
    }

    private void createInstagramIntent(String type, String mediaPath){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }
}