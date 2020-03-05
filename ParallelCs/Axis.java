import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Axis {

	private DataType dt;
	private List<Double> numList;
	private List<String> textList;
	private List<Double> normalizedValues;
	private String colName;
	private double normalized;

	/**
	 * constructor takes the name of the column and the data type of the column
	 * @param name
	 * @param type
	 */
	public Axis(String name, String type) {
		this.colName = name;
		numList = new ArrayList<>();
		textList = new ArrayList<>();
		normalizedValues = new ArrayList<>();
		
		normalized = 0;
		if(type.equals("VARCHAR") || type.equals("CHAR")) {
			dt = DataType.TEXTUAL;
		} else {
			dt = DataType.NUMERIC;
		}
		//System.out.println("I am " + dt + " data type");
	}

	/**
	 * pulls data from result set and adds the result to a specific list
	 * @param rs
	 */
	public void setData(ResultSet rs) {
		try {
			if (dt == DataType.TEXTUAL) {
				textList.add(rs.getString(colName));
			} else {
				numList.add(rs.getDouble(colName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Populates the normalized arraylists
	 */
	public void axisNorms() {
		//int count = 1;
		List<String> temp = new ArrayList<>();
		if (dt == DataType.NUMERIC) {
			double maxVal;
			maxVal = Collections.max(numList);
			for (int i = 0; i < numList.size(); i++) {
				normalized = numList.get((int)i)/maxVal;
				normalizedValues.add(normalized);
			}
		} else {
			for(String s : textList) {
				if (!temp.contains(s)) {
					double normalized2;
					temp.add(s);
					normalized2 = (1.0/(temp.size()+1));
					normalizedValues.add(normalized2);
					System.out.println("This is normalized: " + normalized2);
				}
			}
		}
//		for (double d : normalizedValues) {
//			System.out.println("Normalized INT Value: " + d);
//		}
//		for (String s : temp) {
//			System.out.println("Distinct strings: " + s + " This is the size: " + temp.size());
//		}
		
	}
	
	public void printNormal() {
		for (double s : normalizedValues) {
			System.out.println("Printing normalized vals: " + s);
		}
	}


}
