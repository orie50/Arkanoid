/**
* @author Ori Engelberg <turht50@gmail.com>
* @version 1.0
* @since 2016-03-21 */
public class Line {
    private Point start;
    private Point end;
    private double slope;

    /**
    * Contractor - Create a start point an end point and the line slope.
    * @param start - the first point in the line.
    * @param end - the last point in the line. */
        public Line(Point start, Point end) {
           this.start = start;
           this.end = end;
           this.slope = slope();
           }

        /**
         * Contractor - Create a start point an end point and the line slope.
         * @param x1 - the x value of the start point.
         * @param y1 - the y value of the start point.
         * @param x2 - the x value of the end point.
         * @param y2 - the y value of the end point. */
       public Line(double x1, double y1, double x2, double y2) {
           this.start = new Point(x1, y1);
           this.end = new Point(x2, y2);
           this.slope = slope();
           }

       /**
        * Calculate the length of the line.
        * @return the distance between the first point to the last. */
       public double length() {
           double length = this.start.distance(end);
           return length;
           }

       /**
        * Calculate the middle point.
        * Sum the x values of the start and the end points and their y values and create a new point.
        * @return the middle point of the line. */
       public Point middle() {
           double x1, x2 , y1 , y2;
           x1 = this.start.getX();
           x2 = this.end.getX();
           y1 = this.start.getY();
           y2 = this.end.getY();
           Point middle = new Point((x2 + x1) / 2, (y2 + y1) / 2);
           return middle;
           }

       /**
        * Initialize the start point.
        * @return the start point. */
       public Point start() {
           return start;
       }

       /**
        * Initialize the end point.
        * @return the end point. */
       public Point end() {
           return end;
       }

       /**
        * Initialize the line slope.
        * Do slope = (y1-y2) / (x1-x2).
        * @return the line slope. */
        public double slope() {
           double dx, dy;
           dx = this.start.getX() - this.end.getX();
           dy = this.start.getY() - this.end.getY();
           return (dy / dx);
           }

        /**
         * Check intersection between 2 lines.
         * @param other - another line to check intersection.
         * @return true if the lines intersected, else return false.  */
        public boolean isIntersecting(Line other) {
            Point intersection = intersectionWith(other);
            if (intersection != null) {
                return true;
                }
            return false;
    }

        /**
         * Create the intersection point between the lines.
         * Create 2 equations y = m * x - b and check the where the y values are equal.
         * @param other - another line to check the intersection.
         * @return the intersection point, if the lines don't intersected returns null.  */
        public Point intersectionWith(Line other) {
            double x, y, m1, m2, b1, b2;
            // find the m and b to create the equation y = m * x -b.
            m1 = this.getSlope();
            m2 = other.getSlope();
            b1 = this.start.getY() - m1 * this.start.getX();
            b2 = other.start.getY() - m2 * other.start.getX();
            // If the slopes equal there is't intersection.
            if (m1 == m2) {
                return null;
            } else if (Double.isInfinite(m1)) {
                // If one of the slopes in Infinity then the x value is known and we find the y value.
                x = this.start.getX();
                y = m2 * x + b2;
                // If no intersection return null.
                if (!isInTheDomain(y, this.start.getY(), this.end.getY(), other.start.getY(), other.end.getY())) {
                    return null;
                    }
            } else if (Double.isInfinite(m2)) {
                    x = other.start.getX();
                    y = m1 * x + b1;
                if (!isInTheDomain(y, this.start.getY(), this.end.getY(), other.start.getY(), other.end.getY())) {
                    return null;
                    }
               } else {
               // In other cases find the x and y of the intersection and check if this is in the both lines.
               x = (b2 - b1) / (m1 - m2);
               y = m1 * x + b1;
               if (!isInTheDomain(x, this.start.getX(), this.end.getX(), other.start.getX(), other.end.getX())) {
                   return null;
                   }
               }
            // Create the intersection point.
            Point intersection = new Point(x, y);
            return intersection;
            }

        /**
         * Check intersection between 2 lines.
         * @param x - the x/y value of the intersection point.
         * @param x1Start - the x/y value of the start point of the line.
         * @param x1End - the x/y value of the end point of the line.
         * @param x2Start - the x/y value of the start point of the other line.
         * @param x2End - the x/y value of the end point of the other line.
         * @return true if the intersection in the both lines domains.  */
    boolean isInTheDomain(double x, double x1Start, double x1End, double x2Start, double x2End) {
        return (((x >= x1Start && x <= x1End) || (x <= x1Start && x >= x1End))
                   && ((x >= x2End && x <= x2Start) || (x <= x2End && x >= x2Start)));
        }

    /**
     * Check if 2 lines are equal.
     * @param other - the other line.
     * @return true if the lines equal, else return false.  */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
            }
        // Check if the x values and the y values are equal in the start and the of the lines.
        return (this.start.equals(other.start) && this.end.equals(other.end));
        }

    /**
     * Create a slope to the line.
     * @return the slope. */
    public double getSlope() {
        return slope;
    }
}