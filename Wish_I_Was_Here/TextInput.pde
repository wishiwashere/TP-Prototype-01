public class TextInput extends ClickableElement{
  
  // Creating private variables to store the TextInput's title and value
  // properties, so that they can only be accessed within this class
  private String inputTitle = "";
  private String inputValue = "";
  
  /*-------------------------------------- Constructor() ------------------------------------------------*/
  
  // This constructor is used by all text inputs
  public TextInput(float x, float y, float w, float h, color col, String title){

    // Passing the relevant parametres from the constructor into the constructor 
    // of the super class (ClickableElement)
    super(x, y, w, h, col, title);
    
    // Initialising the inputTitle to be equal to the requested title
    inputTitle = title;
  }
  
  /*-------------------------------------- showTextInput() ------------------------------------------------*/
  
  // Creating a public showTextInput function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  public void showTextInput(){
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();
    
    // Checking if the length of the value is greater than 0 i.e. 
    if(this.inputValue.length() > 0){
      this.addText(this.inputValue, "LEFT", this.getX() - (this.getWidth() * 0.45), this.getY(), this.getWidth() * 0.1);
    }
    if(mousePressed){
      if(this.checkMouseOver()){
        keyboardRequired = true;
        currentTextInput = this;
        currentTextInputValue = "";
        println("The " + inputTitle + " text input was clicked on");
      }
    }
  }
  
  public void setInputValue(String val){
    this.inputValue = val;
  }
  
  public String getInputValue(){
    return this.inputValue;
  }
}