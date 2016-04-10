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

// This class contains the main Sketch for the Processing portion of this app
public class Sketch extends PApplet {

    /*-------------------------------------- Navigation/Status Variables -------------------------*/

    // Setting the default screen to be the LoadingScreen, so that when the app is loaded,
    // this is the first screen that is displayed. Since this global variable is available
    // throughout the sketch (i.e. within all classes as well as the main sketch) we will
    // use this variable to pass in the value of "iconLinkTo" when the icon is clicked on
    // within the checkMouseOver() method of the ClickableElement class. The variable will
    // then be tested against each of the potential screen names (in the this class's
    // switchScreens() function) to decide which class should have the showScreen() method
    // called on it i.e. (which screen should be displayed).
    public String currentScreen = "LoadingScreen";

    // Creating a string to store the name of the screen to which an icon should return the
    // user to i.e. if the user has arrived at screen B from screen A, then clicking
    // cancel on this screen should return them to screen A. They may also have come
    // to this screen from screen C screen, in which case the cancel button should
    // return them there. Defaulting this to be equal to the home screen. This variable will
    // be used in the Icon class's showIcon() method, to determine which screen to return a
    // user to, if the icon's link begins with a "-". This is a naming convention we created
    // so that it is clearer which icons link to an as of yet undetermined screen.
    public String returnTo = "HomeScreen";

    // Creating a global variable, which any icon can use to pass the name of the function
    // they link to. The value of this variable is set in the Icon class when an icon is clicked
    // on, and it's iconLinkTo value begins with a "_". This is a naming convention we created
    // so that it is clearer which icons link to screens and which link to functions.
    public String callFunction = "";

    // Creating a global variable, which can be used to determine if a user has clicked on an
    // element. Using a custom variable to store this, as opposed to using the Processing mousePressed
    // variable, as we want to differentiate between a mouse being clicked and a mouse being pressed
    // (i.e. clicking or scrolling). This variable will be set to true when a mousePressed() event
    // occurs, and then reset to false when a element identifies itself as having been clicked on,
    // or when a scrolling event is detected.
    public Boolean mouseClicked = false;

    // Creating public booleans, to monitor which functionalities are currently turned on/off.
    // If a user has autoSaveModeOn turned on, as defined by their user preferences, this boolean
    // will be true. They can then toggle this functionality on/off in the Settings screen, to choose
    // whether saving will be automatically enabled for all images.
    // If a user has learningModeOn turned on, as defined by their user preferences, this boolean
    // will be true. They can then toggle this functionality on/off in the Settings screen, to choose
    // whether they want to see the learning mode suggestions when they use the app or not.
    // If a user is logged in to Twitter, then senToTwitterOn will be changed to true. If they
    // then decided to disable/enable sending to Twitter for individual images (in SaveShareScreenA)
    // then this boolean will toggle on/off.
    // If a user has autoSaveModeOn turned on, then saveThisImageOn will also be set to true. If a user
    // then wants to turn off saving for an individual image, they can toggle this functionality on/off
    // in SaveShareScreenA, while not affecting their overall autoSaveModeOn setting. Images will only
    // ever save to the device when saveThisImageOn is set to true, regardless of whether autosaveMode
    // is on or off (to allow the user to make individual choices for each image.
    public Boolean autoSaveModeOn = true;
    public Boolean learningModeOn = false;
    public Boolean sendToTwitterOn = false;
    public Boolean saveThisImageOn = true;

    // Creating public booleans, which result screens (such as ShareSaveSuccessfulScreen) will use to
    // determine which actions were successfully completed i.e. if the image was shared, saved or
    // both. These variables will only ever be set to true when the action has completed successfully
    // and reset to false when the user goes back to the CameraLiveViewScreen
    public Boolean imageShared = false;
    public Boolean imageSaved = false;

    // Creating a private boolean, to detect whether an image merge is currently being completed
    // i.e. so no new images will be read in from the device's camera, or sent to the removeGreenScreen()
    // thread (to reduce the risk of the memory of the device becoming overloaded
    private Boolean imageMerging = false;

    // Creating a private boolean, to detect whether an image is currently being read in and keyed by
    // the removeGreenScreen() thread i.e. so no new images will be read in from the device's camera,
    // or sent to the removeGreenScreen() thread (to reduce the risk of the memory of the device becoming
    // overloaded
    private Boolean readingImage = false;

    /*-------------------------------------- Text Inputs -----------------------------------------*/
    // Creating two private variables, which will contain the TextInput object, as well as it's
    // value, that is currently in focus. These variables are initialised within the TextInput
    // class when an input has been clicked on
    public TextInput currentTextInput = null;
    public String currentTextInputValue = "";

    // Creating a global boolean, to determine if the keyboard is required. This value is set to
    // true within the TextInput class if a text input has been clicked on. While this value is
    // set to true, the draw function will call the KetaiKeyboard .show() method, to trigger the
    // device to display it's keyboard. Each time a mousePressed event occurs, this variable
    // is reset to false, so that if a user clicks anywhere else on the screen, the keyboard
    // will automatically close
    public Boolean keyboardRequired = false;

    /*-------------------------------------- XML Data --------------------------------------------*/
    // Creating an array of random locations based on the random location XML file in the
    // assets folder. Storing these in a separate XML file to the user preferences, so that
    // even if a user deletes all of their favourite locations, there will still be random
    // locations available to them.
    // Each location has a latLng attribute, which represents the latitude
    // and longitude of the location, a heading attribute which represents the left/right
    // positioning of the view (between 0 and 360) and a pitch attribute which represents
    // the up/down angle of the view (between -90 and 90).
    // In the original Google Street View URL (from the browser) i.e. the Colosseum
    // url was https://www.google.ie/maps/@41.8902646,12.4905161,3a,75y,90.81h,95.88t/data=!3m6!1e1!3m4!1sR8bILL5qdsO7_m5BHNdSvQ!2e0!7i13312!8i6656!6m1!1e1
    // the first two numbers after the @ represent the latitude and longitude, the number
    // with the h after it represents the heading, and the number with the t after it
    // seems to be to do with the pitch, but never works that way in this
    // method so I just decided the pitch value based on what looks good
    private XML[] randomLocations;

