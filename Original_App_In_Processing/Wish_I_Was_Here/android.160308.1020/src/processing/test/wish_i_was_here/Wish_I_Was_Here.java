package processing.test.wish_i_was_here;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ketai.*; 
import ketai.camera.*; 
import ketai.ui.*; 
import android.os.Environment; 
import android.content.*; 
import twitter4j.*; 
import twitter4j.api.*; 
import twitter4j.auth.*; 
import twitter4j.conf.*; 
import twitter4j.json.*; 
import twitter4j.management.*; 
import twitter4j.util.*; 
import twitter4j.util.function.*; 

import twitter4j.api.*; 
import twitter4j.*; 
import twitter4j.auth.*; 
import twitter4j.conf.*; 
import twitter4j.json.*; 
import twitter4j.management.*; 
import twitter4j.util.*; 
import twitter4j.util.function.*; 
import twitter4j.media.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Wish_I_Was_Here extends PApplet {















/*-------------------------------------- Globally Accessed Variables ------------------------------------------------*/

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
String currentScreen = "LoadingScreen";

// Creating a global variable for ourBrowserApiKey that is required to make requests
// to the Google Street View Image API. This key will be removed before commits to
// GitHub, for security purposes.
String ourBrowserApiKey = "AIzaSyB1t0zfCZYiYe_xXJQhkILcXnfxrnUdUyA";
String ourOAuthConsumerKey = "huoLN2BllLtOzezay2ei07bzo";
String ourOAuthConsumerSecret = "k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb";
String ourOAuthAccessToken = "4833019853-PzhGbWL0lulwsER62Ly7VY7P5WQcJT52j0MSIzI";
String ourOAuthAccessTokenSecret = "TazSQHl662mp6GIJkzlWRI5LkjOEnQZ4ifof7V3X3t30C";

String returnTo = "HomeScreen";

// Creating a global variable, which any icon can use to pass the name of the function
// they link to. The value of this variable is set in the Icon class when an icon is clicked
// on, and it's iconLinkTo value begins with a "_". This is a naming convention we created
// so that it is clearer which icons link to screens and which link to functions.
String callFunction = "";

// Creating a global boolean, to determine if the keyboard is required. This value is set to 
// true within the TextInput class if a text input has been clicked on. While this value is
// set to true, the draw function will call the KetaiKeyboard .show() method, to trigger the 
// device to display it's keyboard. Each time a mousePressed event occurs, this variable
// is reset to false, so that if a user clicks anywhere else on the screen, the keyboard
// will automatically close
Boolean keyboardRequired = false;

TextInput currentTextInput = null;
String currentTextInputValue = "";

// Creating a global variable which will contain the value of the most recent y position of the 
// mouse. This variable gets it's first value each time a mousePressed event occurs (i.e. when the 
// user first holds down the mouse). It will then be continually updated (on any pages that scroll)
// and used to determine how much a user has scrolled, and move the contents of these page's accordingly
float previousMouseY;
float previousMouseX;

PImage compiledImage;

/*-------------------------------------- KetaiCamera ------------------------------------------------*/

// Creating a global variable to store the ketaiCamera object, so that it can be
// accessed thoroughout the sketch once it has been initiated i.e. to read in,
// display and eventually alter the live stream images
KetaiCamera ketaiCamera;

// Creating a global variable to store the number of the camera we want to view
// at any given time. The front facing camera (on a device with more than one camera)
// will be at index 1, and the rear camera will be at index 0. On a device with only
// 1 camera (such as the college Nexus tablets) this camera will always be at index
// 0, regardless of whether it is a front or back camera
int camNum;

// Creating a variable to store a value of 1 or -1, to decide whether and image should be
// flipped i.e. when using the front facing camera, the x scale should always be -1 to 
// avoid things being in reverse
int cameraScale = -1;

// Creating a global variable, which will always be set to either 90 or -90 degrees, to ensure
// that the live stream images from the camera area always oriented in the right rotation. 
// Initialising at -90 degrees, as we will be starting on the front facing camera
int cameraRotation = -90;

Boolean finalKeying = false;

/*----------------------------------- Twitter Tweeting -----------------------------------------*/
// Setting up the configuration for tweeting from an account on Twitter
ConfigurationBuilder cb = new ConfigurationBuilder();

// Using the twitter class for tweeting, so later I can call on the twitter factory, which in turn
// creates a new instance of the configuration builder
Twitter twitter;

String saveToPath;

/*-------------------------------------- Images ------------------------------------------------*/

// Declaring the image holders for the icons that will appear throughout the sketch, 
// so that they can all be loaded in once, and then used throughout the relevant screens
PImage randomPageIconImage;
PImage searchPageIconImage;
PImage aboutPageIconImage;
PImage favouritesPageIconImage;
PImage settingsPageIconImage;
PImage homeIconImage;
PImage favIconImage;
PImage shakeIconImage;
PImage shutterIconImage;
PImage switchViewIconImage;
PImage retryIconImage;
PImage deleteIconImage;
PImage saveIconImage;
PImage disgardIconImage;
PImage keepIconImage;
PImage cancelIconImage;
PImage facebookAccountIconImage;
PImage twitterAccountIconImage;
PImage instagramAccountIconImage;
PImage emailIconImage;
PImage buttonImage;
PImage placeholderBackgroundImage;
PImage toggleSwitchOnIconImage;
PImage toggleSwitchOffIconImage;
PImage overlayImage;

/*-------------------------------------- Sizing ------------------------------------------------*/

// Declaring global variables, which will contain the width and height of the device's
// display, so that these values can be reused throughout all classes (i.e. to calculate
// more dynamic position/width/height's so that the interface responds to different
// screen sizes
int appWidth;
int appHeight;

// Declaring the icon positioning X and Y variables, which will be used globally to ensure that
// the icons on each page all line up with one another. These measurements are all based on percentages
// of the app's display, and are initialised in the setup function of this sketch
float iconLeftX;
float iconRightX;
float iconCenterX;
float iconTopY;
float iconBottomY;
float iconCenterY;
float largeIconSize;
float smallIconSize;
float largeIconBottomY;
float screenTitleY;

// Declaring a global variable which will contain the default text size, which will be
// initialised in the setup() function of the app
float defaultTextSize;

/*-------------------------------------- Screens ------------------------------------------------*/

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
LoadingScreen myLoadingScreen;

/*-------------------------------------- Saving ------------------------------------------------*/
// Creating a string that will hold the directory path of where the images will be saved to
String directory = "";

// Creating a global image variable, to store the currentImage. Currently, this image is
// simply the current frame of the ketaiCamera, but evenually this variable will be
// used to hold the "postcard" of the person standing in a Google Street View enviroment.
// This variable is set to the current frame of the camera each time a new frame is read
// in (i.e. in the onCameraPreviewEvent() of the main sketch)
PImage currentImage;

Boolean readingImage = false;
PImage currentFrame;

/*-------------------------------------- Google Street View Images ------------------------------------------------*/
String searchAddress;
String compiledSearchAddress;
String googleImageLatLng;
float googleImagePitch;
float googleImageHeading;
String currentLocationName = "";

// Declaring the currentLocationImage variable, which will be set within the FavouriteTab's showFavourite()
// method. For the moment, we will be initialising this variable to a random location in the setup() method
// of the main sketch, so that the user will always be able to see a location in the background, even if they
// don't go through the favourites menu of the app
PImage currentLocationImage = null;

/*-------------------------------------- Built In Functions ------------------------------------------------*/

public void setup() {
  /*-------------------------------------- Global ------------------------------------------------*/

  // PC TESTING SETTINGS
  // Setting the size of the sketch (for testing purposes only, will eventually be dynamic)
  //size(360, 640);

  // ANDROID TESTING SETTINGS
  

  // Locking the applications orientation to portrait, so that the image being read in from the 
  // the camera is maintained, even when the device is rotated
  orientation(PORTRAIT);

  // Initialising the appWidth and appHeight variable with the width and height of the device's
  // display, so that these values can be reused throughout all classes (i.e. to calculate
  // more dynamic position/width/height's so that the interface responds to different
  // screen sizes (for testing purposes, I am currently setting these to the width and height
  // of the sketch as the display size of my computer screen gets called when using displayWidth
  // and displayHeight
  appWidth = width;
  appHeight = height;

  /*-------------------------------------- Ketai ------------------------------------------------*/

  // Calling the ketaiCamera constructor to initialise the camera with the same
  // width/height of the device, with a frame rate of 24.
  ketaiCamera = new KetaiCamera(this, appWidth, appHeight, 24);

  // Printing out the list of available cameras i.e. front/rear facing
  println(ketaiCamera.list());

  // Printing out the number of availabe cameras
  println("There is " + ketaiCamera.getNumberOfCameras() + " camera/s available on this device");

  // Check if the device has more than one camera i.e. does it have a front
  // and a rear facing camera?

  if (ketaiCamera.getNumberOfCameras() > 1)
  {
    // If there is more than one camera, then default to the front camera
    // (which as far as I can tell tends to be at index 1)
    camNum = 1;
  } else
  {
    // If there is only one camera, then default to the rear camera
    // (which as far as I can tell tends to be at index 0)
    camNum = 0;
  }

  // Setting the camera to default to the front camera
  ketaiCamera.setCameraID(camNum);

  /*-------------------------------------- Images ------------------------------------------------*/

  // Loading in the icon images, so that they can be accessed globally by all the screen classes. The
  // reason for loading these in the main sketch is that they only have to be loaded once, even if they are
  // reused on multiple pages
  randomPageIconImage = loadImage("randomPageIconImage.png");
  searchPageIconImage = loadImage("searchPageIconImage.png");
  aboutPageIconImage = loadImage("aboutPageIconImage.png");
  favouritesPageIconImage = loadImage("favouritesPageIconImage.png");
  settingsPageIconImage = loadImage("settingsPageIconImage.png");
  homeIconImage = loadImage("homeIconImage.png");
  favIconImage = loadImage("favIconNoImage.png");
  shakeIconImage = loadImage("shakeIconImage.png");
  shutterIconImage = loadImage("shutterIconImage.png");
  switchViewIconImage = loadImage("switchViewIconImage.png");
  retryIconImage = loadImage("retryIconImage.png");
  deleteIconImage = loadImage("deleteIconImage.png");
  saveIconImage = loadImage("saveIconImage.png");
  disgardIconImage = loadImage("disgardIconImage.png");
  keepIconImage = loadImage("keepIconImage.png");
  cancelIconImage = loadImage("cancelIconImage.png");
  facebookAccountIconImage = loadImage("facebookAccountIconImage.png");
  twitterAccountIconImage = loadImage("twitterAccountIconImage.png");
  instagramAccountIconImage = loadImage("instagramAccountIconImage.png");
  emailIconImage = loadImage("emailIconImage.png");
  buttonImage = loadImage("buttonImage.png");
  placeholderBackgroundImage = loadImage("generalPageBackgroundImage.png");
  toggleSwitchOnIconImage = loadImage("toggleSwitchOnIconImage.png");
  toggleSwitchOffIconImage = loadImage("toggleSwitchOffIconImage.png");
  overlayImage = loadImage("overlay.png");

  /*-------------------------------------- Sizing ------------------------------------------------*/

  // Initialising the icon positioning X and Y variables, which will be used globally to ensure that
  // the icons on each page all line up with one another. These measurements are all based on percentages
  // of the app's display width and height (as defined above
  iconLeftX = appWidth * 0.15f;
  iconRightX = appWidth * 0.85f;
  iconCenterX = appWidth * 0.5f;
  iconTopY = appHeight * 0.085f;
  iconBottomY = appHeight * 0.92f;
  iconCenterY = appHeight * 0.5f;
  largeIconSize = appWidth * 0.25f;
  smallIconSize = appWidth * 0.15f;
  largeIconBottomY = iconBottomY - (largeIconSize/2);
  screenTitleY = appHeight * 0.08f;

  // Initialising the defaultTextSize to be equal to a percentage of the app's current height
  defaultTextSize = appHeight * 0.04f;

  /*-------------------------------------- Screens ------------------------------------------------*/

  // Creating the screens which will be used in this application. Setting a random background
  // colour for each of these screens so that transitions between them can be more obvious
  // (for testing purposes only). Note - setting a background color is optional. Depending on the
  // screen's constructor, you can pass in a background color, a background image, or nothing at
  // all if you want to default to white
  myHomeScreen = new HomeScreen(placeholderBackgroundImage);
  myCameraLiveViewScreen = new CameraLiveViewScreen();
  myFavouritesScreen = new FavouritesScreen(loadImage("favouritesScreenBackgroundImage.png"));
  mySettingsScreen = new SettingsScreen(placeholderBackgroundImage);
  myAboutScreen = new AboutScreen (loadImage("aboutScreenBackgroundImage.png"));
  mySearchScreen = new SearchScreen(placeholderBackgroundImage);
  mySearchUnsuccessfulScreen = new SearchUnsuccessfulScreen(placeholderBackgroundImage);
  myImagePreviewScreen = new ImagePreviewScreen();
  mySaveShareScreenA = new SaveShareScreenA(placeholderBackgroundImage);
  mySaveShareScreenB = new SaveShareScreenB(placeholderBackgroundImage);
  mySharingScreen = new SharingScreen(placeholderBackgroundImage);
  myShareSaveSuccessfulScreen = new ShareSaveSuccessfulScreen(placeholderBackgroundImage);
  myShareUnsuccessfulScreen = new ShareUnsuccessfulScreen(placeholderBackgroundImage);
  myShareSaveUnsuccessfulScreen = new ShareSaveUnsuccessfulScreen(placeholderBackgroundImage);
  mySearchingScreen = new SearchingScreen(placeholderBackgroundImage);
  mySocialMediaLoginScreen = new SocialMediaLoginScreen(placeholderBackgroundImage);
  mySocialMediaLogoutScreen = new SocialMediaLogoutScreen(placeholderBackgroundImage);
  myLoadingScreen = new LoadingScreen(loadImage("loadingScreenImage.png"));

  /*-------------------------------------- Saving ------------------------------------------------*/

  // Storing a string that tells the app where to store the images, by default 
  // it goes to the pictures folder and this string as it has WishIWasHereApp 
  // it is creating a folder in the picture folder of the device
  directory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES  + "/WishIWasHereApp/";  

  // Initialising the currentImage to be equal to a plain black image. This is so that if the 
  // currentImage get's referred to before the camera has started, it will just contain a plain
  // black screen. Creating this black image by using createImage to make it the full size
  // of the screen, and then setting each pixel in the image to black
  currentImage = createImage(appWidth, appHeight, RGB);
  currentImage.loadPixels();
  for (int i = 0; i < currentImage.pixels.length; i++) {
    currentImage.pixels[i] = color(0);
  }
  currentImage.updatePixels();

  currentFrame = createImage(ketaiCamera.width, ketaiCamera.height, ARGB);

  /*----------------------------------- Twitter Tweeting -----------------------------------------*/
  //Setting up Twitter and informing twitter of the users credentials to our application can tweet
  // from the users twitter account and access their account
  cb.setOAuthConsumerKey(ourOAuthConsumerKey);
  cb.setOAuthConsumerSecret(ourOAuthConsumerSecret);
  cb.setOAuthAccessToken(ourOAuthAccessToken);
  cb.setOAuthAccessTokenSecret(ourOAuthAccessTokenSecret);

  twitter = new TwitterFactory(cb.build()).getInstance();
}

public void draw() {
  background(0);
  
  // Calling the monitorScreens() function to display the right screen by calling
  // the showScreen() method. This function then calls the super class's drawScreen()
  // method, which not only adds the icons and backgrounds to the screen, it also
  // asks the icons on the screen to call their checkMouseOver() method (inherited from
  // the Icon class) to see if they were clicked on when a mouse event occurs
  switchScreens();
 
  // Checking if any screen's icons are trying to trigger any functions
  if (callFunction.equals("_keepImage")) {
    keepImage();
  } else if (callFunction.equals("_switchCameraView")) {
    myCameraLiveViewScreen.switchCameraView();
  } else if (callFunction.equals("_addToFavourites")) {
    addToFavourites("Favourite");
  } else if (callFunction.equals("_switchLearningMode")) {
    switchLearningMode();
  } else if (callFunction.equals("_switchAutoSave")) {
    switchAutoSave();
  } else if (callFunction.equals("_sendTweet")) {
    currentScreen = "SharingScreen";
    mySharingScreen.showScreen();
    sendTweet();
  } else if (callFunction.equals("_mergeImages")) {
    mergeImages();
  } else if (callFunction.equals("_disgardImage")) {
    disgardImage();
  } else if (callFunction.equals("_searchForLocation")) {
    currentScreen = "SearchingScreen";
    mySearchingScreen.showScreen();
    searchForLocation();
  } else if (callFunction.equals("_getRandomLocation")) {
    currentScreen = "SearchingScreen";
    mySearchingScreen.showScreen();
    getRandomLocation();
  } else {
    //println("This function does not exist / cannot be triggered by this icon");
  }

  // Checking if the keyboard is required i.e. if an input field is currently in focus
  if (keyboardRequired) {
    KetaiKeyboard.show(this);
    callFunction = "";
  } else {
    KetaiKeyboard.hide(this);
    callFunction = "";
  }
}

public void mousePressed() {
  keyboardRequired = false;
  previousMouseY = mouseY;
  previousMouseX = mouseX;
}

public void keyPressed() {
  // Getting the current input value of this text input (i.e. so that if a user clicks between different
  // text fields, they can resume their input instead of the textfield becoming empty)
  currentTextInputValue = currentTextInput.getInputValue();

  // Checking if the key pressed is a coded value i.e. backspace etc
  if (key == CODED) {
    //println(key);
    // Checking if the key pressed was the backspace key
    if (keyCode == 67)
    {
      // Checking that the length of the current currentTextInputValue string is greater than 0 (i.e. 
      // if the string is empty, don't try to delete anything)
      if (currentTextInputValue.length() > 0) {
        //println("BACKSPACE");
        // Removing the last character from currentTextInputValue string, by creating a substring
        // of currentTextInputValue, that is one shorter than the current currentTextInputValue string
        currentTextInputValue = currentTextInputValue.substring(0, currentTextInputValue.length() - 1);
      }
    }
  } else {
    // If the key is not a coded value, adding the character to currentTextInputValue string
    currentTextInputValue += key;
  }
  // Passing the currentTextInputValue string into the setInputValue of the currentTextInput field
  currentTextInput.setInputValue(currentTextInputValue);
}

/*-------------------------------------- Ketai Functions ------------------------------------------------*/

// ketaiCamera event which is automatically called everytime a new frame becomes
// available from the ketaiCamera.
public void onCameraPreviewEvent()
{
  if (readingImage == false && callFunction != "_mergeImages") {
    // Reading in a new frame from the ketaiCamera.
    readingImage = true;
    ketaiCamera.read();
    currentFrame = ketaiCamera.get();
    thread("previewGreenScreen");
  }
}

/*-------------------------------------- Custom Functions ------------------------------------------------*/
public void switchScreens() {
  // Checking if the String that is stored in the currentScreen variable 
  // (which gets set in the Icon class when an icon is clicked on) is
  // equal to a series of class Names (i.e. HomeScreen), and if it is, then show that screen.
  // The showScreen() method of the current screen needs to be called repeatedly,
  // as this is where additonal funcitonality (such as checking of icons being
  // clicked on etc) are called. Note - Each sub class of the Screen class
  // MUST have a showScreen() method (even if this method is only used to call
  // it's super class's (Screen) drawScreen() method
  if (currentScreen.equals("HomeScreen")) {
    myHomeScreen.showScreen();
  } else if (currentScreen.equals("CameraLiveViewScreen")) {
    finalKeying = false;
    myCameraLiveViewScreen.showScreen();
  } else if (currentScreen.equals("FavouritesScreen")) {
    myFavouritesScreen.showScreen();
  } else if (currentScreen.equals("SettingsScreen")) {
    returnTo = "SettingsScreen";
    mySettingsScreen.showScreen();
  } else if (currentScreen.equals("AboutScreen")) {
    myAboutScreen.showScreen();
  } else if (currentScreen.equals("SearchScreen")) {
    mySearchScreen.showScreen();
  } else if (currentScreen.equals("SearchUnsuccessfulScreen")) {
    mySearchUnsuccessfulScreen.showScreen();
    testingTimeoutScreen("SearchUnsuccessfulScreen");
  } else if (currentScreen.equals("ImagePreviewScreen")) {
    /*
    if(readingImage == false && finalKeying == false){
     finalKeying = true;
     removeGreenScreen();
     }
     */
    myImagePreviewScreen.showScreen();
  } else if (currentScreen.equals("SaveShareScreenA")) {
    returnTo = "SaveShareScreenA";
    mySaveShareScreenA.showScreen();
  } else if (currentScreen.equals("SaveShareScreenB")) {
    mySaveShareScreenB.showScreen();
  } else if (currentScreen.equals("SharingScreen")) {
    mySharingScreen.showScreen();
    testingTimeoutScreen("ShareSaveSuccessfulScreen");
  } else if (currentScreen.equals("ShareSaveSuccessfulScreen")) {
    myShareSaveSuccessfulScreen.showScreen();
    testingTimeoutScreen("CameraLiveViewScreen");
  } else if (currentScreen.equals("ShareUnsuccessfulScreen")) {
    myShareUnsuccessfulScreen.showScreen();
  } else if (currentScreen.equals("ShareSaveUnsuccessfulScreen")) {
    myShareSaveUnsuccessfulScreen.showScreen();
  } else if (currentScreen.equals("SearchingScreen")) {
    mySearchingScreen.showScreen();
  } else if (currentScreen.equals("SocialMediaLoginScreen")) {
    mySocialMediaLoginScreen.showScreen();
  } else if (currentScreen.equals("SocialMediaLogoutScreen")) {
    mySocialMediaLogoutScreen.showScreen();
  } else if (currentScreen.equals("LoadingScreen")) {
    myLoadingScreen.showScreen();
    testingTimeoutScreen("HomeScreen");
  } else {
    println("This screen doesn't exist");
    //currentScreen = "HomeScreen";
  }


  // Turning the camera on and off (if the current screen
  // is the camera live view, and the camera is  not yet turned
  // on, then start the camera, otherwise, if you are on any other screen,
  // stop the camera
  if (currentScreen.equals("CameraLiveViewScreen")) {
    if (!ketaiCamera.isStarted()) {
      ketaiCamera.start();
    }
  } else if (ketaiCamera.isStarted()) {
    ketaiCamera.stop();
  }
}

public void keepImage() {
  callFunction = "";
  // Checking if Storage is available
  if (isExternalStorageWritable()) {   
    saveToPath = directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg";
    // Trying to save out the image. Putting this code in an if statement, so that if it fails, a message will be logged
    if (compiledImage.save(saveToPath)) {
      println("Successfully saved image to = " + saveToPath);
      println(saveToPath);
      currentScreen = "SaveShareScreenA";
    } else {
      println("Failed to save image");
    }
  }
}

public Boolean isExternalStorageWritable() {
  Boolean answer = false;

  // Creating a string to store the state of the external storage
  String state = Environment.getExternalStorageState();

  // Testing the string value of the enviroment property media_mounted, against the
  // string value of the state (as declared above). If media_mounted then storage
  // is available to be written/read, and all permissions are in place
  if (Environment.MEDIA_MOUNTED.equals(state)) {
    println("External Storage is writable: " + state);
    answer = true;
  } else {
    println("External Storage is writable: " + state);
  }

  return answer;
}


// TESTING PURPOSES ONLY - FOR SCREENS WITH NO INTERACTION
// eeded a way to clear it from the screen until the
// code that normally would clear it is added
public void testingTimeoutScreen(String fadeToScreen) {
  if (mousePressed)
  { 
    currentScreen = fadeToScreen;
    mousePressed = false;
  }
}

public void addToFavourites(String place) {
  callFunction = "";
  myCameraLiveViewScreen.favouriteLocation = !myCameraLiveViewScreen.favouriteLocation;
  println("Favourite location is now: " + myCameraLiveViewScreen.favouriteLocation);
}

public void switchLearningMode() {
  callFunction = "";
  mySettingsScreen.learningModeOn = !mySettingsScreen.learningModeOn;
  println("Learning mode is now: " + mySettingsScreen.learningModeOn);
}

public void switchAutoSave() {
  callFunction = "";
  mySettingsScreen.autoSaveModeOn = !mySettingsScreen.autoSaveModeOn;
  println("Auto-save is now: " + mySettingsScreen.autoSaveModeOn);
}


public void sendTweet() {
 // getShareableImage();
  callFunction = "";
  
  File twitterImage = new File(saveToPath);
  // Creating a string to to hold the value that is in the message input 
  String message = mySaveShareScreenB.messageInput.getInputValue();

  //making the current screen "Sharing Screen"
  currentScreen = "SharingScreen";
  try {
    StatusUpdate status = new StatusUpdate(message + " #WishIWasHere");
    //System.out.println("Status updated to [" + status.getText() + "].");
    status.setMedia(twitterImage);
    twitter.updateStatus(status);
    // Making a twitter status that will hold the message the user typed 
    // and adding the Wish I Was Here tag onto the end of the message
    
    

    //Changing the current Screen
    currentScreen = "ShareSaveSuccessfulScreen";

    //Cleaing the message input so it is empty the next time the user 
    // arrives to send another tweet
    mySaveShareScreenB.messageInput.clearInputValue();
  }
  catch (TwitterException te)
  {
    //If the tweet can't be sent, it will print out the reason that 
    // is causing the problem
    System.out.println("Error: "+ te.getMessage());

    //Changing the current screen to be the unsuccessul share screen
    currentScreen = "ShareUnsuccessfulScreen";
  }
}
/*
void getShareableImage(File latestImage){
  File getImage = new File(currentImage);
  if(latestImage == null || getImage.lastModified().after(latestImage.lastModified())){
      latestImage = getImage;
      latestImage.getPath();
  }
}*/

public void previewGreenScreen() {
  //println("Starting removing Green Screen at frame " + frameCount);

  // Changing the colour mode to HSB, so that I can work with the hue, satruation and
  // brightness of the pixels. Setting the maximum hue to 360, and the maximum saturation
  // and brightness to 100.
  colorMode(HSB, 360, 100, 100);

  PImage keyedImage = createImage(currentFrame.width, currentFrame.height, ARGB);

  keyedImage = currentFrame.get();

  // Loading in the pixel arrays of the keyed image and the girl green screen image
  keyedImage.loadPixels();

  for (int i = 0; i < keyedImage.pixels.length; i++) {

    // Getting the hue, saturation and brightness values of the current pixel
    float pixelHue = hue(currentFrame.pixels[i]);

    // If the hue of this pixel falls anywhere within the range of green in the colour spectrum
    if (pixelHue > 60 && pixelHue < 180) {

      float pixelSaturation = saturation(currentFrame.pixels[i]);
      float pixelBrightness = brightness(currentFrame.pixels[i]);

      // If the saturation and brightness are above 30, then this is a green pixel
      if (pixelSaturation > 30 && pixelBrightness > 20)
      {
        // Set this pixel in the keyedImage to be transparent (Removing the main areas of the green)
        keyedImage.pixels[i] = color(0, 0, 0, 0);
      } else {
        // Even though this pixel falls within the green range of the colour spectrum, it's saturation and brightness
        // are low enough that it is unlikely to be a part of the green screen, but may just be an element of the scene
        // that is picking up a glow off the green screen. Lowering the hue and saturation to remove the green tinge 
        // from this pixel.
        keyedImage.pixels[i] = color(pixelHue * 0.6f, pixelSaturation * 0.3f, pixelBrightness);
      }
    }
  }

  // Updating the pixel arrays of the ketaiCamera and the keyed image
  keyedImage.updatePixels();

  // Resetting the color mode to RGB
  colorMode(RGB, 255, 255, 255);

  currentImage = keyedImage.get();

  readingImage = false;
  //println("Finished removing Green Screen at frame " + frameCount);
}

public void mergeImages() {
  callFunction = "";
  PGraphics mergedImage = createGraphics(appWidth, appHeight, JAVA2D);
  println(cameraRotation);
  mergedImage.beginDraw();
  mergedImage.imageMode(CENTER);
  mergedImage.image(currentLocationImage, appWidth/2, appHeight/2, appWidth, appHeight);
  mergedImage.endDraw();

  mergedImage.beginDraw();
  mergedImage.pushMatrix();
  mergedImage.translate(appWidth/2, appHeight/2);
  mergedImage.scale(cameraScale, 1);
  mergedImage.rotate(radians(cameraRotation));
  mergedImage.imageMode(CENTER);
  mergedImage.image(currentImage, 0, 0, appHeight, appWidth);
  mergedImage.popMatrix();
  mergedImage.endDraw();

  mergedImage.beginDraw();
  mergedImage.imageMode(CENTER);
  mergedImage.image(overlayImage, appWidth * 0.7f, appHeight * 0.9f, appWidth * 0.55f, appWidth * 0.3f);
  mergedImage.endDraw();

  compiledImage = mergedImage.get();
  currentScreen = "ImagePreviewScreen";
}

public void disgardImage() {
  compiledImage = null;
  currentScreen = "CameraLiveViewScreen";
}

public void searchForLocation() {
  // Getting the current input value of this text input (i.e. the most recent text input will have been the search box)
  searchAddress = currentTextInputValue;
  compiledSearchAddress = searchAddress.replace(" ", "+");

  println("Searching for " + searchAddress);

  googleImageLatLng = "0,0";
  googleImagePitch = 0;

  // Using the Google Maps Geocoding API to query the address the user has specified, and return the relevant XML containing
  // the location data of the place - https://developers.google.com/maps/documentation/geocoding/intro
  XML locationXML = loadXML("https://maps.googleapis.com/maps/api/geocode/xml?address=" + compiledSearchAddress + "&key=" + ourBrowserApiKey);

  if (locationXML.getChild("status").getContent().equals("OK")) {
    String latitude = locationXML.getChildren("result")[0].getChild("geometry").getChild("location").getChild("lat").getContent();
    String longitude = locationXML.getChildren("result")[0].getChild("geometry").getChild("location").getChild("lng").getContent();
    currentLocationName = locationXML.getChildren("result")[0].getChildren("address_component")[0].getChild("long_name").getContent();

    googleImageLatLng = latitude + "," + longitude;
    println("Latitude, Longitude = " + googleImageLatLng);
    loadGoogleImage();
    currentTextInput.clearInputValue(); 
  } else {
    currentScreen = "SearchUnsuccessfulScreen";
  } 
}

public void getRandomLocation() {
  println("Getting a random location");
  String randomLocationURLData = myFavouritesScreen.getRandomLocation();
  googleImageLatLng = randomLocationURLData.split("@")[1].split("&")[0]; 
  googleImageHeading = PApplet.parseFloat(randomLocationURLData.split("heading=")[1].split("&")[0]);
  googleImagePitch = PApplet.parseFloat(randomLocationURLData.split("pitch=")[1]);
  currentLocationName = randomLocationURLData.split("@")[0];

  loadGoogleImage();
}

public void loadGoogleImage() {
  println("Loading in a new image from Google");
  println("LatLng = " + googleImageLatLng);
  println("Heading = " + googleImageHeading);
  println("Pitch = " + googleImagePitch);
  currentLocationImage = loadImage("https://maps.googleapis.com/maps/api/streetview?location=" + googleImageLatLng + "&pitch=" + googleImagePitch + "&heading=" + googleImageHeading + "&key=" + ourBrowserApiKey + "&size=" + appWidth/2 + "x" + appHeight/2);
  println("Image successfully loaded");

  if (!currentScreen.equals("CameraLiveViewScreen")) {
    currentScreen = "CameraLiveViewScreen";
  }
}
/*
void removeGreenScreen() {
 println("Starting removing Green Screen at frame " + frameCount);
 
 // Changing the colour mode to HSB, so that I can work with the hue, satruation and
 // brightness of the pixels. Setting the maximum hue to 360, and the maximum saturation
 // and brightness to 100.
 colorMode(HSB, 360, 100, 100);
 
 PImage keyedImage = createImage(currentFrame.width, currentFrame.height, ARGB);
 
 keyedImage = currentFrame.get();
 
 // Loading in the pixel arrays of the keyed image and the girl green screen image
 keyedImage.loadPixels();
 currentFrame.loadPixels();
 
 int cfPixelsLength = currentFrame.pixels.length;
 int cfWidth = currentFrame.width;
 
 for (int i = 0; i < cfPixelsLength; i++) {
 
 // Getting the hue, saturation and brightness values of the current pixel
 float pixelHue = hue(currentFrame.pixels[i]);
 
 // If the hue of this pixel falls anywhere within the range of green in the colour spectrum
 if (pixelHue > 60 && pixelHue < 180) {
 
 float pixelSaturation = saturation(currentFrame.pixels[i]);
 float pixelBrightness = brightness(currentFrame.pixels[i]);
 
 
 // Creating variables to store the hue of the pixels surrounding the current pixel.
 // Defaulting these the be equal to the current pixels hue, and only changing them if
 // the current pixel is away from the edge of the picture
 float pixelHueToLeft = pixelHue;
 float pixelHueToRight = pixelHue;
 float pixelHueAbove = pixelHue;
 float pixelHueBelow = pixelHue;
 
 
 // If the current pixel is not near the edge of the image, changing the values of the variables
 // for the pixels around it to get their hue values
 if (i > cfWidth + 1 && i < cfPixelsLength - cfWidth - 1) {
 pixelHueToLeft = hue(currentFrame.pixels[i - 1]);
 pixelHueToRight = hue(currentFrame.pixels[i + 1]);
 pixelHueAbove = hue(currentFrame.pixels[i - cfWidth]);
 pixelHueBelow = hue(currentFrame.pixels[i + cfWidth]);
 }
 
 // If the saturation and brightness are above 30, then this is a green pixel
 if (pixelSaturation > 30 && pixelBrightness > 30)
 {
 // If the hue of the pixel is between 90 and 100, this is not fully green, but with a tinge 
 if (pixelHue > 90 && pixelHue < 100) {
 // This seems to effect the girl's hair on the left
 // Lowering the hue, saturation and opacity, to reduce the intensity of the colour
 keyedImage.pixels[i] = color(pixelHue * 0.3, pixelSaturation * 0.4, pixelBrightness, 200);
 } else if (pixelHue > 155) {
 // Increasing the hue, and reducing the saturation
 keyedImage.pixels[i] = color(pixelHue * 1.2, pixelSaturation * 0.5, pixelBrightness, 255);
 } else if (pixelHue < 115) {
 // Reducting the hue and saturation. Fixes the girl's hair (in greenScreenImage1) but adds in some of
 // the green screeen in greenScreenImage2)
 keyedImage.pixels[i] = color(pixelHue * 0.4, pixelSaturation * 0.5, pixelBrightness, 255);
 } else {
 // If the pixels around this pixel are in the more intense are of green, then assume this is part of the green screen
 if (pixelHueToLeft > 90 && pixelHueToLeft < 150 && pixelHueToRight > 90 && pixelHueToRight < 150 && pixelHueAbove > 90 && pixelHueAbove < 150 && pixelHueBelow > 90 && pixelHueBelow < 150) {
 // Set this pixel in the keyedImage to be transparent (Removing the main areas of the green)
 keyedImage.pixels[i] = color(0, 0, 0, 0);
 } else if (pixelHue > 130) {
 // This seems to be the edges around the girl
 // Increasing the hue, reducing the saturation and displaying the pixel at half opacity
 keyedImage.pixels[i] = color(pixelHue * 1.1, pixelSaturation * 0.5, pixelBrightness, 150);
 } else {
 // Set this pixel in the keyedImage to be transparent (Removing the main areas of the green)
 keyedImage.pixels[i] = color(0, 0, 0, 0);
 }
 }
 } else {
 // Even though this pixel falls within the green range of the colour spectrum, it's saturation and brightness
 // are low enough that it is unlikely to be a part of the green screen, but may just be an element of the scene
 // that is picking up a glow off the green screen. Lowering the hue and saturation to remove the green tinge 
 // from this pixel.
 keyedImage.pixels[i] = color(pixelHue * 0.6, pixelSaturation * 0.3, pixelBrightness);
 }
 }
 }
 
 // Updating the pixel arrays of the ketaiCamera and the keyed image
 currentFrame.updatePixels();
 keyedImage.updatePixels();
 
 // Resetting the color mode to RGB
 colorMode(RGB, 255, 255, 255);
 
 println("Finished removing Green Screen and merging images at frame " + frameCount);
 }
 */
public class AboutScreen extends Screen {
  private String aboutText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent maximus, turpis sit amet condimentum gravida, est quam bibendum purus, ac efficitur lectus justo in tortor. Phasellus et interdum mi.";
  private Icon[] pageIcons;
  public Boolean loaded = false;

  // Creating a public constructor for the AboutScreen class, so that
  // an instance of it can be declared in the main sketch
  public AboutScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(appWidth/2, appHeight/2, appWidth, appHeight * 2, loadImage("aboutScreenBackgroundImage.png"));

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon homeIcon = new Icon(iconRightX, iconTopY, homeIconImage, "Home", false, "HomeScreen");
    Icon facebookIcon = new Icon(appWidth * 0.2f, iconBottomY, facebookAccountIconImage, "Facebook", false, "https://www.facebook.com/wishiwashereapp");
    Icon twitterIcon = new Icon(appWidth * 0.5f, iconBottomY, twitterAccountIconImage, "Twitter", false, "https://twitter.com/wishiwashere");
    Icon instagramIcon = new Icon(appWidth * 0.8f, iconBottomY, instagramAccountIconImage, "Instagram", false, "https://www.instagram.com/wishiwashereapp/");
    //Icon emailIcon = new Icon(appWidth * 0.8, iconBottomY, emailIconImage, "Email", false, "mailto:wishiwashere.thenopayholiday@gmail.com");


    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, facebookIcon, twitterIcon, instagramIcon};
    pageIcons = allIcons;

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("About");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {
    if (!this.loaded) {
      // Resetting the position values of the element so on the screen every time the page is opened,
      // so that if a user leaves the screen half scrolled, it will still be reset upon their return
      this.setY(appHeight/2);
      this.getScreenIcons()[0].setY(iconTopY);
      this.getScreenIcons()[1].setY(iconBottomY);
      this.getScreenIcons()[2].setY(iconBottomY);
      this.getScreenIcons()[3].setY(iconBottomY);

      // Setting loaded to true, so that this block of code will only run once (each time this page
      // is opened). This value will be reset to false in the Icon class's checkMouseOver function,
      // when an icon that links to another page has been clicked.
      this.loaded = true;
      println("firstLoad");
    }

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addImage(loadImage("aboutPageTeamImage.jpg"), appWidth/2, this.getY() + (appHeight * -0.25f), appWidth * 0.7f, appHeight * 0.2f);

    rectMode(CORNER);
    textAlign(LEFT);
    textSize(appWidth * 0.05f);

    text(aboutText, appWidth * 0.1f, this.getY()  + (appHeight * -0.1f), appWidth * 0.8f, appHeight * 0.9f);

    // Checking if the page is being scrolled
    if (mousePressed) {

      // Calculating the amount scolled, based on the distance between the previous y position, 
      // and the current y position. When the mouse is first pressed, the previous y position
      // is initialised (in the main sketch) but then while the mouse is held down, the previous
      // y position gets updated each time this function runs (so that the scrolling can occur
      // while the person is still moving their hand (and not just after they release the screen)
      float amountScrolled = dist(0, previousMouseY, 0, mouseY);

      Icon[] icons = this.getScreenIcons();

      // Looping through each of the page icons, which are only being stored in an array within
      // this class so that they can be looped through to be repositioned (i.e. in every other
      // screen, these icons would be stored only in the super class, and not directly accessible
      // within the individual screen classes
      for (int i = 0; i < icons.length; i++) {
        // Checking which direction the user scrolled
        if (previousMouseY > mouseY) {
          // The user has scrolled UP
          // Setting the y position of the icon to it's current position, minus the amount scrolled i.e.
          // moving the icon up the screen
          icons[i].setY(icons[i].getY() - amountScrolled);
        } else {
          // The user has scrolled DOWN
          // Checking if the screen's y position is less than or equal to half of the height i.e. is 
          // so that the screen cannot be down any further once you reach the top
          if (this.getY() <= appHeight/2) {
            // Setting the y position of the icon to it's current position, plus the amount scrolled i.e.
            // moving the icon down the screen
            icons[i].setY(icons[i].getY() + amountScrolled);
          }
        }
      }

      // Checking which direction the user scrolled (the reason I have to do this seperatley from above is
      // that including these lines within the icons loop above makes these elements move faster than the
      // page icons
      if (previousMouseY > mouseY) {
        // The user has scrolled UP
        // Setting the screen's y postion to it's current y position, minus the amount scrolled
        this.setY(this.getY() - amountScrolled);
        // Setting the global positioning variable screenTitleY to be decremented by the amount scrolled. Note:
        // this variable gets reset everytime the page is changed (in the Icon class's checkMouseOver function, when
        // an icon's link is passed in to change a page)
        screenTitleY -= amountScrolled;
      } else {
        // The user has scrolled DOWN
        // Checking if the screen's y position is less than or equal to half of the height i.e. is 
        // so that the screen cannot be down any further once you reach the top
        if (this.getY() <= appHeight/2) {
          // Setting the screen's y postion to it's current y position, plus the amount scrolled
          this.setY(this.getY() + amountScrolled);
          // Setting the global positioning variable screenTitleY to be incremented by the amount scrolled. Note:
          // this variable gets reset everytime the page is changed (in the Icon class's checkMouseOver function, when
          // an icon's link is passed in to change a page)
          screenTitleY += amountScrolled;
        }
      }

      // Updating the previous mouse Y to be equal to the current mouse y, so that the next time this function is
      // called, the scrolling will be detected from this point i.e. so that scrolling appears continous, even if the
      // user keeps there finger/mouse held on the screen while moving up and down
      previousMouseY = mouseY;
    }
  }
}
public class CameraLiveViewScreen extends Screen {
  public Boolean favouriteLocation;

