import biuoop.DrawSurface;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-03-25 */
public class Ball {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private Point lowerFrameEdge;
    private Point upperFrameEdge;
       /**
       * Ball constructor.
       * <p>
       * @param center - the change in x axis.
       * @param r - the change in y axis.
       * @param color - the change in y axis.*/
       public Ball(Point center, int r, java.awt.Color color) {
           this.center = center;
           this.radius = r;
           this.color = color;
           this.velocity = new Velocity(0, 0);
       }
       /**
        Ball constructor.
       * <p>
       * @param x - coordinate.
       * @param y - coordinate.
       * @param r - the change in y axis.
       * @param color - the change in y axis.*/
       public Ball(int x, int y, int r, java.awt.Color color) {
           this(new Point(x, y), r, color);
       }
       /**
       Ball constructor.
       * <p>
       * @param center - the change in x axis.
       * @param r - the change in y axis.
       * @param color - the change in y axis.
       * @param lowerFrameEdge - low right edge
       * @param upperFrameEdge - upper left edge*/
       public Ball(Point center, int r, java.awt.Color color, Point lowerFrameEdge, Point upperFrameEdge) {
           this(center, r, color);
           this.upperFrameEdge = upperFrameEdge;
           this.lowerFrameEdge = lowerFrameEdge;
       }
       /**
       Ball constructor.
       * <p>
       * @param x - coordinate.
       * @param y - coordinate.
       * @param r - the change in y axis.
       * @param color - the change in y axis.
       * @param lowerFrameEdge - low right edge
       * @param upperFrameEdge - upper left edge*/
       public Ball(int x, int y, int r, java.awt.Color color, Point lowerFrameEdge, Point upperFrameEdge) {
           this(new Point(x, y), r, color);
           this.upperFrameEdge = upperFrameEdge;
           this.lowerFrameEdge = lowerFrameEdge;
       }

       /**
        * x coordinate query.
        * <p>
        * @return x - x coordinate*/
       public int getX() {
           return (int) this.center.getX();
       }
       /**
        * y coordinate query.
        * <p>
        * @return y - y coordinate*/
       public int getY() {
           return (int) this.center.getY();
       }
       /**
        * center point query.
        * <p>
        * @return point - center*/
       public Point getCenter() {
           return this.center;
       }
       /**
        * radius query.
        * <p>
        * @return this.radius - radius*/
       public int getSize() {
           return this.radius;
       }
       /**
        * color query.
        * <p>
        * @return Color - ball color*/
       public java.awt.Color getColor() {
           return this.color;
       }
       /**
        * drawing ball method on the given DrawSurface.
        * <p>
        * @param surface - the given DrawSurface*/
       public void drawOn(DrawSurface surface) {
           surface.setColor(this.color);
           surface.fillCircle(this.getX(), this.getY(), this.getSize());
       }
       /**
        * velocity query.
        * <p>
        * @return velocity - this.velocity*/
       public Velocity getVelocity() {
           return this.velocity;
       }
       /**
        * velocity setter.
        * <p>
        * @param v - a give new velocity*/
       public void setVelocity(Velocity v) {
           this.velocity = v;
       }
       /**
        * velocity setter.
        * <p>
        * @param dx - change in x axis
        * @param dy - change in y axis */
       public void setVelocity(double dx, double dy) {
           this.velocity = new Velocity(dx, dy);
       }
       /**
        * ball animation step.
        * <p>
        * changes the center according to the velocity
        * <p>
        * keeps the ball in its boundaries*/
       public void moveOneStep() {
           this.keepInFrame();
           this.center = this.getVelocity().applyToPoint(this.center);
       }
       /**
        * changes the ball velocity according to its position.*/
       private void keepInFrame() {
           double  dx = this.getVelocity().getDx();
           double dy = this.getVelocity().getDy();
           //the ball is near the left boundary
           if (this.getCenter().getX() - this.getSize() + dx < this.upperFrameEdge.getX()) {
               this.setVelocity(new Velocity(Math.abs(dx), dy));
               }
           //the ball is near the right boundary
           if (this.getCenter().getX() + this.getSize() + dx >= this.lowerFrameEdge.getX()) {
               this.setVelocity(new Velocity(-Math.abs(dx), dy));
               }
           //the ball is near the top boundary
           if (this.getCenter().getY() - this.getSize() + dy < this.upperFrameEdge.getY()) {
               this.setVelocity(new Velocity(dx, Math.abs(dy)));
               }
           //the ball is near the bottom boundary
           if (this.getCenter().getY() + this.getSize() + dy >= this.lowerFrameEdge.getY()) {
               this.setVelocity(new Velocity(dx, -Math.abs(dy)));
               }
          }
}
