PImage girlGreenScreen;
PImage keyedImage;
PImage pyramidsImage;
void setup(){
  size(320, 480);
  girlGreenScreen = loadImage("girlGreenScreen.jpg");
  keyedImage = createImage(girlGreenScreen.width, girlGreenScreen.height, ARGB);
  pyramidsImage = loadImage("pyramids.jpg");
  
  colorMode(HSB, 360, 100, 100);
  
  keyedImage.loadPixels();
  girlGreenScreen.loadPixels();
  
  for(int i = 0; i < girlGreenScreen.pixels.length; i++){
    float pixelHue = hue(girlGreenScreen.pixels[i]);
    float pixelSaturation = saturation(girlGreenScreen.pixels[i]);
    float pixelBrightness = brightness(girlGreenScreen.pixels[i]);
    
    if (pixelHue > 85 && pixelHue < 150 && pixelSaturation > 30 && pixelBrightness > 30){
      // Set this pixel in the keyedImage to be transparent
      keyedImage.pixels[i] = color(0, 0, 0, 0);
    } else {
      // Set this pixel in the keyedImage to be equal to the equilivant pixel in the greenScreen image
      keyedImage.pixels[i] = girlGreenScreen.pixels[i];
    }
  }
  girlGreenScreen.updatePixels();
  keyedImage.updatePixels();
  image(pyramidsImage, 0, 0);
  image(keyedImage, 0, 0);
}