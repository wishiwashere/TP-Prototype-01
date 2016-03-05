// Creating a string to hold our Google Play Browser key (which will be removed before all commits, for security purposes
String ourBrowserKey = "";

// Creating a string to store the name of the location we want to search for
String address = "CN tower";

// Creating a string to store the compiled version of this address, i.e. removing all spaces from the address and replacing
// them with + symbols (as this is the only way we could pass this in to the http request in the setup function
String compiledAddress = address.replace(" ", "+");

// Creating an XML object to store the response we recieve from the geocoding API
XML locationXML;

// Creating string elements, to store the latitude and longitude from the locationXML. Storing these as strings, as this is
// how they will be read in, and we do not actually need to be able to treat them as numbers
String lat;
String lng;

String googleMapsURL;

PImage googleMapsImage;

// Creating a string to store the latitude and longitude, seperated by a comma, for passing into the http request to get
// the relevant street view image
String latLng;

void setup(){
  size(400, 400);
  
  // Using the Google Maps Geocoding API to query the address the user has specified, and return the relevant XML containing
  // the location data of the place - https://developers.google.com/maps/documentation/geocoding/intro
  locationXML = loadXML("https://maps.googleapis.com/maps/api/geocode/xml?address=" + compiledAddress + "&key=" + ourBrowserKey);
  
  // Checking if the geocoding API was able to find a location to match the address specified in the search
  if(locationXML.getChildren("status")[0].getContent().equals("OK")){
    // Getting the latitude of the specified location by reading the contents of the "lat" element in the XML
    lat = locationXML.getChild("result").getChild("geometry").getChild("location").getChild("lat").getContent();
    
    // Getting the longitude of the specified location by reading the contents of the "lng" element in the XML
    lng = locationXML.getChild("result").getChild("geometry").getChild("location").getChild("lng").getContent();
 
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
    googleMapsURL = "https://maps.googleapis.com/maps/api/streetview?location=" + latLng + "&heading=80&pitch=0&key=" + ourBrowserKey + "&size=" + width + "x" + height;
    
    googleMapsImage = loadImage(googleMapsURL, "png");
    
    image(googleMapsImage, 0, 0);
  } else {
    // If the status of the XML response is not equal to OK, then a matching location could not be found
    println("Could not find a location for " + address);
  }
}