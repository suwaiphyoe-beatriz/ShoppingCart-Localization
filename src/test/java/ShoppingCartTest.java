import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ShoppingCartTest {

    // ─── ShoppingCart.calculateItemTotal ───

    @Test
    public void testItemTotal() {
        assertEquals(20.0, ShoppingCart.calculateItemTotal(10.0, 2), 0.001);
    }

    @Test
    public void testItemTotalZeroQuantity() {
        assertEquals(0.0, ShoppingCart.calculateItemTotal(10.0, 0), 0.001);
    }

    @Test
    public void testItemTotalZeroPrice() {
        assertEquals(0.0, ShoppingCart.calculateItemTotal(0.0, 5), 0.001);
    }

    @Test
    public void testItemTotalDecimalPrice() {
        assertEquals(14.0, ShoppingCart.calculateItemTotal(3.5, 4), 0.001);
    }

    @Test
    public void testItemTotalLargeValues() {
        assertEquals(99999.0, ShoppingCart.calculateItemTotal(999.99, 100), 0.1);
    }

    @Test
    public void testItemTotalSingleQuantity() {
        assertEquals(7.25, ShoppingCart.calculateItemTotal(7.25, 1), 0.001);
    }

    @Test
    public void testItemTotalFractionalResult() {
        assertEquals(3.3, ShoppingCart.calculateItemTotal(1.1, 3), 0.001);
    }

    @Test
    public void testItemTotalBothZero() {
        assertEquals(0.0, ShoppingCart.calculateItemTotal(0.0, 0), 0.001);
    }

    // ─── ShoppingCart.calculateCartTotal ───

    @Test
    public void testCartTotal() {
        assertEquals(30.0, ShoppingCart.calculateCartTotal(Arrays.asList(10.0, 20.0)), 0.001);
    }

    @Test
    public void testCartTotalEmptyList() {
        assertEquals(0.0, ShoppingCart.calculateCartTotal(new ArrayList<>()), 0.001);
    }

    @Test
    public void testCartTotalSingleItem() {
        assertEquals(15.0, ShoppingCart.calculateCartTotal(List.of(15.0)), 0.001);
    }

    @Test
    public void testCartTotalMultipleItems() {
        assertEquals(50.0, ShoppingCart.calculateCartTotal(Arrays.asList(5.0, 10.0, 15.0, 20.0)), 0.001);
    }

    @Test
    public void testCartTotalWithZeroEntry() {
        assertEquals(15.0, ShoppingCart.calculateCartTotal(Arrays.asList(0.0, 10.0, 5.0)), 0.001);
    }

    @Test
    public void testCartTotalAllZeros() {
        assertEquals(0.0, ShoppingCart.calculateCartTotal(Arrays.asList(0.0, 0.0, 0.0)), 0.001);
    }

    @Test
    public void testCartTotalDecimalValues() {
        assertEquals(7.0, ShoppingCart.calculateCartTotal(Arrays.asList(1.5, 2.5, 3.0)), 0.001);
    }

    @Test
    public void testCartTotalLargeList() {
        List<Double> totals = new ArrayList<>();
        for (int i = 1; i <= 10; i++) totals.add((double) i);
        assertEquals(55.0, ShoppingCart.calculateCartTotal(totals), 0.001);
    }

    @Test
    public void testCartTotalSingleZero() {
        assertEquals(0.0, ShoppingCart.calculateCartTotal(List.of(0.0)), 0.001);
    }

    // ─── CartItem – constructor & getters ───

    @Test
    public void testCartItemGetItemNumber() {
        CartItem item = new CartItem(1, 9.99, 3, 29.97);
        assertEquals(1, item.getItemNumber());
    }

    @Test
    public void testCartItemGetPrice() {
        CartItem item = new CartItem(1, 9.99, 3, 29.97);
        assertEquals(9.99, item.getPrice(), 0.001);
    }

    @Test
    public void testCartItemGetQuantity() {
        CartItem item = new CartItem(1, 9.99, 3, 29.97);
        assertEquals(3, item.getQuantity());
    }

    @Test
    public void testCartItemGetSubtotal() {
        CartItem item = new CartItem(1, 9.99, 3, 29.97);
        assertEquals(29.97, item.getSubtotal(), 0.001);
    }

    @Test
    public void testCartItemZeroValues() {
        CartItem item = new CartItem(0, 0.0, 0, 0.0);
        assertEquals(0,   item.getItemNumber());
        assertEquals(0.0, item.getPrice(),    0.001);
        assertEquals(0,   item.getQuantity());
        assertEquals(0.0, item.getSubtotal(), 0.001);
    }

    @Test
    public void testCartItemHighItemNumber() {
        CartItem item = new CartItem(999, 1.0, 1, 1.0);
        assertEquals(999, item.getItemNumber());
    }

    @Test
    public void testCartItemPrecisePrice() {
        CartItem item = new CartItem(2, 19.99, 5, 99.95);
        assertEquals(19.99, item.getPrice(),    0.001);
        assertEquals(99.95, item.getSubtotal(), 0.001);
    }

    @Test
    public void testCartItemLargeQuantity() {
        CartItem item = new CartItem(3, 0.01, 10000, 100.0);
        assertEquals(10000, item.getQuantity());
        assertEquals(100.0, item.getSubtotal(), 0.001);
    }

    @Test
    public void testCartItemSubtotalMatchesCalculation() {
        double expected = ShoppingCart.calculateItemTotal(5.0, 7);
        CartItem item = new CartItem(1, 5.0, 7, expected);
        assertEquals(expected, item.getSubtotal(), 0.001);
    }

    @Test
    public void testCartItemIndependentInstances() {
        CartItem a = new CartItem(1, 10.0, 2, 20.0);
        CartItem b = new CartItem(2,  5.0, 4, 20.0);
        assertNotEquals(a.getItemNumber(), b.getItemNumber());
        assertEquals(a.getSubtotal(), b.getSubtotal(), 0.001);
    }

    @Test
    public void testCartItemNumberSequence() {
        for (int i = 1; i <= 5; i++) {
            CartItem item = new CartItem(i, 1.0, 1, 1.0);
            assertEquals(i, item.getItemNumber());
        }
    }

    // ─── Integration: CartItem + ShoppingCart ───

    @Test
    public void testFullCartWorkflow() {
        CartItem i1 = new CartItem(1, 10.0, 2, ShoppingCart.calculateItemTotal(10.0, 2));
        CartItem i2 = new CartItem(2,  5.0, 3, ShoppingCart.calculateItemTotal( 5.0, 3));
        CartItem i3 = new CartItem(3, 20.0, 1, ShoppingCart.calculateItemTotal(20.0, 1));

        double total = ShoppingCart.calculateCartTotal(
                Arrays.asList(i1.getSubtotal(), i2.getSubtotal(), i3.getSubtotal()));
        assertEquals(55.0, total, 0.001);
    }

    @Test
    public void testSingleItemCart() {
        double sub = ShoppingCart.calculateItemTotal(15.99, 2);
        CartItem item = new CartItem(1, 15.99, 2, sub);
        assertEquals(31.98, ShoppingCart.calculateCartTotal(List.of(item.getSubtotal())), 0.001);
    }

    @Test
    public void testCartWithTenItems() {
        List<Double> subs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) subs.add(ShoppingCart.calculateItemTotal(i, i));
        // sum of i^2 for i=1..10 = 385
        assertEquals(385.0, ShoppingCart.calculateCartTotal(subs), 0.001);
    }

    @Test
    public void testZeroPriceItemInCart() {
        double sub = ShoppingCart.calculateItemTotal(0.0, 10);
        CartItem item = new CartItem(1, 0.0, 10, sub);
        assertEquals(0.0, ShoppingCart.calculateCartTotal(List.of(item.getSubtotal())), 0.001);
    }

    @Test
    public void testRunningTotalAccumulation() {
        double running = 0;
        double[] prices = {5.0, 10.0, 7.5};
        int[]    qtys   = {2,   1,    4};

        for (int i = 0; i < prices.length; i++) {
            running += ShoppingCart.calculateItemTotal(prices[i], qtys[i]);
        }
        assertEquals(50.0, running, 0.001); // 10 + 10 + 30
    }
}