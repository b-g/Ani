/**
 * how to use Ani in your own classes. 
 * besides the examples shows also the use of: callbacks, setRepeat() and  setPlayMode().
 *
 */

import de.looksgood.ani.*;

Cirlce[] cirlces = new Cirlce[5];

color from = color(255, 8, 8);
color to = color(8, 187, 255);

void setup() {
  size(512,512);
  smooth();
  noStroke();
  textAlign(CENTER);

  // Ani.init() must be called always first!
  Ani.init(this);

  for(int i=0; i<cirlces.length; i++) {
    cirlces[i] = new Cirlce();
  }
}

void draw() {
  background(255);
  // draw the circles
  for(int i=0; i<cirlces.length; i++) cirlces[i].draw();
}

class Cirlce {
  float x = random(0,width);
  float y = random(0,height);
  int diameter = 5;
  Ani diameterAni;
  color c = color(0);

  Cirlce() {
    // diameter animation
    diameterAni = new Ani(this, random(1,5), 0.5, "diameter", 50, Ani.EXPO_IN_OUT, "onEnd:randomize");
    // repeat yoyo style (go up and down)
    diameterAni.setPlayMode(Ani.YOYO);
    // repeat 3 times
    diameterAni.repeat(3);
  }

  void draw() {
    fill(c);
    ellipse(x,y,diameter,diameter);
    fill(0);
    text(diameterAni.getRepeatNumber()+" / "+diameterAni.getRepeatCount(), x, y+diameter);
  }

  void randomize(Ani _ani) {
    c = lerpColor(from, to, random(1));

    // new repeat count
    int newCount = 1+2*round(random(4));
    diameterAni.repeat(newCount);
    // restart
    diameterAni.start();

    // move to new position
    Ani.to(this, 1.5, "x", random(0,width), Ani.EXPO_IN_OUT);
    Ani.to(this, 1.5, "y", random(0,height), Ani.EXPO_IN_OUT);
  }
}