    // Creating a variable to store the full XML which will be read in from the user_preferences.xml file
    // so that it can later be resaved/reloaded as needed
    private XML userPreferencesXML;

    // Creating an array to store the XML elements which contain the favourite locations of the user, as
    // sourced from the userPreferencesXML data which will be stored in the variable above
    public XML[] favouriteLocationsData;

    // Creating an array to store the XML elements which contain the settings of the user, such as autosaveModeOn,
    // as sourced from the userPreferencesXML data which will be stored in the variable above
    private XML[] settingsData;

    /*-------------------------------------- Ketai Camera ----------------------------------------*/
    // Creating a global variable to store the ketaiCamera object, so that we can access the live stream
    // of images coming in from the device camera. The images from this camera will never be displayed
    // in the app, but will instead be passed to the removeGreenScree() method, so any green
    // background can be removed so the user can see themselves as if they are in the current location
    public KetaiCamera ketaiCamera;

    // Creating a global variable to store the number (identifier) of the device camera we want to view
    // at any given time. The front facing camera (on a device with more than one camera)
    // will be at index 1, and the rear camera will be at index 0. On a device with only
    // 1 camera (such as the college Nexus tablets) this camera will always be at index
    // 0, regardless of whether it is a front or back camera
    public int camNum;

    // Creating a variable to store a value of 1 or -1, to decide whether and image should be
    // flipped i.e. when using the front facing camera, the x scale should always be -1 to
    // avoid things being in reverse
    public int cameraScale = -1;

    // Creating a global variable, which will always be set to either 90 or -90 degrees, to ensure
    // that the live stream images from the camera area always oriented in the right rotation.
    // Initialising at -90 degrees, as we will be starting on the front facing camera
    public int cameraRotation = -90;

    /*-------------------------------------- KetaiSensor -----------------------------------------*/

    // Declaring an instance of the ketaiSensore class, so that we can later access the sensors
    // of this device i.e. the accelerometer.
    private KetaiSensor sensor;

    // Creating a public boolean to detect whether shakeMovementOn is enabled or not. When this functionality
    // is enabled, the user can use the accelerometer in the device "look up and down" within the
    // camera live view screen (in fact, they are just updating the heading and pitch values and then
    // requests are being sent to the Google Street View Image API to request new images, so as to imitate
    // the ability to "look around" them.
    public Boolean shakeMovementOn = false;

    // Creating a variable to store the current rotation of the device, based on the readings from
    // the device's accelerometer
    public int orientationRotation = 0;

    /*----------------------------------- Twitter ------------------------------------------------*/
    // Creating variables to store the API keys required for accessing the Twitter API.
    // Setting the Twitter consumer key and Twitter consumer secret key to be equal to our
    // developer keys. Setting the Twitter access token, and access secret token to be equal
    // to the user's tokens, as defined when they logged in using the TwitterLoginActivity.
    // If the user has not logged in, then these will merely be equal to empty strings, but
    // as the option to send the image to Twitter will not be available to them, this will
    // never be an issue.
    private String twitterOAuthConsumerKey = "huoLN2BllLtOzezay2ei07bzo";
    private String twitterOAuthConsumerSecret = "k2OgK1XmjHLBMBRdM9KKyu86GS8wdIsv9Wbk9QOdzObXzHYsjb";
    private String twitterOAuthAccessToken = TwitterLoginActivity.twitterUserAccessToken;
    private String twitterOAuthAccessTokenSecret = TwitterLoginActivity.twitterUserSecretToken;

    // Creating a new instance of the Twitter configuration builder, which will later have the
    // above API keys stored within it, so that it can be passed to the TwitterFactory to generate
    // a new instance of the Twitter class.
    private ConfigurationBuilder cb = new ConfigurationBuilder();

    // Declaring an instance of the Twitter class, which will be generated by the TwitterFactory class,
    // using the above API keys, which will have been setup on the configuration builder class. We
    // will then use the twitter instance to update the user's status i.e. send tweets on the user's
    // account (if they are logged in with Twitter)
    private Twitter twitter;

    /*-------------------------------------- Images ----------------------------------------------*/

    // Loading in the general background image, which will be used to "wipe" the screen each time the
    // switchScreens method is called. The purpose of this is that individual screens do not need to
    // contain their own backgrounds, and thus reduces the load on memory.
    private PImage generalPageBackgroundImage;

    // Creating a public compiledImage variable, which will store the PImage graphic which contains
    // the Google Street View image of the background, the keyed image of the user (from the currentImage,
    // as declared below), and the "Wish I Was Here" overlay image, which can then be saved and/or shared.
    // This image will also be displayed on the ImagePreviewScreen and the SaveShareScreenA screens.
    public PImage compiledImage;

    // Creating a global image variable, to store the currentImage. Currently, this is
    // used to return the keyed image of the user from the removeGreenScreen() thread.
    // It is then used on the CameraLiveViewScreen as an image ontop of the Google Street View
    // image, and eventually is added to the compiled image to create the final image the user
    // will save/send to Twitter
    public PImage currentImage;

    /*-------------------------------------- Sizing ----------------------------------------------*/
    // Declaring global variables, which will contain the width and height of the device's
    // display, so that these values can be reused throughout all classes (i.e. to calculate
    // more dynamic position/width/height's so that the interface responds to different
    // screen sizes
    public int appWidth;
    public int appHeight;

    // Declaring the icon positioning X and Y variables, which will be used globally to ensure that
    // the icons on each page all line up with one another. These measurements will all be based on
    // percentages of the app's display, and are initialised in the setup function of this sketch
    public float iconLeftX;
    public float iconRightX;
    public float iconCenterX;
    public float iconTopY;
    public float iconBottomY;
    public float iconCenterY;
    public float largeIconBottomY;
    public float screenTitleY;

