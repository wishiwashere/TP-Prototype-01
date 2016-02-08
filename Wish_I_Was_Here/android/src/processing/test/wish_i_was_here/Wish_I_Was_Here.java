package processing.test.wish_i_was_here;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ketai.*; 
import ketai.camera.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Wish_I_Was_Here extends PApplet {




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

PImage saveThisImage;

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

  /*-------------------------------------- Sizing ------------------------------------------------*/
  
// Declaring global variables, which will contain the width and height of the device's
// display, so that these values can be reused throughout all classes (i.e. to calculate
// more dynamic position/width/height's so that the interface responds to different
// screen sizes
int appWidth;
int appHeight;

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

// Declaring the icon positioning X and Y variables, which will be used globally to ensure that
// the icons on each page all line up with one another. These measurements are all based on percentages
// of the app's display, and are initialised in the setup function of this sketch
float iconLeftX;
float iconRightX;
float iconCenterX;
float iconTopY;
float iconBottomY;
float iconCenterY;
float squareIconSize;
float squareIconBottomY;

// Declaring a global variable which will contain the default text size, which will be
// initialised in the setup() function of the app
float defaultTextSize;

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

public void setup() {
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

  // Initialising the icon positioning X and Y variables, which will be used globally to ensure that
  // the icons on each page all line up with one another. These measurements are all based on percentages
  // of the app's display width and height (as defined above
  iconLeftX = appWidth * 0.15f;
  iconRightX = appWidth * 0.85f;
  iconCenterX = appWidth * 0.5f;
  iconTopY = appHeight * 0.085f;
  iconBottomY = appHeight * 0.92f;
  iconCenterY = appHeight * 0.5f;
  squareIconSize = appWidth * 0.35f;
  squareIconBottomY = iconBottomY - (squareIconSize/2);
  
  // Initialising the defaultTextSize to be equal to a percentage of the app's current height
  defaultTextSize = appHeight * 0.04f;
  
  // Creating the screens which will be used in this application. Setting a random background
  // colour for each of these screens so that transitions between them can be more obvious
  // (for testing purposes only). Note - setting a background color is optional. Depending on the
  // screen's constructor, you can pass in a background color, a background image, or nothing at
  // all if you want to default to white
  myHomeScreen = new HomeScreen(0xff8CBCD8);
  myCameraLiveViewScreen = new CameraLiveViewScreen(0xff000000);
  myFavouritesScreen = new FavouritesScreen(0xffCE04BA);
  mySettingsScreen = new SettingsScreen(0xff2023A5);
  myAboutScreen = new AboutScreen (0xffCEBD5A);
  mySearchScreen = new SearchScreen(0xffE88121);
  mySearchUnsuccessfulScreen = new SearchUnsuccessfulScreen(0xffF5C811);
  myImagePreviewScreen = new ImagePreviewScreen(0xffE88121);
  mySaveShareScreenA = new SaveShareScreenA(0xff5ACEBE);
  mySaveShareScreenB = new SaveShareScreenB(0xffCEBD5A);
  mySharingScreen = new SharingScreen(0xff1548EF);
  myShareSaveSuccessfulScreen = new ShareSaveSuccessfulScreen(0xffCE04BA);
  myShareUnsuccessfulScreen = new ShareUnsuccessfulScreen(0xff30B727);
  myShareSaveUnsuccessfulScreen = new ShareSaveUnsuccessfulScreen(0xff2023A5);
  mySearchingScreen = new SearchingScreen(0xff2023A5);
  mySocialMediaLoginScreen = new SocialMediaLoginScreen(0xffE88121);
  mySocialMediaLogoutScreen = new SocialMediaLogoutScreen(0xffCEBD54);
  myLoadingScreen = new LoadingScreen();
  
  // Storing a string that tells the app where to store the images, by default 
  // it goes to the pictures folder and this string as it has WishIWasHereApp 
  // it is creating a folder in the picture folder of the device
  directory = "./WishIWasHereApp";
  ketaiCamera.setSaveDirectory(directory);
}

public void draw() {
  // Calling the monitorScreens() function to display the right screen by calling
  // the showScreen() method. This function then calls the super class's drawScreen()
  // method, which not only adds the icons and backgrounds to the screen, it also
  // asks the icons on the screen to call their checkMouseOver() method (inherited from
  // the Icon class) to see if they were clicked on when a mouse event occurs
   switchScreens();
}

public void switchScreens(){
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
  } else if(currentScreen.equals("_SwitchCameraView")){
      myCameraLiveViewScreen.switchCameraView();
      currentScreen = "CameraLiveViewScreen";
  } else if(currentScreen.equals("_keepImage")){
    keepImage();
    currentScreen = "ImagePreviewScreen";
  } else if(currentScreen.equals("LoadingScreen")){
      myLoadingScreen.showScreen();
      testingTimeoutScreen("HomeScreen");
  } else{
    println("This screen doesn't exist");
  }
  
  
  // Turning the camera on and off (if the current screen
  // is the camera live view, and the camera is  not yet turned
  // on, then start the camera, otherwise, if you are on any other screen,
  // stop the camera
  if(currentScreen.equals("CameraLiveViewScreen") || currentScreen.equals("ImagePreviewScreen")){
    if(!ketaiCamera.isStarted()){
      ketaiCamera.start();
    }
  }else if(ketaiCamera.isStarted()) {
    //ketaiCamera.stop();
  }
}

