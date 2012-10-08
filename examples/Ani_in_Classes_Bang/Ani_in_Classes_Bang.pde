/**
 * shows how to use Ani in your own classes and 
 * how to trigger/bang an animation (via keyboard).
 *
 * KEYS
 * space           : bang the circle
 */

import de.looksgood.ani.*;

Cirlce cirlce;

void setup() {
  size(512, 512);
  smooth();
  noStroke();
  textAlign(CENTER);

  // Ani.init() must be called always first!
  Ani.init(this);
  cirlce = new Cirlce();
}

void draw() {
  background(255);
  cirlce.draw();
}

void keyReleased() {
  if (key == ' ') cirlce.bang();
}

class Cirlce {
  float x = random(0, width);
  float y = random(0, height);
  color c = color(0);
  
  Ani diameterAni;
  float diameterStart = 200;
  float diameterEnd = 5;
  float diameter = diameterStart;
  float duration = 0.9;
  
  Cirlce() {
    // diameter animation
    diameterAni = new Ani(this, duration, "diameter", diameterEnd);
    diameterAni.pause();
    diameter = diameterEnd;
  }

  void bang() {
    diameter = diameterStart;
    diameterAni.seek(0);
    diameterAni.resume();
  }

  void draw() {
    fill(c);
    ellipse(x, y, diameter, diameter);
  }
}

