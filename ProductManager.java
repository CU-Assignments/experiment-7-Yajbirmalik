import java.sql.*;
import java.util.Scanner;

public class ProductManager {
    static String url = "jdbc:mysql://localhost:3306/your_database_name";
    static String user = "your_username";
    static String password = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Product\n2. View Products\n3. Update Product\n4. Delete Product\n5. Exit");
            int choice = sc.nextInt();

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                conn.setAutoCommit(false);

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter ProductName, Price, Quantity: ");
                        String name = sc.next();
                        double price = sc.nextDouble();
                        int qty = sc.nextInt();

                        PreparedStatement ps = conn.prepareStatement("INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)");
                        ps.setString(1, name);
                        ps.setDouble(2, price);
                        ps.setInt(3, qty);
                        ps.executeUpdate();
                        conn.commit();
                        System.out.println("Product added.");
                    }
                    case 2 -> {
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                        while (rs.next()) {
                            System.out.println(rs.getInt("ProductID") + " " + rs.getString("ProductName") + " " +
                                    rs.getDouble("Price") + " " + rs.getInt("Quantity"));
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter ProductID to update: ");
                        int id = sc.nextInt();
                        System.out.print("New Name, Price, Quantity: ");
                        String name = sc.next();
                        double price = sc.nextDouble();
                        int qty = sc.nextInt();

                        PreparedStatement ps = conn.prepareStatement("UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?");
                        ps.setString(1, name);
                        ps.setDouble(2, price);
                        ps.setInt(3, qty);
                        ps.setInt(4, id);
                        ps.executeUpdate();
                        conn.commit();
                        System.out.println("Product updated.");
                    }
                    case 4 -> {
                        System.out.print("Enter ProductID to delete: ");
                        int id = sc.nextInt();

                        PreparedStatement ps = conn.prepareStatement("DELETE FROM Product WHERE ProductID=?");
                        ps.setInt(1, id);
                        ps.executeUpdate();
                        conn.commit();
                        System.out.println("Product deleted.");
                    }
                    case 5 -> {
                        sc.close();
                        return;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
