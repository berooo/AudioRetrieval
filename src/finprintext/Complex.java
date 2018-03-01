package finprintext;

/**
 * Created by Acer on 2016-11-06.
 * 复数类
 */
public class Complex {
    private double a, b;

    public Complex(double _a, double _b) {
        a = _a;
        b = _b;
    }
    public Complex() {
        a = 0;
        b = 0;
    }

    //复数类加法
    public Complex add(final Complex e) {
        return new Complex(a + e.a, b + e.b);
    }

    //复数类减法
    public Complex sub(final Complex e) {
        return new Complex(a - e.a, b - e.b);
    }

    //复数类乘法
    public Complex mul(final Complex e) {
        return new Complex (a * e.a - b * e.b, a * e.b + b * e.a);
    }

    //复数类求模
    public double abs() {
        // use hypot instead of sqrt(pow(a, 2), pow(b, 2))
        // to improve performance
        return Math.hypot(a, b);
    }
}
