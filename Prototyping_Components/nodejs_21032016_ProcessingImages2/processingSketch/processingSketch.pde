import processing.net.*;

String[] response;
PImage cameraImage;
PImage keyedImage;

Client client;
int serverData;

void setup(){
  size(400, 600);
  keyedImage = loadImage("http://localhost:3000/", "png");
  response = loadStrings("http://localhost:3000/hello");
  println(response[0]);
  
  /*
  client = new Client(this, "http://wishiwashereapp.azurewebsites.net/", 80);
  
  println(client.available());
  
  if(client.available() > 0){
    println("Client Available");
    client.write("Hello");
  }
  */
  cameraImage = loadImage("girlGreenScreen.jpg");
  cameraImage.loadPixels();
  response = loadStrings("http://localhost:3000/" + cameraImage.pixels.toString());
  //println(cameraImage.pixels);
  cameraImage.updatePixels();
  println(response[0]);
}

void draw(){
  background(255);
  image(keyedImage, 0, 0, width, height);
  
}