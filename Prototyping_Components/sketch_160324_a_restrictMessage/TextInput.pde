public class TextInput extends ClickableElement {

  // Creating private variables to store the TextInput's title and value
  // properties, so that they can only be accessed within this class
  private String inputTitle = "";
  private String inputValue = "";
  private String inputTextAlign = "";
  private Boolean passwordInput;

  private float textX1;
  private float textY1;
  private float textX2;
  private float textY2;
  private int textVertAlign;
  
 // private int textLength = 400;
  //private int twittertTextLength = 125;
 // private String generalMessage = "generalText";
  //private String twitterMessage = "twitterText
  //private int messageLength;

  /*-------------------------------------- Constructor() ------------------------------------------------*/
  // This partial constructor is used by text inputs that do not require their contents
  // to be blocked out i.e. any text input that is not a password
  public TextInput(float x, float y, float w, float h, color col, String title) {

    // Passing the relevant parametres to the main constructor. Since a password value
    // has not been included, passing false for this argument (i.e. assuming this in not
    // a password textInput
    this(x, y, w, h, col, title, false, "LEFT");
  }

  // This partial constructor is used by text inputs that do not require their contents
  // to be blocked out i.e. any text input that is not a password
  public TextInput(float x, float y, float w, float h, color col, String title, String align, int messageLength) {

    // Passing the relevant parametres to the main constructor. Since a password value  
    // has not been included, passing false for this argument (i.e. assuming this in not
    // a password textInput
    this(x, y, w, h, col, title, false, align, messageLength);
  }

  // This partial constructor is used by text inputs that do not require their contents
  // to be blocked out i.e. any text input that is not a password
  public TextInput(float x, float y, float w, float h, color col, String title, Boolean password) {

    // Passing the relevant parametres to the main constructor. Since a password value
    // has not been included, passing false for this argument (i.e. assuming this in not
    // a password textInput
    this(x, y, w, h, col, title, password, "LEFT");
  }

  // This constructor is used by all text inputs
  public TextInput(float x, float y, float w, float h, color col, String title, Boolean password, String align) {

    // Passing the relevant parametres from the constructor into the constructor 
    // of the super class (ClickableElement)
    super(x, y, w, h, col, title);

    // Initialising the inputTitle to be equal to the requested title
    inputTitle = title;

    passwordInput = password;

    inputTextAlign = align;
    
    textX1 = x - (w * 0.48);
    textY1 = y - (h * 0.45);
    textX2 = x + (w * 0.48);
    textY2 = y + (h * 0.45);
    
    if (inputTextAlign.equals("LEFT")) {
      textVertAlign = CENTER;
    } else if (inputTextAlign.equals("LEFT-TOP")) {
      textVertAlign = TOP;
    }
  }

  /*-------------------------------------- showTextInput() ------------------------------------------------*/

  // Creating a public showTextInput function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  public void showTextInput() {
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();

    // Checking if the length of the value is greater than 0 i.e. 
    if (this.inputValue.length() > 0) {
      // Creating a temporary varaible to store the string we are going to display in the text input.
      // This string will either contain the value of the text input, or in the case of a password,
      // stars which represent the value's length (along with the last letter of the password)
      String displayText = "";

      // Checking if this textInput is a password field
      if (passwordInput) {
        // Setting display text to be equal to the value returned from the hidePassword() method
        // i.e. starred out (asides fromt the last letter)
        displayText = hidePassword();
        /*
        // Code to only display last letter of password for a specified period of time. Currently
         // not using, but keeping for future reference
         delay(500);
         displayText = displayText.substring(0, displayText.length() -1) + "*";
         */
      } else {
        // Since this field does not contain a password, set the display text to the value of the input
        // field
        displayText = this.inputValue;
      }
      this.addTextBox(displayText);
    }
    if (mousePressed) {
      if (this.checkMouseOver()) {
        keyboardRequired = true;
        currentTextInput = this;
        currentTextInputValue = "";
        println("The " + inputTitle + " text input was clicked on");
      }
    }
  }

  public void setInputValue(String val) {
    this.inputValue = val;
  }

  public String getInputValue() {
    return this.inputValue;
  }
  public void clearInputValue() {
    this.inputValue = "";
  }

  private String hidePassword() {
    // Creating a temporary string to store the **** for the password (i.e. we do not want
    // to display the user's password, we just want to reflect the length of it in terms
    // of * stars *
    String passwordStars = "";

    // Looping through the length of the myText string, to determine how many stars should
    // be displayed for the current password length, and allowing just the last letter to be
    // displayed
    for (int i = 0; i < this.inputValue.length(); i++) {
      // Creating a temporary variable for the index position of the last character in myText
      // string
      int lastLetter = this.inputValue.length() - 1;

      // Checking if the current loop has reached the last letter in the myText string
      if (i == lastLetter) {
        // Since this is the last letter in the string, we want to display it with the 
        // preceeing stars i.e. so the user can always see the most recent character
        // they have entered
        passwordStars += this.inputValue.charAt(lastLetter);
      } else {
        // Add a star in place of this character
        passwordStars += "*";
      }
    }
    return passwordStars;
  }

  private void addTextBox(String displayText) {
    rectMode(CORNERS);
    fill(0);
    textAlign(LEFT, textVertAlign);
    text(displayText, textX1, textY1, textX2, textY2);
  }
}