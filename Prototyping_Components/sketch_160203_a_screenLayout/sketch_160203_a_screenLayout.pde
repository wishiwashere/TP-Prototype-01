// Declaring instances of the relevant screens, so that they will all be 
// accessible throughout the main sketch i.e. so they can be displayed
// when required
HomeScreen myHomeScreen;
CameraLiveViewScreen myCameraLiveViewScreen;
FavouritesScreen myFavouritesScreen;
SettingsScreen mySettingsScreen;
AboutScreen myAboutScreen;
SearchTravelScreen mySearchTravelScreen;

String currentScreen = "HomeScreen";

void setup(){
  // Setting the size of the sketch
  size(360, 640);
  
  // Creating the screens which will be used in this application
  myHomeScreen = new HomeScreen(#000000);
  myCameraLiveViewScreen = new CameraLiveViewScreen();
  myFavouritesScreen = new FavouritesScreen(#CE04BA);
  mySettingsScreen = new SettingsScreen(#2023A5);
  myAboutScreen = new AboutScreen (#30B727);
  mySearchTravelScreen = new SearchTravelScreen(#E88121);
}

void draw(){
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