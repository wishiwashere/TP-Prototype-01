import ketai.sensors.*;

KetaiSensor sensor;

PImage googleImage;
String googleMapsURL; 

String ourBrowserApiKey = "";
String ourAndroidApiKey = "";

// The first two numbers represent the latitude and longitude of the location
// The heading represents the left/right positioning of the view (between 0 and 360)
// The pitch represents the up/down angle of the view (between -90 and 90)
// In the original Google Street View URL (from the browser) i.e. the Colosseum 
// url was https://www.google.ie/maps/@41.8902646,12.4905161,3a,75y,90.81h,95.88t/data=!3m6!1e1!3m4!1sR8bILL5qdsO7_m5BHNdSvQ!2e0!7i13312!8i6656!6m1!1e1
// the first two numbers after the @ represent the latitude and longitude, the number
// with the h after it represents the heading, and the number with the t after it
// seems to be to do with the pitch, but never works that way in this
// method so I just decided the pitch value based on what looks good
String locationPyramids = "29.9752572,31.1387288";
String locationEiffelTower = "48.8568402,2.2967311";
String locationColosseum = "41.8902646,12.4905161";
String locationTajMahal = "27.1738903,78.0419927";
String locationBigBen = "51.500381,-0.1262538";
String locationLeaningTowerOfPiza = "43.7224555,10.3960728";
String locationTimesSquare = "40.7585806,-73.9850687";

float googleImagePitch;
float googleImageHeading;

String currentLocation;

float previousMouseX;
float previousMouseY;

float accelerometerX;
float accelerometerY;
float accelerometerZ; 
float lastX;
float lastY;
float lastZ;


void setup() {
  fullScreen();
  
  sensor = new KetaiSensor(this);
  sensor.start();
  
  googleImagePitch = 0;
  googleImageHeading = 0;
  
  currentLocation = locationTimesSquare;

  loadGoogleImage();
}

void draw() {
  // Adding the image to the sketch (distorting it to fit the size of the screen
  imageMode(CENTER);
  image(googleImage, width/2, height/2);
  
  // Checking if the mouse is pressed (i.e. the user wants to interact with the image)
   /* if (mousePressed) {
      // Calculating the amount scolled, based on the distance between the previous y position, 
      // and the current y position. When the mouse is first pressed, the previous y position
      // is initialised (in the main sketch) but then while the mouse is held down, the previous
      // y position gets updated each time this function runs (so that the scrolling can occur
      // while the person is still moving their hand (and not just after they release the screen)
      float amountScrolledX = dist(0, previousMouseX, 0, mouseX);
      float amountScrolledY = dist(0, previousMouseY, 0, mouseY);

      if (previousMouseX > mouseX) {
        // The user has scrolled RIGHT
        
        // Decrementing the googleImageHeading by the amount scrolled on the x axis. Using a ternary
        // operator to check that this will not result in a value less than 0 (the minimum
        // value allowed for the heading. If it does, then resetting the heading to 359 i.e. so the 
        // user can continue turn around in that direction, otherwise allowing it to equal to the
        // current heading value minus the amount scrolled on the X
        googleImageHeading = (googleImageHeading + amountScrolledX) > 359 ? 0 : googleImageHeading + amountScrolledX;
        println("scrolled right. heading is now " + googleImageHeading);
      } else {
        // The user has scrolled LEFT

        // Incrementing the googleImageHeading by the amount scrolled on the x axis. Using a ternary
        // operator to check that this will not result in a value greater than 359 (the maximum
        // value allowed for the heading. If it does, then resetting the heading to 0 i.e. so the 
        // user can continue turn around in that direction, otherwise allowing it to equal to the
        // current heading value plus the amount scrolled on the X
        googleImageHeading = (googleImageHeading - amountScrolledX) < 0 ? 359 : googleImageHeading - amountScrolledX;
        println("scrolled left. heading is now " + googleImageHeading);
      }
      
      println("amountScrolledY = " + amountScrolledY);
      if (previousMouseY > mouseY) {
        // The user has scrolled UP

        // Incrementing the googleImagePitch by the amount scrolled on the y axis. Using a ternary
        // operator to check that this will not result in a value greater than 90 (the maximum
        // value allowed for the pitch. If it does, then stopping the pitch at 90 i.e. so the 
        // user cannot excede the maximum value, otherwise allowing it to equal to the current pitch 
        // value plus the amount scrolled on the Y
        googleImagePitch = (googleImagePitch - amountScrolledY) < -90 ? -90 : googleImagePitch - amountScrolledY;
        println("scrolled up. pitch is now " + googleImagePitch);
      } else {
        // The user has scrolled DOWN
        
        // Decrementing the googleImagePitch by the amount scrolled on the y axis. Using a ternary
        // operator to check that this will not result in a value less than -90 (the minimum
        // value allowed for the pitch. If it does, then stopping the pitch at -90 i.e. so the 
        // user cannot excede the minimum value, otherwise allowing it to equal to the current pitch 
        // value minus the amount scrolled on the Y
        googleImagePitch = (googleImagePitch + amountScrolledY) > 90 ? 90 : googleImagePitch + amountScrolledY;
        println("scrolled down. pitch is now " + googleImagePitch);
      }

      loadGoogleImage();
      previousMouseX = mouseX;
      previousMouseY = mouseY;
    }*/
    
  googleImageHeading = map(accelerometerX, 0, 359, 0, displayWidth); 
  googleImagePitch = map(accelerometerY, -90, 90, 0, displayWidth); 
  googleImagePitch = map(accelerometerZ, -90, 90, 0, displayWidth); 
  loadGoogleImage();
}

void mousePressed(){
  previousMouseX = mouseX;
  previousMouseY = mouseY;
}

void loadGoogleImage(){
  /* Using Google Street View Image API to get a static Street View Image (https://developers.google.com/maps/documentation/streetview/intro#url_parameters)
     Works, but only gives back a static image */
  googleMapsURL = "https://maps.googleapis.com/maps/api/streetview?location=" + currentLocation + "&heading=" + googleImageHeading + "&pitch=" + googleImagePitch + "&key=" + ourBrowserApiKey + "&size=" + width + "x" + height;
  
  googleImage = loadImage(googleMapsURL);
}

void onAccelerometerEvent(float x, float y, float z) { 
  accelerometerX = x; 
  accelerometerY = y; 
  accelerometerZ = z;
} 