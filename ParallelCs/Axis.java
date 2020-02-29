import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Axis {

	private DataType dt;
	List<Double> numeric;
	List<String> text;
	private String name;

	
	public Axis(String name, String type) {
		this.name = name;
		numeric = new ArrayList<>();
		text = new ArrayList<>();
		
		if(type.equals("varchar") || type.equals("char")) {
			dt = DataType.TEXTUAL;
		} else {
			dt = DataType.NUMERIC;
		}
	}
	
	private void fetchData(ResultSet rs) throws SQLException {
		if (dt == DataType.TEXTUAL) {
			text.add(rs.getString(name));
		} else {
			numeric.add(rs.getDouble(name));
		}
	}
}
