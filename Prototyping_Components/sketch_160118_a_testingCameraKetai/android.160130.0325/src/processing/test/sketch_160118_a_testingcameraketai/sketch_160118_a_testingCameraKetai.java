package processing.test.sketch_160118_a_testingcameraketai;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ketai.camera.*; 
import ketai.cv.facedetector.*; 
import ketai.data.*; 
import ketai.net.*; 
import ketai.net.bluetooth.*; 
import ketai.net.nfc.*; 
import ketai.net.nfc.record.*; 
import ketai.net.wifidirect.*; 
import ketai.sensors.*; 
import ketai.ui.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_160118_a_testingCameraKetai extends PApplet {












// Creating two global variables to store the width and height of the device in
// so that they can be passed into the constructor of the ketaiCamera object.
// Ideally, it would be easier to just pass the displayWidth and displayHeight
// values directly into the ketaiCamera constructor, but it doesn't seem to
// accept them.
int appWidth, appHeight;

// Creating a global variable to store the ketaiCamera object, so that it can be
// accessed thoroughout the sketch once it has been initiated i.e. to read in,
// display and eventually alter the live stream images.
KetaiCamera ketaiCamera;

public void setup() {
  // Setting the width and height of the sketch to be relative to the width and 
  // height of the device it is being viewed on.
  
  println("The width of the device is: " + displayWidth + "; The height of the device is: " + displayHeight);
  
  // Initialising the values of the appWidth/appHeight variables so that they now
  // contain the values of the device's width and height (as explained when they were
  // originally declared, these variables are required in order to make the camera
  // responsive to the device size aswell).
  appWidth = displayWidth;
  appHeight = displayHeight;
  
  // Locking the applications orientation to landscape, because currently the images
  // coming in from the camera don't seem to be responding to changes in orientation
  orientation(LANDSCAPE);
  
  // Calling the ketaiCamera constructor to initialise the camera with the same
  // width/height of the device, with a frame rate of 24.
  ketaiCamera = new KetaiCamera(this, appWidth, appHeight, 24);
  
  // Starting the ketaiCamera i.e. beginning to capture frames in.
  ketaiCamera.start();
}

public void draw() {
  // Placing the current frame from the ketaiCamera onto the sketch at position
  // 0, 0 i.e. in the top left corner of the sketch.
  image(ketaiCamera, 0, 0);
}

// ketaiCamera event which is automatically called everytime a new frame becomes
// available from the ketaiCamera.
public void onCameraPreviewEvent()
{
  // Reading in a new frame from the ketaiCamera.
  ketaiCamera.read();
  
  // Printing out the size of this image (for testing purposes, to see if the image
  // is responding to the change in orientation - currently it is not).
  println("The width of the image is: " + ketaiCamera.width + "; The height of the image is: " + ketaiCamera.height);
}

public void mousePressed()
{
  // If the camera is already running stop it, or if it is stopped start running it again
  // i.e. effecting its ability to capture new frames.
  if (ketaiCamera.isStarted())
  {
    // Stopping the ketaiCamera so that no new frames will be read in
    ketaiCamera.stop();
  }
  else
  {
    // Starting the ketaiCamera again so that it will start reading in new frames again
    ketaiCamera.start();
  }
}
  public void settings() {  size(displayWidth, displayHeight); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_160118_a_testingCameraKetai" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
