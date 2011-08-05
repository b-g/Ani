/**
 * shows how to set a specific easing (character of movment)
 * list of all easing styles below:
 *
 * Ani.LINEAR
 * Ani.QUAD_IN
 * Ani.QUAD_OUT
 * Ani.QUAD_IN_OUT
 * Ani.CUBIC_IN
 * Ani.CUBIC_IN_OUT
 * Ani.CUBIC_OUT
 * Ani.QUART_IN
 * Ani.QUART_OUT
 * Ani.QUART_IN_OUT
 * Ani.QUINT_IN
 * Ani.QUINT_OUT
 * Ani.QUINT_IN_OUT
 * Ani.SINE_IN
 * Ani.SINE_OUT
 * Ani.SINE_IN_OUT
 * Ani.CIRC_IN
 * Ani.CIRC_OUT
 * Ani.CIRC_IN_OUT
 * Ani.EXPO_IN
 * Ani.EXPO_OUT
 * Ani.EXPO_IN_OUT
 * Ani.BACK_IN
 * Ani.BACK_OUT
 * Ani.BACK_IN_OUT
 * Ani.BOUNCE_IN
 * Ani.BOUNCE_OUT
 * Ani.BOUNCE_IN_OUT
 * Ani.ELASTIC_IN
 * Ani.ELASTIC_OUT
 * Ani.ELASTIC_IN_OUT
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
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,diameter,diameter);
}

void mouseReleased() {
  Ani.to(this, 1.0, "x", mouseX, Ani.BOUNCE_OUT);
  Ani.to(this, 1.0, "y", mouseY, Ani.BOUNCE_OUT);
}



