package processing.test.wish_i_was_here;

import ketai.sensors.KetaiSensor;
import processing.data.XML;
import processing.core.*;
import processing.core.PApplet;
import ketai.camera.*;
import ketai.ui.*;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

import twitter4j.*;
import twitter4j.conf.*;

public class Sketch extends PApplet {

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
    String ourOAuthAccessToken = TwitterLoginActivity.twitterUserAccessToken;
    String ourOAuthAccessTokenSecret = TwitterLoginActivity.twitterUserSecretToken;

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

    Boolean imageMerging = false;

    TextInput currentTextInput = null;
    String currentTextInputValue = "";

    // Creating a global variable which will contain the value of the most recent y position of the
    // mouse. This variable gets it's first value each time a mousePressed event occurs (i.e. when the
    // user first holds down the mouse). It will then be continually updated (on any pages that scroll)
    // and used to determine how much a user has scrolled, and move the contents of these page's accordingly
    float previousMouseY;
    float previousMouseX;

    PImage compiledImage;

    XML userPreferencesXML;
    XML[] favouriteLocationsData;
    XML[] settingsData;
    Boolean autoSaveModeOn = true;
    Boolean learningModeOn = false;

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
    Boolean imageShared = false;
    Boolean imageSaved = false;


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
    PImage generalPageBackgroundImage;

    Boolean mouseClicked = false;

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
    float homeIconSize;
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

    /*-------------------------------------- Ketai Sensor ------------------------------------------------*/

    KetaiSensor sensor;
    Boolean shakeMovementOn = false;
    Boolean orientationDetect = true;

    /*-------------------------------------- Built In Functions ------------------------------------------------*/
    @Override
    public void settings() {
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
    }

    @Override
    public void setup(){
        println("Main Sketch is being reset");
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

    /*---------------------------------- User Preferences XML ---------------------------------------*/
        loadUserPreferencesXML();

    /*-------------------------------------- Images ------------------------------------------------*/

        // Loading in the icon images, so that they can be accessed globally by all the screen classes. The
        // reason for loading these in the main sketch is that they only have to be loaded once, even if they are
        // reused on multiple pages
        generalPageBackgroundImage = loadImage("generalPageBackgroundImage.png");


    /*-------------------------------------- Sizing ------------------------------------------------*/

        // Initialising the icon positioning X and Y variables, which will be used globally to ensure that
        // the icons on each page all line up with one another. These measurements are all based on percentages
        // of the app's display width and height (as defined above
        iconLeftX = (float)(appWidth * 0.15);
        iconRightX = (float)(appWidth * 0.85);
        iconCenterX = (float)(appWidth * 0.5);
        iconTopY = (float)(appHeight * 0.085);
        iconBottomY = (float)(appHeight * 0.87);
        iconCenterY = (float)(appHeight * 0.5);
        largeIconSize = (float)(appWidth * 0.25);
        smallIconSize = (float)(appWidth * 0.15);
        homeIconSize = (float)(largeIconSize * 1.3);
        largeIconBottomY = iconBottomY - (largeIconSize/2);
        screenTitleY = iconTopY;

        // Initialising the defaultTextSize to be equal to a percentage of the app's current height
        defaultTextSize = (float)(appHeight * 0.035);

    /*-------------------------------------- Screens ------------------------------------------------*/
        // Creating the screens which will be used in this application. Setting a random background
        // colour for each of these screens so that transitions between them can be more obvious
        // (for testing purposes only). Note - setting a background color is optional. Depending on the
        // screen's constructor, you can pass in a background color, a background image, or nothing at
        // all if you want to default to white
        myHomeScreen = new HomeScreen(this);
        myCameraLiveViewScreen = new CameraLiveViewScreen(this);
        myFavouritesScreen = new FavouritesScreen(this);
        mySettingsScreen = new SettingsScreen(this);
        myAboutScreen = new AboutScreen (this);
        mySearchScreen = new SearchScreen(this);
        mySearchUnsuccessfulScreen = new SearchUnsuccessfulScreen(this);
        myImagePreviewScreen = new ImagePreviewScreen(this);
        mySaveShareScreenA = new SaveShareScreenA(this);
        mySaveShareScreenB = new SaveShareScreenB(this);
        mySharingScreen = new SharingScreen(this);
        myShareSaveSuccessfulScreen = new ShareSaveSuccessfulScreen(this);
        myShareUnsuccessfulScreen = new ShareUnsuccessfulScreen(this);
        myShareSaveUnsuccessfulScreen = new ShareSaveUnsuccessfulScreen(this);
        mySearchingScreen = new SearchingScreen(this);
        mySocialMediaLogoutScreen = new SocialMediaLogoutScreen(this);
        myLoadingScreen = new LoadingScreen(this, loadImage("loadingScreenImage.png"));

    /*-------------------------------------- Saving ------------------------------------------------*/

        // Storing a string that tells the app where to store the images, by default
        // it goes to the pictures folder and this string as it has WishIWasHereApp
        // it is creating a folder in the picture folder of the device
        directory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES  + "/WishIWasHereApp/";

        // Checking if the directory already exists. If not, then creating it.
        File dir = new File(directory);
        if(!dir.isDirectory()){
            File newDir = new File(directory);
            newDir.mkdirs();
            println("New directory created - " + directory);
        } else {
            println("Directory already exists");
        }

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

    /*----------------------------------- Twitter Tweeting -----------------------------------------*/
        //Setting up Twitter and informing twitter of the users credentials to our application can tweet
        // from the users twitter account and access their account
        cb.setOAuthConsumerKey(ourOAuthConsumerKey);
        cb.setOAuthConsumerSecret(ourOAuthConsumerSecret);
        cb.setOAuthAccessToken(ourOAuthAccessToken);
        cb.setOAuthAccessTokenSecret(ourOAuthAccessTokenSecret);

        twitter = new TwitterFactory(cb.build()).getInstance();

    /*----------------------------------- Ketai Sensor -----------------------------------------*/
        sensor = new KetaiSensor(this);
        sensor.enableAccelerometer();
        sensor.start();
    }

