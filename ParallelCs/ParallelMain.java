import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ParallelMain extends JFrame {

	private MyVis window;
	private Connection conn;
	private List<Axis> axisList;
	private Axis ax;

	public ParallelMain() {
		window = new MyVis();
		setContentPane(window);
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Parallel Coordinates");
		setJMenuBar(setupMenu());
		setupDB();
		setVisible(true);
		axisList = new ArrayList<>();
	}

//	private void runQuery(String sql) {
//		try {
//			Statement s = conn.createStatement();
//			ResultSet rs = s.executeQuery(sql);
//			List<Double> nums = new ArrayList<>();
//			List<String> labels = new ArrayList<>();
//			while (rs.next()) {
//				double dexter = rs.getDouble(1);
//				String yijie = rs.getString(2);
//				nums.add(dexter);
//				labels.add(yijie);
//				System.out.println("There are " + dexter + " students in major " + yijie);
//			}
//			rs.close();
//			s.close();
//			window.setData(nums, labels);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * cis table: consists of two doubles
	 * cisLong table: consists of student data
	 * marathon table: consists of the marathon data
	 */
	private void setupDB() {
		try {
			conn = DriverManager.getConnection("jdbc:derby:chanceDB");
			//			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Sets the menu bars and items. 
	public JMenuBar setupMenu() {
		var menu = new JMenuBar();
		var file = new JMenu("Table");
		var marathon = new JMenuItem("Marathon");
		var cis = new JMenuItem("CIS Students in 2012");
		var cisLong = new JMenuItem("CIS Students from 2012-2017");
		file.add(marathon);
		file.add(cis);
		file.add(cisLong);

		marathon.addActionListener(e -> {
			queryAxis("SELECT * FROM marathon");

			System.out.println("This is the max VALUE: " + ax.getMax());
		});

		cis.addActionListener(e -> {
			queryAxis("SELECT * FROM cis");
			System.out.println("This is the max VALUE: " + ax.getMax());
		});

		cisLong.addActionListener(e -> {
			queryAxis("SELECT * FROM cisLong");
			System.out.println("This is the max VALUE: " + ax.getMax());
		});

		menu.add(file);
		return menu;
	}

	public void queryAxis(String query) {
		axisList.clear();
		Statement s;
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			for (int i=1; i<=n; i++) {
				String chance = rsmd.getColumnName(i) +
						" " + rsmd.getColumnTypeName(i);
				System.out.println(chance);
				ax = new Axis(rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
				axisList.add(ax);
			}
			while (rs.next()) {
				/* for each axis, pass the result set object
				 * to a "setter" method in the axis.
				 The axis object pulls the data it needs from the ResultSet.
				 */
				for (Axis ax1 : axisList) {
					ax1.setData(rs);
					//System.out.println(ax1.toString());
				}
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ParallelMain();
	}

	//dyttp293

}
