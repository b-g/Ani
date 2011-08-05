/**
 * shows how to define an animation in "propertylist" style.
 * this is a shortcut to define similar animations just in one line of code.
 */

import de.looksgood.ani.*;

float x,y,w,h;

void setup() {
  size(512,512);
  smooth();
  noStroke();

  x = 0;
  y = height/2;
  w = 10;
  h = 10;

  // Ani.init() must be called always first!
  Ani.init(this);
  Ani.to(this, 2.5, "x:500,y:256,w:400,h:410", Ani.SINE_IN_OUT);
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,w,h);
}





