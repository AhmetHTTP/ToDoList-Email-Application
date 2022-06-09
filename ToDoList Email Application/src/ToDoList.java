import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class ToDoList extends JFrame {

	private JPanel contentPane;
	private JTextField txtactivity;
	private JTextField txtdate;
	private JTextField txtplace;
	private JTextField txttime;
	private JTable table;
	private JTextField txtacnum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ToDoList frame = new ToDoList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField txtEmail;

	public void table_load() {
		try {
			pst = ToDoList_DB.getConnection().prepareStatement("select * from useractivities");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Mail sender */
	public void sendmail() {

		String to = txtEmail.getText();
		String from = "todo.mail.sent@gmail.com";
		String host = "localhost";
		String port = "25";

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");

		final String username = "todo.mail.sent@gmail.com";//
		final String password = "xjwmuzcwbqmscmwn";

		try {
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication("todo.mail.sent@gmail.com", "xjwmuzcwbqmscmwn");
				}
			});

			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(txtEmail.getText()));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(txtEmail.getText(), false));
			try {

				Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/db_todolist", "root", "");
				Statement myStat = myConn.createStatement();
				ResultSet myRs = myStat.executeQuery("select * from useractivities");

				while (myRs.next()) {
					String sms_activity_name = myRs.getString("ActivityName");
					String sms_activity_date = myRs.getString("Date");
					String sms_activity_time = myRs.getString("Time");
					String sms_activity_place = myRs.getString("Place");
					System.out.println(" Activity Name : " + sms_activity_name + "  Activity Date : "
							+ sms_activity_date + "  Activity Time : " + sms_activity_time + "Activity Place : "
							+ sms_activity_place);
					msg.setText(" Activity Name : " + sms_activity_name + " \n Activity Date : " + sms_activity_date
							+ " \n Activity Time : " + sms_activity_time + " \n Activity Place : "
							+ sms_activity_place);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			long eventMask = 0;
			msg.setSentDate(new Date(eventMask));
			Transport.send(msg);
			JOptionPane.showMessageDialog(null, "Sent");
		} catch (MessagingException e) {
			System.out.println("Sending error, cause: " + e);
		}
	}

	/**
	 * Create the frame.
	 */
	public ToDoList() {

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 986, 661);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(10, 11, 964, 639);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 77, 944, 551);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setForeground(Color.CYAN);
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 255, 255), new Color(160, 160, 160)),
				"Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 255, 255)));
		panel_2.setBounds(10, 46, 389, 355);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Activity");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.CYAN);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 55, 100, 30);
		panel_2.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Date");
		lblNewLabel_2.setForeground(Color.CYAN);
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 112, 100, 30);
		panel_2.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Time");
		lblNewLabel_3.setForeground(Color.CYAN);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(10, 177, 100, 30);
		panel_2.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Place");
		lblNewLabel_4.setForeground(Color.CYAN);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(10, 241, 100, 30);
		panel_2.add(lblNewLabel_4);

		txtactivity = new JTextField();
		txtactivity.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtactivity.setBackground(Color.LIGHT_GRAY);
		txtactivity.setDisabledTextColor(new Color(0, 255, 255));
		txtactivity.setForeground(Color.DARK_GRAY);
		txtactivity.setSelectedTextColor(Color.WHITE);
		txtactivity.setBounds(120, 57, 225, 30);
		panel_2.add(txtactivity);
		txtactivity.setColumns(10);

		txtdate = new JTextField();
		txtdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtdate.setForeground(Color.DARK_GRAY);
		txtdate.setBackground(Color.LIGHT_GRAY);
		txtdate.setColumns(10);
		txtdate.setBounds(120, 114, 225, 30);
		panel_2.add(txtdate);

		txtplace = new JTextField();
		txtplace.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtplace.setForeground(Color.DARK_GRAY);
		txtplace.setBackground(Color.LIGHT_GRAY);
		txtplace.setColumns(10);
		txtplace.setBounds(120, 241, 225, 30);
		panel_2.add(txtplace);

		txttime = new JTextField();
		txttime.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txttime.setForeground(Color.DARK_GRAY);
		txttime.setBackground(Color.LIGHT_GRAY);
		txttime.setColumns(10);
		txttime.setBounds(120, 179, 225, 30);
		panel_2.add(txttime);

		JLabel lblNewLabel_6 = new JLabel("Ex. (2022-10-12)");
		lblNewLabel_6.setForeground(Color.CYAN);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(10, 142, 100, 24);
		panel_2.add(lblNewLabel_6);

		JLabel lblNewLabel_6_1 = new JLabel("Ex. (15:00)");
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6_1.setForeground(Color.CYAN);
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_6_1.setBounds(10, 206, 100, 24);
		panel_2.add(lblNewLabel_6_1);

		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String activity, date, time, place;
				activity = txtactivity.getText();
				date = txtdate.getText();
				time = txttime.getText();
				place = txtplace.getText();

				try {
					pst = ToDoList_DB.getConnection().prepareStatement(
							"insert into useractivities (ActivityName , Date , Time , Place) values(?,?,?,?)");
					pst.setString(1, activity);
					pst.setString(2, date);
					pst.setString(3, time);
					pst.setString(4, place);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Activity Added!");
					table_load();

					txtactivity.setText("");
					txtdate.setText("");
					txttime.setText("");
					txtplace.setText("");
					txtactivity.requestFocus();
				}

				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.CYAN);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnNewButton.setBounds(10, 412, 110, 40);
		panel_1.add(btnNewButton);

		JButton btnExt = new JButton("EXIT");
		btnExt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});
		btnExt.setForeground(Color.CYAN);
		btnExt.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnExt.setBackground(Color.DARK_GRAY);
		btnExt.setBounds(289, 412, 110, 40);
		panel_1.add(btnExt);

		JButton btnClear = new JButton("CLEAR");
		btnClear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				txtactivity.setText("");
				txtdate.setText("");
				txttime.setText("");
				txtplace.setText("");
				txtactivity.requestFocus();
			}
		});
		btnClear.setForeground(Color.CYAN);
		btnClear.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnClear.setBackground(Color.DARK_GRAY);
		btnClear.setBounds(150, 412, 110, 40);
		panel_1.add(btnClear);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(409, 46, 525, 355);
		panel_1.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		panel_3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 255, 255), new Color(160, 160, 160)), "Search",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.CYAN));
		panel_3.setBounds(10, 463, 389, 77);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("Activity Number");
		lblNewLabel_5.setForeground(Color.CYAN);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(20, 11, 147, 55);
		panel_3.add(lblNewLabel_5);

		txtacnum = new JTextField();
		txtacnum.setForeground(Color.DARK_GRAY);
		txtacnum.setBackground(Color.LIGHT_GRAY);
		txtacnum.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtacnum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {

					String AcNum = txtacnum.getText();

					pst = ToDoList_DB.getConnection().prepareStatement(
							"select ActivityName,Date,Time, Place from useractivities where ActivityNo = ?");
					pst.setString(1, AcNum);
					ResultSet rs = pst.executeQuery();

					if (rs.next() == true) {

						String activity = rs.getString(1);
						String date = rs.getString(2);
						String time = rs.getString(3);
						String place = rs.getString(4);

						txtactivity.setText(activity);
						txtdate.setText(date);
						txttime.setText(time);
						txtplace.setText(place);

					} else {
						txtactivity.setText("");
						txtdate.setText("");
						txttime.setText("");
						txtplace.setText("");

					}

				} catch (SQLException ex) {

				}
			}
		});
		txtacnum.setColumns(10);
		txtacnum.setBounds(189, 25, 166, 30);
		panel_3.add(txtacnum);

		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String activity, date, time, place, activityNo;
				activity = txtactivity.getText();
				date = txtdate.getText();
				time = txttime.getText();
				place = txtplace.getText();
				activityNo = txtacnum.getText();

				try {
					pst = ToDoList_DB.getConnection().prepareStatement(
							"update useractivities set ActivityName =?,Date =?,Time =?,Place =? where ActivityNo =? ");
					pst.setString(1, activity);
					pst.setString(2, date);
					pst.setString(3, time);
					pst.setString(4, place);
					pst.setString(5, activityNo);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Activity Updated!");
					table_load();

					txtactivity.setText("");
					txtdate.setText("");
					txttime.setText("");
					txtplace.setText("");
					txtactivity.requestFocus();
				}

				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setForeground(Color.CYAN);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnUpdate.setBackground(Color.DARK_GRAY);
		btnUpdate.setBounds(464, 412, 110, 40);
		panel_1.add(btnUpdate);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String activityNo;

				activityNo = txtacnum.getText();

				try {
					pst = ToDoList_DB.getConnection()
							.prepareStatement("delete from useractivities where ActivityNo =? ");

					pst.setString(1, activityNo);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Activity Deleted!");
					table_load();

					txtactivity.setText("");
					txtdate.setText("");
					txttime.setText("");
					txtplace.setText("");
					txtactivity.requestFocus();
				}

				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setForeground(Color.CYAN);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnDelete.setBackground(Color.DARK_GRAY);
		btnDelete.setBounds(602, 412, 110, 40);
		panel_1.add(btnDelete);

		JPanel panel_3_1 = new JPanel();
		panel_3_1.setLayout(null);
		panel_3_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 255, 255), new Color(160, 160, 160)),
				"Send To-Do-List By E-Mail", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 255, 255)));
		panel_3_1.setBackground(Color.DARK_GRAY);
		panel_3_1.setBounds(409, 463, 525, 77);
		panel_1.add(panel_3_1);

		JLabel lblNewLabel_5_1 = new JLabel("Enter Your E-Mail");
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5_1.setForeground(Color.CYAN);
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_5_1.setBounds(20, 11, 147, 55);
		panel_3_1.add(lblNewLabel_5_1);

		txtEmail = new JTextField();
		txtEmail.setForeground(Color.DARK_GRAY);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtEmail.setColumns(10);
		txtEmail.setBackground(Color.LIGHT_GRAY);
		txtEmail.setBounds(189, 25, 313, 30);
		panel_3_1.add(txtEmail);

		JButton btnmail = new JButton("SEND TO E-MAIL");
		btnmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				sendmail();
			}
		});
		btnmail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnmail.setForeground(Color.CYAN);
		btnmail.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnmail.setBackground(Color.DARK_GRAY);
		btnmail.setBounds(739, 412, 172, 40);
		panel_1.add(btnmail);

		JLabel lblNewLabel = new JLabel("To Do List Application");
		lblNewLabel.setForeground(Color.CYAN);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(327, 11, 337, 55);
		panel.add(lblNewLabel);
	}
}
