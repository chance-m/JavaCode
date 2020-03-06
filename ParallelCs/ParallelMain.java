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
	static List<Axis> axisList;
	private Axis ax;
	private static int counter;

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
		counter = 0;
	}
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
			//ax.printNormal();
			//System.out.println("This is the max VALUE: " + ax.getMax());
		});

		cis.addActionListener(e -> {
			queryAxis("SELECT * FROM cis");
			//ax.printNormal();
			//System.out.println("This is the max VALUE: " + ax.getMax());
		});

		cisLong.addActionListener(e -> {
			queryAxis("SELECT * FROM cisLong");
			//ax.printNormal();
			//System.out.println("This is the max VALUE: " + ax.getMax());
		});

		menu.add(file);
		return menu;
	}
	/**
	 * gets the meta data of the query by passing in an sql query
	 * @param query
	 */
	public void queryAxis(String query) {
		axisList.clear();
		Statement s;
		counter = 0;
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
			
			window.getAxisSize(axisList.size());
			while (rs.next()) {
				for (Axis ax1 : axisList) {
					ax1.setData(rs);
					
				}
				counter += 1;
				
			}
			System.out.println("This is count: " + counter);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		for(Axis a : axisList) {
			a.axisNorms();
			//a.drawAxis(g);
		}
	}
	public static void main(String[] args) {
		new ParallelMain();
	}
	
	public int getAxisSize() {
		return axisList.size();
	}
	public static int getCounter() {
		return counter;
	}

	//dyttp293

}
