// Creating a string to hold our Google Play Browser key (which will be removed before all commits, for security purposes
String ourBrowserKey = "";

// Creating a string to store the name of the location we want to search for
String address = "times square, ny, us";

// Creating a string to store the compiled version of this address, i.e. removing all spaces from the address and replacing
// them with + symbols (as this is the only way we could pass this in to the http request in the setup function
String compiledAddress = address.replace(" ", "+");

// Creating an XML object to store the response we recieve from the geocoding API
XML locationXML;

// Creating string elements, to store the latitude and longitude from the locationXML. Storing these as strings, as this is
// how they will be read in, and we do not actually need to be able to treat them as numbers
String lat;
String lng;

// Creating float variables to store the heading and pitch values for the image. Defaulting these to 0, and allowing the user
// to change them using the mouse (which will regenerate the request to the Google Street View Image API to get a new image
// with this position).
float imgHeading = 0;
float imgPitch = 0;

// Creating a global useResult xml object, to store the result from the locationXML which we will be using
// to get the location from (at the moment, the reason for this is that I am trying to access the best result possible 
// by finding those that are points of interest
XML useResult;

// Creating a string to store the latitude and longitude, seperated by a comma, for passing into the http request to get
// the relevant street view image
String latLng;

String googleMapsURL;
PImage googleMapsImage;

void setup() {
  size(400, 400);

  // Using the Google Maps Geocoding API to query the address the user has specified, and return the relevant XML containing
  // the location data of the place - https://developers.google.com/maps/documentation/geocoding/intro
  locationXML = loadXML("https://maps.googleapis.com/maps/api/geocode/xml?address=" + compiledAddress + "&key=" + ourBrowserKey);

  // Defaulting the result we use to the first one returned in the XML
  useResult = locationXML.getChildren("result")[0];

  // Storing all results in an XML array
  XML[] results = locationXML.getChildren("result");

  // Looping through all results
  for (int r = 0; r < results.length; r++) {

    // Getting all of the results types associated with this result
    XML[] resultTypes = results[r].getChildren("type");

    // Looping throught this result's result types
    for (int t = 0; t < resultTypes.length; t++) {

      // If this results type is equal to a point of interest, then we will use this as our primary result
      if (resultTypes[t].getContent().equals("point_of_interest")) {
        println("------------------------------POINT OF INTEREST------------------------------");

        // Changing useResult so that we now use this result instead of the default (NOTE - there is no reason why the default
        // result may not have already been equal to this result. Just making sure that point's of interest get used first
        useResult = results[r];

        // Breaking out of this loop (as we have now found a point of interest
        break;
      }
    }
  }

  // Checking if the geocoding API was able to find a location to match the address specified in the search
  if (locationXML.getChildren("status")[0].getContent().equals("OK")) {
    // Getting the latitude of the specified location by reading the contents of the "lat" element in the XML
    lat = useResult.getChild("geometry").getChild("location").getChild("lat").getContent();

    // Getting the longitude of the specified location by reading the contents of the "lng" element in the XML
    lng = useResult.getChild("geometry").getChild("location").getChild("lng").getContent();

    println("Full locationXML = ");
    println(locationXML);
    println();
    println("Latitude = " + lat);
    println("Longitude = " + lng);

    // Concatenating the latitude and longitude, sepearated by a comma, so we can use them to create a request to the 
    // Google Street View Image API
    latLng = lat + "," + lng;

    /* Using Google Street View Image API to get a static Street View Image (https://developers.google.com/maps/documentation/streetview/intro#url_parameters)
     Works, but only gives back a static image */
    googleMapsURL = "https://maps.googleapis.com/maps/api/streetview?location=" + latLng + "&heading=" + imgHeading + "&pitch=" + imgPitch + "&key=" + ourBrowserKey + "&size=" + width + "x" + height;
    
      // Loading the image from Google by passing in the googleMapsURL to the loadImage request
    googleMapsImage = loadImage(googleMapsURL, "png");
} else {
    // If the status of the XML response is not equal to OK, then a matching location could not be found
    println("Could not find a location for " + address);
  }
}

void draw() {
  // Checking if the mouse is pressed (i.e. the user wants to interact with the image)
  if (mousePressed) {
    // Setting the heading of the image to be equal to a value between 0 and 360, by mapping
    // the current x position of the mouse. The heading refers to left/right view of the image
    // of the viewer
    imgHeading = map(mouseX, 0, width, 0, 359);
    
    // Setting the pitch of the image to be equal to a balue between 90 and -90, by mapping
    // the current y position of the mouse. The pitch refers to the up/down view of the image
    imgPitch = map(mouseY, 0, height, 90, -90);

    /* Using Google Street View Image API to get a static Street View Image (https://developers.google.com/maps/documentation/streetview/intro#url_parameters)
     Works, but only gives back a static image */
    googleMapsURL = "https://maps.googleapis.com/maps/api/streetview?location=" + latLng + "&heading=" + imgHeading + "&pitch=" + imgPitch + "&key=" + ourBrowserKey + "&size=" + width + "x" + height;

    // Loading the image from Google by passing in the googleMapsURL to the loadImage request
    googleMapsImage = loadImage(googleMapsURL, "png");
  }

  // Adding the image to the sketch
  image(googleMapsImage, 0, 0);
}