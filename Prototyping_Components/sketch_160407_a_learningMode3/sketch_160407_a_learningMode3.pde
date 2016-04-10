Boolean learningModeOn = true;

String favText = "Tap on the star to save your current location to Favourites";
String homeText = "Tap here to return home";
String shakeMovementText = "Tap here to enable movement. By tilting your phone forwards and backwards you can look up and down. Or swiping left or right you can turn around in your location";
String captureText = "Tap here to take a picture";
String switchCameraText = "Tap here to switch camera views";

PImage bgImage;

void setup() {
  size(640, 360);
  bgImage = loadImage("london.jpg");
}

void draw() {
  background(bgImage);
  noStroke();

  if (learningModeOn == true) {
    //For the text box
    fill(0, 255, 0);
    rect(20, 20, 140, 50);
    rect(495, 20, 140, 50);
    rect(20, 275, 150, 80);
    rect(245, 275, 140, 50);
    rect(500, 275, 140, 50);

    //For the text
    fill(255, 0, 0);
    text(favText, 25, 25, 150, 80);         
    text(homeText, 500, 25, 150, 80); 
    text(shakeMovementText, 25, 280, 150, 80); 
    text(captureText, 250, 280, 150, 80);
    text(switchCameraText, 450, 280, 150, 80); 

    //for learning mode box
    fill(48, 2, 125, 100);
  }
  if (mousePressed) {
    learningModeOn = false;
    // println("mosue pressed");
  }

  if (learningModeOn == false) {
    // println("Learning mode was set to false");
    fill(0, 0, 0, 0);
  }
  rect(0, 0, 640, 360);
}