  // Creating a public constructor for the CameraLiveViewScreen class, so that
  // an instance of it can be declared in the main sketch
  public CameraLiveViewScreen() {
    // Calling the super class (Screen), which will in turn call it's super class 
    // (Rectangle) and create a rectangle with the default values i.e. fullscreen, 
    // centered etc.
    super();

    favouriteLocation = false;

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon homeIcon = new Icon(iconRightX, iconTopY, loadImage("homeIconWhiteImage.png"), "Home", false, "HomeScreen");
    Icon favIcon = new Icon(iconLeftX, iconTopY, favIconImage, "Add to Favourites", false, "_addToFavourites");
    Icon shakeIcon = new Icon(iconLeftX, iconBottomY, shakeIconImage, "Turn on/off Shake", false);
    Icon shutterIcon = new Icon(iconCenterX, iconBottomY, shutterIconImage, "Take a Picture", false, "_mergeImages");
    Icon switchViewIcon = new Icon(iconRightX, iconBottomY, switchViewIconImage, "Switch View", false, "_switchCameraView");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, favIcon, shakeIcon, shutterIcon, switchViewIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {
    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle(currentLocationName);
    
    // Checking if the mouse is pressed (i.e. the user wants to interact with the image)
    if (mousePressed) {
      // Calculating the amount scolled, based on the distance between the previous y position, 
      // and the current y position. When the mouse is first pressed, the previous y position
      // is initialised (in the main sketch) but then while the mouse is held down, the previous
      // y position gets updated each time this function runs (so that the scrolling can occur
      // while the person is still moving their hand (and not just after they release the screen)
      float amountScrolledX = dist(0, previousMouseX, 0, mouseX);
      float amountScrolledY = dist(0, previousMouseY, 0, mouseY);

      if (previousMouseX > mouseX) {
        // The user has scrolled RIGHT
        
        // Decrementing the googleImageHeading by the amount scrolled on the x axis. Using a ternary
        // operator to check that this will not result in a value less than 0 (the minimum
        // value allowed for the heading. If it does, then resetting the heading to 359 i.e. so the 
        // user can continue turn around in that direction, otherwise allowing it to equal to the
        // current heading value minus the amount scrolled on the X
        googleImageHeading = (googleImageHeading + amountScrolledX) > 359 ? 0 : googleImageHeading + amountScrolledX;
        println("scrolled right. heading is now " + googleImageHeading);
      } else {
        // The user has scrolled LEFT

        // Incrementing the googleImageHeading by the amount scrolled on the x axis. Using a ternary
        // operator to check that this will not result in a value greater than 359 (the maximum
        // value allowed for the heading. If it does, then resetting the heading to 0 i.e. so the 
        // user can continue turn around in that direction, otherwise allowing it to equal to the
        // current heading value plus the amount scrolled on the X
        googleImageHeading = (googleImageHeading - amountScrolledX) < 0 ? 359 : googleImageHeading - amountScrolledX;
        println("scrolled left. heading is now " + googleImageHeading);
      }
      
      println("amountScrolledY = " + amountScrolledY);
      if (previousMouseY > mouseY) {
        // The user has scrolled UP

        // Incrementing the googleImagePitch by the amount scrolled on the y axis. Using a ternary
        // operator to check that this will not result in a value greater than 90 (the maximum
        // value allowed for the pitch. If it does, then stopping the pitch at 90 i.e. so the 
        // user cannot excede the maximum value, otherwise allowing it to equal to the current pitch 
        // value plus the amount scrolled on the Y
        googleImagePitch = (googleImagePitch - amountScrolledY) < -90 ? -90 : googleImagePitch - amountScrolledY;
        println("scrolled up. pitch is now " + googleImagePitch);
      } else {
        // The user has scrolled DOWN
        
        // Decrementing the googleImagePitch by the amount scrolled on the y axis. Using a ternary
        // operator to check that this will not result in a value less than -90 (the minimum
        // value allowed for the pitch. If it does, then stopping the pitch at -90 i.e. so the 
        // user cannot excede the minimum value, otherwise allowing it to equal to the current pitch 
        // value minus the amount scrolled on the Y
        googleImagePitch = (googleImagePitch + amountScrolledY) > 90 ? 90 : googleImagePitch + amountScrolledY;
        println("scrolled down. pitch is now " + googleImagePitch);
      }

      loadGoogleImage();
      previousMouseX = mouseX;
      previousMouseY = mouseY;
    }

    if (compiledImage != null) {
      compiledImage = null;
    }
    // Using the currentLocationImage as the background for the camera live view i.e. so the user
    // can feel like they are taking a picture in that location
    image(currentLocationImage, appWidth/2, appHeight/2, appWidth, appHeight);

    // Calls super super class (Rectangle). Passing in the current frame image, the width and height
    // which have been reversed - i.e. the width will now be equal to the height of the app, as the 
    // ketaiCamera image requires it's rotation to be offset by 90 degress (either in the plus or the 
    // minus depending on whether you are using the front or rear camera) so the width and the height
    // need to swap to fit with the image's new resolution
    this.addBackgroundImage(currentImage, appHeight, appWidth, cameraScale, cameraRotation);
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }

  private void switchCameraView()
  {    
    callFunction = "";

    // If the camera is already running before we try and effect it
    if (ketaiCamera.isStarted())
    {
      // Checking if the device has more than one camera. If it does we want to toggle between them
      if (ketaiCamera.getNumberOfCameras() > 1)
      {
        // Ternary operator to toggle between cameras 1 & 0 (i.e. front and back)
        camNum = camNum == 0 ? 1 : 0;

        // Toggle the image rotation value between a plus and a minus i.e. -90 and 90
        cameraRotation *= -1;

        // Toggling the scale of the camera image between 1 and -1 (depending on if the camera
        // is front or rear facing (only on devices with more than one camera)
        cameraScale *= -1;

        // Stopping the ketaiCamera so that no new frames will be read in, switching to the camera specified
        // by the camNum, then restarting the camera
        ketaiCamera.stop();
        ketaiCamera.setCameraID(camNum);
        ketaiCamera.start();
      }
    }
  }
}
public class ClickableElement extends Rectangle {
  String elementTitle = "";

