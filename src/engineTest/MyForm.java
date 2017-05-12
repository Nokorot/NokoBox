package engineTest;

import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyForm extends JFrame {

	private JTable table;
	private JTextField txtName;
	private DefaultTableModel model;
	private JLabel lblFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MyForm frame = new MyForm();
				frame.setVisible(true);
			}
		});
	}

	private Connection ConnectDB() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager
					.getConnection("jdbc:mysql://localhost/mydatabase" + "?user=root&password=root");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create the frame.
	 */
	public MyForm() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 239);
		setTitle("ThaiCreate.Com Java GUI Tutorial");
		getContentPane().setLayout(null);

		// ScrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(62, 27, 440, 93);
		getContentPane().add(scrollPane);

		// Table
		table = new JTable();
		scrollPane.setViewportView(table);

		// Label name
		JLabel lblName = new JLabel("Name : ");
		lblName.setBounds(62, 142, 46, 14);
		getContentPane().add(lblName);

		// TextField Name
		txtName = new JTextField();
		txtName.setBounds(102, 139, 97, 20);
		getContentPane().add(txtName);
		txtName.setColumns(10);

		// Label Choose File
		JLabel lblChooseFile = new JLabel("Choose File");
		lblChooseFile.setBounds(208, 142, 65, 14);
		getContentPane().add(lblChooseFile);

		// Label File
		lblFile = new JLabel("File");
		lblFile.setBounds(321, 142, 109, 14);
		getContentPane().add(lblFile);

		// Button Choose
		JButton btnChoose = new JButton("...");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
				int ret = fileopen.showDialog(null, "Choose file");

				if (ret == JFileChooser.APPROVE_OPTION) {
					lblFile.setText(fileopen.getSelectedFile().toString());
				}
			}
		});
		btnChoose.setBounds(281, 140, 33, 18);
		getContentPane().add(btnChoose);

		// Button Save
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveData(); // Save Data
				PopulateData(); // Popup
			}
		});
		btnSave.setBounds(437, 138, 65, 23);
		getContentPane().add(btnSave);

		// Windows Loaded
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				PopulateData();
			}
		});

	}

	// Save Data
	private void SaveData() {
		Connection connect = ConnectDB();
		Statement s = null;

		try {
			s = connect.createStatement();
			// get File name
			String file = lblFile.getText();
			String fileName = file.substring(file.lastIndexOf('\\') + 1, file.length());

			// Copy file
			String desFile = null;
			try {
				desFile = new File(".").getCanonicalPath() + "\\img\\" + fileName;
				Files.copy(Paths.get(file), Paths.get(desFile), StandardCopyOption.COPY_ATTRIBUTES,
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// SQL Insert
			String sql = "INSERT INTO files " + "(Name,FilesName) " + "VALUES ('" + txtName.getText() + "','"
					+ fileName + "') ";
			s.execute(sql);

			// Reset Text Fields
			txtName.setText("");
			lblFile.setText("");

			JOptionPane.showMessageDialog(null, "Add Inserted Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}

		try {
			if (s != null) {
				s.close();
				connect.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Load Data
	private void PopulateData() {

		table.setModel(new DefaultTableModel()); // Clear model

		// Model for Table
		model = (DefaultTableModel) table.getModel();
		model.addColumn("FilesID");
		model.addColumn("Name");
		model.addColumn("FilesName");

		Connection connect = ConnectDB();
		Statement s = null;

		try {
			s = connect.createStatement();
			String sql = "SELECT * FROM  files ORDER BY FilesID ASC";

			ResultSet rec = s.executeQuery(sql);
			int row = 0;
			while ((rec != null) && (rec.next())) {
				model.addRow(new Object[0]);
				model.setValueAt(rec.getString("FilesID"), row, 0);
				model.setValueAt(rec.getString("Name"), row, 1);
				model.setValueAt(rec.getString("FilesName"), row, 2);
				row++;
			}

			rec.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}

		try {
			if (s != null) {
				s.close();
				connect.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}