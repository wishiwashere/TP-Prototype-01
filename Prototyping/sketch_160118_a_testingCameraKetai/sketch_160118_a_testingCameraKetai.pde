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
  orientation(LANDSCAPE);
  imageMode(CORNER);
  ketaiCamera = new KetaiCamera(this, width, height, 24);
  ketaiCamera.start();
}

void draw() {
  image(ketaiCamera, 0, 0);
}

void onCameraPreviewEvent()
{
  ketaiCamera.read();
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