import android.os.Environment;
import android.content.*;

// Creating two global variables to store the width and height of the device in
// so that they can be passed into the constructor of the ketaiCamera object.
// Ideally, it would be easier to just pass the displayWidth and displayHeight
// values directly into the ketaiCamera constructor, but it doesn't seem to
// accept them.
int appWidth, appHeight;

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
  
  // Storing a string that tells the app where to store the images, by default 
  // it goes to the pictures folder and this string as it has WishIWasHereApp 
  // it is creating a folder in the picture folder of the device
  directory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES  + "/WishIWasHereApp/";  
  
  // Setting the background to white, so the image will be obvious against it
  background(255);
}

void draw() {    
}

void mousePressed()
{ 
  // Creating a small green square, to represent an image 
  PImage img = createImage(66, 66, RGB);
  img.loadPixels();
  for (int i = 0; i < img.pixels.length; i++) {
    img.pixels[i] = color(0, 200, 00); 
  }
  img.updatePixels();
  currentImage = img;
  image(currentImage, 17, 17);

  // Checking if Storage is available
  if(isExternalStorageWritable()){    
    // Trying to save out the image. Putting this code in an if statement, so that if it fails, a message will be logged
    if (currentImage.save(directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg")){
      println("Successfully saved image to = " + directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg");
    } else {
      println("Failed to save image");
    }
  }
}

Boolean isExternalStorageWritable() {
  Boolean answer = false;
  
  // Creating a string to store the state of the external storage
  String state = Environment.getExternalStorageState();
  
  // Testing the string value of the enviroment property media_mounted, against the
  // string value of the state (as declared above). If media_mounted then storage
  // is available to be written/read, and all permissions are in place
  if (Environment.MEDIA_MOUNTED.equals(state)) {
    println("External Storage is writable: " + state);
    answer = true;
  } else {
    println("External Storage is writable: " + state);
  }
  
  return answer;
}