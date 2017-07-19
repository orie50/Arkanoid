/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-03-25 */
public class Velocity {
    private double dx;
    private double dy;
      /**
       * velocity constructor.
       * <p>
       * @param dx - the change in x axis.
       * @param dy - the change in y axis.*/
      public Velocity(double dx, double dy) {
           this.dx = dx;
           this.dy = dy;
       }
       /**
        * returns the velocity change in x.
        * <p>
        * @return this.dx - the change in x axis */
       public double getDx() {
           return this.dx;
       }
       /**
        * returns the velocity change in y.
        * <p>
        * @return this.dy - the change in y axis */
       public double getDy() {
           return this.dy;
       }
       /**
        * Take a point with position (x,y) and return a new point
        * with position (x + dx, y + dy).
        * <p>
        * @param point - the point to be changed.
        * @return new Point - the new point after the change */
       public Point applyToPoint(Point point) {
           return new Point(point.getX() + this.dx, point.getY() + this.dy);
       }
       /**
        * Take a point with position (x,y) and return a new point
        * with position (x + dx, y + dy).
        * <p>
        * @param  angle - velocity vector angle.
        * @param  speed - velocity vector size.
        * @return new Velocity - the new Velocity */
       public static Velocity fromAngleAndSpeed(double angle, double speed) {
             double dx = speed * Math.cos(Math.toDegrees(angle));
             double dy = speed * Math.sin(Math.toDegrees(angle));
             return new Velocity(dx, dy);
       }
}
