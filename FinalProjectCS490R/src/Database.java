
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jfree.data.xy.XYSeries;

public class Database {

	private Connection conn;
	private String sql;
	private double dexter;
	private double yijie;
	
	public Database (Connection conn) {
		conn = this.conn;
		//runQuery(sql);
		setupDB();
	}
	
	private void setupDB() {
		try {
			conn = DriverManager.getConnection("jdbc:derby:chanceDB");
//			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public XYSeries runQuery(String sql) {
		//XYDataset ds = null;
		XYSeries series = new XYSeries("Data");
		//double round2;
		setDexter(0);
		setYijie(0);
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				setDexter(rs.getDouble(1));
				setYijie(rs.getDouble(2));
				series.add(getDexter(), getYijie());
				
				System.out.println("First result; " + getDexter() + " , Second Result: " + getYijie());
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return series;
	}

	public double getDexter() {
		return dexter;
	}

	public void setDexter(double dexter) {
		this.dexter = dexter;
	}

	public double getYijie() {
		return yijie;
	}

	public void setYijie(double yijie) {
		this.yijie = yijie;
	}
}
