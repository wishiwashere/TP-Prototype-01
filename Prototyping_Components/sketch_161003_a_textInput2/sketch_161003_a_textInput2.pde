String myText = "";
String textWarning = "";

float x = width/2;
float y = height/2;

float x2 = (x * 3);
float y2 = (y * 3);

// My thinking: A normal text string is formed by a string, a starting x and y position for the text to start
// and a second x and y position to constrict the text within. So... I am thinking that having having the x2 
// and y2 equal the value of the rectangle 2nd parameters, it might work...?

//text(str, x1, y1, x2, y2)

void setup() {
  size(300, 300);
}

void draw() {
  background(0);
  fill(255);
  rect(x, y, x2, y2);
  fill(255,155,48);
  textSize(width * 0.1);
  textAlign(LEFT, TOP); 
  text(myText, x, y, x2, y2);
}


void keyPressed() {
  // Checking if the key pressed was the backspace key
  if (key == 8)
  {
    // Checking that the length of the current myText string is greater than 0 (i.e. 
    // if the string is empty, don't try to delete anything)
    if (myText.length() > 0) {
      println("BACKSPACE");
      // Removing the last character from myText string, by creating a substring
      // of myText, that is one shorter than the current myText string
      myText = myText.substring(0, myText.length() - 1);
    }
  } else if (key != CODED) {
    // If the key is not a coded value, adding the character to myText string
    myText += key;
  }
  // Logging out the current value of myText string
  println(myText);
  //println("Text is being typed !");
}