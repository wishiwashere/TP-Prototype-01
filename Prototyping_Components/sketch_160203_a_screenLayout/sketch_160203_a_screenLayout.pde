// Declaring instances of the relevant screens
HomeScreen myHomeScreen;
CameraLiveViewScreen myCameraLiveViewScreen;
FavouritesScreen myFavouritesScreen;
SettingsScreen mySettingsScreen;

String currentScreen = "HomeScreen";

void setup(){
  // Setting the size of the sketch
  size(360, 640);
  
  // Creating the screens which will be used in this application
  myHomeScreen = new HomeScreen(#000000);
  myCameraLiveViewScreen = new CameraLiveViewScreen();
  myFavouritesScreen = new FavouritesScreen(#CE04BA);
  mySettingsScreen = new SettingsScreen(#2023A5);
}

void draw(){
  if(currentScreen == "HomeScreen")
  {
    // Displaying the Home Screen and it's icons
    myHomeScreen.showHomeScreen();
  }else if(currentScreen == "FavouritesScreen"){  
    // Displaying the Favourites Screen and it's icons
    myFavouritesScreen.showFavouritesScreen();
  }else if(currentScreen == "SettingsScreen"){
    // Displaying the Favourites Screen and it's icons
    mySettingsScreen.showSettingsScreen();
  }else if(currentScreen == "CameraLiveViewScreen"){
    // Displaying the Favourites Screen and it's icons
    myCameraLiveViewScreen.showCameraLiveViewScreen();
  }
}