import ketai.*;
import ketai.camera.*;
import ketai.ui.*;

PImage greenScreenTestingImage;
PImage currentImage;
PImage keyedImage;

Boolean readingImage = false;

int appWidth;
int appHeight;

KetaiCamera ketaiCamera;

void setup() {
  fullScreen();
    // Locking the applications orientation to portrait, so that the image being read in from the 
  // the camera is maintained, even when the device is rotated
  orientation(PORTRAIT);

  // Initialising the appWidth and appHeight variable with the width and height of the device's
  // display, so that these values can be reused throughout all classes (i.e. to calculate
  // more dynamic position/width/height's so that the interface responds to different
  // screen sizes (for testing purposes, I am currently setting these to the width and height
  // of the sketch as the display size of my computer screen gets called when using displayWidth
  // and displayHeight
  appWidth = width;
  appHeight = height;
  
    /*-------------------------------------- Ketai ------------------------------------------------*/
    
  // Calling the ketaiCamera constructor to initialise the camera with the same
  // width/height of the device, with a frame rate of 24.
  ketaiCamera = new KetaiCamera(this, appHeight, appWidth, 24);
  
  // Loading in the relevant images from the data folder
  greenScreenTestingImage = loadImage("greenScreenTestingImage.jpg");

  
  currentImage = createImage(ketaiCamera.width, ketaiCamera.height, ARGB);
  currentImage.loadPixels();
  for(int c = 0; c < currentImage.pixels.length; c++){
    currentImage.pixels[c] = color(0, 0, 0);
  }
  currentImage.updatePixels();
}

void draw(){
  if(!ketaiCamera.isStarted()){
    ketaiCamera.start();
  }
  pushMatrix();
  translate(appWidth/2, appHeight/2);
  rotate(radians(270));
  imageMode(CENTER);
  image(currentImage, 0, 0);
  popMatrix();
}

