import java.lang.reflect.Method;

// Declaring global variables, which will contain the width and height of the device's
// display, so that these values can be reused throughout all classes (i.e. to calculate
// more dynamic position/width/height's so that the interface responds to different
// screen sizes
float appWidth;
float appHeight;

// Declaring the image holders for the icons that will appear throughout the sketch, 
// so that they can all be loaded in once, and then used throughout the relevant screens
PImage randomPageIconImage;
PImage searchPageIconImage;
PImage aboutPageIconImage;
PImage favouritesPageIconImage;
PImage settingsPageIconImage;
PImage homeIconImage;
PImage addFavouriteIconImage;
PImage shakeIconImage;
PImage shutterIconImage;
PImage switchViewIconImage;
PImage retryIconImage;
PImage deleteIconImage;
PImage saveIconImage;
PImage disgardIconImage;
PImage keepIconImage;
PImage cancelIconImage;
PImage twitterAccountIconImage;
PImage instagramAccountIconImage;
PImage buttonImage;

// Declaring the icon positioning X and Y variables, which will be used globally to ensure that
// the icons on each page all line up with one another. These measurements are all based on percentages
// of the app's display, and are initialised in the setup function of this sketch
float iconLeftX;
float iconRightX;
float iconCenterX;
float iconTopY;
float iconBottomY;
float iconCenterY;

// Declaring a global variable which will contain the default text size, which will be
// initialised in the setup() function of the app
float defaultTextSize;

// Setting the default screen to be the HomeScreen, so that when the app is loaded,
// this is the first screen that is displayed. Since this global variable is available
// throughout the sketch (i.e. within all classes as well as the main sketch) we will
// use this variable to pass in the value of "iconLinkTo" when the icon is clicked on
// within the checkMouseOver() method of the Icon class. The variable will then be tested
// against each of the potential screen names (in the main sketch's draw function) to
// decide which sketch should have the showScreen() method called on it i.e. (which
// screen should be displayed). 
// FOR TESTING PURPOSES CHANGING THIS STRING TO THE CLASS NAME OF ANOTHER SCREEN WILL
// FORCE IT TO LOAD FIRST WHEN THE APP RUNS
String currentScreen = "HomeScreen";

// Declaring a new instance of each screen in the application, so that they
// can be accessed by the draw function to be displayed when needed
HomeScreen myHomeScreen;
CameraLiveViewScreen myCameraLiveViewScreen;
FavouritesScreen myFavouritesScreen;
SettingsScreen mySettingsScreen;
AboutScreen myAboutScreen;
SearchScreen mySearchScreen;
SearchUnsuccessfulScreen mySearchUnsuccessfulScreen;
ImagePreviewScreen myImagePreviewScreen;
SaveShareScreenA mySaveShareScreenA;
SaveShareScreenB mySaveShareScreenB;
SharingScreen mySharingScreen;
ShareSaveSuccessfulScreen myShareSaveSuccessfulScreen;
ShareUnsuccessfulScreen myShareUnsuccessfulScreen;
ShareSaveUnsuccessfulScreen myShareSaveUnsuccessfulScreen;
SearchingScreen mySearchingScreen;
SocialMediaLoginScreen mySocialMediaLoginScreen;
SocialMediaLogoutScreen mySocialMediaLogoutScreen;

