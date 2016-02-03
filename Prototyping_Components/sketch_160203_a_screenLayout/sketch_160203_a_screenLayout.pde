// Declaring an instance of the Screen
HomeScreen myHomeScreen;

void setup(){
  // Setting the size of the sketch
  size(360, 640);
  
  // Creating an myScreen using the Screen constructor
  myHomeScreen = new HomeScreen(#000000);
}

void draw(){
  // Calling the show method of the myScreen object (which is 
  // inherited by the Screen class from the Rectangle class)
  myHomeScreen.showIcons();
}