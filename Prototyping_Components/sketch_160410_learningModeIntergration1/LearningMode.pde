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
    rect(appWidth/3, appHeight/4, appWidth/3, appHeight/4);
    rect(495, 20, 140, 50);
    rect(20, 275, 150, 80);
    rect(245, 275, 140, 50);
    rect(500, 275, 140, 50);

    //For the text
    fill(255, 0, 0);
    text(favText, 25, 25, 150, 80);         
    text(homeText, 500, 25, 150, 80); 
    text(shakeMovementText, 25, 280, 150, 80); 
    text(captureText, 250, 280, 150, 80);
    text(switchCameraText, 450, 280, 150, 80); 

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