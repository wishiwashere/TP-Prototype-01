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












KetaiCamera ketaiCamera;

public void setup() {
  orientation(LANDSCAPE);
  imageMode(CORNER);
  ketaiCamera = new KetaiCamera(this, width, height, 24);
  ketaiCamera.start();
}

public void draw() {
  image(ketaiCamera, 0, 0);
}

public void onCameraPreviewEvent()
{
  ketaiCamera.read();
}

public void mousePressed()
{
  if (ketaiCamera.isStarted())
  {
    ketaiCamera.savePhoto();
    ketaiCamera.stop();
  }
  else
  {
    ketaiCamera.start();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_160118_a_testingCameraKetai" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