    // Declaring the icon sizing variables, which will be used globally to ensure that there will be
    // consistency between the sizes of icons throughout the app. These measurements will all be based on
    // percentages of the app's display, and are initialised in the setup function of this sketch
    public float largeIconSize;
    public float smallIconSize;
    public float homeIconSize;

    // Declaring the default text size variables, so that there will be consistency with the sizing
    // of all text within the app
    float defaultTextSize;
    float screenTitleTextSize;

    /*-------------------------------------- Screens ---------------------------------------------*/
    // Declaring a new instance of each screen in the application, so that they can be accessed by the
    // draw function to be displayed when needed. The FavouritesScreen and the AboutScreen have been
    // declared as public, as they will need to be accessed from the HomeScreen class, to reset
    // their "loaded" boolean to false, so the scrolling of the screens can be reset before the user
    // views them again
    private HomeScreen myHomeScreen;
    private CameraLiveViewScreen myCameraLiveViewScreen;
    public FavouritesScreen myFavouritesScreen;
    private SettingsScreen mySettingsScreen;
    public AboutScreen myAboutScreen;
    private SearchScreen mySearchScreen;
    private SearchUnsuccessfulScreen mySearchUnsuccessfulScreen;
    private ImagePreviewScreen myImagePreviewScreen;
    private SaveShareScreenA mySaveShareScreenA;
    private SaveShareScreenB mySaveShareScreenB;
    private SharingScreen mySharingScreen;
    private ShareSaveSuccessfulScreen myShareSaveSuccessfulScreen;
    private ShareUnsuccessfulScreen myShareUnsuccessfulScreen;
    private ShareSaveUnsuccessfulScreen myShareSaveUnsuccessfulScreen;
    private SearchingScreen mySearchingScreen;
    private SocialMediaLogoutScreen mySocialMediaLogoutScreen;
    private LoadingScreen myLoadingScreen;

    /*-------------------------------------- Saving ------------------------------------------------*/
    // Creating a string that will hold the directory path of where the images will be saved to on the
    // device's external storage
    private String directory = "";

    // Creating a string to store the full path, including the directory (declared above) and the
    // filename of the most recent image. This path can then be used either to load the image back
    // in to attach it as media for a tweet (to send it out to Twitter) or to create a sharing
    // intent (so that the user can share the image with another app on their device).
    private String saveToPath;

    /*-------------------------------------- Google Street View Images ---------------------------*/
    // Creating a variable to store the API keys required for accessing the Google Street View Image API,
    // and the Google Geocoding API. Setting the key to be equal to our Google Developer browers developer key.
    private String googleBrowserApiKey = "AIzaSyB1t0zfCZYiYe_xXJQhkILcXnfxrnUdUyA";

    // Creating a String variable, to store the input value of the text box on the SearchScreen
    private String searchAddress;

    // Creating a string to store the searchAddress (as defined above) in the format required to add it
    // as a parameter to the request URL for the Google Geocoding API, which allows us to search for an address
    // and receive an XML response containing the relevant location details (latitude, longitude etc) so that
    // we can then use these to request a new image from the Google Street View Image API
    private String compiledSearchAddress;

    // Creating the global googleImage variables, which will be used to generate the request
    // URLs for the Google Geocoding API and the Google Street View Image API. Each location
    // has a latLng attribute, which represents the latitude and longitude of the location,
    // a heading attribute which represents the left/right positioning of the view (between
    // 0 and 360) and a pitch attribute which represents the up/down angle of the view
    // (between -90 and 90).
    // In the original Google Street View URL (from the browser) i.e. the Colosseum
    // url was https://www.google.ie/maps/@41.8902646,12.4905161,3a,75y,90.81h,95.88t/
    // data=!3m6!1e1!3m4!1sR8bILL5qdsO7_m5BHNdSvQ!2e0!7i13312!8i6656!6m1!1e1
    // the first two numbers after the @ represent the latitude and longitude, the number
    // with the h after it represents the heading, and the number with the t after it
    // seems to be to do with the pitch, but never works that way in this
    // method so I just decided the pitch value based on what looks good
    public String currentLocationName = "";
    public String googleImageLatLng;
    public float googleImagePitch;
    public float googleImageHeading;

    // Declaring the currentLocationImage variable, which will be initialised by loading in an image
    // from a request to the Google Street View Image API, using the location data held in the variables
    // above. It is then used on the CameraLiveViewScreen as an image below of the keyed image of the
    // user (currentImage), and eventually is added to the compiled image to create the final image the user
    // will save/send to Twitter
    public PImage currentLocationImage = null;


    /*-------------------------------------- Settings() ------------------------------------------*/
    // Overriding the Processing settings() method
    @Override
    public void settings() {
        // Setting the size of this sketch to be fullscreen, so that it will be responsive to the size
        // of the device it is running on
        fullScreen();

        // Locking the applications orientation to portrait, so that the image being read in from the
        // the camera is maintained, even when the device is rotated
        orientation(PORTRAIT);

        // Initialising the appWidth and appHeight variable with the width and height of the device's
        // display, so that these values can be reused throughout all classes (i.e. to calculate
        // more dynamic position/width/height's so that the interface responds to different
        // screen sizes
        appWidth = width;
        appHeight = height;
    }

