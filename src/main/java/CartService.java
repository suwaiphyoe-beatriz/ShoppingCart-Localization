import java.sql.*;

public class CartService {

    public int saveCart(int totalItems, double totalCost, String language) {
        int cartId = -1;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, totalItems);
            stmt.setDouble(2, totalCost);
            stmt.setString(3, language);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cartId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cartId;
    }

    public void saveCartItem(int cartId, int itemNumber, double price, int quantity, double subtotal) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, cartId);
            stmt.setInt(2, itemNumber);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, subtotal);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}