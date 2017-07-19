
import java.util.Comparator;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-03-29 */
public class PointByDistanceComparator implements Comparator  {
    //point to compare the dinstace from
    private Point origin;

    /**
     * PointByDistanceComparator Constructor.
     * <p>
     * @param origin - the point to compare distance from*/
    public PointByDistanceComparator(Point origin) {
        this.origin = origin;
    }
    @Override
    /** comparing method for two points
     * <p>
     * compare to points with respect to
     * their distance from an origin point
     * <p>
     * @param obj1 - first point
     * @param obj2 - second point*/
    public int compare(Object obj1, Object obj2) {
        //casting
        if (obj1 instanceof Point && obj2 instanceof Point) {
            Point p1 = (Point) obj1;
            Point p2 = (Point) obj2;

            double dist1 = p1.distance(origin);
            double dist2 = p2.distance(origin);
            if (dist1 < dist2) {
                return -1;
            } else if (dist1 > dist2) {
                return 1;
            } else {
                return 0;
            }
        } else {
            System.out.println("Error: compartor casting, Bad arguments");
            return -1;
        }
    }
}
