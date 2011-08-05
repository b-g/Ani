/**
 * shows the basic use of Ani aka a Hello Ani
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

  // you have to call always Ani.init() first!
  Ani.init(this);
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,120,120);
}

void mouseReleased() {
    // animate the variables x and y in 1.5 sec to mouse click position
    Ani.to(this, 1.5, "x", mouseX);
    Ani.to(this, 1.5, "y", mouseY);
}

