import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginPage extends JFrame {

	public static enum LoginStatus {
		LOGGED_IN, LOGGED_OUT
	};

	public static enum DatabaseName {
		ORACLE, MYSQL
	};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel userLbl, passLbl;
	private JTextField userTxt;
	private JPasswordField passTxt;
	private JButton loginBtn;
	private JRadioButton oracleRdo, mysqlRdo;

	public static DatabaseName dbName;
	public static LoginStatus loginStatus = LoginStatus.LOGGED_OUT;

	private static LoginPage frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginPage() {
		setTitle("Login Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 435, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getUserLbl());
		contentPane.add(getPassLbl());
		contentPane.add(getUserTxt());
		contentPane.add(getLoginBtn());
		contentPane.add(getPassTxt());
		contentPane.add(getOracleRdo());
		contentPane.add(getMysqlRdo());

		ButtonGroup btnGrp = new ButtonGroup();
		btnGrp.add(oracleRdo);
		btnGrp.add(mysqlRdo);

	}

	private JLabel getUserLbl() {
		if (userLbl == null) {
			userLbl = new JLabel("Username:");
			userLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
			userLbl.setBounds(30, 24, 84, 26);
		}
		return userLbl;
	}

	private JLabel getPassLbl() {
		if (passLbl == null) {
			passLbl = new JLabel("Password:");
			passLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
			passLbl.setBounds(30, 79, 84, 26);
		}
		return passLbl;
	}

	private JTextField getUserTxt() {
		if (userTxt == null) {
			userTxt = new JTextField();
			userTxt.setBounds(110, 28, 138, 20);
			userTxt.setColumns(10);
		}
		return userTxt;
	}

	private JButton getLoginBtn() {
		if (loginBtn == null) {
			loginBtn = new JButton("Login");
			loginBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String userName = userTxt.getText().trim();
					StringBuilder sb = new StringBuilder();
					char[] pw = passTxt.getPassword();
					for (char c : pw) {
						sb.append(c);
					}
					String passWord = sb.toString();
					System.out.println("Username: " + userName + " and Password : " + passWord);

					UserDao userDao = new UserDao();
					try {
						List<User> users = userDao.getAllUsers();
						for (User user : users) {
							System.out.println(user);
							if (userName.equals(user.getUsername()) && passWord.equals(user.getPassword())) {
								System.out.println("Logged in");
								loginStatus = LoginStatus.LOGGED_IN;
								Manager.main(null);
								frame.dispose();
							}
						}
						if (loginStatus == LoginStatus.LOGGED_OUT) {
							JOptionPane.showMessageDialog(contentPane, "It looks like you haven't register yet.");
							Registration.main(null);
							frame.dispose();
						}
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
			loginBtn.setBounds(285, 32, 97, 70);
		}
		return loginBtn;
	}

	private JPasswordField getPassTxt() {
		if (passTxt == null) {
			passTxt = new JPasswordField();
			passTxt.setBounds(110, 83, 138, 22);
		}
		return passTxt;
	}

	private JRadioButton getOracleRdo() {
		if (oracleRdo == null) {
			oracleRdo = new JRadioButton("Oracle");
			oracleRdo.setFont(new Font("Tahoma", Font.BOLD, 13));
			oracleRdo.setBounds(30, 120, 70, 23);
		}
		oracleRdo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dbName = DatabaseName.ORACLE;

			}
		});
		return oracleRdo;
	}

	private JRadioButton getMysqlRdo() {
		if (mysqlRdo == null) {
			mysqlRdo = new JRadioButton("MySql");
			mysqlRdo.setFont(new Font("Tahoma", Font.BOLD, 13));
			mysqlRdo.setBounds(130, 120, 70, 23);
		}
		mysqlRdo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dbName = DatabaseName.MYSQL;
			}
		});
		return mysqlRdo;
	}
}
