import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MyVis extends JPanel implements MouseListener, MouseMotionListener {
	
	private String message;
	private List<Double> nums;
	private List<String> labels;
	private List<Double> relative;
	private Rectangle box;
	private Point mouseDownPoint;
	private Color transparentGreen;
	private int axSize;
	
	public MyVis() {
		super();
		nums = new ArrayList<>();
		labels = new ArrayList<>();
		relative = new ArrayList<>();
		addMouseListener(this);
		addMouseMotionListener(this);
		box = new Rectangle();
		transparentGreen = new Color(0,255,0,50);
	}
	
	//draws the axis
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		//g.drawString(message, 10, 10);
//		for (int i=0; i<nums.size(); i++) {
//			g.drawString(""+nums.get(i) + ", " + labels.get(i), 10, i*20+10);
//		}
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	
//		int n = relative.size();
//		int spacing = getHeight() / (n+1);
//		int y = spacing;
//		g.setColor(Color.BLACK);
//		for (int i=0; i<n; i++) {
//			int barLength = (int)(getWidth() * relative.get(i));
//			g.drawLine(0, y, barLength, y);
//			g.drawString(labels.get(i), 0, y);
//			y += spacing;
//		}
//		
//		g.setColor(transparentGreen);
//		g.fill(box);
//		g.setColor(Color.BLUE);
//		g.draw(box);
//		g.setColor(Color.black);
//		g.drawString("" +  axSize, 50, 200);
		g.setColor(Color.black);
		int width = getWidth();
		int height = getHeight();
		int axLine = width/((axSize+1));
		int next = axLine;
		for (int i = 0; i <= axSize; i++) {
			g.drawLine(next, 0, next, height);
			next += axLine;
		}
		
		var axList = ParallelMain.axisList;
		for(double i = 0; i < axList.size(); i++) {
			//System.out.println("TEST: " + axList.get((int)i.getNormalizedValsAt(i)));
			var ax = axList.get((int) i);
			//System.out.println("TEST; " + ax.getNormValsAt(i));
		}
//		if (Axis.isEmpty() == false) {
//			Axis.drawAxis(height, axLine, g);
//		}
		
		for (Axis a : axList) {
			a.drawAxis2(g, ParallelMain.getCounter());
			//System.out.println("TEST : " + axList.size());
		}
			
	}

//	public void setData(List<Double> nums, List<String> labels) {
//		this.nums = nums;
//		this.labels = labels;
//		double max = -1;
//		for (var n : nums) {
//			if (n > max) {
//				max = n;
//			}
//		}
//		for (int i=0; i<nums.size(); i++) {
//			relative.add(nums.get(i) / max);
//		}
//		repaint();
//	}
	
	public void getAxisSize(int i) {
		axSize = i;
		repaint();
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//System.out.println("Mouse down at (" + x + "," + y + ")");
		mouseDownPoint = new Point(x,y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