// ketaiCamera event which is automatically called everytime a new frame becomes
// available from the ketaiCamera.
void onCameraPreviewEvent()
{
  if(readingImage == false){
    ketaiCamera.read();
    readingImage = true;
    println("About to call removeGreenScreen()");
    removeGreenScreen();
  }
}
  
  
void removeGreenScreen(){
  // Changing the colour mode to HSB, so that I can work with the hue, satruation and
  // brightness of the pixels. Setting the maximum hue to 360, and the maximum saturation
  // and brightness to 100.
  colorMode(HSB, 360, 100, 100);
  
  PImage keyedImage = createImage(ketaiCamera.width, ketaiCamera.height, ARGB);

  // Loading in the pixel arrays of the keyed image and the girl green screen image
  keyedImage.loadPixels();
  ketaiCamera.loadPixels();
  /*
  for (int i = 0; i < ketaiCamera.pixels.length; i++) {
    // Determining the current location of the pixel
    keyedImage.pixels[i] = ketaiCamera.pixels[i];
  }
  */
  for (int i = 0; i < ketaiCamera.pixels.length; i++) {
      /*
      // Getting the hue, saturation and brightness values of the current pixel
      float pixelHue = hue(ketaiCamera.pixels[currentPixel]);
      float pixelSaturation = saturation(ketaiCamera.pixels[currentPixel]);
      float pixelBrightness = brightness(ketaiCamera.pixels[currentPixel]);
      
      // Creating variables to store the hue of the pixels surrounding the current pixel.
      // Defaulting these the be equal to the current pixels hue, and only changing them if
      // the current pixel is away from the edge of the picture
      float pixelHueToLeft = pixelHue;
      float pixelHueToRight = pixelHue;
      float pixelHueAbove = pixelHue;
      float pixelHueBelow = pixelHue;
      
      // If the current pixel is not near the edge of the image, changing the values of the variables
      // for the pixels around it to get their hue values
      if (x > 1 && x < ketaiCamera.width - 1 && y > 1 && y < ketaiCamera.height - 1) {
        pixelHueToLeft = hue(ketaiCamera.pixels[(x - 1) + y * ketaiCamera.width]);
        pixelHueToRight = hue(ketaiCamera.pixels[(x + 1) + y * ketaiCamera.width]);
        pixelHueAbove = hue(ketaiCamera.pixels[x + (y - 1) * ketaiCamera.width]);
        pixelHueBelow = hue(ketaiCamera.pixels[x + (y + 1) * ketaiCamera.width]);
      }

      // If the hue of this pixel falls anywhere within the range of green in the colour spectrum
      if (pixelHue > 60 && pixelHue < 180) {
        // If the saturation and brightness are above 30, then this is a green pixel
        if (pixelSaturation > 30 && pixelBrightness > 30)
        {
          // If the hue of the pixel is between 90 and 100, this is not fully green, but with a tinge 
          if (pixelHue > 90 && pixelHue < 100) {
            // This seems to effect the girl's hair on the left
            // Lowering the hue, saturation and opacity, to reduce the intensity of the colour
            keyedImage.pixels[currentPixel] = color(pixelHue * 0.3, pixelSaturation * 0.4, pixelBrightness, 200);
          } else if (pixelHue > 155) {
            // Increasing the hue, and reducing the saturation
            keyedImage.pixels[currentPixel] = color(pixelHue * 1.2, pixelSaturation * 0.5, pixelBrightness, 255);
          } else if (pixelHue < 115) {
            // Reducting the hue and saturation. Fixes the girl's hair (in greenScreenImage1) but adds in some of
            // the green screeen in greenScreenImage2)
            //keyedImage.pixels[currentPixel] = color(pixelHue * 0.4, pixelSaturation * 0.5, pixelBrightness, 255);
          } else {
            // If the pixels around this pixel are in the more intense are of green, then assume this is part of the green screen
            if (pixelHueToLeft > 90 && pixelHueToLeft < 150 && pixelHueToRight > 90 && pixelHueToRight < 150 && pixelHueAbove > 90 && pixelHueAbove < 150 && pixelHueBelow > 90 && pixelHueBelow < 150) {
              // Set this pixel in the keyedImage to be transparent (Removing the main areas of the green)
              keyedImage.pixels[currentPixel] = color(0, 0, 0, 0);
            } else if (pixelHue > 130) {
              // This seems to be the edges around the girl
              // Increasing the hue, reducing the saturation and displaying the pixel at half opacity
              keyedImage.pixels[currentPixel] = color(pixelHue * 1.1, pixelSaturation * 0.5, pixelBrightness, 150);
            } else {
              // Set this pixel in the keyedImage to be transparent (Removing the main areas of the green)
              keyedImage.pixels[currentPixel] = color(0, 0, 0, 0);
            }
          }
        } 
          else if (pixelSaturation > 30 && pixelBrightness < 30){
          // Set this pixel in the keyedImage to be transparent (Removing the main areas of the green)
          // Fixes the issues with the shadow in greenScreenImage 4 (but as it seems to break every other image,
          // not currently using this
          keyedImage.pixels[currentPixel] = color(0, 0, 0, 0);
        } 
   
        else {
          // Even though this pixel falls within the green range of the colour spectrum, it's saturation and brightness
          // are low enough that it is unlikely to be a part of the green screen, but may just be an element of the scene
          // that is picking up a glow off the green screen. Lowering the hue and saturation to remove the green tinge 
          // from this pixel.
          keyedImage.pixels[currentPixel] = color(pixelHue * 0.6, pixelSaturation * 0.3, pixelBrightness);
        }
      } else {
        // Since this pixel did not fall within any of the wider ranges of green in the colour spectrum,
        // we are going to use this pixel exactly as it was read in, by setting the equilivant pixel in the 
        // keyedImage to be equal to the equilivant pixel in the greenScreen image
        keyedImage.pixels[currentPixel] = ketaiCamera.pixels[currentPixel];
      }
      */
      keyedImage.pixels[i] = ketaiCamera.pixels[i];
  }
  
  // Updating the pixel arrays of the ketaiCamera and the keyed image
  ketaiCamera.updatePixels();
  keyedImage.updatePixels();
  
  // Resetting the color mode to RGB
  colorMode(RGB, 255, 255, 255);
  
  currentImage = keyedImage.get();
  
  readingImage = false;
  println("Finished updating image");
}