    @Override
    public void draw() {
        background(0);

        // Calling the monitorScreens() function to display the right screen by calling
        // the showScreen() method. This function then calls the super class's drawScreen()
        // method, which not only adds the icons and backgrounds to the screen, it also
        // asks the icons on the screen to call their checkMouseOver() method (inherited from
        // the Icon class) to see if they were clicked on when a mouse event occurs
        switchScreens();

        // Checking if any screen's icons are trying to trigger any functions
        if (callFunction.equals("")) {
            // No function needs to be called
        } else if (callFunction.equals("_keepImage")) {
            keepImage();
        } else if (callFunction.equals("_switchCameraView")) {
            myCameraLiveViewScreen.switchCameraView();
        } else if (callFunction.equals("_addToFavourites")) {
            thread("addToFavourites");
        } else if (callFunction.equals("_switchLearningMode")) {
            switchLearningMode();
        } else if (callFunction.equals("_switchAutoSave")) {
            switchAutoSave();
        } else if (callFunction.equals("_sendTweet")) {
            sendTweet();
        } else if (callFunction.equals("_mergeImages")) {
            mergeImages();
        } else if (callFunction.equals("_disgardImage")) {
            disgardImage();
        } else if (callFunction.equals("_searchForLocation")) {
            searchForLocation();
        } else if (callFunction.equals("_getRandomLocation")) {
            getRandomLocation();
        } else if (callFunction.equals("_checkTwitterLogin")) {
            checkTwitterLogin();
        } else if(callFunction.equals("_switchShakeMovement")){
            switchShakeMovement();
        } else if(callFunction.equals("_shareImageToDeviceApps")) {
            shareImageToDeviceApps();
        } else {
            println(callFunction + " - This function does not exist / cannot be triggered by this icon");
        }

        // Checking if the keyboard is required i.e. if an input field is currently in focus
        if (keyboardRequired) {
            KetaiKeyboard.show(this);
            callFunction = "";
        } else {
            KetaiKeyboard.hide(this);
            callFunction = "";
        }

        // Forcing the onCameraPreviewEvent() to be called, as ketaiCamera does not seem to be
        // calling it implicitly (as it would have done in Processing).
        if(currentScreen.equals("CameraLiveViewScreen") && imageMerging == false){
            println("CAM - Calling onCameraPreviewEvent");
            onCameraPreviewEvent();
        } else {
            println("CAM - Not on camera live view screen and imageMerging is currently true");
        }
    }

