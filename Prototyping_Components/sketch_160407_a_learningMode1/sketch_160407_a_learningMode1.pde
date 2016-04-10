Boolean learningModeOn = true;

void setup(){
  size(500,500);  
}

void draw(){
  background(215,89,143);
  if(learningModeOn = true){
    if(mousePressed){
      fill(255,0,0,63);
      learningModeOn = false;
    }
  }
  else{
    fill(255,0,0);
  }  
  rect(0,0,500,500); 
}