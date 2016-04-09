package processing.test.wish_i_was_here;

// Importing the Processing library, so this class can declare variables using Processing specific
// datatypes i.e. PImage objects.
import processing.core.*;

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

    private float textX1;
    private float textY1;
    private float textX2;
    private float textY2;
    private int textVertAlign;
    private int maxTextLength = 400;

    /*-------------------------------------- Constructor() ------------------------------------------------*/

    // Partial Constructor
    // This constructor is used by text inputs that require a specific x, y, width, height and title,
    // but will accept left aligned text as default
    public TextInput(Sketch _sketch, double x, double y, double w, double h, String title) {

        // Since not text align value has been passed in, defaulting this to left aligned. Passing this, along
        // with the specified x, y, width, height and title into the full constructor of this class.
        this(_sketch, x, y, w, h, title, "LEFT");
    }

    // Full Constructor
    // The above constructor passes it's values to this constructor, as well as other TextInput's in the app that
    // want to pass arguments for all of the specified values.
    public TextInput(Sketch _sketch, double x, double y, double w, double h, String title, String align) {

        // Passing the relevant parameters from the constructor into the constructor
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
            this.addTextBox(this.inputValue);
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

    private void addTextBox(String displayText) {
        sketch.rectMode(CORNERS);
        sketch.fill(0);
        sketch.textAlign(LEFT, this.textVertAlign);
        sketch.textSize(sketch.defaultTextSize);
        sketch.text(displayText, this.textX1, this.textY1, this.textX2, this.textY2);
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

    public int getMaxTextLength(){
        return maxTextLength;
    }

    public void setMaxTextLength(int maxLength){
        maxTextLength = maxLength;
    }
}