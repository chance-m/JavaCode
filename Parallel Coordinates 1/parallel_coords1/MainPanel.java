package parallel_coords1;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;


public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

	boolean dirty;
	int w,h;
	List<Axis> axes;
	List<HyrumPolyline> lines;
	int numEntities;
	private int leftMargin = 40;
	private Rectangle box;
	private Point mouseDownPoint;
	private Color transparentGreen;
	private Color transparentBlack;
	private boolean lineSwitch;
	double x1, x2, y1, y2;
	double lineX1, lineX2, lineY1, lineY2;
	HyrumPolyline closestLine;
	

	public MainPanel() {
		super();
		dirty = false;
		axes = null;
		lines = new ArrayList<HyrumPolyline>();
		addMouseListener(this);
		addMouseMotionListener(this);
		box = new Rectangle();
		transparentGreen = new Color(0,255,0,50);
		transparentBlack = new Color(0,0,0,(int) 0.4);
		closestLine = new HyrumPolyline();
		
	}

	public void setAxes(List<Axis> ax, int count) {
		axes = ax;
		dirty = true;
		numEntities = count;
		repaint();
	}

	private void prerender(Graphics g) {
		w = getWidth();
		h = getHeight();
		FontMetrics f = g.getFontMetrics();

		if (axes != null) {
			int xbuffer = (w-leftMargin*2) / (axes.size()-1);
			int ybuffer = 20;
			int x = leftMargin;
			lines.clear();
			for (Axis ax : axes) {
				Line2D dim = new Line2D.Float(x,ybuffer,x,h-ybuffer);
				ax.setDimensions(dim);
				x += xbuffer;
			}
			for (int i=0; i<numEntities; ++i) {
				//				System.out.println(axes.get(0).toString()
				//						+ "real value: " + axes.get(0).getPrintedValue(i)
				//						+ "; normalized value: " + axes.get(0).getNormalizedValue(i));
				HyrumPolyline line = new HyrumPolyline();
				for (int j=0; j<axes.size(); ++j) {
					line.addPoint(axes.get(j).getPixelPosition(i));
				}
				lines.add(line);
			}
		}
		setLinePoints();
	}

	@Override
	public void paintComponent(Graphics g1) {
		if (w != getWidth() || h != getHeight() || dirty) {
			prerender(g1);
			dirty = false;
		}

		Graphics2D g = (Graphics2D)g1;

		//	draw blank background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (HyrumPolyline p : lines) {
			
			if (p.select == LineSelector.DEFAULT 
					) {
				g.setColor(Color.BLACK);
				p.draw(g);
			} else if(p.select == LineSelector.SELECTED) {
				if(lineSwitch == false) {
					g.setColor(Color.magenta);
					p.draw(g);
				} else {
					g.setColor(Color.black);
					p.draw(g);
				}
			} else if (p.select == LineSelector.UNSELECTED) {
				if (lineSwitch == true) {
					g.setColor(Color.lightGray);
					p.draw(g);
				} else {
					g.setColor(Color.black);
					p.draw(g);
				}
			}
			
			if (p.highLighted == true) {
				g.setStroke(new BasicStroke(10));
				g.setColor(Color.cyan);
				p.draw(g);
				System.out.println("I AM MAKING LINES THICC");
			} else {
				g.setColor(Color.black);
				g.setStroke(new BasicStroke(0));
			}
		}
		if (axes != null) {
			for (Axis x : axes) {
				x.draw(g);
			}
		}
		g1.setColor(transparentGreen);
		((Graphics2D) g1).fill(box);
		g1.setColor(Color.BLUE);
		((Graphics2D) g1).draw(box);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
//		for (HyrumPolyline p : lines) {
//			if (p.selected = false ||  )
//		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//System.out.println("Mouse down at (" + x + "," + y + ")");
		mouseDownPoint = new Point(x,y);
		switcher(false);
		
	}
	private void switcher(boolean b) {
		lineSwitch = b;
	}
	
	public void setLinePoints() {
		for (HyrumPolyline p : lines) {
			for(int i = 1; i < p.getNumPoints(); i++) {
				lineX1 = p.getPointAt(i).getX();
				lineY1 = p.getPointAt(i).getY();
				lineX2 = p.getPointAt(i-1).getX();
				lineY2 = p.getPointAt(i-1).getY();
				System.out.println("line x1: " + lineX1 + " line y1: " + lineY1 + " line x2: " + lineX2 + " line y2: " + lineY2 );
				
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//System.out.println("Mouse up at (" + x + "," + y + ")");
		double minX = box.getMinX();
		double minY = box.getMinY();
		//same for max x, y
		//or use box.contains() ....
		box = new Rectangle();
		switcher(true);
		repaint();
	}
	
	public void reset() {
		for (HyrumPolyline p : lines) {
			p.select = LineSelector.DEFAULT;
		}
		repaint();
	}
	
	
	// Mouse methods
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		box.setFrameFromDiagonal(mouseDownPoint.x, mouseDownPoint.y, x, y);
		//System.out.println("Mouse dragged at (" + x + "," + y + ")");
		for (HyrumPolyline p : lines) {
			for(int i = 1; i < p.getNumPoints(); i++) {
				x1 = p.getPointAt(i).getX();
				y1 = p.getPointAt(i).getY();
				x2 = p.getPointAt(i-1).getX();
				y2 = p.getPointAt(i-1).getY();
				var currentline = p;
				if (box.intersectsLine(x1, y1, x2, y2) == true) {
					//currentline.selected = true;
					currentline.select = LineSelector.SELECTED;
					//repaint();
					break;
				} else {
					currentline.select = LineSelector.UNSELECTED;
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		double closestDistance = 2;
		for (HyrumPolyline p : lines) {
			//System.out.println("line x1: " + lineX1 + " line y1: " + lineY1 + " line x2: " + lineX2 + " line y2: " + lineY2 + " MOUSE POINTS: " + mouseX + ", " + mouseY);
			for (int i = 1; i < p.getNumPoints(); i++) {
//				if (p.getPointAt(i-1) < e.getPoint() < p.getPointAt(i)) {
//					
//				}
			}
			double dist = Line2D.ptLineDist(lineX1, lineY1, lineX2, lineY2, mouseX, mouseY);
			if (dist < closestDistance ) {
				//closestLine = p;
				//closestDistance = dist;
				p.highLighted = true;
				System.out.println("I am calculating closest distance");
			}
		}
		repaint();
		setToolTipText("Mouse is at " + mouseX + "," + mouseY);
	}

}
