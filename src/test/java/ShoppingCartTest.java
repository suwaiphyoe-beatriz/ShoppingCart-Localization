import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ShoppingCartTest {

    @Test
    public void testItemTotal() {
        double result = ShoppingCart.calculateItemTotal(10.0, 2);
        assertEquals(20.0, result, 0.001);
    }

    @Test
    public void testCartTotal() {

        List<Double> totals = new ArrayList<>();
        totals.add(10.0);
        totals.add(20.0);

        double result = ShoppingCart.calculateCartTotal(totals);

        assertEquals(30.0, result);
    }
}