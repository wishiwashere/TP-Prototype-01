public class AboutScreen extends Screen{
  
  // Creating a public constructor for the AboutScreen class, so that
  // instances of it can be created anywhere in the sketch
  public AboutScreen(color col){
    super(col);
    
    Icon homeIcon = new Icon(width - 50, 50, 50, 50, 0, #ffffff, "Home", false, "HomeScreen");
    
    allIcons = new Icon[1];
    allIcons[0] = homeIcon;
    
    screenTitle = "About";
  }
}