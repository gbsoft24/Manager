import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Registration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Registration frame;
	private JPanel contentPane;
	private JPanel mainPnl;
	private JTextField usernameTxt;
	private JButton submitBtn;
	private JLabel nameLbl;
	private JLabel ageLbl;
	private JPanel genderPnl;
	private JRadioButton maleRdo;
	private JRadioButton femaleRdo;
	private JTextField nameTxt;
	private JTextField ageTxt;
	private JLabel addLbl;
	private JTextField addressTxt;
	private JTextField phoneTxt;
	private JLabel phoneLbl;
	private JLabel usernameLbl;
	private JLabel passwordLbl;
	private JPasswordField passwordTxt;

	public static User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Registration();
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
	public Registration() {
		if (user != null)
			setTitle("Edit User \"" + user.getUsername() + "\"");
		else
			setTitle("Register Here");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getMainPnl());
	}

	private JPanel getMainPnl() {
		if (mainPnl == null) {
			mainPnl = new JPanel();
			mainPnl.setLayout(null);
			mainPnl.setBorder(new EmptyBorder(5, 5, 5, 5));
			mainPnl.setBounds(10, 11, 418, 231);
			mainPnl.add(getUsernameTxt());
			mainPnl.add(getSubmitBtn());
			mainPnl.add(getNameLbl());
			mainPnl.add(getAgeLbl());
			mainPnl.add(getGenderPnl());
			mainPnl.add(getNameTxt());
			mainPnl.add(getAgeTxt());
			mainPnl.add(getAddLbl());
			mainPnl.add(getAddressTxt());
			mainPnl.add(getPhoneTxt());
			mainPnl.add(getPhoneLbl());
			mainPnl.add(getUsernameLbl());
			mainPnl.add(getPasswordLbl());
			mainPnl.add(getPasswordTxt());
		}
		return mainPnl;
	}

	private JTextField getUsernameTxt() {
		if (usernameTxt == null) {
			usernameTxt = new JTextField();
			usernameTxt.setColumns(10);
			usernameTxt.setBounds(90, 163, 140, 20);
			if (user != null) {
				usernameTxt.setText(user.getUsername());
			}
		}
		return usernameTxt;
	}

	private JButton getSubmitBtn() {
		if (submitBtn == null) {
			submitBtn = new JButton();
			submitBtn.setText("Submit");
			submitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UserDao userDao = new UserDao();
					User editedUser = new User();
					editedUser.setName(nameTxt.getText());
					editedUser.setAddress(addressTxt.getText());
					editedUser.setAge(Integer.parseInt(ageTxt.getText()));
					if (maleRdo.isSelected()) {
						editedUser.setGender('M');
					} else if (femaleRdo.isSelected()) {
						editedUser.setGender('F');
					} else {
						JOptionPane.showMessageDialog(contentPane, "Who are you? Select Male or Female to continue.");
					}
					editedUser.setPhone(Long.parseLong(phoneTxt.getText()));
					editedUser.setUsername(usernameTxt.getText().trim());
					StringBuilder sb = new StringBuilder();
					char[] pw = passwordTxt.getPassword();
					for (char c : pw) {
						sb.append(c);
					}
					editedUser.setPassword(sb.toString());
					try {
						if (user != null)
							userDao.editAUser(editedUser, user.getUsername());
						else
							userDao.registerAUser(editedUser);
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					if (user != null)
						Manager.main(null);
					else
						LoginPage.main(null);
					frame.dispose();
				}
			});
			submitBtn.setBounds(295, 142, 97, 62);
		}
		return submitBtn;

	}

	private JLabel getNameLbl() {
		if (nameLbl == null) {
			nameLbl = new JLabel("Name*:");
			nameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
			nameLbl.setBounds(10, 11, 66, 26);
		}
		return nameLbl;
	}

	private JLabel getAgeLbl() {
		if (ageLbl == null) {
			ageLbl = new JLabel("Age:");
			ageLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
			ageLbl.setBounds(10, 48, 66, 26);
		}
		return ageLbl;
	}

	private JPanel getGenderPnl() {
		if (genderPnl == null) {
			genderPnl = new JPanel();
			genderPnl.setFont(new Font("Tahoma", Font.BOLD, 13));
			genderPnl.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Gender",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 204, 51)));
			genderPnl.setBounds(295, 0, 113, 73);
			genderPnl.setLayout(null);
			genderPnl.add(getMaleRdo());
			genderPnl.add(getFemaleRdo());

			ButtonGroup bg = new ButtonGroup();
			bg.add(maleRdo);
			bg.add(femaleRdo);

		}
		return genderPnl;
	}

	private JRadioButton getMaleRdo() {
		if (maleRdo == null) {
			maleRdo = new JRadioButton("Male");
			maleRdo.setFont(new Font("Tahoma", Font.BOLD, 13));
			maleRdo.setBounds(6, 20, 109, 23);
			if (user != null) {
				if (user.getGender() == 'M')
					maleRdo.setSelected(true);
			}
		}
		return maleRdo;
	}

	private JRadioButton getFemaleRdo() {
		if (femaleRdo == null) {
			femaleRdo = new JRadioButton("Female");
			femaleRdo.setFont(new Font("Tahoma", Font.BOLD, 13));
			femaleRdo.setBounds(6, 46, 109, 23);
			if (user != null) {
				if (user.getGender() == 'F')
					femaleRdo.setSelected(true);
			}
		}
		return femaleRdo;
	}

	private JTextField getNameTxt() {
		if (nameTxt == null) {
			nameTxt = new JTextField();
			nameTxt.setBounds(90, 15, 195, 20);
			nameTxt.setColumns(10);
			if (user != null) {
				nameTxt.setText(user.getName());
			}
		}
		return nameTxt;
	}

	private JTextField getAgeTxt() {
		if (ageTxt == null) {
			ageTxt = new JTextField();
			ageTxt.setColumns(10);
			ageTxt.setBounds(90, 52, 42, 20);
			if (user != null) {
				ageTxt.setText(String.valueOf(user.getAge()));
			}
		}
		return ageTxt;
	}

	private JLabel getAddLbl() {
		if (addLbl == null) {
			addLbl = new JLabel("Address:");
			addLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
			addLbl.setBounds(10, 85, 66, 26);
		}
		return addLbl;
	}

	private JTextField getAddressTxt() {
		if (addressTxt == null) {
			addressTxt = new JTextField();
			addressTxt.setColumns(10);
			addressTxt.setBounds(90, 89, 195, 20);
			if (user != null)
				addressTxt.setText(user.getAddress());
		}
		return addressTxt;
	}

	private JTextField getPhoneTxt() {
		if (phoneTxt == null) {
			phoneTxt = new JTextField();
			phoneTxt.setColumns(10);
			phoneTxt.setBounds(89, 122, 123, 20);
			if (user != null)
				phoneTxt.setText(String.valueOf(user.getPhone()));
		}
		return phoneTxt;
	}

	private JLabel getPhoneLbl() {
		if (phoneLbl == null) {
			phoneLbl = new JLabel("Phone:");
			phoneLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
			phoneLbl.setBounds(10, 122, 66, 26);
		}
		return phoneLbl;
	}

	private JLabel getUsernameLbl() {
		if (usernameLbl == null) {
			usernameLbl = new JLabel("Username:");
			usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
			usernameLbl.setBounds(10, 157, 80, 26);
		}
		return usernameLbl;
	}

	private JLabel getPasswordLbl() {
		if (passwordLbl == null) {
			passwordLbl = new JLabel("Password:");
			passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
			passwordLbl.setBounds(10, 194, 80, 26);
		}
		return passwordLbl;
	}

	private JPasswordField getPasswordTxt() {
		if (passwordTxt == null) {
			passwordTxt = new JPasswordField();
			passwordTxt.setBounds(90, 198, 140, 20);
			if (user != null)
				passwordTxt.setText(user.getPassword());
		}
		return passwordTxt;
	}
}