public void keepImage(){  
  // Saving the current frame of the ketaiCamera and assigning it a name, and incrementing 
  // number to ensure images are not overwritten and to allow for multiple images
  // Also assigning an image format so the frame is saved as a jpeg to the users phone and 
  // can be seen in their gallery under a folder title of Wish I Was Here App
  ketaiCamera.savePhoto("WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg"); 
  println("Trying to keep the image, but not fully saved yet");
}

// Calling the onSavePhotoEvent when a savePhoto gets called, this function adds it 
// to the media lirary of the android device
public void onSavePhotoEvent(String filename)
{
  println("About to save");
  ketaiCamera.addToMediaLibrary(filename);
  // Printing out this line as it indicates whether this function is being 
  // called and whether the image has actaully been saved to the directory / device
  println("I saved!");
  
  currentScreen = "ImagePreviewScreen";
}

// TESTING PURPOSES ONLY - FOR SCREENS WITH NO INTERACTION
// eeded a way to clear it from the screen until the
// code that normally would clear it is added
public void testingTimeoutScreen(String fadeToScreen){
  if(mousePressed)
  { 
    
    currentScreen = fadeToScreen;
    mousePressed = false;
  }
}

// ketaiCamera event which is automatically called everytime a new frame becomes
// available from the ketaiCamera.
public void onCameraPreviewEvent()
{
  // Reading in a new frame from the ketaiCamera.
  ketaiCamera.read();
}
public class AboutScreen extends Screen{
    
  // Creating a public constructor for the AboutScreen class, so that
  // an instance of it can be declared in the main sketch
  public AboutScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
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
    this.setScreenTitle("About");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class CameraLiveViewScreen extends Screen{
  
  // Creating a public constructor for the CameraLiveViewScreen class, so that
  // an instance of it can be declared in the main sketch
  public CameraLiveViewScreen(int col){
    
    // Calling the super class (Screen), which will in turn call it's super class 
    // (Rectangle) and create a rectangle with the default values i.e. fullscreen, 
    // centered etc.
    super(col);
    
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
    Icon favIcon = new Icon(iconLeftX, iconTopY, favIconImage, "Add to Favourites", false);
    Icon shakeIcon = new Icon(iconLeftX, iconBottomY, shakeIconImage, "Turn on/off Shake", false);
    Icon shutterIcon = new Icon(iconCenterX, iconBottomY, shutterIconImage, "Take a Picture", false, "_keepImage");
    Icon switchViewIcon = new Icon(iconRightX, iconBottomY, switchViewIconImage, "Switch View", false, "_SwitchCameraView");
    
    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, favIcon, shakeIcon, shutterIcon, switchViewIcon};
    
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
    this.setScreenTitle("Live View");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    // Calls super super class (Rectangle). Passing in the current frame image, the width and height
    // which have been reversed - i.e. the width will now be equal to the height of the app, as the 
    // ketaiCamera image requires it's rotation to be offset by 90 degress (either in the plus or the 
    // minus depending on whether you are using the front or rear camera) so the width and the height
    // need to swap to fit with the image's new resolution
    this.addBackgroundImage(ketaiCamera, appHeight, appWidth, cameraScale, cameraRotation);
  }
  
