/**
 * How to use the CallbackObject to call methods of objects other than its own.
 *
 */

import de.looksgood.ani.*;

Circle c1,c2;

void setup() {
  size(512,512);
  smooth();
  noStroke();
  textAlign(CENTER);

  // Ani.init() must be called always first!
  Ani.init(this);
  Ani.noAutostart();

  c1 = new Circle(200, 100);
  c2 = new Circle(300, 400);
 
  c1.otherCircle = c2;
  c1.makeAni();

  c2.otherCircle = c1;
  c2.makeAni();
  
  c1.ShrinkAndEnlarge();
}

void draw() {
  background(255);
  c1.draw(); c2.draw();
}

class Circle {
  int x,y, diameter = 70;
  Circle otherCircle;
  Ani diameterAni;

  Circle(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  void makeAni() {
    diameterAni = Ani.to(this, 0.5, 0.5, "diameter", 5, Ani.EXPO_IN_OUT, otherCircle, "onEnd:ShrinkAndEnlarge");    
  }
  
  void ShrinkAndEnlarge() {
    otherCircle.diameterAni.reverse();
    diameterAni.start();
  }

  void draw() {
    fill(0);
    ellipse(x,y,diameter,diameter);
  }

}








