import java.sql.*;

public class EmployeeFetcher {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database_name";
        String user = "your_username";
        String password = "your_password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM Employee";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("EmpID\tName\tSalary");
            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(id + "\t" + name + "\t" + salary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
