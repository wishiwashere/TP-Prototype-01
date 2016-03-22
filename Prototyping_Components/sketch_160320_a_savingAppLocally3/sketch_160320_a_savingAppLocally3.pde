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

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

int appWidth, appHeight;
KetaiCamera ketaiCamera;
KetaiSensor ketaiSensor;

int orientationZ = -90;
int camNum;
int cameraScale = -1;

String directory = "";
PImage currentImage;

//Twitter Image
String saveToPath;


String filename = "myfile";
String outputString = "Hello world!";

FileOutputStream outputStream;
FileInputStream inputStream;

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
  if (ketaiCamera.getNumberOfCameras() > 1)
  {
    camNum = 1;
  } else
  {
    camNum = 0;
  }

  ketaiCamera.setCameraID(camNum);

  ketaiCamera.start();

  //directory = "/storage/emulated/0/Pictures/./WishIWasHereApp/"; 
   directory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES  + "/WishIWasHereApp/";
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
  //tempStore();
  keepImage();
}

//Trying to save out a file to internal storage
void keepImage() {
    try {
          outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
          outputStream.write(outputString.getBytes());
          outputStream.close();
    } catch (Exception e) {
          e.printStackTrace();
    }

    try {
        inputStream = openFileInput(filename);
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        r.close();
        inputStream.close();
        println("File", "File contents: " + total);
    } catch (Exception e) {
        e.printStackTrace();
    }
  /*outPut = createOutput("data.txt");
    try {
    outPut.write(string.getBytes());
    outPut.close();
    } catch (Exception e) {
      e.printStackTrace();
  }*/
/*
  if (isExternalStorageWritable()) {   
    saveToPath = directory + "WishIWasHereTemp-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg";
    if (currentImage.save(saveToPath)) {
      println("Successfully saved image to = " + saveToPath);
      println(saveToPath);
    } else {
      println("Failed to save image");
    }
  }*/
}