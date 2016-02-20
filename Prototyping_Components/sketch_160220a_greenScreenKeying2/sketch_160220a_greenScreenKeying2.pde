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
    
    if (pixelHue > 85 && pixelHue < 150 && pixelSaturation > 30 && pixelBrightness > 30){
      pixels[i] = color(#ffffff);
    } else {
      pixels[i] = girlGreenScreen.pixels[i];
    }
  }
  girlGreenScreen.updatePixels();
  updatePixels();
  //image(girlGreenScreen, 320, 0);
}