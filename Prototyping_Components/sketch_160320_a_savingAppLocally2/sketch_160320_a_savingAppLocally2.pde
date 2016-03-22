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

import java.io.FileOutputStream;
import android.content.Context;

int appWidth, appHeight;
KetaiCamera ketaiCamera;
KetaiSensor ketaiSensor;

int orientationZ = -90;
int camNum;
int cameraScale = -1;

File directory;
PImage currentImage;

//Twitter Image
String saveToPath;

FileOutputStream outputStream;

String filename = "WishIWasHereFile";
String content = "Testing to see if internal storage of any file will work internally";

void setup() {
  size(displayWidth, displayHeight);
  println("The width of the device is: " + displayWidth + "; The height of the device is: " + displayHeight);

  appWidth = displayWidth;
  appHeight = displayHeight;
  
  imageMode(CENTER);
  ketaiSensor = new KetaiSensor(this);
  ketaiSensor.start();
  ketaiSensor.enableOrientation();
  ketaiCamera = new KetaiCamera(this, appWidth, appHeight, 24);
  println(ketaiCamera.list());
  
  println("There is " + ketaiCamera.getNumberOfCameras() + " camera/s available on this device");
  if(ketaiCamera.getNumberOfCameras() > 1)
  {
    camNum = 1;
  }
  else
  {
    camNum = 0;
  }

  ketaiCamera.setCameraID(camNum);

  ketaiCamera.start();

  //directory = "/storage/emulated/0/Pictures/./WishIWasHereApp/"; 
  // directory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES  + "/WishIWasHereApp/";  

}

void draw() {    
  background(0);
  pushMatrix();
  translate(displayWidth/2, displayHeight/2);
  scale(cameraScale, 1);
  rotate(radians(orientationZ));
  image(ketaiCamera, 0, 0);
  popMatrix();
}

void onCameraPreviewEvent()
{
  ketaiCamera.read();
  currentImage = ketaiCamera.get();
}

void mousePressed()
{ 
  //currentImage.save(directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg"); 
  tempStore();
}
float openFileOutput;
void tempStore(){
   println("calling the store image locally function");
   //ContextWrapper cw = new ContextWrapper(getApplicationContext());
   directory = cw.getDir("twitterImageDir", Context.MODE_Private);
}

//The way we are normally saving an image in main app
void keepImage() {
  callFunction = "";
  // Checking if Storage is available
  if (isExternalStorageWritable()) {   
    saveToPath = directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg";
    // Trying to save out the image. Putting this code in an if statement, so that if it fails, a message will be logged
    if (compiledImage.save(saveToPath)) {
      println("Successfully saved image to = " + saveToPath);
      println(saveToPath);
      currentScreen = "SaveShareScreenA";
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