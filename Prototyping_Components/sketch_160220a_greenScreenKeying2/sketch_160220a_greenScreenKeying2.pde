PImage girlGreenScreen;
PImage pyramidsImage;
PImage keyedImage;

void setup() {
  size(320, 480);
  
  // Loading in the relevant images from the data folder
  girlGreenScreen = loadImage("girlGreenScreen.jpg");
  pyramidsImage = loadImage("pyramids.jpg");
  
  // Creating an image to store the relevant pixels from the girlGreenScreen image i.e.
  // those that are not part of the green screen
  keyedImage = createImage(girlGreenScreen.width, girlGreenScreen.height, ARGB);
  
  
  // Changing the colour mode to HSB, so that I can work with the hue, satruation and
  // brightness of the pixels. Setting the maximum hue to 360, and the maximum saturation
  // and brightness to 100.
  colorMode(HSB, 360, 100, 100);

  // Loading in the pixel arrays of the keyed image and the girl green screen image
  keyedImage.loadPixels();
  girlGreenScreen.loadPixels();

  for (int x = 0; x < girlGreenScreen.width; x++) {
    for (int y = 0; y < girlGreenScreen.height; y++) {
      // Determining the current location of the pixel
      int currentPixel = x + y * girlGreenScreen.width;
      
      // Getting the hue, saturation and brightness values of the current pixel
      float pixelHue = hue(girlGreenScreen.pixels[currentPixel]);
      float pixelSaturation = saturation(girlGreenScreen.pixels[currentPixel]);
      float pixelBrightness = brightness(girlGreenScreen.pixels[currentPixel]);
      
      // Creating variables to store the hue of the pixels surrounding the current pixel.
      // Defaulting these the be equal to the current pixels hue, and only changing them if
      // the current pixel is away from the edge of the picture
      float pixelHueToLeft = pixelHue;
      float pixelHueToRight = pixelHue;
      float pixelHueAbove = pixelHue;
      float pixelHueBelow = pixelHue;
      
      // If the current pixel is not near the edge of the image, changing the values of the variables
      // for the pixels around it to get their hue values
      if (x > 1 && x < girlGreenScreen.width - 1 && y > 1 && y < girlGreenScreen.height - 1) {
        pixelHueToLeft = hue(girlGreenScreen.pixels[(x - 1) + y * girlGreenScreen.width]);
        pixelHueToRight = hue(girlGreenScreen.pixels[(x + 1) + y * girlGreenScreen.width]);
        pixelHueAbove = hue(girlGreenScreen.pixels[x + (y - 1) * girlGreenScreen.width]);
        pixelHueBelow = hue(girlGreenScreen.pixels[x + (y + 1) * girlGreenScreen.width]);
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
            // Reducting the hue and saturation
            keyedImage.pixels[currentPixel] = color(pixelHue * 0.4, pixelSaturation * 0.5, pixelBrightness, 255);
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
        } else {
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
        keyedImage.pixels[currentPixel] = girlGreenScreen.pixels[currentPixel];
      }
    }
  }
  
  // Updating the pixel arrays of the girl green screen image and the keyed image
  girlGreenScreen.updatePixels();
  keyedImage.updatePixels();
  
  // Setting the background to black
  background(0, 0, 0);
  
  // Adding the background image and the keyed version of the girl without the green screen, to the main sketch
  //image(pyramidsImage, 0, 0);
  image(keyedImage, 0, 0);
}