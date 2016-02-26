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
String locationPyramids = "29.9752572,31.1387288&heading=292.67&pitch=-0.76";
String locationEiffelTower = "48.8568402,2.2967311&heading=314.59&pitch=20";
String locationColosseum = "41.8902646,12.4905161&heading=80&pitch=10";
String locationTajMahal = "27.1738903,78.0419927&heading=10&&pitch=10";
String locationBigBen = "51.500381,-0.1262538&heading=105&pitch=10";
String locationLeaningTowerOfPiza = "43.7224555,10.3960728&heading=54.4&pitch=10";
String locationTimesSquare = "40.7585806,-73.9850687&heading=30&pitch=20";

String currentLocation;


void setup() {
  size(400, 400);
  
  currentLocation = locationTimesSquare;

  /* Using Google Street View Image API to get a static Street View Image (https://developers.google.com/maps/documentation/streetview/intro#url_parameters)
     Works, but only gives back a static image */
  googleMapsURL = "https://maps.googleapis.com/maps/api/streetview?location=" + currentLocation + "&key=" + ourBrowserApiKey + "&size=" + width + "x" + height;
  
  googleImage = loadImage(googleMapsURL, "png");
}

void draw() {
  // Adding the image to the sketch (distorting it to fit the size of the screen
  imageMode(CENTER);
  image(googleImage, width/2, height/2);
}