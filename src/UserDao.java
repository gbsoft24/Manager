import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
	private Connection conn;

	public void registerAUser(User user) throws ClassNotFoundException, SQLException {

		String sql;

		if (LoginPage.dbName == LoginPage.DatabaseName.MYSQL) {

			conn = ConnectionManager.getMySqlConnection();
			sql = "INSERT INTO `manager`.`user`( `name`, `age`, `gender`, `address`, `phone`, `username`, `password`) VALUES(?,?,?,?,?,?,?)";

		} else {

			conn = ConnectionManager.getOjdbcConnection();
			sql = "INSERT INTO users ( name, age, gender, address, phone, user_name, pass_word) VALUES(?,?,?,?,?,?,?)";

		}

		PreparedStatement stat = conn.prepareStatement(sql);
		// `name`, `age`, `gender`, `address`, `phone`, `username`, `password`

		stat.setString(1, user.getName());
		stat.setInt(2, user.getAge());
		stat.setString(3, String.valueOf(user.getGender()));
		stat.setString(4, user.getAddress());
		stat.setLong(5, user.getPhone());
		stat.setString(6, user.getUsername());
		stat.setString(7, user.getPassword());

		stat.executeUpdate();

//		oracle table creation query
//		user_id number(10) not null,
//		constraint users_pk primary key (user_id)

//		create table users (
//		name varchar2(20) not null,
//		age number(5),
//		gender varchar2(5),
//		address varchar2(5),
//		phone number(15),
//		user_name varchar2(20) not null,
//		pass_word varchar2(20) not null );

		conn.close();
	}

	public List<User> getAllUsers() throws ClassNotFoundException, SQLException {

		List<User> userList = new ArrayList<>();
		LoginPage.dbName = LoginPage.DatabaseName.ORACLE;

		if (LoginPage.dbName == LoginPage.DatabaseName.MYSQL) {
			conn = ConnectionManager.getMySqlConnection();
		} else {
			conn = ConnectionManager.getOjdbcConnection();
		}

		Statement stat = conn.createStatement();
		ResultSet rs;
		if (LoginPage.dbName == LoginPage.DatabaseName.MYSQL) {
			rs = stat.executeQuery("SELECT * FROM `manager`.`user`");
		} else {
			rs = stat.executeQuery("SELECT * FROM users");
		}

		while (rs.next()) {
			User user = new User();
			user.setName(rs.getString("name"));
			user.setAge(rs.getInt("age"));
			user.setGender(rs.getString("gender").charAt(0));
			user.setAddress(rs.getString("address"));
			user.setPhone(rs.getLong("phone"));
			user.setUsername(rs.getString("user_name"));
			user.setPassword(rs.getString("pass_word"));

			userList.add(user);
		}

		conn.close();
		return userList;
	}

	public User getAUser(String userName) {
		User user = new User();
		try {
			conn = ConnectionManager.getOjdbcConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_name = '" + userName + "'");
			while (rs.next()) {
				user.setName(rs.getString("name"));
				user.setAge(rs.getInt("age"));
				user.setGender(rs.getString("gender").charAt(0));
				user.setAddress(rs.getString("address"));
				user.setPhone(rs.getLong("phone"));
				user.setUsername(rs.getString("user_name"));
				user.setPassword(rs.getString("pass_word"));
			}
			return user;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteAUser(String userName) {
		try {
			conn = ConnectionManager.getOjdbcConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM users WHERE user_name = '" + userName + "'");
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void editAUser(User user, String oldUsername) {
		try {
			conn = ConnectionManager.getOjdbcConnection();
			Statement stmt = conn.createStatement();
			
			String updateUser = "UPDATE users SET name = '" + user.getName() 
								+ "', age = " + user.getAge()
								+ ", gender = '" + user.getGender()
								+ "', address = '" + user.getAddress()
								+ "', phone = " + user.getPhone()
								+ ", user_name = '" + user.getUsername()
								+ "', pass_word = '" + user.getPassword()
								+"' WHERE user_name = '" + oldUsername + "'";
			
			stmt.executeUpdate(updateUser);
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
