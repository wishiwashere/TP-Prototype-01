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

KetaiCamera ketaiCamera;

void setup() {
  size(1280, 720);
  orientation(LANDSCAPE);
  imageMode(CORNER);
  ketaiCamera = new KetaiCamera(this, 1280, 720, 24);
  ketaiCamera.start();
}

void draw() {
  image(ketaiCamera, 0, 0);
}

void onCameraPreviewEvent()
{
  ketaiCamera.read();
  println("reading_in_new_image");
}

void mousePressed()
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