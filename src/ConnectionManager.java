import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	public static Connection getMySqlConnection() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/manager";
		String user = "root";
		String password = "";

		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	public static Connection getOjdbcConnection() throws ClassNotFoundException, SQLException {

		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String userName = "chiran";
		String passWord = "anonlol2020";

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(url, userName, passWord);
		return conn;

	}

}