  private void switchCameraView()
  {
    // If the camera is already running before we try and effect it
    if (ketaiCamera.isStarted())
    {
      // Checking if the device has more than one camera. If it does we want to toggle between them
      if(ketaiCamera.getNumberOfCameras() > 1)
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
public class FavouritesScreen extends Screen{
  
  // Creating a public constructor for the FavouriteScreen class, so that
  // an instance of it can be declared in the main sketch
  public FavouritesScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
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
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class HomeScreen extends Screen{
  
  // Creating a public constructor for the HomeScreen class, so that
  // an instance of it can be declared in the main sketch
  public HomeScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
       
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon searchTravelIcon = new Icon(appWidth * 0.3f, appHeight * 0.2f, squareIconSize, squareIconSize, searchPageIconImage, "Search", true, "SearchScreen");
    Icon randomTravelIcon = new Icon(appWidth * 0.7f, appHeight * 0.2f, squareIconSize, squareIconSize, randomPageIconImage, "Random", true, "CameraLiveViewScreen");
    Icon myFavouritesIcon = new Icon(appWidth * 0.3f, appHeight * 0.5f, squareIconSize, squareIconSize, favouritesPageIconImage, "My Favourites", true, "FavouritesScreen");
    Icon aboutIcon = new Icon(appWidth * 0.7f, appHeight * 0.5f, squareIconSize, squareIconSize, aboutPageIconImage, "About", true, "AboutScreen");
    Icon settingsIcon = new Icon(appWidth * 0.5f, appHeight * 0.8f, squareIconSize, squareIconSize, settingsPageIconImage, "Settings", true, "SettingsScreen");

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
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class Icon extends Rectangle{
  
  // Creating private variables to store the icon's link, title and show title
  // properties, so that they can only be accessed within this class
  private String iconLinkTo;
  private String iconTitle;
  private Boolean showIconTitle;
  
  /*-------------------------------------- Constructor() ------------------------------------------------*/
  
  // This constructor is used by icons in the CameraLiveView Screen, that want to accept the 
  // default width and height of an icon, but do not link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle){
    
    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, appWidth * 0.2f, appWidth * 0.2f, img, title, showTitle, "");
  }
  
  // This constructor is used by icons such as the homeIcon, that want to accept the default 
  // width and height of an icon, and also link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle, String linkTo){
    
    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, appWidth * 0.2f, appWidth * 0.2f, img, title, showTitle, linkTo);
  }   
  
  // Full Constructor. Both of the above constructors both pass their values to this constructor, as
  // well as other icon's in the app that want to pass arguments for all of the specified values
  public Icon(float x, float y, float w, float h, PImage img, String title, Boolean showTitle, String linkTo){
    
    // Passing the relevant parametres from the constructor into the constructor 
    // of the super class (Rectangle)
    super(x, y, w, h, img);
    
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
  }
  
  /*-------------------------------------- showIcon() ------------------------------------------------*/
  
  // Creating a public showIcon function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  public void showIcon(){
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();
        
    // Checking if this icon's title should be displayed as a header on the screen
    if(showIconTitle)
    {
      // Calling the super class's (Rectangle) addText method, to add the title to
      // the icon. Passing in the String containing the title for the icon, the current
      // x and y positions of the icon itself, and the font size (which is relative
      // to the icon's current height
      this.addText(this.iconTitle, this.getX(), this.getY(), this.getWidth() * 0.20f);
    }
  }
  
