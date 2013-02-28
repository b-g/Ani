/**
 * shows how to set the default time mode of Ani 
 * 	 
 * MOUSE
 * click           : set end position of animation
 */

import de.looksgood.ani.*;

float x = 256;
float y = 256;

void setup() {
  size(512,512);
  smooth();
  noStroke();
  Ani.init(this);
  
  // the default time mode of Ani is:
  println(Ani.getDefaultTimeMode());
  
  // sets the time mode: SECONDS or FRAMES
  Ani.setDefaultTimeMode(Ani.FRAMES);
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,120,120);
}

void mouseReleased() {
    // animate the variables x and y in 30 frames to mouse click position
    Ani.to(this, 30, "x", mouseX);
    Ani.to(this, 30, "y", mouseY);
}

