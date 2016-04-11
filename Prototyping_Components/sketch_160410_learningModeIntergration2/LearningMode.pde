public class LearningMode extends Screen {

  private String favText = "Save your current location";
  private String homeText = "Return to home";
  private String shakeMovementText = "Enable movement. By tilting your phone forwards and backwards you can look up and down";
  private String captureText = "Take a picture";
  private String switchCameraText = "Switch camera views";
  private String movement = "Swipe left or right to move around your current location";

  //public Boolean learningModeOn = true;
  
  public void showLearningMode() {
    //Colour for the background of the learning mode box, filling it a light shade od grey
    fill(240, 240, 240);
    rect(appWidth/32, appHeight/16, appWidth/4, appHeight/6);
    rect(appWidth/1.4, appHeight/16, appWidth/4, appHeight/6);
    rect(appWidth/32, appHeight/1.3, appWidth/4, appHeight/4.8);
    rect(appWidth/2.8, appHeight/1.3, appWidth/4, appHeight/6);
    rect(appWidth/1.4, appHeight/1.3, appWidth/4, appHeight/6);

    //For the text
    fill(33, 33, 33);
    text(favText, appWidth/24.5, appHeight/14.2, appWidth/4, appHeight/6);         
    text(homeText, appWidth/1.38, appHeight/14.2, appWidth/4, appHeight/6); 
    text(shakeMovementText, appWidth/24.5, appHeight/1.29, appWidth/4, appHeight/4.8); 
    text(captureText, appWidth/2.7, appHeight/1.29, appWidth/4, appHeight/6);
    text(switchCameraText, appWidth/1.38, appHeight/1.29, appWidth/4, appHeight/6); 

    //for learning mode box
    fill(33, 33, 33, 100);

    if (mousePressed) {
      learningModeOn = false;
    }

    if (learningModeOn == false) {
      fill(0);
    }
    rect(0, 0, appWidth, appHeight);
  }
}