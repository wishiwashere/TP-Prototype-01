PImage googleImage;
String googleMapsURL; 

String ourBrowserApiKey = "";
String ourAndroidApiKey = "";

void setup() {
  size(400, 400);
  
  /* Using Google Maps Embed API (https://developers.google.com/maps/documentation/embed/guide#street_view_mode)
     Doesn't work as it expects the request to come from a HTML iFrame */
  //googleMapsURL = "https://www.google.com/maps/embed/v1/streetview?key=" + ourBrowserApiKey + "&location=46.414382,10.013988";
  //googleMapsURL = "https://www.google.com/maps/embed/v1/streetview?key=" + ourBrowserApiKey + "&location=46.414382,10.013988&heading=210&pitch=10&fov=35";
  
  /* Using Google Street View Image API to get a static Street View Image (https://developers.google.com/maps/documentation/streetview/intro#url_parameters)
     Works, but only gives back a static image */
  //googleMapsURL = "https://maps.googleapis.com/maps/api/streetview?size=600x300&location=46.414382,10.013988&heading=151.78&pitch=-0.76&key=" + ourBrowserApiKey;
  /* The Pyramids!!! */
  googleMapsURL = "https://maps.googleapis.com/maps/api/streetview?location=29.975269,31.1387544&heading=292.67&pitch=-0.76&key=" + ourBrowserApiKey + "&size=" + width + "x" + height;
  
  /* Using Google Static Maps API to get a static Google Map (https://developers.google.com/maps/documentation/static-maps/intro#URL_Parameters)
     Works, but only gives back a static map */
  //googleMapsURL = "http://maps.google.com/maps/api/staticmap?center=45.464160,9.191614&zoom=3&size=400x400&sensor=true&markers";
  
  googleImage = loadImage(googleMapsURL, "png");
}

void draw() {
  // Adding the image to the sketch (distorting it to fit the size of the screen
  imageMode(CENTER);
  image(googleImage, width/2, height/2);
}