// shows how to use Ani in combination with PVector

import de.looksgood.ani.*;
import de.looksgood.ani.easing.*;
float duration = 1;

PVector point1, target1;
PVector point2, target2;
Easing[] easings = { 
  Ani.LINEAR, Ani.QUAD_IN, Ani.QUAD_OUT, Ani.QUAD_IN_OUT, Ani.CUBIC_IN, Ani.CUBIC_IN_OUT, Ani.CUBIC_OUT, Ani.QUART_IN, Ani.QUART_OUT, Ani.QUART_IN_OUT, Ani.QUINT_IN, Ani.QUINT_OUT, Ani.QUINT_IN_OUT, Ani.SINE_IN, Ani.SINE_OUT, Ani.SINE_IN_OUT, Ani.CIRC_IN, Ani.CIRC_OUT, Ani.CIRC_IN_OUT, Ani.EXPO_IN, Ani.EXPO_OUT, Ani.EXPO_IN_OUT, Ani.BACK_IN, Ani.BACK_OUT, Ani.BACK_IN_OUT, Ani.BOUNCE_IN, Ani.BOUNCE_OUT, Ani.BOUNCE_IN_OUT, Ani.ELASTIC_IN, Ani.ELASTIC_OUT, Ani.ELASTIC_IN_OUT
};
String[] easingsVariableNames = {
  "Ani.LINEAR", "Ani.QUAD_IN", "Ani.QUAD_OUT", "Ani.QUAD_IN_OUT", "Ani.CUBIC_IN", "Ani.CUBIC_IN_OUT", "Ani.CUBIC_OUT", "Ani.QUART_IN", "Ani.QUART_OUT", "Ani.QUART_IN_OUT", "Ani.QUINT_IN", "Ani.QUINT_OUT", "Ani.QUINT_IN_OUT", "Ani.SINE_IN", "Ani.SINE_OUT", "Ani.SINE_IN_OUT", "Ani.CIRC_IN", "Ani.CIRC_OUT", "Ani.CIRC_IN_OUT", "Ani.EXPO_IN", "Ani.EXPO_OUT", "Ani.EXPO_IN_OUT", "Ani.BACK_IN", "Ani.BACK_OUT", "Ani.BACK_IN_OUT", "Ani.BOUNCE_IN", "Ani.BOUNCE_OUT", "Ani.BOUNCE_IN_OUT", "Ani.ELASTIC_IN", "Ani.ELASTIC_OUT", "Ani.ELASTIC_IN_OUT"
};
String code = "";
float x = 256;
float y = 256;
int index = 26;
Easing currentEasing = easings[index];

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
  code = "Ani.to(this, "+nf(duration, 0, 2)+", \"x\", mouseX, "+easingsVariableNames[index]+");";
  text(code, 10, 20);
}

void mousePressed() {
  if (mouseButton == LEFT) {
    target1.x = mouseX;
    target1.y = mouseY;
    Ani.to(point1, duration, "x", target1.x, easings[index]);
    Ani.to(point1, duration, "y", target1.y, easings[index]);
    println("set target1");
  }
  if (mouseButton == RIGHT) {
    target2.x = mouseX;
    target2.y = mouseY;
    Ani.to(point2, 1.0f, "x", target2.x, Ani.ELASTIC_OUT);
    Ani.to(point2, 1.0f, "y", target2.y, Ani.ELASTIC_OUT);
    println("set target2");
  }
}

void keyPressed() {
  if (keyCode == UP) duration += 0.1;
  if (keyCode == DOWN) duration -= 0.1;
  duration = max(0.25, duration);
}

void keyReleased() {
  if (keyCode == LEFT) index--;
  if (keyCode == RIGHT) index++;
  index = constrain(index, 0, easings.length-1);
}

