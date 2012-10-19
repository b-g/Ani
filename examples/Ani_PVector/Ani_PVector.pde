/**
 * shows how to use Ani in combination with PVector
 *
 */

import de.looksgood.ani.*;

PVector point1, target1;
PVector point2, target2;

void setup() {
  size(800, 600);
  smooth();

  point1 = new PVector(width/2, height/2);
  target1 = new PVector(width/2, height/2);

  point2 = new PVector(width/2, height/2);
  target2 = new PVector(width/2, height/2);

  Ani.init(this);
}

void draw() {
  background(250);

  noStroke();
  fill(255, 0, 0, 128);
  ellipse(point1.x, point1.y, 15, 15);
  stroke(255, 0, 0, 128);
  strokeWeight(2);
  noFill();
  ellipse(target1.x, target1.y, 20, 20);

  noStroke();
  fill(0, 0, 255, 128);
  ellipse(point2.x, point2.y, 15, 15);
  stroke(0, 0, 255, 128);
  strokeWeight(2);
  noFill();
  ellipse(target2.x, target2.y, 20, 20);
}

void mousePressed() {
  if (mouseButton == LEFT) {
    target1.x = mouseX;
    target1.y = mouseY;
    println("set target1");
    Ani.to(point1, 1.0f, "x", target1.x);
    Ani.to(point1, 1.0f, "y", target1.y);
  }
  if (mouseButton == RIGHT) {
    target2.x = mouseX;
    target2.y = mouseY;
    println("set target2");
    Ani.to(point2, 1.0f, "x", target2.x);
    Ani.to(point2, 1.0f, "y", target2.y);
  }
}