    /*-------------------------------------- Setup() ---------------------------------------------*/
    // Overriding the Processing setup() method
    @Override
    public void setup() {
        println("Main Sketch is being reset");
        /*---------------------------------- XML Data --------------------------------------------*/
        // Initialising the array of random locations based on the random location XML file in the
        // assets folder. Storing these in a separate XML file to the user preferences, so that
        // even if a user deletes all of their favourite locations, there will still be random
        // locations available to them.
        randomLocations = loadXML("random_locations.xml").getChildren("location");

        // Loading in the user's preferences by calling this class's loadUserPreferencesXML() method, as this
        // process will need to be completed multiple times later on in the app (i.e. to save/delete favourite
        // locations, update settings such as autosave mode etc.
        loadUserPreferencesXML();

        /*---------------------------------- Ketai Camera ----------------------------------------*/
        // Calling the ketaiCamera constructor to initialise the camera with the same
        // width/height of the device, with a frame rate of 24.
        ketaiCamera = new KetaiCamera(this, appWidth, appHeight, 24);

        // Printing out the list of available cameras i.e. front/rear facing
        println(ketaiCamera.list());

        // Printing out the number of available cameras
        println("There are " + ketaiCamera.getNumberOfCameras() + " camera/s available on this device");

        // Checking if the device has more than one camera i.e. does it have a front and a rear facing camera?
        if (ketaiCamera.getNumberOfCameras() > 1) {
            // If there is more than one camera, then default to the front camera
            // (which exists at index 1, when there is more than 1 camera)
            camNum = 1;
        } else {
            // If there is only one camera, then default to that camera
            // (which exists at index 0)
            camNum = 0;
        }

        // Setting the camera to default to the front camera
        ketaiCamera.setCameraID(camNum);

        /*----------------------------------- Ketai Sensor ---------------------------------------*/
        // Initialising the Ketai Sensor, passing in an instance of the PApplet, so that we can access
        // the device's sensors
        sensor = new KetaiSensor(this);

        // Enabling the accelerometer of the device, so that we can use it to detect the device orientation
        // and to allow the user to "look up and down" in the CameraLiveViewScreen
        sensor.enableAccelerometer();

        // Starting the sensors that have been enabled (i.e. the accelerometer) so that the relevant
        // events will be triggered e.g. onAccelerometerEvent()
        sensor.start();

        /*----------------------------------- Twitter --------------------------------------------*/
        // Checking if the user has logged in to the app using their Twitter account, by accessing
        // the twitterLoggedIn static variable of the TwitterLoginActivity class
        if (TwitterLoginActivity.twitterLoggedIn) {

            // If a user is logged in to Twitter, then senToTwitterOn will be changed to true. If they
            // then decided to disable/enable sending to Twitter for individual images (in SaveShareScreenA)
            // then this boolean will toggle on/off.
            sendToTwitterOn = true;

            // Setting the Twitter OAuth Consumer keys to our applications credentials, as sourced from
            // our Twitter developer account
            cb.setOAuthConsumerKey(twitterOAuthConsumerKey);
            cb.setOAuthConsumerSecret(twitterOAuthConsumerSecret);

            // Setting the oAuth Access tokens to be equal to those supplied by the user when they logged
            // into Twitter in the TwitterLoginActivity
            cb.setOAuthAccessToken(twitterOAuthAccessToken);
            cb.setOAuthAccessTokenSecret(twitterOAuthAccessTokenSecret);

            // Initialising a new instance of the Twitter class, by passing the configuration builder,
            // which has been set up with the relevant API keys, to the TwitterFactory class. We
            // will then use the twitter instance to update the user's status i.e. send tweets on the user's
            // account (if they are logged in with Twitter)
            twitter = new TwitterFactory(cb.build()).getInstance();
        }

        /*----------------------------------- Images ---------------------------------------------*/

        // Loading in the general background image, which will be used to "wipe" the screen each time the
        // switchScreens method is called. The purpose of this is that individual screens do not need to contain
        // their own backgrounds, and thus reduces the load on memory.
        generalPageBackgroundImage = loadImage("generalPageBackgroundImage.png");

        // Initialising the currentImage to be equal to a plain black image. This is so that if the
        // currentImage get's referred to before the camera has started, it will just contain a plain
        // black screen. Creating this black image by using createImage to make it the full size
        // of the screen, and then setting each pixel in the image to black. This image will be
        // used to return the keyed image of the user from the removeGreenScreen() thread.
        // It is then used on the CameraLiveViewScreen as an image ontop of the Google Street View
        // image, and eventually is added to the compiled image to create the final image the user
        // will save/send to Twitter
        currentImage = createImage(appWidth, appHeight, RGB);
        currentImage.loadPixels();
        for (int i = 0; i < currentImage.pixels.length; i++) {
            // Setting every pixel of the image to be black
            currentImage.pixels[i] = color(0);
        }
        currentImage.updatePixels();

        /*---------------------------------- Sizing ----------------------------------------------*/
        // Initialising the icon positioning X and Y variables, which will be used globally to ensure that
        // the icons on each page all line up with one another. These measurements are all based on percentages
        // of the app's display width and height (as defined above)
        iconLeftX = (float) (appWidth * 0.15);
        iconRightX = (float) (appWidth * 0.85);
        iconCenterX = (float) (appWidth * 0.5);
        iconTopY = (float) (appHeight * 0.085);
        iconBottomY = (float) (appHeight * 0.87);
        iconCenterY = (float) (appHeight * 0.5);
        largeIconBottomY = iconBottomY - (largeIconSize / 2);
        screenTitleY = iconTopY;

        // Declaring the icon sizing variables, which will be used globally to ensure that there will be
        // consistency between the sizes of icons throughout the app. These measurements will all be based on
        // percentages of the app's display, and are initialised in the setup function of this sketch
        largeIconSize = (float) (appWidth * 0.25);
        smallIconSize = (float) (appWidth * 0.15);
        homeIconSize = (float) (largeIconSize * 1.3);

        // Declaring the default text size variables, so that there will be consistency with the sizing
        // of all text within the app
        defaultTextSize = (float) (appHeight * 0.035);
        screenTitleTextSize = (float) (appHeight * 0.07);

        /*---------------------------------- Screens ---------------------------------------------*/
        // Declaring a new instance of each screen in the application, so that they can be accessed by the
        // draw function to be displayed when needed. The FavouritesScreen and the AboutScreen have been
        // declared as public, as they will need to be accessed from the HomeScreen class, to reset
        // their "loaded" boolean to false, so the scrolling of the screens can be reset before the user
        // views them again. Passing the current instance of the Sketch class (this) to the constructors
        // of each screen, so that they can in turn pass it to their super class (Screen), which will
        // in turn pass it to it's super class (Rectangle). The purpose of this variable is so that
        // we can access the Processing library, along with other global methods and variables of the
        // main sketch class, from all other classes.
        myHomeScreen = new HomeScreen(this);
        myCameraLiveViewScreen = new CameraLiveViewScreen(this);
        myFavouritesScreen = new FavouritesScreen(this);
        mySettingsScreen = new SettingsScreen(this);
        myAboutScreen = new AboutScreen(this);
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
        myLoadingScreen = new LoadingScreen(this);

        /*---------------------------------- Saving ----------------------------------------------*/
        // Storing a string that tells the app where to store the images externally on the users device.
        // Using the Environment class to determine the path to the external storage directory, as well
        // as the Pictures directory, and then concatenating a name for a new directory ("WishIWasHere")
        // so that images saved from our app will be stored in their own folder.
        directory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES + "/WishIWasHere/";

        // Creating a new File object, using the directory path specified above
        File wishIWasHereDirectory = new File(directory);

        // Checking if this directory already exists
        if (wishIWasHereDirectory.isDirectory() == false) {
            // Since the directory does not already exist, using the File mkdirs() method to create it
            wishIWasHereDirectory.mkdirs();
            println("New directory created - " + directory);
        } else {
            println("Directory already exists");
        }
    }

