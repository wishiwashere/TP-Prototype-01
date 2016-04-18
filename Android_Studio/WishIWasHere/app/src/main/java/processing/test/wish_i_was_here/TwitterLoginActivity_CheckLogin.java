package processing.test.wish_i_was_here;

import processing.core.*;
import processing.data.XML;

import java.io.File;

public class TwitterLoginActivity_CheckLogin extends PApplet {

    // Creating a variable to store the full XML which will be read in from the user_preferences.xml file
    public XML userPreferencesXML;

    // Creating a private variable to store the instance of the TwitterLoginActivity class, which will be passed into
    // the constructors of this class when it is initialised. The purpose of this, is so that we can access the
    // methods, such as goToMainActivity(), from within this class, without having to make them static (as some
    // calls to methods on variables within the activity are not able to be called from static classes i.e.
    // .perfromClick() to trigger clicks on buttons)
    private TwitterLoginActivity twitterLoginActivity;

    /*-------------------------------------- Constructor() ---------------------------------------*/
    // Creating a public constructor for this class, which takes no parameters, as is required to reload
    // the sketch upon an additional Twitter login
    public TwitterLoginActivity_CheckLogin(){
    }

    // Creating a public constructor for this class, which takes in an instance of the TwitterLoginActivity class
    public TwitterLoginActivity_CheckLogin(TwitterLoginActivity _twitterLoginActivity){

        // Storing this instance of the TwitterLoginActivity in a local variable, for reasons described above
        twitterLoginActivity = _twitterLoginActivity;
    }

    /*-------------------------------------- Setup() ---------------------------------------------*/
    // Overriding the Processing setup() method
    @Override
    public void setup(){

        println("TWITTER - Checking if user is already logged into Twitter");

        // Creating a new File object, which contains the path to where the user's preferences will
        // have been stored locally within the app
        File localUserPreferencesPath = new File(sketchPath("user_preferences.xml"));

        // Checking if this path already exists i.e. does the user already have preferences stored within
        // the app, or is this their first time using the app
        if (localUserPreferencesPath.exists()) {

            println("TWITTER - User already has a preferences file stored");

            // Since this path already exists, then loading in the user's previously saved preferences
            // from the app's local files
            userPreferencesXML = loadXML(sketchPath("user_preferences.xml"));

            // Creating an array to store the XML elements which contain the settings of the user, such as autosaveModeOn,
            // as sourced from the userPreferencesXML data which will be stored in the variable above
            XML[] settingsData = userPreferencesXML.getChildren("setting");

            // Looping through all of the settings data of the app
            for (int i = 0; i < settingsData.length; i++) {

                // Finding the relevant elements which contain the value for the learningMode setting, so that
                // it's value can be updated to reflect the current setting in the app
                if (settingsData[i].getString("name").equals("twitter")) {
                    println("TWITTER - User already has a Twitter login stored");

                    // Setting the TwitterLoginActivity's twitterLoggedIn variable to true, as this user is already
                    // logged in
                    TwitterLoginActivity.twitterLoggedIn = true;

                    // Setting the TwitterLoginActivity's static variables to be equal to the relevant
                    // values of the attributes from the twitter settings elelement in the user_preferences.xml
                    // file
                    TwitterLoginActivity.twitterUserUsername = settingsData[i].getString("username");
                    TwitterLoginActivity.twitterUserAccessToken = settingsData[i].getString("accessToken");
                    TwitterLoginActivity.twitterUserSecretToken = settingsData[i].getString("secretToken");
                    TwitterLoginActivity.twitterUserUserId = Long.parseLong(settingsData[i].getString("userId"));

                    // Calling the goToMainActivity() method of the TwitterLoginActivity class, using the local
                    // variable which contains the instance of that class (as passed to the constructor of this
                    // class) so that the user can skip the Twitter login screen (as they are already logged in)
                    twitterLoginActivity.goToMainActivity();
                }
            }
        } else {
            println("TWITTER - User has no user preferences saved");

            // Since the user does not already have preferences stored within the app, loading in the
            // preferences from the default user_preferences.xml file, on our GitHub.io site, so that
            // we can update the user's default favourite locations remotely. This file will only
            // be loaded in the first time the user opens the app so that it can be used to generate the
            // default preferences for this user, and will be the template for their locally stored
            // preferences within the app
            userPreferencesXML = loadXML("https://wishiwashere.github.io/user_preferences.xml");
            println("TWITTER - " + userPreferencesXML);
        }
    }

    public void saveNewTwitterLoginDetails(){
        println("Twitter - About to save new login details");

        // Creating a new XML location element in the userPreferencesXML variable
        XML newTwitterAccountDetails = userPreferencesXML.addChild("setting");

        // Storing the user's Twitter details in the attributes of this new XML element
        newTwitterAccountDetails.setString("name", "twitter");
        newTwitterAccountDetails.setString("username", TwitterLoginActivity.twitterUserUsername);
        newTwitterAccountDetails.setString("accessToken", TwitterLoginActivity.twitterUserAccessToken);
        newTwitterAccountDetails.setString("secretToken", TwitterLoginActivity.twitterUserSecretToken);
        newTwitterAccountDetails.setString("userId", String.valueOf(TwitterLoginActivity.twitterUserUserId));

        // Saving the current userPreferencesXML variable in the app's local user_preferences.xml file
        // so that the user's settings can be persisted between app sessions
        if(saveXML(userPreferencesXML, sketchPath("user_preferences.xml"))){
            println("TWITTER - New Twitter login details saved for - " + newTwitterAccountDetails.getString("username"));
        }
    }
}
