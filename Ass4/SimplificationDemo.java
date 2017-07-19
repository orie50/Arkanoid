/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-02 */
public class SimplificationDemo {
    /**
     * run advanced simplification.
     * <p>
     * @param args - no args expected*/
    public static void main(String[] args) {

        // (((2.0 * x)^(y + z))^(2.0 * x))
        Expression e = new Pow(new Pow(new Mult(2, "x"), new Plus("y", "z")), new Mult(2, "x"));
        System.out.println(e);
        //simplify to ((2.0 * x)^((2.0 * x) * (y + z)))
        System.out.println(e.simplify());

        //Cos(90.0 + x)
        e = new Cos(new Plus(90, "x"));
        System.out.println(e);
        //-(Sin(x))
        e = e.simplify();
        System.out.println(e);

        //Sin(90.0 + x)
        e = new Sin(new Plus(90, "x"));
        System.out.println(e);
        //simplify to Cos(x)
        e = e.simplify();
        System.out.println(e);

        // -(-(-(-(-(y - x)))))
        e = new Neg(new Neg(new Neg(new Neg(new Neg(new Minus("y", "x"))))));
        System.out.println(e);
        // simplify to -(y - x)
        e = e.simplify();
        System.out.println(e);

        // x^0
        e = new Pow("x", 0);
        System.out.println(e);
        // simplify to 1
        e = e.simplify();
        System.out.println(e);

        // x^1
        e = new Pow("x", 1);
        System.out.println(e);
        // simplify to x
        e = e.simplify();
        System.out.println(e);

        // (x+y)/1
        e = new Div(new Plus("x", "y"), 1);
        System.out.println(e);
        // simplify to x+y
        e = e.simplify();
        System.out.println(e);
    }
}
