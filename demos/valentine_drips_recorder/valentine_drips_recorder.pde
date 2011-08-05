/**
 * this sketch is a helper application for valentine_drips_player.
 * draw something, press p for saving the information to 
 * a file and load everyting back in valentine_drips ... 
 *
 * MOUSE
 * press               : new drawing point
 * 
 * KEYS
 * del                 : clear screen
 * 1,2                 : set color
 * 3,4                 : set diameter
 * z                   : undo last action
 * u                   : show underlay picture
 * s                   : save png
 * p                   : save drawing values to file
 * 
 * Ani (a processing animation library) 
 * Copyright (c) 2010 Benedikt Groß
 * 
 * http://www.looksgood.de/libraries/Ani
 */

ArrayList drawItems;

boolean showUndelay = false;
float beginTime = 0.0;

PImage img;
int imageAlpha = 100;
int diameter = 10;

int col = 0;

void setup() {
  // use full screen size 
  //size(screen.width, screen.height);
  size(600,800);
  background(255);
  smooth();
  drawItems = new ArrayList();
  cursor(CROSS);

  // load an image in background
  img = loadImage("underlay.png");
}

void draw() {

}


void mousePressed() { 
  DrawItem drw = new DrawItem(drawItems.size(),mouseX,mouseY,getStopwatch(),diameter,col);
  drw.draw();
  drawItems.add(drw);
  resetStopwatch();
}


void keyReleased() {
  if (key == 's' || key == 'S') saveFrame(timestamp()+"_##.png");
  if (key == 'p' || key == 'P') savedrawItems();
  
  if (key == '1') col = 0;
  if (key == '2') col = 1;
  
  if (key == '3') diameter = 10;
  if (key == '4') diameter = 0;

  if (key == DELETE || key == BACKSPACE) {
    newdrawItemsList();
  }
  if (key == 'u' || key == 'U') showUndelay = !showUndelay; 

  if (showUndelay) {
    background(255);
    reDrawAlldrawItems();
    tint(255, imageAlpha);
    image(img, 0, 0);
  } 
  else {
    reDrawAlldrawItems();
  }

  if (key == 'z' || key == 'Z') {
    undodrawItems();
  }
}

// timestamp
String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}

void savedrawItems() {
  String[] tokens = new String[drawItems.size()];
  for (int i = 0; i < drawItems.size(); i++) { 
    DrawItem tmp = (DrawItem) drawItems.get(i);
    tokens[i] = tmp.toString();
    println(tokens[i]);
  } 
  saveStrings(dataPath("recording.txt"), tokens);
}

void reDrawAlldrawItems() {
  background(255);
  for (int i = 0; i < drawItems.size(); i++) { 
    DrawItem tmp = (DrawItem) drawItems.get(i);
    tmp.draw();
  } 
}

void newdrawItemsList() {
  background(255);
  drawItems = new ArrayList();
  resetStopwatch();
}

void undodrawItems() {
  int undoIndex = drawItems.size()-1;
  undoIndex = max(undoIndex,0);

  if (undoIndex >= 0) {
    for (int i = drawItems.size()-1; i >= undoIndex; i--) {
      drawItems.remove(i);
    }
  }
  reDrawAlldrawItems();
  resetStopwatch();
}

float getStopwatch() {
  return (millis() - beginTime) / 1000;
}
void resetStopwatch() {
  beginTime = millis();
}