    /*-------------------------------------- Draw() ----------------------------------------------*/
    // Overriding the Processing draw() method
    @Override
    public void draw() {
        // Clearing the background of the sketch by setting it to black
        background(0);

        // Calling the switchScreens() function to display the right screen by checking the
        // currentScreen variable and calling the showScreen() method on the appropriate screen.
        // The showScreen() method then calls the super class's (Screen) drawScreen() method,
        // which adds the icons and backgrounds to the screen. As each icon is displayed on each
        // call to it's showIcon() method, if a click occurs, it uses the checkMouseOver() method
        // it has inherited from the ClickableElement class, to see if the mouse was over it when
        // the click took place
        switchScreens();

        // Calling the checkFunctionCalls() method, to check if an icon in the sketch has requested
        // a function to be called. It does this by checking the callFunciton variable, and calling
        // the appropriate function if necessary.
        checkFunctionCalls();

        // Checking if the keyboard is required i.e. if a TextInput is currently in focus
        if (keyboardRequired) {
            KetaiKeyboard.show(this);
        } else {
            KetaiKeyboard.hide(this);
        }

        // Turning the camera on and off (if the current screen is the CameraLiveViewScreen and the
        // camera is not yet turned on, then start the camera, otherwise, if you are on any other screen,
        // stop the camera
        if (currentScreen.equals("CameraLiveViewScreen")) {
            if (!ketaiCamera.isStarted()) {
                ketaiCamera.start();
                println("CAM - Starting Ketai Camera");
            }
        }

        // Forcing the onCameraPreviewEvent() to be called, as ketaiCamera does not seem to be
        // calling it implicitly (as it would have done in Processing).
        if (currentScreen.equals("CameraLiveViewScreen") && imageMerging == false) {
            onCameraPreviewEvent();
        }
    }

    /*-------------------------------------- mousePressed() --------------------------------------*/
    // Overriding the Processing mousePressed() method
    @Override
    public void mousePressed() {
        // Setting mouseClicked to true, so that clicks can be dealt with separate to scrolling events
        mouseClicked = true;

        // Resetting keyboardRequired to false, so that the device's keyboard will be hidden (so that
        // users can click anywhere on the screen to hide the keyboard
        keyboardRequired = false;
    }

