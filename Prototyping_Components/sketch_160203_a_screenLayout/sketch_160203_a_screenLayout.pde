// Declaring instances of the relevant screens
HomeScreen myHomeScreen;
FavouritesScreen myFavouritesScreen;

String currentScreen = "HomeScreen";

void setup(){
  // Setting the size of the sketch
  size(360, 640);
  
  // Creating the screens which will be used in this application
  myHomeScreen = new HomeScreen(#000000);
  myFavouritesScreen = new FavouritesScreen(#CE04BA);
}

void draw(){
  if(currentScreen == "HomeScreen")
  {
    // Displaying the Home Screen and it's icons
    myHomeScreen.showHomeScreen();
  }else if(currentScreen == "FavouritesScreen"){  
    // Displaying the Favourites Screen and it's icons
    myFavouritesScreen.showFavouritesScreen();
  }
}