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


        favXML = loadXML("favourite_locations.xml");
        favLocations = favXML.getChildren("location");
        //println(favXML);

        for(int i = 0; i < favLocations.length; i++){
            println((i+1) + ") " + "XML - Name: " + favLocations[i].getString("name")  + "; LatLng: " + favLocations[i].getString("latLng") + "; Heading: " + favLocations[i].getString("heading")  + "; Pitch: " + favLocations[i].getString("pitch"));
        }

        XML newChild = favXML.addChild("location");
        newChild.setString("name", "Clonmel");
        println("NEW - name = " + newChild.getString("name"));
        newChild.setString("latLng", "55.1256354,44.135465");
        println("NEW - latLng = " + newChild.getString("latLng"));
        newChild.setString("heading", "180.0");
        println("NEW - heading = " + newChild.getString("heading"));
        newChild.setString("pitch", "20.0");
        println("NEW - pitch = " + newChild.getString("pitch"));
        //saveXML(favXML, favourite_locations.xml");
        File newPath = new File(Environment.getRootDirectory() + "/src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(sketchPath + "/src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(sketchPath("/src/main/assets/"));
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(Environment.getDataDirectory() + "/data/src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(sketchPath + "../src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(sketchPath);
        newPath = new File(newPath.getParent() + "/src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(Environment.getDataDirectory() + "/app/src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(Environment.getRootDirectory() + "/app/src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(getActivity().getFilesDir() + "/app/src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }
        newPath = new File(getActivity().getFilesDir() + "/app/src/main/assets/");
        if(newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File("/src/main/assets/favourite_locations.xml");
        if (newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File("/src/main/xml/favourite_locations.xml");
        if (newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(sketchPath + "/xml/favourite_locations.xml");
        if (newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File("/xml/favourite_locations.xml");
        if (newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(sketchPath + "/src/main/xml/favourite_locations.xml");
        if (newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File("/data/src/main/xml/favourite_locations.xml");
        if (newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

        newPath = new File(dataPath("src/main/xml/favourite_locations.xml"));
        if (newPath.exists()) {
            println("PATH EXISTS - " + newPath);
        } else {
            println("PATH does not exist - " + newPath);
        }

    }

    @Override
    public void draw() {
        if(frameCount == 3){
            favXML = loadXML("favourite_locations.xml");
            favLocations = favXML.getChildren("location");
            println(favLocations);
        }
        fill(0);
        textSize(30);
        textAlign(CENTER, CENTER);
        text("Hello World", width/2, height/2 - 100);
    }
}