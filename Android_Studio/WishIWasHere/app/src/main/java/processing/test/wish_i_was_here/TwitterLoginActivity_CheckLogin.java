package processing.test.wish_i_was_here;

import java.io.File;

import processing.core.PApplet;
import processing.data.XML;

public class TwitterLoginActivity_CheckLogin extends PApplet {
    public XML userPreferencesXML;
    private TwitterLoginActivity twitterLoginActivity;

    public TwitterLoginActivity_CheckLogin(TwitterLoginActivity _twitterLoginActivity){
        twitterLoginActivity = _twitterLoginActivity;
    }

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

                    TwitterLoginActivity.twitterLoggedIn = true;

                    TwitterLoginActivity.twitterUserUsername = settingsData[i].getString("username");
                    TwitterLoginActivity.twitterUserAccessToken = settingsData[i].getString("accessToken");
                    TwitterLoginActivity.twitterUserSecretToken = settingsData[i].getString("secretToken");
                    TwitterLoginActivity.twitterUserUserId = Long.parseLong(settingsData[i].getString("userId"));

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

        // Storing the user's Twitter details in their local user_preferences.xml file
        newTwitterAccountDetails.setString("name", "twitter");
        newTwitterAccountDetails.setString("username", TwitterLoginActivity.twitterUserUsername);
        newTwitterAccountDetails.setString("accessToken", TwitterLoginActivity.twitterUserAccessToken);
        newTwitterAccountDetails.setString("secretToken", TwitterLoginActivity.twitterUserSecretToken);
        newTwitterAccountDetails.setString("userId", String.valueOf(TwitterLoginActivity.twitterUserUserId));

        // Saving the current userPreferencesXML variable in the app's local user_preferences.xml file
        // so that the user's settings (and favourite locations) can be persisted between app sessions
        if(saveXML(userPreferencesXML, sketchPath("user_preferences.xml"))){
            println("TWITTER - New Twitter login details saved");
            println(userPreferencesXML);
        }
    }
}
