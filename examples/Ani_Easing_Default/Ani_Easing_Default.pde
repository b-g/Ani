/**
 * shows how to set the default easing. 
 * this can be usefull if you have a lot animations of the same easings.
 *
 * MOUSE
 * click           : set end position of the animations and trigger new one
 */

import de.looksgood.ani.*;

float x = 256;
float y = 256;
int diameter = 50;

void setup() {
  size(512,512);
  smooth();
  noStroke();

  // you have to call always Ani.init() first!
  Ani.init(this);
  // set the default easing
  Ani.setDefaultEasing(Ani.QUART_IN_OUT);

}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,diameter,diameter);
}

void mouseReleased() {
  Ani.to(this, 1.0, "x", mouseX);
  Ani.to(this, 1.0, "y", mouseY);
}