    /*-------------------------------------- keyPressed() ----------------------------------------*/
    // Overriding the Processing keyPressed() method
    @Override
    public void keyPressed() {

        // Getting the current input value of this text input (i.e. so that if a user clicks between different
        // text fields, they can resume their input instead of the TextInput becoming empty)
        currentTextInputValue = currentTextInput.getInputValue();

        // Checking if the key pressed is a coded value i.e. backspace etc
        if (key == CODED) {
            //println(key);
            // Checking if the key pressed was the backspace key
            if (keyCode == 67) {
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
            // This is a character key
            // Checking if the current length of the text in this TextInput exceeds it's maximum character length,
            // i.e. if this is the TextInput for adding a message to a tweet, then the maximum length will be set
            // to ensure that a user cannot exceed this
            if (currentTextInputValue.length() < currentTextInput.getMaxTextLength() - 1) {
                // Adding the character to currentTextInputValue string
                currentTextInputValue += key;
            } else {
                println("This text is too long");
            }

        }

        // Passing the currentTextInputValue string into the setInputValue of the currentTextInput field
        currentTextInput.setInputValue(currentTextInputValue);
    }

    /*-------------------------------------- Ketai Camera ----------------------------------------*/
    // Normally, this method would be implicitly called by the Ketai library, to alert the sketch
    // of the availability of a new frame. For some reason, this is not happening in Android Studio,
    // so instead explicitly calling this method from the draw() method in this class, only if we are
    // on the CameraLiveViewScreen.
    public void onCameraPreviewEvent() {
        //println("CAM - New frame available " + this.readingImage);

        // Checking if the Sketch is currently reading in and keying an image (as we only want to handle
        // one image at a time to avoid memory overload
        if (this.readingImage == false) {
            // Reading in a new frame from the ketaiCamera object
            //println("CAM - New frame about to be read");

            // Setting the readingImage variable to true so that no more images can be read in until
            // this one has been completed
            this.readingImage = true;

            // Reading in the newest frame from the Ketai Camera
            ketaiCamera.read();

            // Calling the removeGreenScreen thread, to remove the green background from the current
            // frame of Ketai Camera, and pass it back to the sketch by setting currentImage to be
            // equal to it's contents
            thread("removeGreenScreen");
        }
    }

    /*-------------------------------------- Ketai Sensor ----------------------------------------*/
    // This event is implicitly called by the Ketai library every time an accelerometer event occurs
    // i.e. every time the device moves
    public void onAccelerometerEvent(float accelerometerX, float accelerometerY, float accelerometerZ) {

        // Checking if we are currently on the CameraLiveViewScreen, as we are not using the accelerometer
        // information on any other screen
        if (currentScreen.equals("CameraLiveViewScreen")) {

            // Checking if shakeMovementOn is true i.e. has the user clicked the shakeMovementIcon, so that
            // they can now move their device to "look up and down" within the location they are currently
            // in
            if (shakeMovementOn) {

                // Using modulus to slow down how often the following code is called (i.e. it will currently
                // be called on every 4th frame)
                if (frameCount % 4 == 0) {

                    // Setting the image pitch to be equal to the accelerometerZ value, mapped from a range
                    // of 10 to -10, to a larger range of -90 to 90, as these are the maximum allowed values
                    // for the pitch of a Google Street View Image
                    googleImagePitch = map(round(accelerometerZ), 10, -10, -90, 90);

                    // Calling the loadGoogleImage() method in a thread, to load in a new version of the
                    // current location's image, with the pitch altered to reflect the new value assigned
                    // above
                    thread("loadGoogleImage");
                }
            }

            // Creating a temporary array for the screen icons which belong
            // to the camera live view screen
            Icon[] alteredIcons = myCameraLiveViewScreen.getScreenIcons();

            // Looping through the temporary array, to access each of the icons
            for (int i = 0; i < alteredIcons.length; i++) {
                // Determining the orientation of the device based on the value
                // of it's accelerometer on the X axis
                if (accelerometerX > 7) {
                    // The device is being turned to the left
                    // Setting the rotation of the icons to be 90 degrees
                    alteredIcons[i].setRotation(90);
                } else if (accelerometerX < -7) {
                    // The device is being turned to the right
                    // Setting the rotation of the icons to be -90 degrees
                    alteredIcons[i].setRotation(-90);
                } else {
                    // The device is standing straight up
                    // Setting the rotation of the icons to be 0 degrees
                    alteredIcons[i].setRotation(0);
                }
            }
        } else {
            // Since we are not currently on the CameraLiveViewScreen, resetting the shakeMovementOn
            // variable to false
            shakeMovementOn = false;
        }
    }

    /*-------------------------------------- SwitchScreens() -------------------------------------*/
    public void switchScreens() {
        // Adding the general background image. The purpose of this is that individual screens do not
        // need to contain their own backgrounds, and thus reduces the load on memory.
        image(generalPageBackgroundImage, appWidth / 2, appHeight / 2, appWidth, appHeight);

        // Checking if the String that is stored in the currentScreen variable
        // (which gets set in the ClickableElement class when an icon is clicked on) is
        // equal to a series of class Names (i.e. HomeScreen), and if it is, then show that screen.
        // The showScreen() method of the current screen needs to be called repeatedly,
        // as this is where additional functionality (such as checking of icons being
        // clicked on etc) are called.
        if (currentScreen.equals("HomeScreen")) {
            myHomeScreen.showScreen();
        } else if (currentScreen.equals("CameraLiveViewScreen")) {
            // Resetting imageSaved, imageShared and compiledImage, incase they have not already been reset
            imageSaved = false;
            imageShared = false;
            compiledImage = null;
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
            // Calling the fadeToScreen method, so that if a click occurs while on this screen, the
            // user will be taken to the "HomeScreen"
            fadeToScreen("HomeScreen");
        } else {
            println("This screen doesn't exist");
        }
    }

    /*-------------------------------------- CheckFunctionCalls() --------------------------------*/
    public void checkFunctionCalls(){
        // Checking if any screen's icons are trying to trigger any functions
        if (callFunction.equals("")) {
            // No function needs to be called
        } else if (callFunction.equals("_keepImage")) {
            keepImage();
        } else if (callFunction.equals("_toggleSavingOfCurrentImage")) {
            toggleSavingOfCurrentImage();
        } else if (callFunction.equals("_switchSendToTwitter")) {
            switchSendToTwitter();
        } else if (callFunction.equals("_switchCameraView")) {
            myCameraLiveViewScreen.switchCameraView();
        } else if (callFunction.equals("_addToFavourites")) {
            thread("addToFavourites");
        } else if (callFunction.equals("_switchLearningMode")) {
            switchLearningMode();
        } else if (callFunction.equals("_switchAutoSaveMode")) {
            switchAutoSaveMode();
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
        } else if (callFunction.equals("_switchShakeMovement")) {
            switchShakeMovement();
        } else if (callFunction.equals("_shareImageToDeviceApps")) {
            shareImageToDeviceApps();
        } else {
            println(callFunction + " - This function does not exist / cannot be triggered by this icon");
        }

        // Resetting the callFunction variable so that functions do not get called repeatedly
        callFunction = "";
    }

    /*-------------------------------------- KeepImage() -----------------------------------------*/
    public void keepImage() {
        callFunction = "";
        if (saveThisImageOn) {
            println("KEEP IMAGE - This image was saved. autoSaveModeOn = " + autoSaveModeOn + " and saveThisImageOn = " + saveThisImageOn);
            // Checking if Storage is available
            if (isExternalStorageWritable()) {
                if (saveImageToPhotoGallery()) {
                    currentScreen = sendToTwitterOn ? "SaveShareScreenB" : "ShareSaveSuccessfulScreen";
                } else {
                    println("Failed to save image");
                    currentScreen = "ShareSaveUnsuccessfulScreen";
                }
            }
        } else {
            println("KEEP IMAGE - This image was not saved. autoSaveModeOn = " + autoSaveModeOn + " and saveThisImageOn = " + saveThisImageOn);
            currentScreen = sendToTwitterOn ? "SaveShareScreenB" : "CameraLiveViewScreen";
        }
    }

    /*-------------------------------------- IsExternalStorageWritable()--------------------------*/
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

    /*-------------------------------------- SaveImageToPhotoGallery() ---------------------------*/
    public Boolean saveImageToPhotoGallery() {
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
                imageSaved = true;
            }
        }
        return successfull;
    }

    /*-------------------------------------- SaveImageLocally() ----------------------------------*/
    public Boolean saveImageLocally() {
        Boolean successfull = false;
        if (compiledImage.save(sketchPath("twitterImage.jpg"))) {
            println("Successfully saved image locally - " + sketchPath("twitterImage.jpg"));
            successfull = true;
        } else {
            println("Failed to save image locally - " + sketchPath("twitterImage.jpg"));
        }
        return successfull;
    }

    /*-------------------------------------- FadeToScreen() --------------------------------------*/
    public void fadeToScreen(String nextScreen) {
        if (mouseClicked) {
            if (currentScreen.equals("LoadingScreen")) {
                myLoadingScreen = null;
            }
            currentScreen = nextScreen;
            mousePressed = false;
            mouseClicked = false;
        }
    }

    /*-------------------------------------- AddToFavourites() -----------------------------------*/
    public void addToFavourites() {
        callFunction = "";

        Boolean favouriteLocation = false;

        int favLocationIndex = checkIfFavourite(currentLocationName);

        if (favLocationIndex > -1) {
            for (int i = 0; i < favouriteLocationsData.length; i++) {
                if (favouriteLocationsData[i].getString("name").equals(currentLocationName)) {
                    userPreferencesXML.removeChild(favouriteLocationsData[i]);
                    myFavouritesScreen.favTabs.remove(favLocationIndex);
                    saveUserPreferencesXML();
                    favouriteLocation = false;
                }
            }
        } else {
            XML newChild = userPreferencesXML.addChild("location");
            newChild.setString("name", currentLocationName);
            newChild.setString("latLng", googleImageLatLng);
            newChild.setString("heading", String.valueOf(googleImageHeading));
            newChild.setString("pitch", String.valueOf(googleImagePitch));
            saveUserPreferencesXML();

            FavouriteTab newFavTab = new FavouriteTab(this, currentLocationName, googleImageLatLng, googleImageHeading, googleImagePitch);
            myFavouritesScreen.favTabs.add(newFavTab);
            favouriteLocation = true;
        }

        checkFavIcon();
        println("FAV - Favourite location " + currentLocationName + " is now " + favouriteLocation);
    }

    /*-------------------------------------- SwitchLearningMode() --------------------------------*/
    public void switchLearningMode() {
        callFunction = "";
        learningModeOn = !learningModeOn;

        for (int i = 0; i < settingsData.length; i++) {
            if (settingsData[i].getString("name").equals("learningMode")) {
                settingsData[i].setString("on", learningModeOn.toString());
                saveUserPreferencesXML();
            }
        }

        if (learningModeOn) {
            mySettingsScreen.learningModeIcon.setImage(loadImage("toggleSwitchOnIconImage.png"));
        } else {
            mySettingsScreen.learningModeIcon.setImage(loadImage("toggleSwitchOffIconImage.png"));
        }

        println("Learning mode is now: " + learningModeOn);
    }

    /*-------------------------------------- SwitchAutoSaveMode() --------------------------------*/
    public void switchAutoSaveMode() {
        callFunction = "";
        autoSaveModeOn = !autoSaveModeOn;
        saveThisImageOn = autoSaveModeOn;


        for (int i = 0; i < settingsData.length; i++) {
            if (settingsData[i].getString("name").equals("autoSaveMode")) {
                settingsData[i].setString("on", autoSaveModeOn.toString());
                saveUserPreferencesXML();
            }
        }

        if (autoSaveModeOn) {
            mySettingsScreen.autoSaveIcon.setImage(loadImage("toggleSwitchOnIconImage.png"));
            mySaveShareScreenA.saveIcon.setImage(loadImage("saveIconOnImage.png"));
        } else {
            mySettingsScreen.autoSaveIcon.setImage(loadImage("toggleSwitchOffIconImage.png"));
            mySaveShareScreenA.saveIcon.setImage(loadImage("saveIconOffImage.png"));
        }

        println("Auto-save is now: " + autoSaveModeOn);
    }

    /*-------------------------------------- ToggleSavingOfCurrentImage() ------------------------*/
    public void toggleSavingOfCurrentImage() {
        saveThisImageOn = !saveThisImageOn;

        if (saveThisImageOn) {
            mySaveShareScreenA.saveIcon.setImage(loadImage("saveIconOnImage.png"));
        } else {
            mySaveShareScreenA.saveIcon.setImage(loadImage("saveIconOffImage.png"));
        }
    }

    /*-------------------------------------- SwitchShakeMovement() -------------------------------*/
    public void switchShakeMovement() {
        shakeMovementOn = !shakeMovementOn;
        if (shakeMovementOn) {
            myCameraLiveViewScreen.shakeIcon.setImage(loadImage("shakeIconOnImage.png"));
        } else {
            myCameraLiveViewScreen.shakeIcon.setImage(loadImage("shakeIconOffImage.png"));
        }
    }

    /*-------------------------------------- SwitchSentToTwitter() -------------------------------*/
    public void switchSendToTwitter() {
        if (TwitterLoginActivity.twitterLoggedIn) {
            sendToTwitterOn = !sendToTwitterOn;

            if (sendToTwitterOn) {
                mySaveShareScreenA.twitterIcon.setImage(loadImage("twitterAccountIconOnImage.png"));
            } else {
                mySaveShareScreenA.twitterIcon.setImage(loadImage("twitterAccountIconOffImage.png"));
            }

        } else {
            println("This user can not turn on Twitter, as they have not previously logged in with Twitter");
        }
    }

    /*-------------------------------------- SendTweet() -----------------------------------------*/
    public void sendTweet() {
        callFunction = "";
        if (sendToTwitterOn) {
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
            if (this.imageShared == false && this.imageSaved == false) {
                currentScreen = "CameraLiveViewScreen";
            } else {
                currentScreen = "ShareSaveSuccessfulScreen";
            }
            println("Twitter - No twitter account logged in");
        }
    }

    /*-------------------------------------- RemoveGreenScreen() ---------------------------------*/
    public void removeGreenScreen() {
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
        } catch (OutOfMemoryError e) {
            println("Green screen keying could not be completed - " + e);
            keyedImage = null;
            currentFrame = null;
            this.readingImage = false;
        }
    }

    /*-------------------------------------- MergeImages() ---------------------------------------*/
    public void mergeImages() {
        try {
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
            mergedImage.image(overlayImage, (float) (appWidth * 0.7), (float) (appHeight - (appWidth * 0.22)), (float) (appWidth * 0.55), (float) (appWidth * 0.3));
            mergedImage.endDraw();

            compiledImage = mergedImage.get();
            mergedImage = null;
            overlayImage = null;

            currentScreen = "ImagePreviewScreen";
        } catch (OutOfMemoryError e) {
            println("Could not save image - " + e);
            currentScreen = "ShareSaveUnsuccessfulScreen";
        }
        imageMerging = false;
        readingImage = false;
    }

    /*-------------------------------------- DisgardImage() --------------------------------------*/
    public void disgardImage() {
        compiledImage = null;
        currentScreen = "CameraLiveViewScreen";
    }

    /*-------------------------------------- SearchForLocation() ---------------------------------*/
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
        XML locationXML = loadXML("https://maps.googleapis.com/maps/api/geocode/xml?address=" + compiledSearchAddress + "&key=" + googleBrowserApiKey);

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

    /*-------------------------------------- GetRandomLocation() ---------------------------------*/
    public void getRandomLocation() {
        currentScreen = "SearchingScreen";
        switchScreens();

        println("Getting a random location");

        int randomIndex = round(random(randomLocations.length - 1));

        googleImageLatLng = randomLocations[randomIndex].getString("latLng");
        googleImageHeading = Float.parseFloat(randomLocations[randomIndex].getString("heading"));
        googleImagePitch = Float.parseFloat(randomLocations[randomIndex].getString("pitch"));

        currentLocationName = randomLocations[randomIndex].getString("name");

        println("Random location selected: " + currentLocationName);
        loadGoogleImage();
    }

    /*-------------------------------------- LoadGoogleImage() -----------------------------------*/
    public void loadGoogleImage() {
        println("Loading in a new image from Google");
        println("LatLng = " + googleImageLatLng);
        println("Heading = " + googleImageHeading);
        println("Pitch = " + googleImagePitch);
        currentLocationImage = loadImage("https://maps.googleapis.com/maps/api/streetview?location=" + googleImageLatLng + "&pitch=" + googleImagePitch + "&heading=" + googleImageHeading + "&key=" + googleBrowserApiKey + "&size=" + appWidth / 2 + "x" + appHeight / 2);
        println("Image successfully loaded");

        checkFavIcon();

        if (!currentScreen.equals("CameraLiveViewScreen")) {
            currentScreen = "CameraLiveViewScreen";
        }
    }

    /*-------------------------------------- CheckIfFavourite() ----------------------------------*/
    public int checkIfFavourite(String currentFavTitle) {

        println("FAV - Checking if " + currentFavTitle + " is a favourite location");
        int favIndex = -1;

        ArrayList<FavouriteTab> favouriteTabs = myFavouritesScreen.getFavTabs();

        for (int i = 0; i < favouriteTabs.size(); i++) {
            if (favouriteTabs.get(i).getFavLocationName().equals(currentFavTitle)) {
                favIndex = i;
            }
        }

        return favIndex;
    }

    /*-------------------------------------- CheckFavIcon() --------------------------------------*/
    public void checkFavIcon() {
        println("FAV - Switching favicon image");
        if (checkIfFavourite(currentLocationName) > -1) {
            myCameraLiveViewScreen.favIcon.setImage(loadImage("favIconYesImage.png"));
        } else {
            myCameraLiveViewScreen.favIcon.setImage(loadImage("favIconNoImage.png"));
        }
    }

    /*-------------------------------------- CheckTwitterLogin() ---------------------------------*/
    public void checkTwitterLogin() {
        println("Checking if Twitter logged in");
        if (TwitterLoginActivity.twitterLoggedIn) {
            currentScreen = "SocialMediaLogoutScreen";
            println("Twitter already logged in");
            println("In SKETCH - Twitter username = " + TwitterLoginActivity.twitterUserUsername);
            println("In SKETCH - Twitter userid = " + TwitterLoginActivity.twitterUserUserId);
            println("In SKETCH - Twitter access token = " + TwitterLoginActivity.twitterUserAccessToken);
            println("In SKETCH - Twitter secret token = " + TwitterLoginActivity.twitterUserSecretToken);
        }
    }

    /*-------------------------------------- LoadUserPreferencesXML() ----------------------------*/
    public void loadUserPreferencesXML() {
        File localUserPreferencesPath = new File(sketchPath("user_preferences.xml"));
        if (localUserPreferencesPath.exists()) {
            userPreferencesXML = loadXML(sketchPath("user_preferences.xml"));
        } else {
            userPreferencesXML = loadXML("user_preferences.xml");
        }

        println("USER PREFERENCES = " + userPreferencesXML);
        favouriteLocationsData = userPreferencesXML.getChildren("location");

        settingsData = userPreferencesXML.getChildren("setting");
        for (int i = 0; i < settingsData.length; i++) {
            if (settingsData[i].getString("name").equals("autoSaveMode")) {
                autoSaveModeOn = Boolean.parseBoolean(settingsData[i].getString("on"));
                saveThisImageOn = autoSaveModeOn;
            } else if (settingsData[i].getString("name").equals("learningMode")) {
                learningModeOn = Boolean.parseBoolean(settingsData[i].getString("on"));
            }
        }
    }

    /*-------------------------------------- SaveUserPreferencesXML() ----------------------------*/
    public void saveUserPreferencesXML() {
        saveXML(userPreferencesXML, sketchPath("user_preferences.xml"));
        loadUserPreferencesXML();
    }

    /*-------------------------------------- ShareImageToDeviceApps() ----------------------------*/
    public void shareImageToDeviceApps() {
        if (this.imageSaved == false) {
            saveImageToPhotoGallery();
        }
        createInstagramIntent(saveToPath);
    }

    /*-------------------------------------- CreateInstagramIntent() -----------------------------*/
    public void createInstagramIntent(String imagePath) {

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