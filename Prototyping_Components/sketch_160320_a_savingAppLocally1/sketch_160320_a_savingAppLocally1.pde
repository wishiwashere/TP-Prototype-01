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
import android.os.Environment;

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
int orientationZ = -90;

// Creating a global variable to store the number of the camera we want to view
// at any given time. The front facing camera (on a device with more than one camera)
// will be at index 1, and the rear camera will be at index 0. On a device with only
// 1 camera (such as the college Nexus tablets) this camera will always be at index
// 0, regardless of whether it is a front or back camera
int camNum;

// Creating a variable to store a value of 1 or -1, to decide whether and image should be
// flipped i.e. when using the front facing camera, the x scale should always be -1 to 
// avoid things being in reverse
int cameraScale = -1;

// Creating a string that will hold the directory of where the images will be saved to
String directory = "";

PImage currentImage;

void setup() {
  // Setting the width and height of the sketch to be relative to the width and 
  // height of the device it is being viewed on.
  size(displayWidth, displayHeight);
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
  

  // Storing a string that tells the app where to store the images, by default 
  // it goes to the pictures folder and this string as it has WishIWasHereApp 
  // it is creating a folder in the picture folder of the device
  directory = "/storage/emulated/0/Pictures/./WishIWasHereApp/"; 
  //directory = "/storage/emulated/0/Pictures/WishIWasHereApp/";  

}

void draw() {    
  background(0);
  
  // Storing the current state of the matrix
  pushMatrix();
  translate(displayWidth/2, displayHeight/2);
  
  // Flipping the image so that it better represents the camera it is using i.e.
  // on front facing cameras, the image will be flipped horizontally, so that things
  // don't appear in reverse.
  scale(cameraScale, 1);
  
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
void onCameraPreviewEvent()
{
  // Reading in a new frame from the ketaiCamera.
  ketaiCamera.read();
  
  currentImage = ketaiCamera.get();
}

void mousePressed()
{ 
  // Saving the current frame of the ketaiCamera and assigning it a name, and incrementing 
  // number to ensure images are not overwritten and to allow for multiple images
  // Also assigning an image format so the frame is saved as a jpeg to the users phone and 
  // can be seen in their gallery under a folder title of Wish I Was Here App
  currentImage.save(directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg"); 

}