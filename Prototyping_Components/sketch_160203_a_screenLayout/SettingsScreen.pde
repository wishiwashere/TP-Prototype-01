public class SettingsScreen extends Screen{
  
  // Creating a public constructor for the SettingsScreen class, so that
  // instances of it can be created anywhere in the sketch
  public SettingsScreen(color col){
    super(col);
    
    Icon homeIcon = new Icon(width/2, 100, 260, 50, 0, #ffffff, "HomeScreen", "Go Home");
    
    allIcons = new Icon[1];
    allIcons[0] = homeIcon;
  }
}