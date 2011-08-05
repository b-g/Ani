public class Drip {
  float x = 0, y = 0;
  float time = 0;
  float diameter = 2;
  Ani aniX, aniY;
  color col;
  Drip(float theX, float theY, color theCol) {
    x = theX;
    y = theY;
    time = random(1,5);
    diameter = random(1,4);
    aniX = Ani.to(this, time, "x", x+random(-2,2), Ani.QUAD_OUT);
    aniY = Ani.to(this, time, "y", y+random(5,70), Ani.QUAD_OUT);
    col = theCol;
  }

  void draw() {
    if (aniX.isPlaying() && aniY.isPlaying()) {
      fill(col,50);
      ellipse(x,y,diameter,diameter);
    }
  }
}