  /*-------------------------------------- checkMouseOver() ------------------------------------------------*/
  
  public void checkMouseOver(){ 
    // Checking if the mouse (or finger) is over this icon (called by the Screen
    // class if a mouse event has occurred while this icon's screen is being 
    // displayed. Since the icons are drawn from the center, a bit of additional 
    // calculations are required to find out if the mouse was over them (i.e. 
    // to see if the mouseX was over this icon, we first have to take half 
    // the width from the x from the x postion, to get the furthest left point, 
    // and then add half of the width to the x position of the icon, to get 
    // it's furthest right point. The process is simular for determining the mouseY)
    if((mouseX > (this.getX() - (this.getWidth()/2))) &&
       (mouseX < (this.getX() + (this.getWidth()/2))) &&
       (mouseY > (this.getY() - (this.getHeight()/2))) &&
       (mouseY < (this.getY() + (this.getHeight()/2)))){
         
         // Logging out the name of the icon that was clicked on
          println(this.iconTitle + " was clicked");
          
          // Checking if this icon has a link associated with it
          if(this.iconLinkTo.length() > 0)
          {
            // Setting the global currentScreen variable to be equal to the link
            // contained within the icon that was clicked on (so it can be used
            // in the main sketch to determine which page to display)
            currentScreen = this.iconLinkTo;
            
            // Logging out what page the app will now be taken to
            println("Going to page " + this.iconLinkTo);
          }
          
          // Setting mousePressed back to false, so that if the user still has their
          // mouse pressed after the screen changes, this will not be considered
          // a new click (as otherwise they could inadvertantly click on another button)
          mousePressed = false;
    }
  }
}
public class ImagePreviewScreen extends Screen{
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public ImagePreviewScreen(int col){
    
    // Calling the super class (Screen), which will in turn call it's super class 
    // (Rectangle) and create a rectangle with the default values i.e. fullscreen, 
    // centered etc.
    super(col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon disgardIcon = new Icon(iconLeftX, iconBottomY, disgardIconImage, "Disgard Image", false, "CameraLiveViewScreen");
    Icon keepIcon = new Icon(iconRightX, iconBottomY, keepIconImage, "Keep Image", false, "SaveShareScreenA");
    
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
    this.setScreenTitle("Image Preview Screen");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    // Calls super super class (Rectangle). Passing in the current frame image, the width and height
    // which have been reversed - i.e. the width will now be equal to the height of the app, as the 
    // ketaiCamera image requires it's rotation to be offset by 90 degress (either in the plus or the 
    // minus depending on whether you are using the front or rear camera) so the width and the height
    // need to swap to fit with the image's new resolution
    this.addBackgroundImage(ketaiCamera, appHeight, appWidth, cameraScale, cameraRotation);
  }
}
public class LoadingScreen extends Screen{
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public LoadingScreen(){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(loadImage("loadingScreenImage.png"));
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
protected class Rectangle{
  
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
  protected Rectangle(){
    // If no x, y, or rotation are passed in, defaulting these to half of the sketch's
    // width and height (i.e. so that the rectangle will appear centered). If no width
    // and height are passed in, defaulting these to the current width and height of
    // the sketch. If no rotation value is specified, defaulting this value to 0, and 
    // finally, if no color is specified, defaulting this to white
    this(appWidth/2, appHeight/2, appWidth, appHeight, 0xffFFFFFF, null);
  }
  
  // This constructor is used by screens that want to accept all the defaults, as well
  // as setting a background image
  protected Rectangle(PImage img){
    // If no x, y passed in, defaulting these to half of the sketch's width and height 
    // (i.e. so that the rectangle will appear centered). If no width and height are 
    // passed in, defaulting these to the current width and height of the sketch. If 
    // no color is specified, defaulting this to white. Passing these default values, 
    // along with the image that was passed in, to the main constructor of this class
    this(appWidth/2, appHeight/2, appWidth, appHeight, 0xffFFFFFF, img);
  }
  
  // This constructor is used by screens that want to accept all the defaults, as well
  // as setting a background color
  protected Rectangle(int col){
    // If no x, y passed in, defaulting these to half of the sketch's width and height 
    // (i.e. so that the rectangle will appear centered). If no width and height are 
    // passed in, defaulting these to the current width and height of the sketch. If 
    // no image is specified, defaulting this null. Passing these default values, along 
    // with the color that was passed in, to the main constructor of this class
    this(appWidth/2, appHeight/2, appWidth, appHeight, col, null);
  }
  
  // This constructor is used by icons that do not link to anything, and that
  // want to have an image as their background
  protected Rectangle(float x, float y, float w, float h, PImage img){
    // If no color passed in, defaulting it to white and then passing this default value,
    // along with the x, y, width, height and image that was passed in, to the main 
    // constructor of this class
    this(x, y, w, h, 0xffFFFFFF, img);
  }
  
  // This constructor is used by text input boxes (TEMPORARILY)
  protected Rectangle(float x, float y, float w, float h, int col){
    this(x, y, w, h, col, null);
  }
  
  // This is the main constructor of this class, to which all other constructors pass
  // their values to be stored as the instance's properties
  protected Rectangle(float x, float y, float w, float h, int col, PImage img){
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
  protected void show(){
    
    if(rectCol != 0xffFFFFFF){
      // Storing the current state of the matrix
      pushMatrix();
      
      // Translating the position of the matrix to the specified x and y of the object
      translate(rectX, rectY);
      
      // Rotating the matrix by the specified rotation value of the object (which has been
      // stored as a radian value)
      rotate(rectRotation);
      // Setting the fill colour of the object to the value specified
      fill(rectCol);
      
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
    if(rectBackgroundImg != null){    
      // Calling the addImage() method of the this class, to add the background image to the screen,
      // passing in the image, along with the current x, y, width and height of the instance,
      // so that the image will appear the full size of the object
      this.addImage(rectBackgroundImg, rectX, rectY, rectWidth, rectHeight, rectBackgroundImgScaleX, rectBackgroundImgRotate); 
    }
    
    // Checking if an image has been passed in
    if(rectImage != null){    
      // Calling the addImage() method of the this class, to add the image to the screen,
      // passing in the image, along with the current x, y, width and height of the instance,
      // so that the image will appear the full size of the object
      this.addImage(rectImage, rectX, rectY, rectWidth, rectHeight); 
    }
  }
  
  /*-------------------------------------- addText() ------------------------------------------------*/
  
  // Partial addText() method that adds text to the object using the default text size
  protected void addText(String text, float textX, float textY){
    // If no alignment specified, defaulting it to center on the x-axis. If no
    // text size specified, defaulting it to the defaultTextSize variable (as
    // defined in the main sketch. Passing these default values, along with the
    // specified text, x and y to the main addText() method
    this.addText(text, "CENTER", textX, textY, defaultTextSize);
  }
  
  // Partial addText() method that adds text to the object, using the default text size, and
  // changing the alignment on the x-axis to a specified alignment (as it will
  // default to CENTER otherwise
  protected void addText(String text, String align, float textX, float textY){
    // If no text size specified, defaulting it to the defaultTextSize variable
    // (as defined in the main sketch. Passing these default values, along with
    // the specified text, alignment, x and y to the main addText() method
    this.addText(text, align, textX, textY, defaultTextSize);
  }
  
  // Partial addText() method that adds text to the object, with a specific text size
  protected void addText(String text, float textX, float textY, float textSize){
    // If no alignment specified, defaulting it to center on the x-axis. Passing
    // this default value, along with the specified text, x, y and text size to
    // the main addText() method
    this.addText(text, "CENTER", textX, textY, textSize);
  }
  
  // Full addText() method, which takes in the values specified (some of which may have
  // been defaulted by the partial addText() methods above)
  protected void addText(String text, String align, float textX, float textY, float textSize){
    // Storing the current state of the matrix
    pushMatrix();
      
    // Translating the position of the matrix be equal to the x and y positions
    // passed into the function
    translate(textX, textY);
    
    // Rotating the matrix by the currnet rotation value of this object (which has been
    // stored as a radian value)
    rotate(this.getRotation());
    
    if(align.equals("LEFT")){
      // Setting the text align to Left on the x axis, and Center on the y so that
      // the text will be drawn from the center point of it's position on the left of
      // the page
      textAlign(LEFT, CENTER);
    }else{
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
  protected void addImage(PImage img, int scaleX, int rotate){
    // If no x, y, width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values to the full addImage() method
    this.addImage(img, appWidth/2, appHeight/2, img.width, img.height, scaleX, rotate);
  }
  
  // Partial addImage() method which is used by images that need to be displayed
  // at their default resolution
  protected void addImage(PImage img, float imgX, float imgY){
    // If no image width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values, along with the image, x and y to the full addImage() method
    this.addImage(img, imgX, imgY, img.width, img.height, 1, (int)this.getRotation());
  }
  
  // Partial addImage() method which is used by images that require a specific width and height
  // (Some of these values may have been defaulted by the partial addImage() method)
  protected void addImage(PImage img, float imgX, float imgY, float imgWidth, float imgHeight){
    // If no image width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values, along with the image, x and y to the full addImage() method
    this.addImage(img, imgX, imgY, imgWidth, imgHeight, 1, (int)this.getRotation());
  }
  
  // Full addImage() method which is used by images that require a specific width and height
  // (Some of these values may have been defaulted by the partial addImage() method)
  protected void addImage(PImage img, float imgX, float imgY, float imgWidth, float imgHeight, int scaleX, int rotate){
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
  protected float getX(){
    return rectX;
  }
  
  // Get method that returns the instance's y position
  protected float getY(){
    return rectY;
  }
  
  // Get method that returns the instance's width
  protected float getWidth(){
    return rectWidth;
  }
  
  // Get method that returns the instance's height
  protected float getHeight(){
    return rectHeight;
  }
  
  // Get method that returns the instance's rotation
  protected float getRotation(){
    return rectRotation;
  }
  
  // Set method that sets the rotation of instance
  protected void setRotation(int r){
    rectRotation = radians(r);
  }
  
  protected void setBackgroundImgScaleX(int sX){
    if(sX == 1 || sX == -1){
      rectBackgroundImgScaleX = sX;
    }
  }
  
  protected void addBackgroundImage(PImage bgImg, float w, float h, int scaleX, int rotate){
    rectBackgroundImg = bgImg;
    rectWidth = w;
    rectHeight = h;
    rectBackgroundImgScaleX =  scaleX;
    rectBackgroundImgRotate = rotate;
  }
  
  public PImage getBackgroundImage(){
    return rectBackgroundImg;
  }
}
public class SaveShareScreenA extends Screen{
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public SaveShareScreenA(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon cancelIcon = new Icon(appWidth * 0.3f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Cancel", true, "CameraLiveViewScreen");
    Icon nextIcon = new Icon(appWidth * 0.7f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Next", true, "SaveShareScreenB");
    
    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {cancelIcon, nextIcon};
    
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
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class SaveShareScreenB extends Screen{
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public SaveShareScreenB(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon cancelIcon = new Icon(appWidth * 0.3f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Cancel", true, "SaveShareScreenA");
    Icon shareIcon = new Icon(appWidth * 0.7f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Share", true, "SharingScreen");
    
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
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
protected class Screen extends Rectangle{
  
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
  
  protected Screen(){    
    // Calling this class's super class (Screen) to create a screen using
    // the default settings
    super();
  }
  
  protected Screen(PImage img){    
    // Calling this class's super class (Screen) to create a screen using
    // the default settings along with a background image
    super(img);
  }
  
  protected Screen(int col){
    // Calling this class's super class (Screen) to create a screen using
    // the default settings, along with setting the colour as specified
    super(col);
  }
  
  /*-------------------------------------- drawScreen() ------------------------------------------------*/
   
  // Creating a public method so that this screen can be displayed from
  // within the main sketch
  public void drawScreen(){    
    // Calling the show() method (as inherited from the Rectangle class)
    // to display this screen's background
    this.show();
    
    // Checking if this screen has been given a title (i.e. if the contents
    // of the screenTitle is at least one character long
    if(screenTitle.length() > 0)
    {
      this.addText(screenTitle, appWidth/2, appHeight * 0.08f);
    }
    
    // Checking if this screen has any icons to be displayed
    if(screenIcons.length > 0)
    {
      // Looping through each of the screen's icons
      for(int i = 0; i < screenIcons.length; i++){
        // Calling the showIcon() method (as inherited from the Icon class)
        // to display the icon on screen
        screenIcons[i].showIcon();
        
        // Checking if the mouse is currently pressed i.e. if a click has
        // been detected
        if(mousePressed)
        {
          // Calling the checkMouseOver() method (as inherited from the Icon
          // class, to see if the mouse was over this icon when it was clicked
          screenIcons[i].checkMouseOver();
        }
      }
    }
  }
  
  /*-------------------------------------- get() and set() ------------------------------------------------*/
  
  protected void setScreenTitle(String title){
    // Setting the screenTitle to the title passed in by each screen. If no
    // title is passed, this variable has already been initialised to an 
    // empty string above
    screenTitle = title;
  }
  
  protected void setScreenIcons(Icon[] icons){
    // Initialising the screenIcons array with the contents from the allIcons
    // array that each screen will pass in
    screenIcons = icons;
  }
}
public class SearchScreen extends Screen{
  
  // Creating a public constructor for the SearchTravelScreen class, so that
  // an instance of it can be declared in the main sketch
  public SearchScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
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
    Icon cancelIcon = new Icon(appWidth * 0.3f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Cancel", true, "HomeScreen");
    Icon searchIcon = new Icon(appWidth * 0.7f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Search", true, "SearchingScreen");
    
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
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class SearchUnsuccessfulScreen extends Screen{
  
  // Declaring a variable to hold an image that will be added to the screen
  private PImage searchUnsuccessfulScreenImage;
  
  // Creating a public constructor for the SearchFailedScreen class, so that
  // an instance of it can be declared in the main sketch
  public SearchUnsuccessfulScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Initialising the page image by loading in an image
    searchUnsuccessfulScreenImage = loadImage("searchUnsuccessfulScreenImage.png");
    
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
    Icon searchTravelIcon = new Icon(appWidth * 0.3f, squareIconBottomY, squareIconSize, squareIconSize, searchPageIconImage, "Search Again", true, "SearchScreen");
    Icon randomTravelIcon = new Icon(appWidth * 0.7f, squareIconBottomY, squareIconSize, squareIconSize, randomPageIconImage, "Random", true, "CameraLiveViewScreen");
    
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
    this.setScreenTitle("We're sorry!");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    // Calling the super class's super class (Rectangle) to add an image to the screen, passing
    // in the image, x, y, width and height
    this.addImage(searchUnsuccessfulScreenImage, appWidth/2, appHeight/2, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class SearchingScreen extends Screen{
  
  private PImage searchingImage;
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public SearchingScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    searchingImage = loadImage("placeholder.PNG");
    
    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Searching...");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    this.addImage(searchingImage, appWidth/2, appHeight/2, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class SettingsScreen extends Screen{
  
  // Creating a public constructor for the SettingsScreen class, so that
  // an instance of it can be declared in the main sketch
  public SettingsScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
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
    this.setScreenTitle("Settings");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class ShareSaveSuccessfulScreen extends Screen{
  
  private PImage shareSaveSuccessfulScreenImage;
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public ShareSaveSuccessfulScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    shareSaveSuccessfulScreenImage = loadImage("sharingScreenImage.png");
    
    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Successful Share Save");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    this.addImage(shareSaveSuccessfulScreenImage, appWidth/2, appHeight/2, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class ShareSaveUnsuccessfulScreen extends Screen{
  PImage shareSaveUnsuccessfulScreenImage;
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public ShareSaveUnsuccessfulScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon retryIcon = new Icon(iconCenterX, squareIconBottomY * 0.9f, retryIconImage, "Retry Sending", false, "SharingScreen");
    Icon deleteIcon = new Icon(iconLeftX, squareIconBottomY, deleteIconImage, "Delete Image", false, "CameraLiveViewScreen");
    Icon saveIcon = new Icon(iconRightX, squareIconBottomY, saveIconImage, "Save Image", false, "ShareSaveSuccessfulScreen");
    
    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {retryIcon, deleteIcon, saveIcon};
    
    shareSaveUnsuccessfulScreenImage = loadImage("shareSaveUnsuccessfulScreenImage.png");
    
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
    this.setScreenTitle("Share Unsuccessful Screen");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    
    this.addImage(shareSaveUnsuccessfulScreenImage, appWidth/2, appHeight/2, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class ShareUnsuccessfulScreen extends Screen{
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public ShareUnsuccessfulScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon cancelIcon = new Icon(iconLeftX, iconBottomY, cancelIconImage, "Cancel Sending", false, "CameraLiveViewScreen");
    Icon retryIcon = new Icon(iconRightX, iconBottomY, retryIconImage, "Retry Sending", false, "SharingScreen");
    
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
    this.setScreenTitle("Share Unsuccessful Screen");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}
public class SharingScreen extends Screen{
  
  private PImage sharingScreenImage;
  
  // Creating a public constructor for the TemplateScreen class, so that
  // an instance of it can be declared in the main sketch
  public SharingScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    sharingScreenImage = loadImage("sharingScreenImage.png");
    
    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Sharing...");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    this.addImage(sharingScreenImage, appWidth/2, appHeight/2, appWidth * 0.8f, appWidth * 0.4f);
  }
}
public class SocialMediaLoginScreen extends Screen{
  
  Rectangle usernameInput;
  Rectangle passwordInput;
  
  // Creating a public constructor for the SearchTravelScreen class, so that
  // an instance of it can be declared in the main sketch
  public SocialMediaLoginScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon cancelIcon = new Icon(appWidth * 0.3f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Cancel", true, "CameraLiveViewScreen");
    Icon loginIcon = new Icon(appWidth * 0.7f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Login", true, "CameraLiveViewScreen");
    
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
   
    usernameInput = new Rectangle(iconCenterX, iconCenterY * 0.85f, appWidth * 0.8f, appHeight * 0.08f, 0xffFFFFFF);
    passwordInput = new Rectangle(iconCenterX, iconCenterY * 1.35f, appWidth * 0.8f, appHeight * 0.08f, 0xffFFFFFF);
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    this.addText("Username:", "LEFT", iconLeftX, iconCenterY * 0.7f);
    this.addText("Password:", "LEFT", iconLeftX, iconCenterY * 1.2f);
    usernameInput.show();
    passwordInput.show();
  }
}
public class SocialMediaLogoutScreen extends Screen{
  
  // Creating a public constructor for the SearchTravelScreen class, so that
  // an instance of it can be declared in the main sketch
  public SocialMediaLogoutScreen(int col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon noIcon = new Icon(appWidth * 0.3f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "No", true, "SettingsScreen");
    Icon yesIcon = new Icon(appWidth * 0.7f, iconBottomY, appWidth * 0.4f, appHeight * 0.08f, buttonImage, "Yes", true, "SettingsScreen");
    
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
  }
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
