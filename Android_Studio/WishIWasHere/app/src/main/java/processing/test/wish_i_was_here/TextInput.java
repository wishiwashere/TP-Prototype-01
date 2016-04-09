package processing.test.wish_i_was_here;

import processing.core.PApplet;
import processing.core.PConstants;

public class TextInput extends ClickableElement {

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this class.
    private Sketch sketch;

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
    private int maxTextLength = 400;

    /*-------------------------------------- Constructor() ------------------------------------------------*/
    // This partial constructor is used by text inputs that do not require their contents
    // to be blocked out i.e. any text input that is not a password
    public TextInput(Sketch _sketch, double x, double y, double w, double h, String title) {

        // Passing the relevant parametres to the main constructor. Since a password value
        // has not been included, passing false for this argument (i.e. assuming this in not
        // a password textInput
        this(_sketch, x, y, w, h, title, false, "LEFT");
    }

    // This partial constructor is used by text inputs that do not require their contents
    // to be blocked out i.e. any text input that is not a password
    public TextInput(Sketch _sketch, double x, double y, double w, double h, String title, String align) {

        // Passing the relevant parametres to the main constructor. Since a password value
        // has not been included, passing false for this argument (i.e. assuming this in not
        // a password textInput
        this(_sketch, x, y, w, h, title, false, align);
    }

    // This partial constructor is used by text inputs that do not require their contents
    // to be blocked out i.e. any text input that is not a password
    public TextInput(Sketch _sketch, double x, double y, double w, double h, String title, Boolean password) {

        // Passing the relevant parametres to the main constructor. Since a password value
        // has not been included, passing false for this argument (i.e. assuming this in not
        // a password textInput
        this(_sketch, x, y, w, h, title, password, "LEFT");
    }

    // This constructor is used by all text inputs
    public TextInput(Sketch _sketch, double x, double y, double w, double h, String title, Boolean password, String align) {

        // Passing the relevant parametres from the constructor into the constructor
        // of the super class (ClickableElement)
        super(_sketch, x, y, w, h, title);

        // Initialising this class's local sketch variable, with the instance which was passed to the
        // constructor of this class. The purpose of this variable is so that we can access the Processing
        // library, along with other global methods and variables of the main sketch class, from within
        // this class. Every reference to a Processing method/variable, or a public method/variable of
        // the main sketch, must be prefixed with this object while within this class.
        sketch = _sketch;

        // Calling the Rectangle class's setBackgroundColor() method, which this class had inherited through the
        // ClickableElement class, to default all instances of the TextInput class to have an opaque white background.
        // This now means that each TextInput class will have a white rectangle as their background.
        super.setBackgroundColor(sketch.color(255, 255, 255, 255));

        // Initialising the inputTitle to be equal to the requested title
        this.inputTitle = title;

        this.passwordInput = password;

        this.inputTextAlign = align;

        this.textX1 = (float)(x - (w * 0.48));
        this.textY1 = (float)(y - (h * 0.45));
        this.textX2 = (float)(x + (w * 0.48));
        this.textY2 = (float)(y + (h * 0.45));

        if (this.inputTextAlign.equals("LEFT")) {
            this.textVertAlign = CENTER;
        } else if (inputTextAlign.equals("LEFT-TOP")) {
            this.textVertAlign = TOP;
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
            if (this.passwordInput) {
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
        if (sketch.mouseClicked) {
            if (this.checkMouseOver()) {
                sketch.keyboardRequired = true;
                sketch.currentTextInput = this;
                sketch.currentTextInputValue = "";
                println("The " + this.inputTitle + " text input was clicked on");
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
        sketch.rectMode(CORNERS);
        sketch.fill(0);
        sketch.textAlign(LEFT, this.textVertAlign);
        sketch.textSize(sketch.defaultTextSize);
        sketch.text(displayText, this.textX1, this.textY1, this.textX2, this.textY2);
    }

    public int getMaxTextLength(){
        return maxTextLength;
    }

    public void setMaxTextLength(int maxLength){
        maxTextLength = maxLength;
    }
}