import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Axis {

	private DataType dt;
	List<String> numeric;
	List<String> text;
	private String name;
	private String numType;

	
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
	
	public void setAxisData() {
		
	}
	
	//@SuppressWarnings("unused")
	public void fetchData(ResultSet rs) {
		try {
			if (dt == DataType.TEXTUAL) {
				text.add(rs.getString(name));
				System.out.println("I am textual Data.");
			} else {
				numeric.add(rs.getString(numType));
			}
			//System.out.println(rs.getMetaData());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