    @Override
    public void mousePressed() {
        mouseClicked = true;
        keyboardRequired = false;
        previousMouseY = mouseY;
        previousMouseX = mouseX;
    }

    @Override
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
            if(currentTextInputValue.length() < currentTextInput.getMaxTextLength() - 1){
                // If the key is not a coded value, adding the character to currentTextInputValue string
                currentTextInputValue += key;
            }else{
                println("This text is too long");
            }

        }
        // Passing the currentTextInputValue string into the setInputValue of the currentTextInput field
        currentTextInput.setInputValue(currentTextInputValue);
    }

    /*-------------------------------------- Ketai Functions ------------------------------------------------*/

    // ketaiCamera event which is automatically called everytime a new frame becomes
    // available from the ketaiCamera.
    public void onCameraPreviewEvent()
    {
        println("CAM - New frame available " + this.readingImage);
        if (this.readingImage == false) {
            // Reading in a new frame from the ketaiCamera.
            println("CAM - New frame about to be read");
            this.readingImage = true;
            ketaiCamera.read();

            thread("previewGreenScreen");
        }
    }

    public void onAccelerometerEvent(float accelerometerX, float accelerometerY, float accelerometerZ){
        if (currentScreen.equals("CameraLiveViewScreen")) {
            if (shakeMovementOn) {
                if (frameCount % 4 == 0) {
                    println("The value of X is " + (accelerometerX));
                    googleImagePitch = map(round(accelerometerZ), 10, -10, -90, 90);
                    thread("loadGoogleImage");
                }
            }
            Icon[] alteredIcons = myCameraLiveViewScreen.getScreenIcons();
            for (int i = 0; i < alteredIcons.length; i++) {
                //println(i);
                if (accelerometerX > 7) {
                    //println("Device is being turned to the left");
                    alteredIcons[i].setRotation(90);
                }else if (accelerometerX < -7) {
                    //println("Device is being turned to the right");
                    alteredIcons[i].setRotation(-90);
                }else {
                    //println("Device standing straight");
                    alteredIcons[i].setRotation(0);
                }

            }
        }else {
            shakeMovementOn = false;
        }
    }

    /*-------------------------------------- Custom Functions ------------------------------------------------*/
    public void switchScreens() {
        image(generalPageBackgroundImage, appWidth / 2, appHeight / 2, appWidth, appHeight);

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
            imageSaved = false;
            imageShared = false;
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
        } else if (currentScreen.equals("ImagePreviewScreen")) {
            myImagePreviewScreen.showScreen();
        } else if (currentScreen.equals("SaveShareScreenA")) {
            returnTo = "SaveShareScreenA";
            mySaveShareScreenA.showScreen();
        } else if (currentScreen.equals("SaveShareScreenB")) {
            mySaveShareScreenB.showScreen();
        } else if (currentScreen.equals("SharingScreen")) {
            mySharingScreen.showScreen();
        } else if (currentScreen.equals("ShareSaveSuccessfulScreen")) {
            myShareSaveSuccessfulScreen.showScreen();
        } else if (currentScreen.equals("ShareUnsuccessfulScreen")) {
            myShareUnsuccessfulScreen.showScreen();
        } else if (currentScreen.equals("ShareSaveUnsuccessfulScreen")) {
            myShareSaveUnsuccessfulScreen.showScreen();
        } else if (currentScreen.equals("SearchingScreen")) {
            mySearchingScreen.showScreen();
        } else if (currentScreen.equals("SocialMediaLogoutScreen")) {
            mySocialMediaLogoutScreen.showScreen();
        } else if (currentScreen.equals("LoadingScreen")) {
            myLoadingScreen.showScreen();
            fadeToScreen("HomeScreen");
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
                println("CAM - Starting Ketai Camera");
            }
            imageMerging = false;
        } else if (ketaiCamera.isStarted()) {
            imageMerging = true;
        }
    }

    public void keepImage() {
        callFunction = "";
        if(autoSaveModeOn) {
            // Checking if Storage is available
            if (isExternalStorageWritable()) {
                if (saveImageToPhotoGallery()) {
                    currentScreen = "SaveShareScreenA";
                } else {
                    println("Failed to save image");
                }
            }
        } else {
            currentScreen = "SaveShareScreenA";
        }
    }

    public Boolean saveImageToPhotoGallery(){
        Boolean successfull = false;
        if (isExternalStorageWritable()) {
            saveToPath = directory + "WishIWasHere-" + day() + month() + year() + "-" + hour() + minute() + second() + ".jpg";
            // Trying to save out the image. Putting this code in an if statement, so that if it fails, a message will be logged
            if (compiledImage.save(saveToPath)) {
                println("Successfully saved image to - " + saveToPath);
                successfull = true;
                imageSaved = true;
            } else {
                println("Failed to save image");
            }
        }
        return successfull;
    }

    public Boolean saveImageLocally(){
        Boolean successfull = false;
        if (compiledImage.save(sketchPath("twitterImage.jpg"))) {
            println("Successfully saved image locally - " + sketchPath("twitterImage.jpg"));
            successfull = true;
        } else {
            println("Failed to save image locally - " + sketchPath("twitterImage.jpg"));
        }
        return successfull;
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

    public void fadeToScreen(String nextScreen) {
        if (mouseClicked)
        {
            currentScreen = nextScreen;
            mousePressed = false;
            mouseClicked = false;
        }
    }

    public void addToFavourites() {
        callFunction = "";
        int favLocationIndex = checkIfFavourite(currentLocationName);

        if(favLocationIndex > -1){
            for(int i = 0; i < favouriteLocationsData.length; i++){
                if(favouriteLocationsData[i].getString("name").equals(currentLocationName)){
                    userPreferencesXML.removeChild(favouriteLocationsData[i]);
                    myFavouritesScreen.favTabs.remove(favLocationIndex);
                    myCameraLiveViewScreen.favouriteLocation = false;
                    saveUserPreferencesXML();
                }
            }
        } else {
            XML newChild = userPreferencesXML.addChild("location");
            newChild.setString("name", currentLocationName);
            newChild.setString("latLng", googleImageLatLng);
            newChild.setString("heading", String.valueOf(googleImageHeading));
            newChild.setString("pitch", String.valueOf(googleImagePitch));
            saveUserPreferencesXML();

            FavouriteTab newFavTab = new FavouriteTab(this, currentLocationName, googleImageLatLng + "&heading=" + googleImageHeading + "&pitch=" + googleImagePitch, myFavouritesScreen.favTabs.size() - 1);
            myFavouritesScreen.favTabs.add(newFavTab);
            myCameraLiveViewScreen.favouriteLocation = true;
        }

        checkFavIcon();
        println("FAV - Favourite location " + currentLocationName + " is now: " + myCameraLiveViewScreen.favouriteLocation);
    }

    public void switchLearningMode() {
        callFunction = "";
        mySettingsScreen.learningModeOn = !mySettingsScreen.learningModeOn;
        println("Learning mode is now: " + mySettingsScreen.learningModeOn);
    }

    public void switchAutoSave() {
        callFunction = "";
        autoSaveModeOn = !autoSaveModeOn;

        for(int i = 0; i < settingsData.length; i++){
            if(settingsData[i].getString("name").equals("autoSaveMode")){
                settingsData[i].setString("turnedOn", autoSaveModeOn.toString());
                saveUserPreferencesXML();
            }
        }

        println("Auto-save is now: " + autoSaveModeOn);
    }

    public void switchShakeMovement(){
        shakeMovementOn = !shakeMovementOn;
    }


    public void sendTweet() {
        callFunction = "";
        if(TwitterLoginActivity.twitterLoggedIn) {
            currentScreen = "SharingScreen";
            mySharingScreen.showScreen();

            // Creating a string to to hold the value that is in the message input
            String message = mySaveShareScreenB.messageInput.getInputValue();

            //making the current screen "Sharing Screen"
            currentScreen = "SharingScreen";
            try {
                StatusUpdate status = new StatusUpdate(message + " #WishIWasHere");
                //System.out.println("Status updated to [" + status.getText() + "].");
                if (saveImageLocally()) {
                    File twitterImage = new File(sketchPath("twitterImage.jpg"));
                    status.setMedia(twitterImage);
                }
                twitter.updateStatus(status);
                // Making a twitter status that will hold the message the user typed
                // and adding the Wish I Was Here tag onto the end of the message

                this.imageShared = true;

                //Changing the current Screen
                currentScreen = "ShareSaveSuccessfulScreen";

                //Cleaing the message input so it is empty the next time the user
                // arrives to send another tweet
                mySaveShareScreenB.messageInput.clearInputValue();
            } catch (TwitterException te) {
                //If the tweet can't be sent, it will print out the reason that
                // is causing the problem
                System.out.println("Error: " + te.getMessage());

                //Changing the current screen to be the unsuccessul share screen
                currentScreen = "ShareUnsuccessfulScreen";
            }
        } else {
            if(this.imageShared == false && this.imageSaved == false){
                currentScreen = "CameraLiveViewScreen";
            } else {
                currentScreen = "ShareSaveSuccessfulScreen";
            }
            println("Twitter - No twitter account logged in");
        }
    }

    public void previewGreenScreen() {
        PImage currentFrame;
        PImage keyedImage;

        try {
            println("Starting removing Green Screen at frame " + frameCount);

            currentFrame = ketaiCamera.get();

            // Changing the colour mode to HSB, so that I can work with the hue, satruation and
            // brightness of the pixels. Setting the maximum hue to 360, and the maximum saturation
            // and brightness to 100.
            colorMode(HSB, 360, 100, 100);

            keyedImage = createImage(currentFrame.width, currentFrame.height, ARGB);

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
                    if (pixelSaturation > 30 && pixelBrightness > 20) {
                        // Set this pixel in the keyedImage to be transparent (Removing the main areas of the green)
                        keyedImage.pixels[i] = color(0, 0, 0, 0);
                    } else {
                        // Even though this pixel falls within the green range of the colour spectrum, it's saturation and brightness
                        // are low enough that it is unlikely to be a part of the green screen, but may just be an element of the scene
                        // that is picking up a glow off the green screen. Lowering the hue and saturation to remove the green tinge
                        // from this pixel.
                        keyedImage.pixels[i] = color((int) (pixelHue * 0.6), (int) (pixelSaturation * 0.3), (int) (pixelBrightness));
                    }
                }
            }

            // Updating the pixel arrays of the ketaiCamera and the keyed image
            keyedImage.updatePixels();

            // Resetting the color mode to RGB
            colorMode(RGB, 255, 255, 255);

            currentImage = keyedImage.get();

            keyedImage = null;
            currentFrame = null;

            readingImage = false;
            println("Finished removing Green Screen at frame " + frameCount);
        } catch(OutOfMemoryError e){
            println("Green screen keying could not be completed - " + e);
            keyedImage = null;
            currentFrame = null;
            this.readingImage = false;
        }
    }

    public void mergeImages() {
        PImage overlayImage = loadImage("overlay.png");
        imageMerging = true;

        PGraphics mergedImage = createGraphics(appWidth, appHeight, JAVA2D);
        mergedImage.beginDraw();
        mergedImage.imageMode(CENTER);
        mergedImage.image(currentLocationImage, appWidth / 2, appHeight / 2, appWidth, appHeight);
        mergedImage.endDraw();

        mergedImage.beginDraw();
        mergedImage.pushMatrix();
        mergedImage.translate(appWidth / 2, appHeight / 2);
        mergedImage.scale(cameraScale, 1);
        mergedImage.rotate(radians(cameraRotation));
        mergedImage.imageMode(CENTER);
        mergedImage.image(currentImage, 0, 0, appHeight, appWidth);
        mergedImage.popMatrix();
        mergedImage.endDraw();

        mergedImage.beginDraw();
        mergedImage.imageMode(CENTER);
        mergedImage.image(overlayImage, (float) (appWidth * 0.7), (float)(appHeight - (appWidth * 0.22)), (float) (appWidth * 0.55), (float) (appWidth * 0.3));
        mergedImage.endDraw();

        compiledImage = mergedImage.get();
        mergedImage = null;
        overlayImage = null;

        currentScreen = "ImagePreviewScreen";
        imageMerging = false;
        readingImage = false;
    }

    public void disgardImage() {
        compiledImage = null;
        currentScreen = "CameraLiveViewScreen";
    }

    public void searchForLocation() {
        currentScreen = "SearchingScreen";
        switchScreens();

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
        currentScreen = "SearchingScreen";
        switchScreens();

        println("Getting a random location");
        String randomLocationURLData = myFavouritesScreen.getRandomFavourite();
        googleImageLatLng = randomLocationURLData.split("@")[1].split("&")[0];
        googleImageHeading = Float.parseFloat(randomLocationURLData.split("heading=")[1].split("&")[0]);
        googleImagePitch = Float.parseFloat(randomLocationURLData.split("pitch=")[1]);

        currentLocationName = randomLocationURLData.split("@")[0];

        loadGoogleImage();
    }

    public void loadGoogleImage() {
        println("Loading in a new image from Google");
        println("LatLng = " + googleImageLatLng);
        println("Heading = " + googleImageHeading);
        println("Pitch = " + googleImagePitch);
        currentLocationImage = loadImage("https://maps.googleapis.com/maps/api/streetview?location=" + googleImageLatLng + "&pitch=" + googleImagePitch + "&heading=" + googleImageHeading + "&key=" + ourBrowserApiKey + "&size=" + appWidth / 2 + "x" + appHeight / 2);
        println("Image successfully loaded");

        checkFavIcon();

        if (!currentScreen.equals("CameraLiveViewScreen")) {
            currentScreen = "CameraLiveViewScreen";
        }
    }

    public int checkIfFavourite(String currentFavTitle) {

        println("FAV - Checking if " + currentFavTitle + " is a favourite location");
        int favIndex = -1;

        ArrayList<FavouriteTab> favouriteTabs = myFavouritesScreen.getFavTabs();

        for (int i = 0; i < favouriteTabs.size(); i++) {
            if (favouriteTabs.get(i).getFavTitle().equals(currentFavTitle)) {
                favIndex = i;
            }
        }

        return favIndex;
    }

    public void checkFavIcon() {
        println("FAV - Switching favicon image");
        if (checkIfFavourite(currentLocationName) > -1) {
            myCameraLiveViewScreen.favIcon.setImage(loadImage("favIconYesImage.png"));
        } else {
            myCameraLiveViewScreen.favIcon.setImage(loadImage("favIconNoImage.png"));
        }
    }

    public void checkTwitterLogin(){
        println("Checking if Twitter logged in");
        if(TwitterLoginActivity.twitterLoggedIn) {
            println("Twitter already logged in");
            println("In SKETCH - Twitter username = " + TwitterLoginActivity.twitterUserUsername);
            println("In SKETCH - Twitter userid = " + TwitterLoginActivity.twitterUserUserId);
            println("In SKETCH - Twitter access token = " + TwitterLoginActivity.twitterUserAccessToken);
            println("In SKETCH - Twitter secret token = " + TwitterLoginActivity.twitterUserSecretToken);
        }
    }

    public void loadUserPreferencesXML(){
        File localUserPreferencesPath = new File(sketchPath("user_preferences.xml"));
        if(localUserPreferencesPath.exists()){
            userPreferencesXML = loadXML(sketchPath("user_preferences.xml"));
        } else {
            userPreferencesXML = loadXML("user_preferences.xml");
        }

        println("USER PREFERENCES = " + userPreferencesXML);
        favouriteLocationsData = userPreferencesXML.getChildren("location");

        settingsData = userPreferencesXML.getChildren("setting");
        for(int i = 0; i < settingsData.length; i++){
            if(settingsData[i].getString("name").equals("autoSaveMode")){
                autoSaveModeOn = Boolean.parseBoolean(settingsData[i].getString("turnedOn"));
            }
        }
    }

    public void saveUserPreferencesXML(){
        saveXML(userPreferencesXML, sketchPath("user_preferences.xml"));
        loadUserPreferencesXML();
    }

    public void shareImageToDeviceApps(){
        if(this.imageSaved == false) {
            saveImageToPhotoGallery();
        }
        createInstagramIntent(saveToPath);
    }

    public void createInstagramIntent(String imagePath){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType("image/*");

        // Create the URI from the media
        File media = new File(imagePath);
        Uri uri = Uri.fromFile(media);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
        this.getActivity().finish();
    }
}