package com.pigottlaura.www.wishiwashere;


import android.content.Context;
import android.os.Environment;

import java.io.File;

import processing.core.*;
import processing.data.XML;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class Sketch extends PApplet {

    XML favXML;
    XML[] favLocations;
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

        File dir = getActivity().getFilesDir();
        println("File - getActivity().getFilesDir() = " + dir);
        println("File - sketchPath = " + sketchPath);


        favXML = loadXML(sketchPath("favourite_locations.xml"));
        favLocations = favXML.getChildren("location");
        //println(favXML);

        for(int i = 0; i < favLocations.length; i++){
            println((i+1) + ") " + "XML - Name: " + favLocations[i].getString("name")  + "; LatLng: " + favLocations[i].getString("latLng") + "; Heading: " + favLocations[i].getString("heading")  + "; Pitch: " + favLocations[i].getString("pitch"));
        }

        XML newChild = favXML.addChild("location");
        newChild.setString("name", "Clonmel");
        newChild.setString("latLng", "55.1256354,44.135465");
        newChild.setString("heading", "180.0");
        newChild.setString("pitch", "20.0");
        saveXML(favXML, sketchPath("favourite_locations.xml"));

    }

    @Override
    public void draw() {
        if(frameCount == 3){
            favXML = loadXML(sketchPath("favourite_locations.xml"));
            favLocations = favXML.getChildren("location");
            println(favLocations);
        }
        fill(0);
        textSize(30);
        textAlign(CENTER, CENTER);
        text("Hello World", width/2, height/2 - 100);
    }
}