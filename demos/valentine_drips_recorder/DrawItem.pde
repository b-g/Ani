class DrawItem {
  float x, y;
  float time;
  int index;
  int diameter;
  int col;
  DrawItem(int theIndex, float theX, float theY, float theTime, int theDiameter, int theCol) {
    index = theIndex;
    x = theX;
    y = theY;
    time = theTime;
    diameter = theDiameter;
    col = theCol;
    
  }
  void draw() {
    if (col == 0) {
      fill(0);
      stroke(0);
    }
    if (col == 1) {
      fill(165,196,0);
      stroke(165,196,0);
    }

    if (index > 0) {
      DrawItem tmp = (DrawItem) drawItems.get(index-1);
      line(x,y,tmp.x,tmp.y);
    }

    ellipse(x,y,diameter,diameter);
  }

  String toString() {
    return x+";"+y+";"+time+";"+diameter+";"+col;
  }
}