void setup() {
  // PC TESTING SETTINGS
  // Setting the size of the sketch (for testing purposes only, will eventually be dynamic)
  // size(360, 640);
  
  // ANDROID TESTING SETTINGS
  fullScreen();

  // Initialising the appWidth and appHeight variable with the width and height of the device's
  // display, so that these values can be reused throughout all classes (i.e. to calculate
  // more dynamic position/width/height's so that the interface responds to different
  // screen sizes (for testing purposes, I am currently setting these to the width and height
  // of the sketch as the display size of my computer screen gets called when using displayWidth
  // and displayHeight
  appWidth = width;
  appHeight = height;
  
  // Loading in the icon images, so that they can be accessed globally by all the screen classes. The
  // reason for loading these in the main sketch is that they only have to be loaded once, even if they are
  // reused on multiple pages
  randomPageIconImage = loadImage("iconPlaceholder.png");
  searchPageIconImage = loadImage("iconPlaceholder.png");
  aboutPageIconImage = loadImage("iconPlaceholder.png");
  favouritesPageIconImage = loadImage("iconPlaceholder.png");
  settingsPageIconImage = loadImage("iconPlaceholder.png");
  homeIconImage = loadImage("iconPlaceholder.png");
  addFavouriteIconImage = loadImage("iconPlaceholder.png");
  shakeIconImage = loadImage("iconPlaceholder.png");
  shutterIconImage = loadImage("iconPlaceholder.png");
  switchViewIconImage = loadImage("iconPlaceholder.png");
  retryIconImage = loadImage("iconPlaceholder.png");
  deleteIconImage = loadImage("iconPlaceholder.png");
  saveIconImage = loadImage("iconPlaceholder.png");
  disgardIconImage = loadImage("iconPlaceholder.png");
  keepIconImage = loadImage("iconPlaceholder.png");
  cancelIconImage = loadImage("iconPlaceholder.png");
  twitterAccountIconImage = loadImage("iconPlaceholder.png");
  instagramAccountIconImage = loadImage("iconPlaceholder.png");
  buttonImage = loadImage("iconPlaceholder.png");

  // Initialising the icon positioning X and Y variables, which will be used globally to ensure that
  // the icons on each page all line up with one another. These measurements are all based on percentages
  // of the app's display width and height (as defined above
  iconLeftX = appWidth * 0.15;
  iconRightX = appWidth * 0.85;
  iconCenterX = appWidth * 0.5;
  iconTopY = appHeight * 0.085;
  iconBottomY = appHeight * 0.92;
  iconCenterY = appHeight * 0.5;
  
  // Initialising the defaultTextSize to be equal to a percentage of the app's current height
  defaultTextSize = appHeight * 0.04;
  
  // Creating the screens which will be used in this application. Setting a random background
  // colour for each of these screens so that transitions between them can be more obvious
  // (for testing purposes only). Note - setting a background color is optional. Depending on the
  // screen's constructor, you can pass in a background color, a background image, or nothing at
  // all if you want to default to white
  myHomeScreen = new HomeScreen(#000000);
  myCameraLiveViewScreen = new CameraLiveViewScreen();
  myFavouritesScreen = new FavouritesScreen(#CE04BA);
  mySettingsScreen = new SettingsScreen(#2023A5);
  myAboutScreen = new AboutScreen (#CEBD5A);
  mySearchScreen = new SearchScreen(#E88121);
  mySearchUnsuccessfulScreen = new SearchUnsuccessfulScreen(#F5C811);
  myImagePreviewScreen = new ImagePreviewScreen();
  mySaveShareScreenA = new SaveShareScreenA(#5ACEBE);
  mySaveShareScreenB = new SaveShareScreenB(#CEBD5A);
  mySharingScreen = new SharingScreen(#1548EF);
  myShareSaveSuccessfulScreen = new ShareSaveSuccessfulScreen(#CE04BA);
  myShareUnsuccessfulScreen = new ShareUnsuccessfulScreen(#30B727);
  myShareSaveUnsuccessfulScreen = new ShareSaveUnsuccessfulScreen(#2023A5);
  mySearchingScreen = new SearchingScreen(#2023A5);
  mySocialMediaLoginScreen = new SocialMediaLoginScreen(#E88121);
  mySocialMediaLogoutScreen = new SocialMediaLogoutScreen(#CEBD54);
}

void draw() {
  // Calling the monitorScreens() function to display the right screen by calling
  // the showScreen() method. This function then calls the super class's drawScreen()
  // method, which not only adds the icons and backgrounds to the screen, it also
  // asks the icons on the screen to call their checkMouseOver() method (inherited from
  // the Icon class) to see if they were clicked on when a mouse event occurs
   switchScreens();
}

void switchScreens(){
  // Checking if the String that is stored in the currentScreen variable 
  // (which gets set in the Icon class when an icon is clicked on) is
  // equal to a series of class Names (i.e. HomeScreen), and if it is, then show that screen.
  // The showScreen() method of the current screen needs to be called repeatedly,
  // as this is where additonal funcitonality (such as checking of icons being
  // clicked on etc) are called. Note - Each sub class of the Screen class
  // MUST have a showScreen() method (even if this method is only used to call
  // it's super class's (Screen) drawScreen() method
  if(currentScreen.equals("HomeScreen")){
    myHomeScreen.showScreen();
  } else if(currentScreen.equals("CameraLiveViewScreen")){
    myCameraLiveViewScreen.showScreen();
  } else if(currentScreen.equals("FavouritesScreen")){
      myFavouritesScreen.showScreen();
  } else if(currentScreen.equals("SettingsScreen")){
      mySettingsScreen.showScreen();
  } else if(currentScreen.equals("AboutScreen")){
      myAboutScreen.showScreen();
  } else if(currentScreen.equals("SearchScreen")){
      mySearchScreen.showScreen();
  } else if(currentScreen.equals("SearchUnsuccessfulScreen")){
      mySearchUnsuccessfulScreen.showScreen();
  } else if(currentScreen.equals("ImagePreviewScreen")){
      myImagePreviewScreen.showScreen();
  } else if(currentScreen.equals("SaveShareScreenA")){
      mySaveShareScreenA.showScreen();
  } else if(currentScreen.equals("SaveShareScreenB")){
      mySaveShareScreenB.showScreen();
  } else if(currentScreen.equals("SharingScreen")){
      mySharingScreen.showScreen();
      testingTimeoutScreen("ShareSaveSuccessfulScreen");
  } else if(currentScreen.equals("ShareSaveSuccessfulScreen")){
      myShareSaveSuccessfulScreen.showScreen();
      testingTimeoutScreen("CameraLiveViewScreen");
  } else if(currentScreen.equals("ShareUnsuccessfulScreen")){
      myShareUnsuccessfulScreen.showScreen();
  } else if(currentScreen.equals("ShareSaveUnsuccessfulScreen")){
      myShareSaveUnsuccessfulScreen.showScreen();
  } else if(currentScreen.equals("SearchingScreen")){
      mySearchingScreen.showScreen();
      testingTimeoutScreen("CameraLiveViewScreen");
  } else if(currentScreen.equals("SocialMediaLoginScreen")){
      mySocialMediaLoginScreen.showScreen();
  } else if(currentScreen.equals("SocialMediaLogoutScreen")){
      mySocialMediaLogoutScreen.showScreen();
  }
}

// TESTING PURPOSES ONLY - FOR SCREENS WITH NO INTERACTION
// eeded a way to clear it from the screen until the
// code that normally would clear it is added
void testingTimeoutScreen(String fadeToScreen){
  if(mousePressed)
  { 
    
    currentScreen = fadeToScreen;
    mousePressed = false;
  }
}