  /*-------------------------------------- Constructor() ------------------------------------------------*/

  // This constructor is used by icons
  public ClickableElement(float x, float y, float w, float h, PImage img, String title) {
    super(x, y, w, h, img);
    elementTitle = title;
  }

  // This constructor is used by textInputs
  public ClickableElement(float x, float y, float w, float h, int col, String title) {
    super(x, y, w, h, col);
    elementTitle = title;
  }

  /*-------------------------------------- checkMouseOver() ------------------------------------------------*/

  protected Boolean checkMouseOver() { 
    Boolean clickedOn = false;
    // Checking if the mouse (or finger) is over this icon (called by the Screen
    // class if a mouse event has occurred while this icon's screen is being 
    // displayed. Since the icons are drawn from the center, a bit of additional 
    // calculations are required to find out if the mouse was over them (i.e. 
    // to see if the mouseX was over this icon, we first have to take half 
    // the width from the x from the x postion, to get the furthest left point, 
    // and then add half of the width to the x position of the icon, to get 
    // it's furthest right point. The process is simular for determining the mouseY)
    if ((mouseX > (this.getX() - (this.getWidth()/2))) &&
      (mouseX < (this.getX() + (this.getWidth()/2))) &&
      (mouseY > (this.getY() - (this.getHeight()/2))) &&
      (mouseY < (this.getY() + (this.getHeight()/2)))) {

      // Logging out the name of the element that was clicked on
      println(this.elementTitle + " was clicked");

      // Setting mousePressed back to false, so that if the user still has their
      // mouse pressed after the screen changes, this will not be considered
      // a new click (as otherwise they could inadvertantly click on another button)
      mousePressed = false;

      clickedOn = true;
    }
    return clickedOn;
  }
}
public class FavouriteTab extends ClickableElement {  
  // Creating two private strings, one to hold the favourite title, and the other to hold
  // the location URL data of the favourite. The title will be used as text on the tab
  // to show what location it links to. The favLocation will be passing in as part of the
  // googleStreetViewImageApiURL in this class's showFavourite() method, if this tab
  // is clicked on  
  private String favTitle;
  private String favLocation;

