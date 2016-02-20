PImage colorSpectrum;
PImage keyedImage;
void setup(){
  size(960, 300);
  colorSpectrum = loadImage("colorSpectrum.png");
  
  colorMode(HSB, 360, 100, 100);
  
  loadPixels();
  colorSpectrum.loadPixels();
  
  for(int i = 0; i < colorSpectrum.pixels.length; i++){
    float pixelHue = hue(colorSpectrum.pixels[i]);
    
    if (pixelHue > 85 && pixelHue < 150){
      pixels[i] = color(#ffffff);
    } else {
      pixels[i] = colorSpectrum.pixels[i];
    }
  }
  colorSpectrum.updatePixels();
  updatePixels();
}