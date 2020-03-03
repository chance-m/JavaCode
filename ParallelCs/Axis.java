import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Axis {

	private DataType dt;
	List<Double> numList;
	List<String> textList;
	List<Double> normalizedValues;
	private String colName;

	
	public Axis(String name, String type) {
		this.colName = name;
		numList = new ArrayList<>();
		textList = new ArrayList<>();
		normalizedValues = new ArrayList<>();
		if(type.equals("VARCHAR") || type.equals("CHAR")) {
			dt = DataType.TEXTUAL;
		} else {
			dt = DataType.NUMERIC;
		}
		//System.out.println("I am " + dt + " data type");
	}

	
	//@SuppressWarnings("unused")
	public void setData(ResultSet rs) {
		try {
			if (dt == DataType.TEXTUAL) {
				textList.add(rs.getString(colName));
				//System.out.println("I am textual Data.");
			} else {
				numList.add(rs.getDouble(colName));
				//System.out.println("I am numerical Data. " );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public double getMax() {
		double maxVal;
		maxVal = Collections.max(numList);
		return maxVal;
	}


}
