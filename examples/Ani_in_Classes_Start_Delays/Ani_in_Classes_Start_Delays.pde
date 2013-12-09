/**
 * shows how to use Ani in class based objects 
 * and how to gradually change the (start) delay of an animation
 */

import de.looksgood.ani.*;

Cirlce[] cirlces = new Cirlce[23];

void setup() {
  size(512,512);
  smooth();
  noStroke();

  // Ani.init() must be called always first!
  Ani.init(this);

  // arrange the dots in a cirlce layout
  for(int i=0; i<cirlces.length; i++) {
    float angle = map(i,0,cirlces.length,0,TWO_PI);
    float delay = map(i,0,cirlces.length,1,10);
    int radius = 200;
    float x = width/2 + cos(angle)*radius;
    float y = height/2 + sin(angle)*radius;
    cirlces[i] = new Cirlce(x,y,delay);
  }
}

void draw() {
  background(255);
  // draw the circles
  for(int i=0; i<cirlces.length; i++) cirlces[i].draw();
}

class Cirlce {
  float x;
  float y;
  int diameter = 10;
  Ani posAniX;
  Ani posAniY;

  Cirlce(float theX, float theY, float theDelay) {
    float duration = 2.2;
    x = theX;
    y = theY;
    posAniX = Ani.to(this, duration, theDelay, "x", width/2, Ani.ELASTIC_OUT);
    posAniY = Ani.to(this, duration, theDelay, "y", height/2, Ani.ELASTIC_OUT);
  }

  void draw() {
    fill(color(0));
    ellipse(x,y,diameter,diameter);
  }
}








