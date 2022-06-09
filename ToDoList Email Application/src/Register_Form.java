import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Register_Form extends JFrame {

	private JPanel contentPane;
	private JTextField jtextField_Fullname;
	private JPasswordField jpasswordField_1;
	private JPasswordField jpasswordField_2;
	private JTextField jtextField_Username;
	private JTextField jtextField_Phone;
	private String gender;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Register_Form frame = new Register_Form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// create a function to verify the empty fields
	public boolean verifyFields() {

		String fname = jtextField_Fullname.getText();
		String uname = jtextField_Username.getText();
		String phone = jtextField_Phone.getText();
		String pass1 = String.valueOf(jpasswordField_1.getPassword());
		String pass2 = String.valueOf(jpasswordField_2.getPassword());

		// check empty fields
		if (fname.trim().equals("") || uname.trim().equals("") || phone.trim().equals("") || pass1.trim().equals("")
				|| pass2.trim().equals("")) {

			JOptionPane.showMessageDialog(null, "One Or More Fileds Are Empty", "Empty Fields", 2);
			return false;
		}

		// check if the two password are equals
		else if (!pass1.equals(pass2)) {

			JOptionPane.showMessageDialog(null, "Password Does Not Match", "Confirm Password", 2);
			return false;
		}

		// if everything is OK
		else {
			return true;
		}

	}

	// create a function to check if the entered username already exists in the
	// database
	public boolean checkUsername(String username) {

		PreparedStatement st;
		ResultSet rs;
		boolean username_exist = false;

		String query = "SELECT * FROM `users` WHERE `username` = ?";

		try {

			st = My_CNX.getConnection().prepareStatement(query);
			st.setString(1, username);
			rs = st.executeQuery();

			if (rs.next()) {

				username_exist = true;
				JOptionPane.showMessageDialog(null, "This Username is Already Taken, Choose Another One",
						"Username Failed", 2);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(Register_Form.class.getName()).log(Level.SEVERE, null, e);
		}

		return username_exist;

	}

	/**
	 * Create the frame.
	 */
	public Register_Form() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 516, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// center the form
		this.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(10, 11, 494, 549);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 78, 474, 460);
		panel.add(panel_1);
		panel_1.setLayout(null);

		// Register button starts
		JButton jButton_Register = new JButton("Register");
		jButton_Register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String fname = jtextField_Fullname.getText();
				String username = jtextField_Username.getText();
				String pass1 = String.valueOf(jpasswordField_1.getPassword());
				String pass2 = String.valueOf(jpasswordField_2.getPassword());
				String phone = jtextField_Phone.getText();

				JRadioButton jRadioButton_Female = new JRadioButton("Female");

				if (jRadioButton_Female.isSelected()) {

					gender = "Female";
				}

				if (verifyFields()) {

					if (!checkUsername(username)) {

						PreparedStatement ps;
						ResultSet rs;
						String registerUserQuery = "INSERT INTO `users`(`full_name`, `username`, `password`, `phone`, `gender`) VALUES (?,?,?,?,?)";

						try {

							ps = My_CNX.getConnection().prepareStatement(registerUserQuery);
							ps.setString(1, fname);
							ps.setString(2, username);
							ps.setString(3, pass1);
							ps.setString(4, phone);
							ps.setString(5, gender);

							if (ps.executeUpdate() != 0) {

								JOptionPane.showMessageDialog(null, "Your Account Has Been Created");
							} else {

								JOptionPane.showMessageDialog(null, "Error: Check Your Information");
							}

						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							Logger.getLogger(Register_Form.class.getName()).log(Level.SEVERE, null, e1);

						}

					}
				}

			}
		});
		jButton_Register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jButton_Register.setBackground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jButton_Register.setBackground(Color.DARK_GRAY);
			}
		});
		jButton_Register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButton_Register.setFont(new Font("Tahoma", Font.BOLD, 15));
		jButton_Register.setBackground(Color.DARK_GRAY);
		jButton_Register.setForeground(Color.CYAN);
		jButton_Register.setBounds(84, 349, 320, 50);
		panel_1.add(jButton_Register);
		// Register button ends

		JLabel lblNewLabel_1 = new JLabel("Full Name :");
		lblNewLabel_1.setForeground(Color.DARK_GRAY);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(74, 43, 90, 40);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("User Name :");
		lblNewLabel_2.setForeground(Color.DARK_GRAY);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(54, 94, 110, 40);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Password :");
		lblNewLabel_3.setForeground(Color.DARK_GRAY);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(74, 145, 90, 40);
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Confirm Password :");
		lblNewLabel_4.setForeground(Color.DARK_GRAY);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(14, 196, 150, 40);
		panel_1.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Phone :");
		lblNewLabel_5.setForeground(Color.DARK_GRAY);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(74, 247, 90, 40);
		panel_1.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Gender :");
		lblNewLabel_6.setForeground(Color.DARK_GRAY);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setBounds(74, 298, 90, 40);
		panel_1.add(lblNewLabel_6);

		jtextField_Fullname = new JTextField();
		jtextField_Fullname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jtextField_Fullname.setBackground(Color.DARK_GRAY);
		jtextField_Fullname.setForeground(Color.CYAN);
		jtextField_Fullname.setBounds(174, 43, 220, 40);
		panel_1.add(jtextField_Fullname);
		jtextField_Fullname.setColumns(10);

		jpasswordField_1 = new JPasswordField();
		jpasswordField_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jpasswordField_1.setForeground(Color.CYAN);
		jpasswordField_1.setBackground(Color.DARK_GRAY);
		jpasswordField_1.setBounds(174, 145, 220, 40);
		panel_1.add(jpasswordField_1);

		jpasswordField_2 = new JPasswordField();
		jpasswordField_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jpasswordField_2.setForeground(Color.CYAN);
		jpasswordField_2.setBackground(Color.DARK_GRAY);
		jpasswordField_2.setBounds(174, 196, 220, 40);
		panel_1.add(jpasswordField_2);

		jtextField_Username = new JTextField();
		jtextField_Username.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jtextField_Username.setForeground(Color.CYAN);
		jtextField_Username.setBackground(Color.DARK_GRAY);
		jtextField_Username.setBounds(174, 94, 220, 40);
		panel_1.add(jtextField_Username);
		jtextField_Username.setColumns(10);

		// Phone text field starts
		jtextField_Phone = new JTextField();
		jtextField_Phone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jtextField_Phone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				// allow only numbers
				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		jtextField_Phone.setForeground(Color.CYAN);
		jtextField_Phone.setBackground(Color.DARK_GRAY);
		jtextField_Phone.setBounds(174, 247, 220, 40);
		panel_1.add(jtextField_Phone);
		jtextField_Phone.setColumns(10);
		// Phone text field ends

		JRadioButton jRadioButton_Male = new JRadioButton("Male");
		jRadioButton_Male.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jRadioButton_Male.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gender = "Male";
			}
		});
		jRadioButton_Male.setSelected(true);
		jRadioButton_Male.setFont(new Font("Tahoma", Font.BOLD, 12));
		jRadioButton_Male.setForeground(Color.DARK_GRAY);
		jRadioButton_Male.setBackground(Color.LIGHT_GRAY);
		jRadioButton_Male.setHorizontalAlignment(SwingConstants.CENTER);
		jRadioButton_Male.setBounds(204, 299, 65, 40);
		panel_1.add(jRadioButton_Male);

		JRadioButton jRadioButton_Female = new JRadioButton("Female");
		jRadioButton_Female.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jRadioButton_Female.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gender = "Female";
			}
		});
		jRadioButton_Female.setFont(new Font("Tahoma", Font.BOLD, 12));
		jRadioButton_Female.setBackground(Color.LIGHT_GRAY);
		jRadioButton_Female.setForeground(Color.DARK_GRAY);
		jRadioButton_Female.setHorizontalAlignment(SwingConstants.CENTER);
		jRadioButton_Female.setBounds(271, 299, 80, 40);
		panel_1.add(jRadioButton_Female);

		JLabel lblNewLabel = new JLabel("REGISTER");
		lblNewLabel.setForeground(Color.CYAN);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(140, 11, 231, 40);
		panel.add(lblNewLabel);

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
		jLabel_close.setBounds(464, 0, 30, 30);
		panel.add(jLabel_close);
		// Close box ends

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
		jLabel_minimize.setForeground(Color.DARK_GRAY);
		jLabel_minimize.setFont(new Font("Tahoma", Font.BOLD, 30));
		jLabel_minimize.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_minimize.setBounds(424, 0, 30, 30);
		panel.add(jLabel_minimize);
		// Minimize box ends

		// create border for the fullname, username, password and phone field
		Border field_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.CYAN);
		jtextField_Fullname.setBorder(field_border);
		jtextField_Username.setBorder(field_border);
		jpasswordField_1.setBorder(field_border);
		jpasswordField_2.setBorder(field_border);
		jtextField_Phone.setBorder(field_border);

		// create a button group for the radio buttons
		ButtonGroup bg = new ButtonGroup();
		bg.add(jRadioButton_Male);
		bg.add(jRadioButton_Female);

		JLabel jNewLabel_Login_Account = new JLabel(">> Already Have an Account? Login!");
		jNewLabel_Login_Account.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				Border label_border = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.CYAN);
				jNewLabel_Login_Account.setBorder(label_border);
			}

			@Override
			public void mouseExited(MouseEvent e) {

				jNewLabel_Login_Account.setBorder(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				new Login_Form().setVisible(true); // show
				setVisible(false); // hide
			}
		});
		jNewLabel_Login_Account.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jNewLabel_Login_Account.setForeground(Color.DARK_GRAY);
		jNewLabel_Login_Account.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		jNewLabel_Login_Account.setHorizontalAlignment(SwingConstants.CENTER);
		jNewLabel_Login_Account.setBounds(109, 418, 270, 19);
		panel_1.add(jNewLabel_Login_Account);

	}
}
