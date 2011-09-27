/**
 * shows how to use a custom easing.
 * to define your own easing curve please use the editor of greensock:
 * http://www.greensock.com/customease/
 * the definition of the custom easing curve is based on greensock's excellent customease implementation!
 *
 * MOUSE
 * click           : set end position of the animations and trigger new one
 */


import de.looksgood.ani.*;
import de.looksgood.ani.easing.*;

CustomEasing myOwnEasing = CustomEasing.create("myOwnEasing","[{s:0,cp:0.383,e:0.644},{s:0.644,cp:0.905,e:1.044},{s:1.044,cp:1.183,e:1.012},{s:1.012,cp:0.841,e:1}]");
// you can access your easings also in a more static way: CustomEasing.byName("myOwnEasing")
// e.g. Ani.to(this, 1, "x", random(0,width), CustomEasing.byName("myOwnEasing") );

float x = 256;
float y = 256;

Ani aniX;
Ani aniY;

void setup() {
  size(512,512);
  smooth();
  noStroke();

  // you have to call always Ani.init() first!
  Ani.init(this);

  // save the references to the anis
  aniX = Ani.to(this, 1, "x", random(0,width), myOwnEasing );
  aniY = Ani.to(this, 1, "y", random(0,height), myOwnEasing );
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,120,120);
}

void mouseReleased() {
  aniX = Ani.to(this, 1, "x", mouseX, myOwnEasing);
  aniY = Ani.to(this, 1, "y", mouseY, myOwnEasing);
}



