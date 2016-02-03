// Declaring instances of the relevant screens
HomeScreen myHomeScreen;
FavouritesScreen myFavouritesScreen;

String currentScreen = "HomeScreen";

void setup(){
  // Setting the size of the sketch
  size(360, 640);
  
  // Creating an myScreen using the Screen constructor
  myHomeScreen = new HomeScreen(#000000);
  
  myFavouritesScreen = new FavouritesScreen(#CE04BA);
}

void draw(){
  // Displaying the Home Screen and it's icons
  myHomeScreen.showHomeScreen();
  
  // Displaying the Favourites Screen and it's icons
  //myFavouritesScreen.showFavouritesScreen();
}

void mousePressed(){
  if(currentScreen == "HomeScreen"){
    myHomeScreen.checkIcons();
  }
}