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

// Creating a global variable to store the ketaiSensor object, so that I can potentially
// listen to and access sensor events (such as orientation changes) throughout the sketch
KetaiSensor ketaiSensor;

// Creating a variable to store the z orientation of the device, so that hopefully I can
// get the camera input image to rotate when the device is turned (currently not working).
int orientationZ = 0;

// Creating a global variable to store the number of the camera we want to view
// at any given time. The front facing camera (on a device with more than one camera)
// will be at index 1, and the rear camera will be at index 0. On a device with only
// 1 camera (such as the college Nexus tablets) this camera will always be at index
// 0, regardless of whether it is a front or back camera
int camNum;

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
  //orientation(LANDSCAPE);
  
  // Setting imageMode to center, so that the image will now rotate around it's own center
  // point (currently not working).
  imageMode(CENTER);
  
  // Initialising a new KetaiSensor object, to detect changes in the devices sensors
  ketaiSensor = new KetaiSensor(this);
  
  // Starting teh ketaiSensor so that the sketch begins listenting for sensor events
  ketaiSensor.start();
  
  // Enabling orientation, so that the device will feedback it's current orientation later
  // on in the onOrientationEvent method.
  ketaiSensor.enableOrientation();
  
  // Calling the ketaiCamera constructor to initialise the camera with the same
  // width/height of the device, with a frame rate of 24.
  ketaiCamera = new KetaiCamera(this, appWidth, appHeight, 24);
  
  // Printing out the list of available cameras i.e. front/rear facing
  println(ketaiCamera.list());
  
  // Printing out the number of availabe cameras
  println("There is " + ketaiCamera.getNumberOfCameras() + " camera/s available on this device");
  
  // Check if the device has more than one camera i.e. does it have a front
  // and a rear facing camera?
  if(ketaiCamera.getNumberOfCameras() > 1)
  {
    // If there is more than one camera, then default to the front camera
    // (which as far as I can tell tends to be at index 1)
    camNum = 1;
  }
  else
  {
    // If there is only one camera, then default to the rear camera
    // (which as far as I can tell tends to be at index 0)
    camNum = 0;
  }
  
  // Setting the camera to default to the front camera
  ketaiCamera.setCameraID(camNum);
  
  
  // Starting the ketaiCamera i.e. beginning to capture frames in.
  ketaiCamera.start();
  
  // Below is not working, but appears to be a built in method
  // println("ORIENTATION OF CAMERA IS NOW " + ketaiCamera.getOrientation());
}

public void draw() {    
  background(0);
  
  // Storing the current state of the matrix
  pushMatrix();
  translate(displayWidth/2, displayHeight/2);
  
  // Rotating the matrix (instead of the image, so i don't need to keep
  // working out where the center point would be).
  rotate(radians(orientationZ));
  
  // Placing the current frame from the ketaiCamera onto the sketch at position
  // 0, 0 i.e. in the top left corner of the sketch.
  image(ketaiCamera, 0, 0);
  
  // Restoring the previous state of the matrix
  popMatrix();
}

// ketaiCamera event which is automatically called everytime a new frame becomes
// available from the ketaiCamera.
public void onCameraPreviewEvent()
{
  // Reading in a new frame from the ketaiCamera.
  ketaiCamera.read();
  
  // Printing out the size of this image (for testing purposes, to see if the image
  // is responding to the change in orientation - currently it is not).
  //println("The width of the image is: " + ketaiCamera.width + "; The height of the image is: " + ketaiCamera.height);
}

public void mousePressed()
{
  // If the camera is already running stop it, or if it is stopped start running it again
  // i.e. effecting its ability to capture new frames.
  if (ketaiCamera.isStarted())
  {
    // Stopping the ketaiCamera so that no new frames will be read in
    ketaiCamera.stop();
    
    // Checking if the device has more than one camera. If it does we want to toggle between them
    if(ketaiCamera.getNumberOfCameras() > 1)
    {
      // Ternary operator to toggle between cameras 1 & 0 (i.e. front and back)
      camNum = camNum == 0 ? 1 : 0;
      ketaiCamera.setCameraID(camNum);
    }
    // Rotating the ketaiCamera image by 90 radians (as long as it is still less than
    // 360, otherwise resetting it to 0.
    orientationZ = orientationZ < 360 ? orientationZ + 90 : 0;
  }
  else
  {    
    // Starting the ketaiCamera again so that it will start reading in new frames again
    ketaiCamera.start();
  }
}

public void onOrientationEvent(float x, float y, float z) {
  //println("------------------------------------ x = " + x + "; y = " + y + "; z = " + z + ";");
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