  public FavouriteTab(String title, String location, float y) {
    // Calling the super class (ClickableElement) constructor, passing in the required
    // x value (which is centered), y value (which is incremented by this favourite's
    // position in the FavouriteScreen favourites array - plus 1 so that this value
    // never equals 0), setting a relative width and height, as well as a semi transparent
    // almost white colour (as pure white will not be shown - previous work around in the 
    // Rectangle class), and the title of the location (for printing to the console to 
    // let us know which tab was clicked on - if one has been clicked).
    super(iconCenterX, (y + 1) * appHeight * 0.25f, appWidth * 0.7f, appHeight * 0.2f, color(255, 255, 249, 149), title);

    // Initialising the two private strings, one to hold the favourite title, and the other to hold
    // the location URL data of the favourite. The title will be used as text on the tab
    // to show what location it links to. The favLocation will be passing in as part of the
    // googleStreetViewImageApiURL in this class's showFavourite() method, if this tab
    // is clicked on 
    favTitle = title;
    favLocation = location;
  }

  public void showFavourite() {
    // Showing this tab (Using the super Rectangle class's method show())
    this.show();

    // Adding the title text to this tab (as specified in the tab's constructor)
    this.addText(favTitle, this.getX(), this.getY(), this.getWidth() * 0.1f);

    // If the mouse is currently pressed, checking if the mouse was over this
    // tab when the press/click occurred
    if (mousePressed) {
      // Checking the mouse was over this by using the super class ClickableElement's
      // checkMouseOver() method
      if (this.checkMouseOver()) {
        
        googleImageLatLng = this.favLocation.split("&")[0]; 
        googleImageHeading = PApplet.parseFloat(this.favLocation.split("heading=")[1].split("&")[0]);
        googleImagePitch = PApplet.parseFloat(this.favLocation.split("pitch=")[1]);
        
        loadGoogleImage();
      }
    }
  }
}
public class FavouritesScreen extends Screen {
  // Creating a private array of all the favourite locations we want to display
  // in the favourites screen. The data before the "@" represents the location's title
  // while the data after is used to generate a URL to request this location's Google
  // Street View Image (in this class's showScreen() method). (Note - this is a naming
  // convention that we have created, so that the favourite title and location details
  // can all be stored in the one string). The values from these strings will be split and
  // passed into the relevant constructor of each favTab (in the constructor of this class).
  // The first two numbers after the "@" represent the latitude and longitude of the location
  // The heading represents the left/right positioning of the view (between 0 and 360)
  // The pitch represents the up/down angle of the view (between -90 and 90)
  // In the original Google Street View URL (from the browser) i.e. the Colosseum 
  // url was https://www.google.ie/maps/@41.8902646,12.4905161,3a,75y,90.81h,95.88t/data=!3m6!1e1!3m4!1sR8bILL5qdsO7_m5BHNdSvQ!2e0!7i13312!8i6656!6m1!1e1
  // the first two numbers after the @ represent the latitude and longitude, the number
  // with the h after it represents the heading, and the number with the t after it
  // seems to be to do with the pitch, but never works that way in this
  // method so I just decided the pitch value based on what looks good
  private String[] favourites = {
    "Pyramids Of Giza@29.9752572,31.1387288&heading=292.67&pitch=-0.76",
    "Eiffel Tower@48.8568402,2.2967311&heading=314.59&pitch=20",
    "Colosseum@41.8902646,12.4905161&heading=80&pitch=10",
    "Taj Mahal@27.1738903,78.0419927&heading=10&&pitch=10",
    "Big Ben@51.500381,-0.1262538&heading=105&pitch=10",
    "Leaning Tower Of Piza@43.7224555,10.3960728&heading=54.4&pitch=10",
    "TimesSquare@40.7585806,-73.9850687&heading=30&pitch=20"
  };
  
