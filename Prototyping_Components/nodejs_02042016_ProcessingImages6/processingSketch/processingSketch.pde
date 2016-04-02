// This sketch requires the "HTTP Requests for Processing" library
// which can be installed from the Processing contribution manager
// within Processing
import http.requests.*;

import java.io.File;

PImage serverImage;

File greenImage;

PostRequest post;


void setup() {
  size(320, 480);
  greenImage = new File(sketchPath("/data/girlGreenScreen.jpg"));
  
  post = new PostRequest("http://localhost:3000/");
  //post.addHeader("Content-Type", "multipart/form-data");
  //post.addHeader("Content-Type", "application/x-www-form-data");
  //post.addHeader("Content-Type", "text/plain");
  //post.addHeader("Content-Type", "application/json");
  
  /*
  post.addHeader("Content-Type", "application/x-www-form-urlencoded");
  post.addData("name", "laura");
  */
  
  //post.addHeader("Content-Type", "multipart/form-data");
  post.addFile("GreenImage", greenImage);
  post.addData("name", "laura");
  post.send();
  println("Reponse Content: " + post.getContent());
  println("Reponse Content-Type Header: " + post.getHeader("Content-type"));
  serverImage = loadImage(post.getContent(), "jpeg");
}

void draw() {
  image(serverImage, 0, 0);
}