// This sketch requires the "HTTP Requests for Processing" library
// which can be installed from the Processing contribution manager
// within Processing
import http.requests.*;

import java.io.File;

import java.util.Date;

PImage serverImage;

File greenImage;

PostRequest post;


void setup() {
  size(320, 480);  
}

void draw() {
  Date now = new Date();
  long startedDraw = now.getTime();
  
  greenImage = new File(sketchPath("/data/girlGreenScreen.jpg"));
  
  println("Sending image to server to be processed");
  
  post = new PostRequest("http://wishiwashere.azurewebsites.net/");
  post.addFile("GreenImage", greenImage);
  post.addData("name", "laura");
  post.send();
  println("Response - " + post.getContent());
  println("Response Headers - " + post.getHeader("Content-type"));
  
  serverImage = loadImage(post.getContent(), "png");
  image(serverImage, 0, 0);
  println("Image returned from server");
  
  now = new Date();
  long finishedDraw = now.getTime();
  
  long timeTaken = (finishedDraw - startedDraw) / 1000;
  println("Image keyed in " + timeTaken + " seconds");  
}