import java.util.List;

public class ShoppingCart {
    public static double calculateItemTotal(double price, int quantity) {
        return price * quantity;
    }

    public static double calculateCartTotal(List<Double> totals) {
        double sum = 0;
        for (double t : totals) {
            sum += t;
        }
        return sum;
    }
}