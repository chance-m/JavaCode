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
	private List<Double> numNormalizedValues;
	private List<Double> normalizedStrings;
	private String colName;
	private double normalized;

	
	public Axis(String name, String type) {
		this.colName = name;
		numList = new ArrayList<>();
		textList = new ArrayList<>();
		numNormalizedValues = new ArrayList<>();
		
		normalizedStrings = new ArrayList<>();
		normalized = 0;
		if(type.equals("VARCHAR") || type.equals("CHAR")) {
			dt = DataType.TEXTUAL;
		} else {
			dt = DataType.NUMERIC;
		}
		//System.out.println("I am " + dt + " data type");
	}

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
	
	public void axisNorms() {
		if (dt == DataType.NUMERIC) {
			double maxVal;
			maxVal = Collections.max(numList);
			for (int i = 0; i < numList.size(); i++) {
				normalized = numList.get(((int)i))/maxVal;
				numNormalizedValues.add(normalized);
			}
		} /*else {
			
		}*/
		for (double d : numNormalizedValues) {
			System.out.println("Normalized Value: " + d);
		}
		
	}


}
