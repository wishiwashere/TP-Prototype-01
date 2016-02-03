// Declaring an instance of the Screen
Screen myScreen;

void setup(){
  // Setting the size of the sketch
  size(360, 640);
  
  // Creating an myScreen using the Screen constructor
  myScreen = new Screen(200, 300, #000000);
}

void draw(){
  // Calling the show method of the myScreen object (which is 
  // inherited by the Screen class from the Rectangle class)
  myScreen.show();
}