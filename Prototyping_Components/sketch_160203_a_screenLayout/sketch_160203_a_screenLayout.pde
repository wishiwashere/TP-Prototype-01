// Declaring global variables, which will contain the width and height of the device's
// display, so that these values can be reused throughout all classes (i.e. to calculate
// more dynamic position/width/height's so that the interface responds to different
// screen sizes
float appWidth;
float appHeight;

// Declaring instances of the each screen, so that they will all be 
// accessible throughout the main sketch (i.e. so they can be displayed
// when requested)
HomeScreen myHomeScreen;
CameraLiveViewScreen myCameraLiveViewScreen;
FavouritesScreen myFavouritesScreen;
SettingsScreen mySettingsScreen;
AboutScreen myAboutScreen;
SearchTravelScreen mySearchTravelScreen;

// Setting the default screen to be the HomeScreen, so that when the app is loaded,
// this is the first screen that is displayed. Since this global variable is available
// throughout the sketch (i.e. within all classes as well as the main sketch) we will
// use this variable to pass in the value of "iconLinkTo" when the icon is clicked on
// within the checkMouseOver() method of the Icon class. The variable will then be tested
// against each of the potential screen names (in the main sketch's draw function) to
// decide which sketch should have the showScreen() method called on it i.e. (which
// screen should be displayed
String currentScreen = "HomeScreen";

void setup(){
  // Setting the size of the sketch (for testing purposes only, will eventually be dynamic)
  size(360, 640);
  
  // Initialising the appWidth and appHeight variable with the width and height of the device's
  // display, so that these values can be reused throughout all classes (i.e. to calculate
  // more dynamic position/width/height's so that the interface responds to different
  // screen sizes (for testing purposes, I am currently setting these to the width and height
  // of the sketch as the display size of my computer screen gets called when using displayWidth
  // and displayHeight
  appWidth = width;
  appHeight = height;
  
  // Creating the screens which will be used in this application. Setting a random background
  // colour for each of these screens so that transitions between them can be more obvious
  // (for testing purposes only)
  myHomeScreen = new HomeScreen(#000000);
  myCameraLiveViewScreen = new CameraLiveViewScreen();
  myFavouritesScreen = new FavouritesScreen(#CE04BA);
  mySettingsScreen = new SettingsScreen(#2023A5);
  myAboutScreen = new AboutScreen (#30B727);
  mySearchTravelScreen = new SearchTravelScreen(#E88121);
}

void draw(){
  // Using the currentScreen variable to decide which screen to show
  if(currentScreen == "HomeScreen")
  {
    // Displaying the Home Screen and it's icons
    myHomeScreen.showScreen();
  }else if(currentScreen == "FavouritesScreen"){  
    // Displaying the Favourites Screen and it's icons
    myFavouritesScreen.showScreen();
  }else if(currentScreen == "SettingsScreen"){
    // Displaying the Favourites Screen and it's icons
    mySettingsScreen.showScreen();
  }else if(currentScreen == "CameraLiveViewScreen"){
    // Displaying the Camera Live View Screen and it's icons
    myCameraLiveViewScreen.showScreen();
  }else if(currentScreen == "AboutScreen"){
    // Displaying the About Screen and it's icons
    myAboutScreen.showScreen();
  }else if(currentScreen == "SearchTravelScreen"){
    // Displaying the Search Travel Screen and it's icons
    mySearchTravelScreen.showScreen();
  }
}