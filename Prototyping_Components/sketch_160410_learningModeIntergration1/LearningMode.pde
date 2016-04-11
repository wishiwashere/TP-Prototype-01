public class LearningMode extends ClickableElement {

  public String favText = "Tap on the star to save your current location to Favourites";
  public String homeText = "Tap here to return home";
  public String shakeMovementText = "Tap here to enable movement. By tilting your phone forwards and backwards you can look up and down. Or swiping left or right you can turn around in your location";
  public String captureText = "Tap here to take a picture";
  public String switchCameraText = "Tap here to switch camera views";


  /* -------------------------- Constructor -------------------------------*/
  // This constructor is used by icons such as the homeIcon, that want to accept the default 
  // width and height of an icon, and also link to another page
  public learningMdoe(float x, float y, float w, float h, String learningText) {

    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, w, h, learningText);
  }

  /*-------------------------------------------------------------------------*/

  public void showLearningMode() {
    fill(0, 255, 0);
    rect(appWidth/32, appHeight/16, appWidth/4, appHeight/6);
    rect(appWidth/1.4, appHeight/16, appWidth/4, appHeight/6);
    rect(appWidth/32, appHeight/1.3, appWidth/4, appHeight/4.8);
    rect(appWidth/2.8, appHeight/1.3, appWidth/4, appHeight/6);
    rect(appWidth/1.4, appHeight/1.3, appWidth/4, appHeight/6);

    //For the text
    fill(255, 0, 0);
    text(favText, appWidth/24.5, appHeight/14.2, appWidth/4, appHeight/6);         
    text(homeText, appWidth/1.38, appHeight/14.2, appWidth/4, appHeight/6); 
    text(shakeMovementText, appWidth/24.5, appHeight/1.29, appWidth/4, appHeight/4.8); 
    text(captureText, appWidth/2.7, appHeight/1.29, appWidth/4, appHeight/6);
    text(switchCameraText, appWidth/1.38, appHeight/1.29, appWidth/4, appHeight/6); 

    //for learning mode box
    fill(48, 2, 125, 100);
  
  
  if (mousePressed) {
    learningModeOn = false;
  }

  if (learningModeOn == false) {
    fill(0);
    rect(rectX, rectY, 50, 50);
    fill(0, 0, 0, 0);
  }
  rect(0, 0, 640, 360);

}