import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Scatter extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFreeChart chart;
	private JFreeChart chart2;
	private ChartPanel panel;
	private XYDataset dataset;
	private PieDataset pieData;
	private Connection conn;
	private Database db;
	private String query = "SELECT credits_attempted, credits_passed FROM cis";
	private int queryNum;
	private AreaRenderer render;

	public Scatter(String title) {
		super(title);
		setJMenuBar(setupMenu());
		db = new Database(conn);
		render = new AreaRenderer();
		// Create dataset
		queryNum = 0;
		dataset = createDataset(queryNum);
		pieData = new DefaultPieDataset();
		render.setSeriesPaint(1, Color.green);
		// Create chart
		chart = ChartFactory.createScatterPlot(
				"Chance's Chart", 
				"credits attempted", "credits passed", dataset);
		
		chart2 = ChartFactory.createXYAreaChart("Chance's Chart", "X-Axis", "Y-Axis", dataset);
		
		//chart2 = ChartFactory.createPieChart("Chance's Pie", pieData);
		DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
		dataset3.addValue(30, "Test1", "Test2");
		dataset3.addValue(50, "Test1", "Test2");
		dataset3.addValue(150, "Test1", "Test2");
		//Changes background color
		//XYPlot plot = (XYPlot)chart2.getPlot();
		//plot.setBackgroundPaint(Color.white);
		//plot.setBackgroundPaint(new Color(255,228,196));
		
		
		// Create Panel
		panel = new ChartPanel(chart2);
		setContentPane(panel);
		
	}

	/**
	 * Creates the data to be put into the scatter plot, runs the queries
	 * @return
	 */
	public XYDataset createDataset(int q) {
		XYSeriesCollection data = new XYSeriesCollection();
		if (q == 0 ) {
			
			data.addSeries(db.runQuery("SELECT credits_attempted, credits_passed FROM cis"));
		}
		else if (q == 1 ) {
			data.addSeries(db.runQuery("SELECT credits_attempted, gpa FROM cis"));
		}
		else if (q == 2) {
			data.addSeries(db.runQuery("SELECT credits_passed, gpa FROM cis"));
		}
		else if (q == 3) {
			data.addSeries(db.runQuery("SELECT age, gpa FROM cis"));
		}
		else if (q == 4) {
			data.addSeries(db.runQuery("SELECT age, credits_passed FROM cis"));
		}
		return data;
	}

	/**
	 * Sets up the menu bar
	 * @return
	 */
	public JMenuBar setupMenu() {
		var menu = new JMenuBar();
		var dexter = new JMenu("Run Query");
		var yoshiki = new JMenuItem("Default - credits attempted vs. credits passed");
		var yijie = new JMenuItem("Credits attemped vs. GPA");
		var query3 = new JMenuItem("Credits passed vs. GPA");
		var query4 = new JMenuItem("Age vs. GPA");
		var query5 = new JMenuItem("Age vs. credits passed");
		var changeChart = new JMenu("Chart Properties");
		var reset = new JMenuItem("Reset chart");
		//changeChart.add(bar);
		//changeChart.add(line);
		changeChart.add(reset);
		dexter.add(yoshiki);
		dexter.add(yijie);
		dexter.add(query3);
		dexter.add(query4);
		dexter.add(query5);
		menu.add(dexter);
		menu.add(changeChart);

		/**
		 * run the queries in lambda syntax
		 */
		yoshiki.addActionListener(e ->  changeDataset(createDataset(0)));
		yijie.addActionListener(e ->  changeDataset(createDataset(1)));
		query3.addActionListener(e ->  changeDataset(createDataset(2)));
		query4.addActionListener(e ->  changeDataset(createDataset(3)));
		query5.addActionListener(e ->  changeDataset(createDataset(4)));
		reset.addActionListener(e -> panel.restoreAutoBounds());

		return menu;
	}
	/**
	 * Invokes the setData method to assign the new dataset to the plot
	 * @param ds
	 */
	public void changeDataset(XYDataset ds) {
		((XYPlot)chart2.getPlot()).setDataset(ds);
	}
	
	public DefaultCategoryDataset createAreaData() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		dataset.addValue(db.getDexter(), "" + db.getDexter(),"" + db.getYijie());
		
		return dataset;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Scatter scatterChart = new Scatter("Chance's Area Chart Implementation");
			scatterChart.setSize(800, 500);
			scatterChart.setLocationRelativeTo(null);
			scatterChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			scatterChart.setVisible(true);

		});
	}
}