  public Boolean loaded = false;
  
  // Declaring a private favTabs array, to store each of the favourite tabs we create,
  // so that they can be looped through in this class's showScreen() method, to display
  // the tabs, as well as checking if they are being clicked on.
  private FavouriteTab[] favTabs;

  // Creating a public constructor for the FavouriteScreen class, so that
  // an instance of it can be declared in the main sketch
  public FavouritesScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);
    
    // Creating the favTabs array to be long enough to contain each of the favourite
    // places we have declared in the favourites array above i.e. so that we will have
    // enough tabs to display each of the favourites
    favTabs = new FavouriteTab[favourites.length];
    
    // Looping through the favourites array, so that we can create a new tab for
    // each favourite place
    for (int f = 0; f < favourites.length; f++) {
      
      // Getting the title of the favourite location by splitting the value stored for
      // the favourite at the "@" symbol (Note - this is a naming convention that we have
      // created, so that the favourite title and location details can all be stored in
      // the one string). Once the string has been split, it results in a new array that
      // contains the two half's, the first being the title of the favourite, so in this
      // instance we want to get the portion of the string at index 0
      String favTitle = split(favourites[f], "@")[0];
      
      // As with the favTitle, we are splitting the favourite string at the "@", except
      // this time it is the portion of the string at index 1 we want (i.e. the second
      // half of the string which contains the URL data we require to request this
      // specific location (longitude, latitude, heading and pitch). 
      String favLocation = split(favourites[f], "@")[1];

      // Creating a temporary variable to hold the new Favourite Tab, passing in the title
      // and location URL data for the current favourite, as well as the increment variable,
      // which will be used to space out the favourite tabs on the page within the constructor
      // of the FavouriteTab class (as it passes the y value to the super class, it multiplies
      // the y value by this number, so that it increases with each tab i.e. they are spaced
      // vertically down along the screen)
      FavouriteTab newFavTab = new FavouriteTab(favTitle, favLocation, f);
      
      // Adding the new FavTab to this class's favTabs array, so that we can loop through them
      // in the showScreen() method, to display them, as well as checking if they are being
      // clicked on.
      favTabs[f] = newFavTab;
    }

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon homeIcon = new Icon(iconRightX, iconTopY, homeIconImage, "Home", false, "HomeScreen");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("My Favourites");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {
    
    if (!this.loaded) {
      // Resetting the position values of the element so on the screen every time the page is opened,
      // so that if a user leaves the screen half scrolled, it will still be reset upon their return
      this.setY(appHeight/2);
      this.getScreenIcons()[0].setY(iconTopY);
      
      for (int i = 0; i < favTabs.length; i++) {
        favTabs[i].setY((i + 1) * appHeight * 0.25f);
      }

      // Setting loaded to true, so that this block of code will only run once (each time this page
      // is opened). This value will be reset to false in the Icon class's checkMouseOver function,
      // when an icon that links to another page has been clicked.
      this.loaded = true;
      println("firstLoad");
    }

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    // Looping through our array of favourite tabs, and calling the showFavourite() method (of
    // the FavouriteTab class) to display the tab on screen
    for (int f = 0; f < favourites.length; f++) {
      favTabs[f].showFavourite();
    }

    if (mousePressed) {
      // Calculating the amount scolled, based on the distance between the previous y position, 
      // and the current y position. When the mouse is first pressed, the previous y position
      // is initialised (in the main sketch) but then while the mouse is held down, the previous
      // y position gets updated each time this function runs (so that the scrolling can occur
      // while the person is still moving their hand (and not just after they release the screen)
      float amountScrolled = dist(0, previousMouseY, 0, mouseY);

      Icon[] icons = this.getScreenIcons();

      // Looping through each of the page icons, which are only being stored in an array within
      // this class so that they can be looped through to be repositioned (i.e. in every other
      // screen, these icons would be stored only in the super class, and not directly accessible
      // within the individual screen classes
      for (int i = 0; i < icons.length; i++) {
        // Checking which direction the user scrolled
        if (previousMouseY > mouseY) {
          // The user has scrolled UP
          // Setting the y position of the icon to it's current position, minus the amount scrolled i.e.
          // moving the icon up the screen
          icons[i].setY(icons[i].getY() - amountScrolled);
        } else {
          // The user has scrolled DOWN
          // Checking if the screen's y position is less than or equal to half of the height i.e. is 
          // so that the screen cannot be down any further once you reach the top
          if (this.getY() <= appHeight/2) {
            // Setting the y position of the icon to it's current position, plus the amount scrolled i.e.
            // moving the icon down the screen
            icons[i].setY(icons[i].getY() + amountScrolled);
          }
        }
      }

      // Looping through each of the page icons, which are only being stored in an array within
      // this class so that they can be looped through to be repositioned (i.e. in every other
      // screen, these icons would be stored only in the super class, and not directly accessible
      // within the individual screen classes
      for (int i = 0; i < favTabs.length; i++) {
        // Checking which direction the user scrolled
        if (previousMouseY > mouseY) {
          // The user has scrolled UP
          // Setting the y position of the icon to it's current position, minus the amount scrolled i.e.
          // moving the icon up the screen
          favTabs[i].setY(favTabs[i].getY() - amountScrolled);
        } else {
          // The user has scrolled DOWN
          // Checking if the screen's y position is less than or equal to half of the height i.e. is 
          // so that the screen cannot be down any further once you reach the top
          if (this.getY() <= appHeight/2) {
            // Setting the y position of the icon to it's current position, plus the amount scrolled i.e.
            // moving the icon down the screen
            favTabs[i].setY(favTabs[i].getY() + amountScrolled);
          }
        }
      }

      // Checking which direction the user scrolled (the reason I have to do this seperatley from above is
      // that including these lines within the icons loop above makes these elements move faster than the
      // page icons
      if (previousMouseY > mouseY) {
        // The user has scrolled UP
        // Setting the screen's y postion to it's current y position, minus the amount scrolled
        this.setY(this.getY() - amountScrolled);
        // Setting the global positioning variable screenTitleY to be decremented by the amount scrolled. Note:
        // this variable gets reset everytime the page is changed (in the Icon class's checkMouseOver function, when
        // an icon's link is passed in to change a page)
        screenTitleY -= amountScrolled;
      } else {
        // The user has scrolled DOWN
        // Checking if the screen's y position is less than or equal to half of the height i.e. is 
        // so that the screen cannot be down any further once you reach the top
        if (this.getY() <= appHeight/2) {
          // Setting the screen's y postion to it's current y position, plus the amount scrolled
          this.setY(this.getY() + amountScrolled);
          // Setting the global positioning variable screenTitleY to be incremented by the amount scrolled. Note:
          // this variable gets reset everytime the page is changed (in the Icon class's checkMouseOver function, when
          // an icon's link is passed in to change a page)
          screenTitleY += amountScrolled;
        }
      }

      // Updating the previous mouse Y to be equal to the current mouse y, so that the next time this function is
      // called, the scrolling will be detected from this point i.e. so that scrolling appears continous, even if the
      // user keeps there finger/mouse held on the screen while moving up and down
      previousMouseY = mouseY;
    }
  }
  
  public String getRandomLocation(){
    // Splitting the favourite string at the "@". Getting data at index 1 of the new split
    // string array (i.e. the second half of the string which contains the URL data we 
    // require to request this specific location (longitude, latitude, heading and pitch). 
    // Getting a random location by generating a random index value within the length of the
    // favourites array (rounding it off so that it will always equal an int)
    String locationURLData = favourites[round(random(favourites.length-1))];
    return locationURLData;
  }
}
public class HomeScreen extends Screen {

  // Creating a public constructor for the HomeScreen class, so that
  // an instance of it can be declared in the main sketch
  public HomeScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    float iconSize = largeIconSize * 1.3f;

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon searchTravelIcon = new Icon(appWidth * 0.28f, appHeight * 0.2f, iconSize, iconSize, searchPageIconImage, "Search", true, "Below", "SearchScreen");
    Icon randomTravelIcon = new Icon(appWidth * 0.72f, appHeight * 0.2f, iconSize, iconSize, randomPageIconImage, "Random", true, "Below", "_getRandomLocation");
    Icon myFavouritesIcon = new Icon(appWidth * 0.28f, appHeight * 0.5f, iconSize, iconSize, favouritesPageIconImage, "My Favourites", true, "Below", "FavouritesScreen");
    Icon aboutIcon = new Icon(appWidth * 0.72f, appHeight * 0.5f, iconSize, iconSize, aboutPageIconImage, "About", true, "Below", "AboutScreen");
    Icon settingsIcon = new Icon(appWidth * 0.5f, appHeight * 0.8f, iconSize, iconSize, settingsPageIconImage, "Settings", true, "Below", "SettingsScreen");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {randomTravelIcon, searchTravelIcon, myFavouritesIcon, aboutIcon, settingsIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class Icon extends ClickableElement {

  // Creating private variables to store the icon's link, title and show title
  // properties, so that they can only be accessed within this class
  private String iconLinkTo;
  private String iconTitle;
  private Boolean showIconTitle;
  private String iconTitlePosition;

  /*-------------------------------------- Constructor() ------------------------------------------------*/

  // This constructor is used by icons in the CameraLiveView Screen, that want to accept the 
  // default width and height of an icon, but do not link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle) {

    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, smallIconSize, smallIconSize, img, title, showTitle, "Middle", "");
  }

