package com.pigottlaura.www.wishiwashere;


import java.io.File;

import processing.core.*;
import processing.data.XML;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class Sketch extends PApplet {

    XML[] favXML;
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

        try {
            favXML = loadXML("favourite_locations.xml").getChildren("favourite");
            //println(favXML);

            for(int i = 0; i < favXML.length; i++){
                println((i+1) + ") " + "Location: " + favXML[i].getString("location")  + "; LatLng: " + favXML[i].getString("latLng") + "; Heading: " + favXML[i].getString("heading")  + "; Pitch: " + favXML[i].getString("pitch"));
            }
        } catch(NullPointerException e){
            println("Could not load xml - " + e);
        }


    }

    @Override
    public void draw() {
        fill(0);
        textSize(30);
        textAlign(CENTER, CENTER);
        text("Hello World", width/2, height/2 - 100);
    }
}