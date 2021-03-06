import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class Axis extends JPanel {

	private DataType dt;
	private List<Double> numList;
	private List<String> textList;
	private static List<Double> normalizedValues;
	private String colName;
	private double normalized;
	private static HyrumPolyline line;
	private List<HyrumPolyline> lines;

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
		lines = new ArrayList<>();
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
					//System.out.println("This is normalized: " + normalized2);
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

	/**
	 * Draws the axis
	 * @param g
	 */
	public void drawAxis(Graphics g) {
		lines.clear();
		double val = 0.0;
		int x = getWidth();
		double y = 0;
		int h = getHeight();
		int axLine = 0;
		for (int i = 0; i < normalizedValues.size(); i++) {
			line = new HyrumPolyline();
			for(Axis a : ParallelMain.axisList) {
				
				y = (h - (h*val));
				axLine = x/((ParallelMain.axisList.size()+1));
				line.addPoint((double)axLine,(double)y);
				line.addPoint((double)axLine+50,(double)y+50);
				axLine += axLine;
			}
			line.draw((Graphics2D) g);
//			line.addPoint(500.1, 200.1);
//			line.draw((Graphics2D) g);
		}
		repaint();
	}
	
	public void drawAxis2(Graphics g, int counter) {
		//lines.clear();
		double val = 0.0;
		int width = getWidth();
		double y = 0;
		double x = 0;
		int h = getHeight();
		double axLine = x/(ParallelMain.axisList.size()+1);
		double seg = axLine;
		for (int i= 0; i < counter; i++) {
			line = new HyrumPolyline();
			for (int j = 0; j < ParallelMain.axisList.size(); j++) {
				val = ParallelMain.axisList.get(j).getNormValsAt(i);
				y = (h - (h*val));
				//axLine = width/((ParallelMain.axisList.size()+1));
				//x = 
				//axLine += axLine;
				line.addPoint((double) axLine, y);
				//line.addPoint(100.0, 100.0);
			}
			lines.add(line);
			//seg += axLine;
		}
		
		for (HyrumPolyline p : lines) {
			p.draw((Graphics2D) g);
			//System.out.println("This prints a polyline");
		}
	}
	//returns normalized values
	public double getNormValsAt(double i) {
		return normalizedValues.get((int) i);
	}

	//	public static boolean isEmpty() {
	//		//normalizedValues = new ArrayList<>();
	//		boolean b;
	//		if (normalizedValues.size().e) {
	//			b = true;
	//		} else {
	//			b = false;
	//		}
	//		return b;
	//	}

	public void printNormal() {
		for (double s : normalizedValues) {
			System.out.println("Printing normalized vals: " + s);
		}
	}


}
