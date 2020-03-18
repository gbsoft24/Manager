import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Manager extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private JPanel contentPane;
	private UserDao userDao;
	private List<User> users;

	private String[] headers = { "Name", "Age", "Gender", "Adress", "Phone", "Username", "Password" };
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel myTableModel;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Manager();
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
	public Manager() {
		setTitle("Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		userDao = new UserDao();
		addPopup(getTable());
		contentPane.add(getScrollPane());
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(6, 6, 578, 455);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();

			try {
				users = userDao.getAllUsers();

				myTableModel = new DefaultTableModel(headers, 0);
				table.setModel(myTableModel);
				for (User user : users) {
					myTableModel.addRow(new Object[] { user.getName(), user.getAge(), user.getGender(),
							user.getAddress(), user.getPhone(), user.getUsername(), user.getPassword() });
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return table;
	}

	private void addPopup(Component component) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				getPopupMenu(row).show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private JPopupMenu getPopupMenu(int row) {
		if (popupMenu == null) {
			popupMenu = new JPopupMenu();
			popupMenu.add(getMenuItemEdit(row));
			popupMenu.add(getMenuItemDelete(row));
		}
		return popupMenu;
	}

	private JMenuItem getMenuItemEdit(int row) {
		if (menuItemEdit == null) {
			menuItemEdit = new JMenuItem("Edit");
			menuItemEdit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					// will create a Registration constructor which will take a user object as a
					// param and then uses the data
					// from that object to fillup every UI of Registration form....then also allows
					// the user to type in the boxes
					// and then later save that changes permanently in the db as well as in our
					// tablemodel

					// `name`, `age`, `gender`, `address`, `phone`, `username`, `password`
//					User editedUser = new User();
//					editedUser.setName(String.valueOf(table.getValueAt(row, 0)));
//					editedUser.setAge(Integer.parseInt(String.valueOf(table.getValueAt(row, 1))));
//					editedUser.setGender(String.valueOf(table.getValueAt(row, 2)).charAt(0));
//					editedUser.setAddress(String.valueOf(table.getValueAt(row, 3)));
//					editedUser.setPhone(Integer.parseInt(String.valueOf(table.getValueAt(row, 4))));
//					editedUser.setUsername(String.valueOf(table.getValueAt(row, 5)));
//					editedUser.setPassword(String.valueOf(table.getValueAt(row, 6)));
					
					Registration.user = users.get(row);
					Registration.main(null);
					frame.dispose();

				}
			});
		}
		return menuItemEdit;
	}

	private JMenuItem getMenuItemDelete(int row) {
		if (menuItemDelete == null) {
			menuItemDelete = new JMenuItem("Delete");
			menuItemDelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String username = users.get(row).getUsername();
					userDao.deleteAUser(username);
					users.remove(row);
					myTableModel.removeRow(row);
					JOptionPane.showMessageDialog(contentPane, "The row has been deleted successfully.");
				}
			});
		}
		return menuItemDelete;
	}
}
