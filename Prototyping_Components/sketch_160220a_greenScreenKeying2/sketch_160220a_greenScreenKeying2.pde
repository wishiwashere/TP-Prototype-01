PImage girlGreenScreen;
PImage keyedImage;
void setup(){
  size(320, 480);
  girlGreenScreen = loadImage("girlGreenScreen.jpg");
  
  colorMode(HSB, 360, 100, 100);
  
  loadPixels();
  girlGreenScreen.loadPixels();
  
  for(int i = 0; i < girlGreenScreen.pixels.length; i++){
    float pixelHue = hue(girlGreenScreen.pixels[i]);
    float pixelSaturation = saturation(girlGreenScreen.pixels[i]);
    float pixelBrightness = brightness(girlGreenScreen.pixels[i]);
    float previousPixelHue = pixelHue;
    float nextPixelHue = pixelHue;
    if(i > 1){
      previousPixelHue = hue(girlGreenScreen.pixels[i - 1]);
    }
    if(i < girlGreenScreen.pixels.length - 1){
      nextPixelHue = hue(girlGreenScreen.pixels[i + 1]);
    }
    
    if (previousPixelHue > 96 && previousPixelHue < 150 && pixelHue > 85 && pixelHue < 150 && previousPixelHue > 96 && nextPixelHue < 150 && pixelSaturation > 30 && pixelBrightness > 30){
      pixels[i] = color(#ffffff);
    } else {
      pixels[i] = girlGreenScreen.pixels[i];
    }
  }
  girlGreenScreen.updatePixels();
  updatePixels();
  //image(girlGreenScreen, 320, 0);
}