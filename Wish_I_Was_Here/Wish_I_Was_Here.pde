import ketai.*;
import ketai.camera.*;
import ketai.ui.*;
import android.os.Environment;
import android.content.*;

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
String currentScreen = "SocialMediaLoginScreen";

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
PImage twitterAccountIconImage;
PImage instagramAccountIconImage;
PImage buttonImage;

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

 /*-------------------------------------- Built In Functions ------------------------------------------------*/

void setup() {
   /*-------------------------------------- Global ------------------------------------------------*/
   
  // PC TESTING SETTINGS
  // Setting the size of the sketch (for testing purposes only, will eventually be dynamic)
  //size(360, 640);
  
  // ANDROID TESTING SETTINGS
  fullScreen();
  
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
  
  if(ketaiCamera.getNumberOfCameras() > 1)
  {
    // If there is more than one camera, then default to the front camera
    // (which as far as I can tell tends to be at index 1)
    camNum = 1;
  }
  else
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
  favIconImage = loadImage("favIconYesImage.png");
  shakeIconImage = loadImage("iconPlaceholder.png");
  shutterIconImage = loadImage("shutterIconImage.png");
  switchViewIconImage = loadImage("switchViewIconImage.png");
  retryIconImage = loadImage("iconPlaceholder.png");
  deleteIconImage = loadImage("deleteIconImage.png");
  saveIconImage = loadImage("saveIconImage.png");
  disgardIconImage = loadImage("disgardIconImage.png");
  keepIconImage = loadImage("keepIconImage.png");
  cancelIconImage = loadImage("iconPlaceholder.png");
  twitterAccountIconImage = loadImage("iconPlaceholder.png");
  instagramAccountIconImage = loadImage("iconPlaceholder.png");
  buttonImage = loadImage("buttonImage.png");

   /*-------------------------------------- Sizing ------------------------------------------------*/
   
  // Initialising the icon positioning X and Y variables, which will be used globally to ensure that
  // the icons on each page all line up with one another. These measurements are all based on percentages
  // of the app's display width and height (as defined above
  iconLeftX = appWidth * 0.15;
  iconRightX = appWidth * 0.85;
  iconCenterX = appWidth * 0.5;
  iconTopY = appHeight * 0.085;
  iconBottomY = appHeight * 0.92;
  iconCenterY = appHeight * 0.5;
  largeIconSize = appWidth * 0.35;
  smallIconSize = appWidth * 0.15;
  largeIconBottomY = iconBottomY - (largeIconSize/2);
  screenTitleY = appHeight * 0.08;
  
  // Initialising the defaultTextSize to be equal to a percentage of the app's current height
  defaultTextSize = appHeight * 0.04;
  
   /*-------------------------------------- Screens ------------------------------------------------*/
   
  // Creating the screens which will be used in this application. Setting a random background
  // colour for each of these screens so that transitions between them can be more obvious
  // (for testing purposes only). Note - setting a background color is optional. Depending on the
  // screen's constructor, you can pass in a background color, a background image, or nothing at
  // all if you want to default to white
  myHomeScreen = new HomeScreen(#8CBCD8);
  myCameraLiveViewScreen = new CameraLiveViewScreen(#000000);
  myFavouritesScreen = new FavouritesScreen(#CE04BA);
  mySettingsScreen = new SettingsScreen(#2023A5);
  myAboutScreen = new AboutScreen (#CEBD5A);
  mySearchScreen = new SearchScreen(#E88121);
  mySearchUnsuccessfulScreen = new SearchUnsuccessfulScreen(#F5C811);
  myImagePreviewScreen = new ImagePreviewScreen(#E88121);
  mySaveShareScreenA = new SaveShareScreenA(#5ACEBE);
  mySaveShareScreenB = new SaveShareScreenB(#CEBD5A);
  mySharingScreen = new SharingScreen(#1548EF);
  myShareSaveSuccessfulScreen = new ShareSaveSuccessfulScreen(#CE04BA);
  myShareUnsuccessfulScreen = new ShareUnsuccessfulScreen(#30B727);
  myShareSaveUnsuccessfulScreen = new ShareSaveUnsuccessfulScreen(#2023A5);
  mySearchingScreen = new SearchingScreen(#2023A5);
  mySocialMediaLoginScreen = new SocialMediaLoginScreen(#E88121);
  mySocialMediaLogoutScreen = new SocialMediaLogoutScreen(#CEBD54);
  myLoadingScreen = new LoadingScreen();
  
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
  }

void draw() {
  // Calling the monitorScreens() function to display the right screen by calling
  // the showScreen() method. This function then calls the super class's drawScreen()
  // method, which not only adds the icons and backgrounds to the screen, it also
  // asks the icons on the screen to call their checkMouseOver() method (inherited from
  // the Icon class) to see if they were clicked on when a mouse event occurs
   switchScreens();
}

void mousePressed(){
  keyboardRequired = false;
  previousMouseY = mouseY;
}

void keyPressed(){
  // Getting the current input value of this text input (i.e. so that if a user clicks between different
  // text fields, they can resume their input instead of the textfield becoming empty)
  currentTextInputValue = currentTextInput.getInputValue();
  
  // Checking if the key pressed is a coded value i.e. backspace etc
  if(key == CODED){
    //println(key);
    // Checking if the key pressed was the backspace key
    if(keyCode == 67)
    {
      // Checking that the length of the current currentTextInputValue string is greater than 0 (i.e. 
      // if the string is empty, don't try to delete anything)
      if(currentTextInputValue.length() > 0){
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
void onCameraPreviewEvent()
{
  // Reading in a new frame from the ketaiCamera.
  ketaiCamera.read();
  currentImage = ketaiCamera.get();
  //manipulatePixels();
}

 /*-------------------------------------- Custom Functions ------------------------------------------------*/
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
  } else if(currentScreen.equals("LoadingScreen")){
      myLoadingScreen.showScreen();
      testingTimeoutScreen("HomeScreen");
  } else{
    println("This screen doesn't exist");
  }
  
  if(callFunction.equals("_keepImage"))
  {
    keepImage();
  } else if(callFunction.equals("_SwitchCameraView")){
     myCameraLiveViewScreen.switchCameraView();
  } 
  
  if(keyboardRequired){
    KetaiKeyboard.show(this);
    callFunction = "";
  } else {
    KetaiKeyboard.hide(this);
    callFunction = "";
  }
  
  // Turning the camera on and off (if the current screen
  // is the camera live view, and the camera is  not yet turned
  // on, then start the camera, otherwise, if you are on any other screen,
  // stop the camera
  
  
  if(currentScreen.equals("CameraLiveViewScreen")){
    if(!ketaiCamera.isStarted()){
      ketaiCamera.start();
    }
  } else if(ketaiCamera.isStarted()) {
    ketaiCamera.stop();
  }
}
 
void keepImage(){  
  // Checking if Storage is available
  if(isExternalStorageWritable()){    
    // Trying to save out the image. Putting this code in an if statement, so that if it fails, a message will be logged
    if (currentImage.save(directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg")){
      println("Successfully saved image to = " + directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg");
      currentScreen = "SaveShareScreenA";
    } else {
      println("Failed to save image");
    }
  }  
}

Boolean isExternalStorageWritable() {
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
void testingTimeoutScreen(String fadeToScreen){
  if(mousePressed)
  { 
    currentScreen = fadeToScreen;
    mousePressed = false;
  }
}
 
void manipulatePixels(){
  currentImage.loadPixels();
  for(int x = 0; x < currentImage.width; x++)
  {
    for(int y = 0; y < 50; y++)
    {
      int currentPixel = x + y * currentImage.width;
      
      currentImage.pixels[currentPixel] = color(#0000FF);
    }
  }
  currentImage.updatePixels();
  
  //ketaiCamera = currentImage.get(0, 0, 100, 100);
}