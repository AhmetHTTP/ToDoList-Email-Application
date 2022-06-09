import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Login_Form extends JFrame {

	private JFrame frame;
	private JTextField jtextField_Username;
	private JPasswordField jpasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Login_Form frame = new Login_Form();
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
	public Login_Form() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 370);

		// center the form
		this.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(10, 11, 444, 349);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 82, 444, 267);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		// Username field starts
		jtextField_Username = new JTextField();
		jtextField_Username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {

				// clear the textfield on focus if the text is "username"
				if (jtextField_Username.getText().trim().toLowerCase().equals("username")) {
					jtextField_Username.setText("");
					jtextField_Username.setForeground(Color.CYAN);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {

				// if the text field is equal to username or empty
				// we will set the "username" text in the field
				// on focus lost event
				if (jtextField_Username.getText().trim().equals("")
						|| jtextField_Username.getText().trim().toLowerCase().equals("username")) {
					jtextField_Username.setText("username");
					jtextField_Username.setForeground(Color.CYAN);
				}
			}
		});
		jtextField_Username.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextField_Username.setForeground(Color.CYAN);
		jtextField_Username.setBackground(Color.DARK_GRAY);
		jtextField_Username.setText("username");
		jtextField_Username.setBounds(84, 40, 298, 38);
		panel_2.add(jtextField_Username);
		jtextField_Username.setColumns(10);
		// Username field ends

		// Password filed starts
		jpasswordField = new JPasswordField();
		jpasswordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {

				// clear the password field on focus if the text is "password"

				// get the password text
				String pass = String.valueOf(jpasswordField.getPassword());

				if (pass.trim().toLowerCase().equals("password")) {
					jpasswordField.setText("");
					jpasswordField.setForeground(Color.CYAN);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {

				// if the password field is equal to password or empty
				// we will set the "password" text in the field
				// on focus lost event

				// get the password text
				String pass = String.valueOf(jpasswordField.getPassword());

				if (pass.trim().equals("") || pass.trim().toLowerCase().equals("password")) {
					jpasswordField.setText("password");
					jpasswordField.setForeground(Color.CYAN);
				}
			}
		});
		jpasswordField.setText("password");
		jpasswordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jpasswordField.setForeground(Color.CYAN);
		jpasswordField.setBackground(Color.DARK_GRAY);
		jpasswordField.setToolTipText("");
		jpasswordField.setBounds(84, 109, 298, 38);
		panel_2.add(jpasswordField);
		// Password filed ends

		// Username image starts
		JLabel jLabel_username = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/username_icon.png")).getImage();
		jLabel_username.setIcon(new ImageIcon(img));
		jLabel_username.setBounds(42, 40, 32, 38);
		panel_2.add(jLabel_username);
		// Username image ends

		// Password image starts
		JLabel jLabel_password = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/password_icon.png")).getImage();
		jLabel_password.setIcon(new ImageIcon(img1));
		jLabel_password.setBounds(42, 109, 32, 38);
		panel_2.add(jLabel_password);
		// Password image ends

		// Login button starts
		JButton jButton_Login = new JButton("Login");
		jButton_Login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				PreparedStatement st;
				ResultSet rs;

				// get the username and password
				String username = jtextField_Username.getText();
				String password = String.valueOf(jpasswordField.getPassword());

				// create a select query to check if the username and the password exist in the
				// database
				String query = "SELECT * FROM `users` WHERE `username` = ? AND `password` = ?";

				// show a message if the username or the password fields are empty
				if (username.trim().equals("username")) {

					JOptionPane.showMessageDialog(null, "Enter Your Username", "Empty Username!!", 2);
				}

				else if (password.trim().equals("password")) {

					JOptionPane.showMessageDialog(null, "Enter Your Password", "Empty Password!!", 2);
				}

				else {

					try {
						st = My_CNX.getConnection().prepareStatement(query);

						st.setString(1, username);
						st.setString(2, password);
						rs = st.executeQuery();

						if (rs.next()) {

							// show a new form

							setVisible(false); // hide
							new ToDoList().setVisible(true); // show

							// close the current form (login form)
							dispose();
						} else {
							// error message
							JOptionPane.showMessageDialog(null, "Invalid Username / Password", "Login Error", 2);
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						Logger.getLogger(Login_Form.class.getName()).log(Level.SEVERE, null, e1);
					}

				}

			}
		});
		jButton_Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jButton_Login.setBackground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jButton_Login.setBackground(Color.DARK_GRAY);
			}
		});
		jButton_Login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButton_Login.setForeground(Color.CYAN);
		jButton_Login.setFont(new Font("Tahoma", Font.BOLD, 15));
		jButton_Login.setBackground(Color.DARK_GRAY);
		jButton_Login.setBounds(84, 170, 298, 38);
		panel_2.add(jButton_Login);
		// Login button ends

		// Login panel title starts
		JLabel jPanel_title = new JLabel("LOGIN");
		jPanel_title.setForeground(Color.CYAN);
		jPanel_title.setHorizontalAlignment(SwingConstants.CENTER);
		jPanel_title.setFont(new Font("Tahoma", Font.BOLD, 30));
		jPanel_title.setBounds(175, 11, 104, 60);
		panel_1.add(jPanel_title);
		// Login panel title ends

		// Minimize box starts
		JLabel jLabel_minimize = new JLabel("-");
		jLabel_minimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabel_minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Border label_border1 = BorderFactory.createMatteBorder(0, 1, 1, 1, Color.CYAN);
				jLabel_minimize.setBorder(label_border1);
				jLabel_minimize.setForeground(Color.CYAN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Border label_border1 = BorderFactory.createMatteBorder(0, 1, 1, 1, Color.DARK_GRAY);
				jLabel_minimize.setBorder(label_border1);
				jLabel_minimize.setForeground(Color.DARK_GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				setState(JFrame.ICONIFIED);

			}
		});
		jLabel_minimize.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_minimize.setFont(new Font("Tahoma", Font.BOLD, 30));
		jLabel_minimize.setForeground(Color.DARK_GRAY);
		jLabel_minimize.setBounds(374, 0, 30, 30);
		panel_1.add(jLabel_minimize);
		// Minimize box ends

		// Close box starts
		JLabel jLabel_close = new JLabel("x");
		jLabel_close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabel_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Border label_border2 = BorderFactory.createMatteBorder(0, 1, 1, 0, Color.CYAN);
				jLabel_close.setBorder(label_border2);
				jLabel_close.setForeground(Color.CYAN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Border label_border2 = BorderFactory.createMatteBorder(0, 1, 1, 0, Color.DARK_GRAY);
				jLabel_close.setBorder(label_border2);
				jLabel_close.setForeground(Color.DARK_GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		jLabel_close.setForeground(Color.DARK_GRAY);
		jLabel_close.setFont(new Font("Tahoma", Font.BOLD, 30));
		jLabel_close.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_close.setBounds(414, 0, 30, 30);
		panel_1.add(jLabel_close);
		// Close box ends

		// create border for the username and password field
		Border field_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.CYAN);
		jtextField_Username.setBorder(field_border);
		jpasswordField.setBorder(field_border);

		JLabel jNewLabel_Create_Account = new JLabel(">> No Account? Create One!");
		jNewLabel_Create_Account.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jNewLabel_Create_Account.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				Border label_border = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.CYAN);
				jNewLabel_Create_Account.setBorder(label_border);
			}

			@Override
			public void mouseExited(MouseEvent e) {

				jNewLabel_Create_Account.setBorder(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				setVisible(false); // hide
				new Register_Form().setVisible(true); // show

			}
		});
		jNewLabel_Create_Account.setForeground(Color.DARK_GRAY);
		jNewLabel_Create_Account.setHorizontalAlignment(SwingConstants.CENTER);
		jNewLabel_Create_Account.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		jNewLabel_Create_Account.setBounds(117, 225, 230, 20);
		panel_2.add(jNewLabel_Create_Account);

	}
}