  // This constructor is used by icons such as the homeIcon, that want to accept the default 
  // width and height of an icon, and also link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle, String linkTo) {

    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, smallIconSize, smallIconSize, img, title, showTitle, "Middle", linkTo);
  }  

  // This constructor is used by icons such as the homeIcon, that want to accept the default 
  // width and height of an icon, and also link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle, String titlePosition, String linkTo) {

    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, smallIconSize, smallIconSize, img, title, showTitle, titlePosition, linkTo);
  }   
  
  // Partial Constructor
  public Icon(float x, float y, float w, float h, PImage img, String title, Boolean showTitle, String linkTo){
    this(x, y, w, h, img, title, showTitle, "", linkTo);
  }

  // Full Constructor. Both of the above constructors both pass their values to this constructor, as
  // well as other icon's in the app that want to pass arguments for all of the specified values
  public Icon(float x, float y, float w, float h, PImage img, String title, Boolean showTitle, String titlePosition, String linkTo) {

    // Passing the relevant parametres from the constructor into the constructor 
    // of the super class (Rectangle)
    super(x, y, w, h, img, title);

    // Initialising the iconLinkTo to be equal to the requested link
    // specified in the object's constructor. This link will be passed to the global
    // currentScreen variable if the link is clicked on, so that in the draw function,
    // we can determine which page to display
    iconLinkTo = linkTo;

    // Initialising the iconTitle to be equal to the requested title.
    // If no title was provided, then an empty string will have been passed
    // in, so we will temporarily store this and figure out later (in the showIcon
    // function) whether or not to display the text
    iconTitle = title;

    // Initialising the showIconTitle boolean to be equal to the specified value, which
    // will be used later to decide if this page's title will be displayed as a heading
    // on the page or not
    showIconTitle = showTitle;

    iconTitlePosition = titlePosition;
  }

  /*-------------------------------------- showIcon() ------------------------------------------------*/

  // Creating a public showIcon function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  public void showIcon() {
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();

    // Checking if this icon's title should be displayed as a header on the screen
    if (showIconTitle)
    {
      // Calling the super class's (Rectangle) addText method, to add the title to
      // the icon. Passing in the String containing the title for the icon, the current
      // x and y positions of the icon itself, and the font size (which is relative
      // to the icon's current height
      if (iconTitlePosition == "Middle") {
        this.addText(this.iconTitle, this.getX(), this.getY(), this.getWidth() * 0.20f);
      } else if (iconTitlePosition == "Below") {
        this.addText(this.iconTitle, this.getX(), this.getY() + (this.getHeight()*0.6f), this.getWidth() * 0.20f);
      }
    }
    if (mousePressed) {
      if (this.checkMouseOver()) {
        // Checking if this icon has a link associated with it
        if (this.iconLinkTo.length() > 0)
        {
          // Checking if the iconTitle contains "http" i.e. if it is an external link
          if (this.iconLinkTo.indexOf("http") > -1)
          {
            // This is an EXTERNAL link
            // Passing the icon's link into the link() method, so that it can be treated as 
            // an external link i.e. to a website
            link(this.iconLinkTo);

            // Logging out what site the app will now be taken to
            println("Going to " + this.iconLinkTo);
          } else if (this.iconLinkTo.indexOf("_") == 0) {
            callFunction = this.iconLinkTo;
            if (this.iconLinkTo.equals("_addToFavourites")) {
              if (myCameraLiveViewScreen.favouriteLocation) {
                this.setImage(loadImage("favIconNoImage.png"));
              } else {
                this.setImage(loadImage("favIconYesImage.png"));
              }
            }
            else if(this.iconLinkTo.equals("_switchLearningMode")){
              if(mySettingsScreen.learningModeOn){
                this.setImage(toggleSwitchOffIconImage);
              }
              else{
                this.setImage(toggleSwitchOnIconImage);
              }
            }else if(this.iconLinkTo.equals("_switchAutoSave")){
              if(mySettingsScreen.autoSaveModeOn){
                this.setImage(toggleSwitchOffIconImage);
              }
              else{
                this.setImage(toggleSwitchOnIconImage);
              }
            }

            // Logging out what page the app will now be taken to
            println("Calling the " + this.iconLinkTo + "() function");
          } else if (this.iconLinkTo.indexOf("-") == 0) {
            currentScreen = returnTo;
            // Logging out what page the app will now be taken to
            println("Returning to the " + this.iconLinkTo + "screen");
          } else {
            if(this.iconTitle.indexOf("Random") == 0){
            }
            // This is an INTERNAL link
            // Setting the global currentScreen variable to be equal to the link
            // contained within the icon that was clicked on (so it can be used
            // in the main sketch to determine which page to display)
            currentScreen = this.iconLinkTo;

            // Resets required for the About Screen.
            // Resetting teh screenTitleY position to it's original value (as it may have been
            // incremented if the about screen was scrolled
            screenTitleY = appHeight * 0.08f;
            // Resetting the about screen's loaded value to false, so that the next time it is opened
            // it will reset to it's original positions
            myAboutScreen.loaded = false;
            myFavouritesScreen.loaded = false;
            
            // Logging out what page the app will now be taken to
            println("Going to the " + this.iconLinkTo);
          }
        }
      }
    }
  }
}
public class ImagePreviewScreen extends Screen {

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public ImagePreviewScreen() {

    // Calling the super class (Screen), which will in turn call it's super class 
    // (Rectangle) and create a rectangle with the default values i.e. fullscreen, 
    // centered etc.
    super();

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon disgardIcon = new Icon(iconLeftX, iconBottomY, disgardIconImage, "Disgard Image", false, "_disgardImage");
    Icon keepIcon = new Icon(iconRightX, iconBottomY, keepIconImage, "Keep Image", false, "_keepImage");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {disgardIcon, keepIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {
    image(compiledImage, appWidth/2, appHeight/2, appWidth, appHeight);
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class LoadingScreen extends Screen {

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public LoadingScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
protected class Rectangle {

  // Creating private variables to store the properties of each object.
  // These variables are private so that they can only be accessed within
  // this class i.e. must be set through the constructor of the object, or
  // retrieved using the relevant get methods of this class
  private float rectX;
  private float rectY;
  private float rectWidth;
  private float rectHeight;
  private int rectCol = 0xffFFFFFF;
  private float rectRotation;
  private PImage rectImage = null;
  public PImage rectBackgroundImg = null;
  private int rectBackgroundImgScaleX = 1;
  private int rectBackgroundImgRotate = 0;

  /*-------------------------------------- Constructor() ------------------------------------------------*/

  // This constructor is used by screens that want to accept all the defaults
  protected Rectangle() {
    // If no x, y, or rotation are passed in, defaulting these to half of the sketch's
    // width and height (i.e. so that the rectangle will appear centered). If no width
    // and height are passed in, defaulting these to the current width and height of
    // the sketch. If no rotation value is specified, defaulting this value to 0, and 
    // finally, if no color is specified, defaulting this to white
    this(appWidth/2, appHeight/2, appWidth, appHeight, 0xffFFFFFF, null);
  }

  // This constructor is used by screens that want to accept all the defaults, as well
  // as setting a background image
  protected Rectangle(PImage img) {
    // If no x, y passed in, defaulting these to half of the sketch's width and height 
    // (i.e. so that the rectangle will appear centered). If no width and height are 
    // passed in, defaulting these to the current width and height of the sketch. If 
    // no color is specified, defaulting this to white. Passing these default values, 
    // along with the image that was passed in, to the main constructor of this class
    this(appWidth/2, appHeight/2, appWidth, appHeight, 0xffFFFFFF, img);
  }

  // This constructor is used by screens that want to accept all the defaults, as well
  // as setting a background color
  protected Rectangle(int col) {
    // If no x, y passed in, defaulting these to half of the sketch's width and height 
    // (i.e. so that the rectangle will appear centered). If no width and height are 
    // passed in, defaulting these to the current width and height of the sketch. If 
    // no image is specified, defaulting this null. Passing these default values, along 
    // with the color that was passed in, to the main constructor of this class
    this(appWidth/2, appHeight/2, appWidth, appHeight, col, null);
  }

  // This constructor is used by icons that do not link to anything, and that
  // want to have an image as their background
  protected Rectangle(float x, float y, float w, float h, PImage img) {
    // If no color passed in, defaulting it to white and then passing this default value,
    // along with the x, y, width, height and image that was passed in, to the main 
    // constructor of this class
    this(x, y, w, h, 0xffFFFFFF, img);
  }

  // This constructor is used by text input boxes (TEMPORARILY)
  protected Rectangle(float x, float y, float w, float h, int col) {
    this(x, y, w, h, col, null);
  }

  // This is the main constructor of this class, to which all other constructors pass
  // their values to be stored as the instance's properties
  protected Rectangle(float x, float y, float w, float h, int col, PImage img) {
    // Storing the values that are passed into the constructor in the private
    // variables of this class, so that they can be accessed by other functions
    // within this class, but not from anywhere outside of this class. Defaulting
    // the rotation of the instance to 0 (as it can be changed later using the
    // setRotation() method
    rectX = x;
    rectY = y;
    rectWidth = w;
    rectHeight = h;
    rectCol = col;
    rectImage = img;
  }

  /*-------------------------------------- show() ------------------------------------------------*/

  // Creating a method to redraw the object or "show" it on the screen (i.e so that only 
  // descendants of this class can access
  protected void show() {

    if (rectCol != 0xffFFFFFF) {
      // Storing the current state of the matrix
      pushMatrix();

      // Translating the position of the matrix to the specified x and y of the object
      translate(rectX, rectY);

      // Rotating the matrix by the specified rotation value of the object (which has been
      // stored as a radian value)
      rotate(rectRotation);
      // Setting the fill colour of the object to the value specified
      fill(rectCol);

      noStroke();

      // Setting the drawing mode of the rectangle to be centered. This way, if a rotation has
      // been applied to the rectangle, it will pivot around it's center point
      rectMode(CENTER);

      // Drawing the rectangle with x and y values based on half of the width and height of
      // the object, so that it appears centered on it's point of origin. The actual position
      // on the screen will depend on the matrix's translation, as this will control where 
      // the object is drawn
      rect(0, 0, rectWidth, rectHeight);

      // Restoring the matrix to it's previous state
      popMatrix();
    }

    // Checking if a Background Image has been passed in
    if (rectBackgroundImg != null) {    
      // Calling the addImage() method of the this class, to add the background image to the screen,
      // passing in the image, along with the current x, y, width and height of the instance,
      // so that the image will appear the full size of the object
      this.addImage(rectBackgroundImg, rectX, rectY, rectWidth, rectHeight, rectBackgroundImgScaleX, rectBackgroundImgRotate);
    }

    // Checking if an image has been passed in
    if (rectImage != null) {    
      // Calling the addImage() method of the this class, to add the image to the screen,
      // passing in the image, along with the current x, y, width and height of the instance,
      // so that the image will appear the full size of the object
      this.addImage(rectImage, rectX, rectY, rectWidth, rectHeight);
    }
  }

  /*-------------------------------------- addText() ------------------------------------------------*/

  // Partial addText() method that adds text to the object using the default text size
  protected void addText(String text, float textX, float textY) {
    // If no alignment specified, defaulting it to center on the x-axis. If no
    // text size specified, defaulting it to the defaultTextSize variable (as
    // defined in the main sketch. Passing these default values, along with the
    // specified text, x and y to the main addText() method
    this.addText(text, "CENTER", textX, textY, defaultTextSize);
  }

  // Partial addText() method that adds text to the object, using the default text size, and
  // changing the alignment on the x-axis to a specified alignment (as it will
  // default to CENTER otherwise
  protected void addText(String text, String align, float textX, float textY) {
    // If no text size specified, defaulting it to the defaultTextSize variable
    // (as defined in the main sketch. Passing these default values, along with
    // the specified text, alignment, x and y to the main addText() method
    this.addText(text, align, textX, textY, defaultTextSize);
  }

  // Partial addText() method that adds text to the object, with a specific text size
  protected void addText(String text, float textX, float textY, float textSize) {
    // If no alignment specified, defaulting it to center on the x-axis. Passing
    // this default value, along with the specified text, x, y and text size to
    // the main addText() method
    this.addText(text, "CENTER", textX, textY, textSize);
  }

  // Full addText() method, which takes in the values specified (some of which may have
  // been defaulted by the partial addText() methods above)
  protected void addText(String text, String align, float textX, float textY, float textSize) {
    // Storing the current state of the matrix
    pushMatrix();

    // Translating the position of the matrix be equal to the x and y positions
    // passed into the function
    translate(textX, textY);

    // Rotating the matrix by the currnet rotation value of this object (which has been
    // stored as a radian value)
    rotate(this.getRotation());

    if (align.equals("LEFT")) {
      // Setting the text align to Left on the x axis, and Center on the y so that
      // the text will be drawn from the center point of it's position on the left of
      // the page
      textAlign(LEFT, CENTER);
    } else if (align.equals("LEFT-TOP")) {
      // Setting the text align to center (on both the x and the y) so that
      // the text will be drawn from the center point of it's position on
      // the page
      textAlign(LEFT, TOP);
    } else {
      // Setting the text align to center (on both the x and the y) so that
      // the text will be drawn from the center point of it's position on
      // the page
      textAlign(CENTER, CENTER);
    }

    // Setting the text size to be responsive to the height of the app
    textSize(textSize);

    // Setting the fill color for the text to black
    fill(0);

    // Adding the text to the screen, setting the x and y positions to 0, 
    // as the actual position on the screen will depend on the matrix's translation,
    // as this will control where the text is drawn
    text(text, 0, 0);

    // Restoring the matrix to it's previous state
    popMatrix();
  }

  /*-------------------------------------- addImage() ------------------------------------------------*/

  // Partial addImage() method which is used by images that need to be displayed
  // centered in full resolution of the screen
  protected void addImage(PImage img, int scaleX, int rotate) {
    // If no x, y, width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values to the full addImage() method
    this.addImage(img, appWidth/2, appHeight/2, img.width, img.height, scaleX, rotate);
  }

  // Partial addImage() method which is used by images that need to be displayed
  // at their default resolution
  protected void addImage(PImage img, float imgX, float imgY) {
    // If no image width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values, along with the image, x and y to the full addImage() method
    this.addImage(img, imgX, imgY, img.width, img.height, 1, (int)this.getRotation());
  }

  // Partial addImage() method which is used by images that require a specific width and height
  // (Some of these values may have been defaulted by the partial addImage() method)
  protected void addImage(PImage img, float imgX, float imgY, float imgWidth, float imgHeight) {
    // If no image width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values, along with the image, x and y to the full addImage() method
    this.addImage(img, imgX, imgY, imgWidth, imgHeight, 1, (int)this.getRotation());
  }

  // Full addImage() method which is used by images that require a specific width and height
  // (Some of these values may have been defaulted by the partial addImage() method)
  protected void addImage(PImage img, float imgX, float imgY, float imgWidth, float imgHeight, int scaleX, int rotate) {
    // Storing the current state of the matrix
    pushMatrix();

    // Translating the position of the matrix be equal to the x and y positions
    // passed into the function
    translate(imgX, imgY);

    // Flipping the image so that it better represents the camera it is using i.e.
    // on front facing cameras, the image will be flipped horizontally, so that things
    // don't appear in reverse.
    scale(scaleX, 1);

    // Rotating the matrix by the current rotation value of this screen (which has been
    // stored as a radian value)
    rotate(radians(rotate));

    // Setting the imageMode to center so that the image will be drawn from the center 
    // point of it's position on the page
    imageMode(CENTER);

    // Adding the image to the screen, setting the x and y positions to 0, 
    // as the actual position on the screen will depend on the matrix's translation,
    // as this will control where the text is drawn. Setting the width and height of the image
    // to be equal to the values passed into the function
    image(img, 0, 0, imgWidth, imgHeight);

    // Restoring the matrix to it's previous state
    popMatrix();
  }  

  /*-------------------------------------- get() and set() ------------------------------------------------*/

  // Get method that returns the instance's x position
  protected float getX() {
    return rectX;
  }

  // Get method that returns the instance's y position
  protected float getY() {
    return rectY;
  }

  protected void setY(float y) {
    //if((y < appHeight/2) && (y > -appHeight * 0.75)){
    rectY = y;
    //}
  }

  // Get method that returns the instance's width
  protected float getWidth() {
    return rectWidth;
  }

  // Get method that returns the instance's height
  protected float getHeight() {
    return rectHeight;
  }

  // Get method that returns the instance's rotation
  protected float getRotation() {
    return rectRotation;
  }

  protected void setImage(PImage img) {
    if (img.width == this.rectImage.width) {
      rectImage = img;
    }
  }

  // Set method that sets the rotation of instance
  protected void setRotation(int r) {
    rectRotation = radians(r);
  }

  protected void setBackgroundImgScaleX(int sX) {
    if (sX == 1 || sX == -1) {
      rectBackgroundImgScaleX = sX;
    }
  }

  protected void addBackgroundImage(PImage bgImg, float w, float h, int scaleX, int rotate) {
    rectBackgroundImg = bgImg;
    rectWidth = w;
    rectHeight = h;
    rectBackgroundImgScaleX =  scaleX;
    rectBackgroundImgRotate = rotate;
  }

  public PImage getBackgroundImage() {
    return rectBackgroundImg;
  }
}
public class SaveShareScreenA extends Screen {

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public SaveShareScreenA(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon instagramIcon = new Icon(iconCenterX * 0.55f, iconCenterY, largeIconSize, largeIconSize, instagramAccountIconImage, "Instagram", true, "Below", "SocialMediaLoginScreen");
    Icon twitterIcon = new Icon(iconCenterX * 1.45f, iconCenterY, largeIconSize, largeIconSize, twitterAccountIconImage, "Twitter", true, "Below", "SocialMediaLoginScreen");
    Icon saveIcon = new Icon(iconCenterX, iconCenterY * 1.5f, largeIconSize, largeIconSize, saveIconImage, "Save", true, "Below", "_keepImage");
    Icon cancelIcon = new Icon(appWidth * 0.3f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Cancel", true, "Middle", "CameraLiveViewScreen");
    Icon nextIcon = new Icon(appWidth * 0.7f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Next", true, "Middle", "SaveShareScreenB");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {instagramIcon, twitterIcon, saveIcon, cancelIcon, nextIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Save Share Screen A");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addImage(compiledImage, iconCenterX, iconCenterY * 0.5f, appWidth * 0.3f, appHeight * 0.3f);
  }
}
public class SaveShareScreenB extends Screen {
  public TextInput messageInput;

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public SaveShareScreenB(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    messageInput = new TextInput(iconCenterX, iconCenterY * 0.98f, appWidth * 0.8f, appHeight * 0.2f, 0xffFFFFFE, "messageInput", "LEFT-TOP");

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon cancelIcon = new Icon(appWidth * 0.3f, iconCenterY * 1.3f, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Cancel", true, "Middle", "SaveShareScreenA");
    //Icon shareIcon = new Icon(appWidth * 0.7, iconCenterY * 1.3, appWidth * 0.4, appHeight * 0.08, buttonImage, "Share", true, "Middle", "SharingScreen");
    Icon shareIcon = new Icon(appWidth * 0.7f, iconCenterY * 1.3f, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Share", true, "Middle", "_sendTweet");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {cancelIcon, shareIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Save Share Screen B");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addImage(compiledImage, iconCenterX, iconCenterY * 0.4f, appWidth * 0.8f, appWidth * 0.6f);

    messageInput.showTextInput();
  }
}
protected class Screen extends Rectangle {

  // Declaring the screenIcons array, which will be used to store the 
  // icons from each screen. This array will be initialised through the
  // setScreenIcons() method, where each screen will pass in an array
  // or the icons that are present on it's screen. The purpose of storing
  // this array within the Screen class, as opposed to in each instance's
  // own class, is that now we can deal with looping through, and calling
  // methods on multiple icons of a screen only when their screen is being
  // shown i.e. when showScreen() is being called
  private Icon[] screenIcons = {};

  // Creating and initialising the screenTitle property of the screen to be
  // equal to an empty string. Each instance of the Screen class can then
  // use the setScreenTitle() method to specify a custom title, which
  // will then be used as the header text on that instance of the screen.
  // If no title is specified, then header text will not be added to the 
  // screen
  public String screenTitle = "";

  // Creating protected constructors for the Screen class, so that they can
  // only be accessed by classes which extend from this class

  /*-------------------------------------- Constructor() ------------------------------------------------*/

  protected Screen() {    
    // Calling this class's super class (Screen) to create a screen using
    // the default settings
    super();
  }

  protected Screen(PImage img) {    
    // Calling this class's super class (Screen) to create a screen using
    // the default settings along with a background image
    super(img);
  }

  protected Screen(int col) {
    // Calling this class's super class (Screen) to create a screen using
    // the default settings, along with setting the colour as specified
    super(col);
  }

  protected Screen(float x, float y, float w, float h, PImage img) {
    super(x, y, w, h, img);
  }


  protected Screen(float x, float y, float w, float h, int col) {
    super(x, y, w, h, col);
  }

  /*-------------------------------------- drawScreen() ------------------------------------------------*/

  // Creating a public method so that this screen can be displayed from
  // within the main sketch
  public void drawScreen() {    
    // Calling the show() method (as inherited from the Rectangle class)
    // to display this screen's background
    this.show();

    // Checking if this screen has been given a title (i.e. if the contents
    // of the screenTitle is at least one character long
    if (screenTitle.length() > 0)
    {
      fill(0);
      this.addText(screenTitle, appWidth/2, screenTitleY);
    }

    // Checking if this screen has any icons to be displayed
    if (screenIcons.length > 0)
    {
      // Looping through each of the screen's icons
      for (int i = 0; i < screenIcons.length; i++) {
        // Calling the showIcon() method (as inherited from the Icon class)
        // to display the icon on screen
        screenIcons[i].showIcon();

        // Checking if the mouse is currently pressed i.e. if a click has
        // been detected
        if (mousePressed)
        {
          // Calling the checkMouseOver() method (as inherited from the Icon
          // class, to see if the mouse was over this icon when it was clicked
          screenIcons[i].checkMouseOver();
        }
      }
    }
  }

  /*-------------------------------------- get() and set() ------------------------------------------------*/

  protected void setScreenTitle(String title) {
    // Setting the screenTitle to the title passed in by each screen. If no
    // title is passed, this variable has already been initialised to an 
    // empty string above
    screenTitle = title;
  }

  protected Icon[] getScreenIcons() {
    return screenIcons;
  }

  protected void setScreenIcons(Icon[] icons) {
    // Initialising the screenIcons array with the contents from the allIcons
    // array that each screen will pass in
    screenIcons = icons;
  }
}
public class SearchScreen extends Screen {
  private TextInput searchInput;
  // Creating a public constructor for the SearchTravelScreen class, so that
  // an instance of it can be declared in the main sketch
  public SearchScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    searchInput = new TextInput(iconCenterX, iconCenterY * 0.7f, appWidth * 0.8f, appHeight * 0.2f, 0xffFFFFFE, "searchInput", "LEFT-TOP");

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional

    Icon homeIcon = new Icon(iconRightX, iconTopY, homeIconImage, "Home", false, "HomeScreen");
    Icon cancelIcon = new Icon(appWidth * 0.3f, iconCenterY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Cancel", true, "Middle", "HomeScreen");
    Icon searchIcon = new Icon(appWidth * 0.7f, iconCenterY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Search", true, "Middle", "_searchForLocation");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, searchIcon, cancelIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Search");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    searchInput.showTextInput();
  }
}
public class SearchUnsuccessfulScreen extends Screen {

  // Creating a public constructor for the SearchFailedScreen class, so that
  // an instance of it can be declared in the main sketch
  public SearchUnsuccessfulScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon homeIcon = new Icon(iconRightX, iconTopY, homeIconImage, "Home", false, "HomeScreen");
    Icon searchTravelIcon = new Icon(appWidth * 0.3f, largeIconBottomY, largeIconSize, largeIconSize, searchPageIconImage, "Search Again", true, "Below", "SearchScreen");
    Icon randomTravelIcon = new Icon(appWidth * 0.7f, largeIconBottomY, largeIconSize, largeIconSize, randomPageIconImage, "Random", true, "Below", "_getRandomLocation");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, searchTravelIcon, randomTravelIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addText("We're sorry :(", iconCenterX, appHeight * 0.1f);
    this.addText("We could not", iconCenterX, appHeight * 0.18f);
    this.addText("find what you", iconCenterX, appHeight * 0.26f);
    this.addText("were looking for...", iconCenterX, appHeight * 0.34f);

    // Calling the super class's super class (Rectangle) to add an image to the screen, passing
    // in the image, x, y, width and height
    this.addImage(loadImage("searchUnsuccessfulScreenImage.png"), iconCenterX, appHeight * 0.55f, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class SearchingScreen extends Screen {

  private PImage searchingImage;

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public SearchingScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    searchingImage = loadImage("placeholder.PNG");

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Searching...");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addImage(searchingImage, appWidth/2, appHeight/2, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class SettingsScreen extends Screen {

  // Creating Booleans for the learning mode and auto save mode, these variables will be used to 
  // toggle between turing these settings on and off depending on the users input
  public Boolean learningModeOn;
  public Boolean autoSaveModeOn;


  // Creating a public constructor for the SettingsScreen class, so that
  // an instance of it can be declared in the main sketch
  public SettingsScreen(PImage bgImage) {
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);
    
    // Setting the learning mode to off
    learningModeOn = false;
    
    // Setting the auto save to on
    autoSaveModeOn = true;

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon homeIcon = new Icon(iconRightX, iconTopY, homeIconImage, "Home", false, "HomeScreen");
    Icon learningModeIcon = new Icon(iconRightX * 0.9f, iconCenterY * 0.5f, smallIconSize * 1.8f, smallIconSize * 0.9f, toggleSwitchOffIconImage, "Learning mode switch", false, "_switchLearningMode");
    Icon autoSaveIcon = new Icon(iconRightX * 0.9f, iconCenterY * 0.8f, smallIconSize * 1.8f, smallIconSize * 0.9f, toggleSwitchOnIconImage, "Auto-save switch", false, "_switchAutoSave");
    Icon instagramAccountIcon = new Icon(iconCenterX * 0.55f, iconCenterY * 1.2f, largeIconSize, largeIconSize, instagramAccountIconImage, "Instagram", true, "Below", "SocialMediaLoginScreen");
    Icon twitterAccountIcon = new Icon(iconCenterX * 1.45f, iconCenterY * 1.2f, largeIconSize, largeIconSize, twitterAccountIconImage, "Home", true, "Below", "SocialMediaLoginScreen");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, learningModeIcon, autoSaveIcon, instagramAccountIcon, twitterAccountIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Settings");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    // Adding text to go beside the icon that will be toggled in order label each setting for the users convenience 
    this.addText("Learning Mode", "LEFT", iconLeftX, iconCenterY * 0.5f);
    this.addText("Autosave Image", "LEFT", iconLeftX, iconCenterY * 0.8f);
  }
}
public class ShareSaveSuccessfulScreen extends Screen {

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public ShareSaveSuccessfulScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    this.addText("Your postcard", iconCenterX, appHeight * 0.1f);
    this.addText("has been", iconCenterX, appHeight * 0.18f);
    this.addText("successfully", iconCenterX, appHeight * 0.26f);
    this.addText("shared & saved", iconCenterX, appHeight * 0.34f);
    this.addImage(loadImage("sharingScreenImage.png"), appWidth/2, appHeight * 0.6f, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class ShareSaveUnsuccessfulScreen extends Screen {

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public ShareSaveUnsuccessfulScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon retryIcon = new Icon(iconCenterX, largeIconBottomY * 0.75f, largeIconSize, largeIconSize, retryIconImage, "Retry", true, "Below", "_sendTweet");
    Icon deleteIcon = new Icon(iconCenterX * 0.55f, largeIconBottomY, largeIconSize, largeIconSize, deleteIconImage, "Delete", true, "Below", "CameraLiveViewScreen");
    Icon saveIcon = new Icon(iconCenterX * 1.45f, largeIconBottomY, largeIconSize, largeIconSize, saveIconImage, "Save", true, "Below", "ShareSaveSuccessfulScreen");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {retryIcon, deleteIcon, saveIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addText("We're sorry :(", iconCenterX, appHeight * 0.1f);
    this.addText("Your postcard", iconCenterX, appHeight * 0.18f);
    this.addText("was not sent", iconCenterX, appHeight * 0.26f);
    this.addImage(loadImage("shareSaveUnsuccessfulScreenImage.png"), iconCenterX, appHeight * 0.4f, appWidth * 0.5f, appHeight * 0.16f);
  }
}
public class ShareUnsuccessfulScreen extends Screen {

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public ShareUnsuccessfulScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional

    Icon cancelIcon = new Icon(iconCenterX * 0.55f, largeIconBottomY, largeIconSize, largeIconSize, cancelIconImage, "Cancel", true, "Below", "CameraLiveViewScreen");
    Icon retryIcon = new Icon(iconCenterX * 1.45f, largeIconBottomY, largeIconSize, largeIconSize, retryIconImage, "Retry", true, "Below", "_sendTweet");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {cancelIcon, retryIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addText("We're sorry :(", iconCenterX, appHeight * 0.1f);
    this.addText("Your postcard", iconCenterX, appHeight * 0.18f);
    this.addText("was not sent", iconCenterX, appHeight * 0.26f);
    this.addImage(loadImage("sharingScreenImage.png"), iconCenterX, appHeight * 0.4f, appWidth * 0.5f, appHeight * 0.16f);
    this.addText("But good news :)", iconCenterX, appHeight * 0.54f);
    this.addText("We have still", iconCenterX, appHeight * 0.62f);
    this.addText("saved it for you!", iconCenterX, appHeight * 0.7f);
  }
}
public class SharingScreen extends Screen {

  private PImage sharingScreenImage;

  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public SharingScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    sharingScreenImage = loadImage("sharingScreenImage.png");

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Sharing...");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addImage(sharingScreenImage, appWidth/2, appHeight/2, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class SocialMediaLoginScreen extends Screen {
  private TextInput usernameInput;
  private TextInput passwordInput;

  // Creating a public constructor for the SearchTravelScreen class, so that
  // an instance of it can be declared in the main sketch
  public SocialMediaLoginScreen(PImage bgImage) {

    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon cancelIcon = new Icon(appWidth * 0.3f, iconCenterY * 1.4f, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Cancel", true, "Middle", "-returnTo");
    Icon loginIcon = new Icon(appWidth * 0.7f, iconCenterY * 1.4f, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Login", true, "Middle", "-returnTo");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {cancelIcon, loginIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Social Media Login Screen");

    usernameInput = new TextInput(iconCenterX, iconCenterY * 0.65f, appWidth * 0.8f, appHeight * 0.08f, 0xffFFFFFE, "usernameInput");
    passwordInput = new TextInput(iconCenterX, iconCenterY * 1.15f, appWidth * 0.8f, appHeight * 0.08f, 0xffFFFFFE, "passwordInput", true);
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    this.addText("Username:", "LEFT", iconLeftX, iconCenterY * 0.5f);
    this.addText("Password:", "LEFT", iconLeftX, iconCenterY * 1);
    usernameInput.showTextInput();
    passwordInput.showTextInput();
  }
}
public class SocialMediaLogoutScreen extends Screen{
  
  // Creating a public constructor for the SearchTravelScreen class, so that
  // an instance of it can be declared in the main sketch
  public SocialMediaLogoutScreen(PImage bgImage){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(bgImage);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon noIcon = new Icon(appWidth * 0.3f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "No", true, "Middle", "SettingsScreen");
    Icon yesIcon = new Icon(appWidth * 0.7f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Yes", true, "Middle", "SettingsScreen");
    
    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {noIcon, yesIcon};
    
    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);
    
    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Social Media Logout Screen");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    this.addText("Are you sure you want", iconCenterX, appHeight * 0.3f);
    this.addText("to remove your", iconCenterX, appHeight * 0.38f);
    this.addText("@username", iconCenterX, appHeight * 0.46f);
    this.addText("Instagram account from", iconCenterX, appHeight * 0.54f);
    this.addText("our app?", iconCenterX, appHeight * 0.62f);
  }
}
public class TextInput extends ClickableElement{
  
  // Creating private variables to store the TextInput's title and value
  // properties, so that they can only be accessed within this class
  private String inputTitle = "";
  private String inputValue = "";
  private String inputTextAlign = "";
  private Boolean passwordInput;
  private float textX;
  private float textY;
  
  /*
  // Working on creating text boxes (to restrain the text to within the bounds of the TextInput area
  private int textAlignX;
  private int textAlignY;
  private int textBoxMode;
  private float textWidth;
  private float textHeight;
  */
  
  /*-------------------------------------- Constructor() ------------------------------------------------*/
  // This partial constructor is used by text inputs that do not require their contents
  // to be blocked out i.e. any text input that is not a password
  public TextInput(float x, float y, float w, float h, int col, String title){

    // Passing the relevant parametres to the main constructor. Since a password value
    // has not been included, passing false for this argument (i.e. assuming this in not
    // a password textInput
    this(x, y, w, h, col, title, false, "LEFT");
  }
  
  // This partial constructor is used by text inputs that do not require their contents
  // to be blocked out i.e. any text input that is not a password
  public TextInput(float x, float y, float w, float h, int col, String title, String align){

    // Passing the relevant parametres to the main constructor. Since a password value
    // has not been included, passing false for this argument (i.e. assuming this in not
    // a password textInput
    this(x, y, w, h, col, title, false, align);
  }
  
  // This partial constructor is used by text inputs that do not require their contents
  // to be blocked out i.e. any text input that is not a password
  public TextInput(float x, float y, float w, float h, int col, String title, Boolean password){

    // Passing the relevant parametres to the main constructor. Since a password value
    // has not been included, passing false for this argument (i.e. assuming this in not
    // a password textInput
    this(x, y, w, h, col, title, password, "LEFT");
  }
  
  // This constructor is used by all text inputs
  public TextInput(float x, float y, float w, float h, int col, String title, Boolean password, String align){

    // Passing the relevant parametres from the constructor into the constructor 
    // of the super class (ClickableElement)
    super(x, y, w, h, col, title);
    
    // Initialising the inputTitle to be equal to the requested title
    inputTitle = title;
    
    passwordInput = password;
    
    inputTextAlign = align;
    
    if(inputTextAlign.equals("LEFT")){
      textX = x - (w * 0.45f);
      textY = y;
    } else if(inputTextAlign.equals("LEFT-TOP")){
      textX = x - (w * 0.45f);
      textY = y - (h * 0.45f);
    } else {
      textX = x;
      textY = y;
    }
    
    /*
    // Working on creating text boxes (to restrain the text to within the bounds of the TextInput area
    if(inputTextAlign.equals("LEFT")){
      textX = this.getX() - (w * 0.45);
      textY = this.getY();
      textAlignX = LEFT;
      textAlignY = CENTER;
    } else if(inputTextAlign.equals("LEFT-TOP")){
      textX = this.getX()  - (w * 0.45);
      textY = this.getY() * 1.1;
      textAlignX = LEFT;
      textAlignY = TOP;
    } else {
      textX = this.getX();
      textY = this.getY();
      textAlignX = CENTER;
      textAlignY = CENTER;
    }
    
    textWidth = textX + (this.getWidth() * 0.9);
    textHeight = textY + (this.getHeight() * 0.9);
    */
  }
  
  /*-------------------------------------- showTextInput() ------------------------------------------------*/
  
  // Creating a public showTextInput function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  public void showTextInput(){
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();
    
    // Checking if the length of the value is greater than 0 i.e. 
    if(this.inputValue.length() > 0){
      // Creating a temporary varaible to store the string we are going to display in the text input.
      // This string will either contain the value of the text input, or in the case of a password,
      // stars which represent the value's length (along with the last letter of the password)
      String displayText = "";
      
      // Checking if this textInput is a password field
      if(passwordInput){
        // Setting display text to be equal to the value returned from the hidePassword() method
        // i.e. starred out (asides fromt the last letter)
        displayText = hidePassword();
        /*
        // Code to only display last letter of password for a specified period of time. Currently
        // not using, but keeping for future reference
        delay(500);
        displayText = displayText.substring(0, displayText.length() -1) + "*";
        */
      } else {
        // Since this field does not contain a password, set the display text to the value of the input
        // field
        displayText = this.inputValue;
      }
      this.addText(displayText, inputTextAlign, textX, textY, this.getWidth() * 0.1f);
      // Working on creating text boxes (to restrain the text to within the bounds of the TextInput area
      //this.addTextBox(displayText);
    }
    if(mousePressed){
      if(this.checkMouseOver()){
        keyboardRequired = true;
        currentTextInput = this;
        currentTextInputValue = "";
        println("The " + inputTitle + " text input was clicked on");
      }
    }
  }
  
  public void setInputValue(String val){
    this.inputValue = val;
  }
  
  public String getInputValue(){
    return this.inputValue;
  }
  public void clearInputValue(){
    this.inputValue = "";
  }
  
  private String hidePassword(){
    // Creating a temporary string to store the **** for the password (i.e. we do not want
    // to display the user's password, we just want to reflect the length of it in terms
    // of * stars *
    String passwordStars = "";
    
    // Looping through the length of the myText string, to determine how many stars should
    // be displayed for the current password length, and allowing just the last letter to be
    // displayed
    for(int i = 0; i < this.inputValue.length(); i++){
      // Creating a temporary variable for the index position of the last character in myText
      // string
      int lastLetter = this.inputValue.length() - 1;
      
      // Checking if the current loop has reached the last letter in the myText string
      if(i == lastLetter){
        // Since this is the last letter in the string, we want to display it with the 
        // preceeing stars i.e. so the user can always see the most recent character
        // they have entered
        passwordStars += this.inputValue.charAt(lastLetter);
      } else {
        // Add a star in place of this character
        passwordStars += "*";
      }
    }
    return passwordStars;
  }

  /*
  // Working on creating text boxes (to restrain the text to within the bounds of the TextInput area
  private void addTextBox(String text){
    rectMode(CORNERS);
    textAlign(textAlignX, textAlignY);
    fill(255, 0, 0);
    rect(textX, textY, textWidth, textHeight); 
    fill(0);
    text(text, textX, textY, textWidth, textHeight);
    rectMode(CORNER);
  }
  */
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Wish_I_Was_Here" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
