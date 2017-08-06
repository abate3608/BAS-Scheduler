package edu.psu.sweng500.userinterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.DBBaselineTable;

public class Graph extends JPanel{

	// Event listeners
	private final static EventHandler eventHandler = EventHandler.getInstance();

	private int width = 800;
	private int heigth = 400;
	private int padding = 25;
	private int labelPadding = 25;
	private Color roomTempColor = Color.BLUE; //new Color(44, 102, 230, 180);
	private Color oatColor = Color.GREEN;
	private Color occSPColor = Color.RED;
	private Color unoccSPColor = Color.MAGENTA;
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 8;
	private int numberYDivisions = 10;
	private List<Float> temperatures;
	private static DBBaselineTable g_baseline = new DBBaselineTable();
	private boolean loading = true;
	private static JFrame frame;

	public Graph (String roomNumber) {

		if (frame == null) {
			eventHandler.addListener(new EventQueueListener());
		}
		eventHandler.fireGetBaseline(roomNumber);

	}
	public Graph(List<Float> temperatures) {
		
		this.temperatures = temperatures;
		repaint();
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue
		@Override
		public void getBaselineRespond(DBBaselineTable baseline) {
			g_baseline = baseline;
	           
		   
			try {
				List<Float> temperatures = new ArrayList<>();


				temperatures.add(baseline.getYunocc()); 
				temperatures.add(baseline.getY4());
				temperatures.add(baseline.getY5());
				temperatures.add(baseline.getY6());
				temperatures.add(baseline.getY7());
				temperatures.add(baseline.getYz0()); 
				temperatures.add(baseline.getYz0());
				temperatures.add(baseline.getYz0());
				temperatures.add(baseline.getYz0());
				temperatures.add(baseline.getYocc());
				temperatures.add(baseline.getY0());
				temperatures.add(baseline.getY1());
				temperatures.add(baseline.getY2());
				temperatures.add(baseline.getY3());
				temperatures.add(baseline.getY());

				Graph mainPanel = new Graph(temperatures);
				mainPanel.setPreferredSize(new Dimension(1000, 600));
				//if (frame == null) {
					frame = new JFrame("Room: " + g_baseline.getRoomNumber() + " || Baseline Graph - Optimized On/Off Model");
					frame.setAlwaysOnTop(true);
					frame.setResizable(false);
					frame.getContentPane().add(mainPanel);
					frame.addWindowListener(getWindowAdapter());
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
					frame.getContentPane().add(mainPanel);
					frame.pack();
					frame.setLocationRelativeTo(null);
				//} 
				frame.repaint();
				
				frame.setVisible(true);



			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
	}

	//listen to frame action and stop frame from minimizing or closing
	private static WindowAdapter getWindowAdapter() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {//overrode to show message
				super.windowClosing(we);
				
				//JOptionPane.showMessageDialog(frame, "Cant Exit");
			}

			//cannot minimize frame
			@Override
			public void windowIconified(WindowEvent we) {
				frame.setState(JFrame.NORMAL);

			}
		};
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		numberYDivisions = (int) (getMaxTemperature() - getMinTemperature());

		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (temperatures.size() - 1);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxTemperature() - getMinTemperature());


		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < temperatures.size(); i++) {
			int x1 = (int) (i * xScale + padding + labelPadding);
			int y1 = (int) ((getMaxTemperature() - temperatures.get(i)) * yScale + padding);
			graphPoints.add(new Point(x1, y1));
		}


		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			int y1 = y0;
			if (temperatures.size() > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((getMinTemperature() + (getMaxTemperature() - getMinTemperature()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "\u00b0F";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (int)(metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		for (int i = 0; i < temperatures.size(); i++) {
			if (temperatures.size() > 1) {
				int x0 = i * (getWidth() - padding * 2 - labelPadding) / (temperatures.size() - 1) + padding + labelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - labelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((temperatures.size() / 20.0)) + 1)) == 0) {
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
					g2.setColor(Color.BLACK);
					String xLabel = "";
					DecimalFormat df1 = new DecimalFormat("####.00");
					switch (i)
					{ 
					case 0:
						xLabel = "Xunocc";
						break;
					case 1:
						xLabel = "X4 " + g_baseline.getX4();
						g2.drawString("Outside Air Temperature " + g_baseline.getOAT() + " deg F", 60, 50);
						g2.drawString("Room Temperature " + g_baseline.getRoomTemp() + " deg F", 60, 70);
						g2.drawString("Unoccupied Trend", 60, 90);
						g2.drawString("Changed 1 deg F every " + df1.format((g_baseline.getX4()/60) / g_baseline.getY4()) + " minutes", 70, 110);
						g2.drawString("Unoocupied Offset " + g_baseline.getUnoccOffset() + " minutes", 70, 130);
						break; 
					case 2:
						xLabel = "X5 " + g_baseline.getX5();
						break;
					case 3:
						xLabel = "X6 " + g_baseline.getX6();
						break;
					case 4:
						xLabel = "X7 " + g_baseline.getX7();
						break;
					case 5:
						xLabel = "Xz0 " + g_baseline.getXz0();
						break;
					case 6:
						xLabel = "Xz1 " + g_baseline.getXz1();
						g2.drawString("Occupied Trend", 680, 90);
						g2.drawString("Changed 1 deg F every " + df1.format((g_baseline.getYocc() / (g_baseline.getX() / 60))) + " minutes", 690, 110);
						g2.drawString("Unoocupied Offset " + g_baseline.getOccOffset() + " minutes", 690, 130);
						break;
					case 7:
						xLabel = "Xz2 " + g_baseline.getXz2();
						break;
					case 8:
						xLabel = "Xz3 " + g_baseline.getXz3();
						break;
					case 9:
						xLabel = "Xocc";
						break; 
					case 10:
						xLabel = "X0 " + g_baseline.getX0();
						break;
					case 11:
						xLabel = "X1 " + g_baseline.getX1();
						break;
					case 12:
						xLabel = "X2 " + g_baseline.getX2();
						break;
					case 13:
						xLabel = "X3 " + g_baseline.getX3();
						break;
					default:
						xLabel = "X " + g_baseline.getX();;
						break;
					}

					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);

				}
				g2.drawLine(x0, y0, x1, y1);
			}
		}

		// create x and y axes 
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

		Stroke oldStroke = g2.getStroke();

		//graph room temp
		g2.setColor(roomTempColor);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);
		}


		//graph oat
		g2.setColor(oatColor);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = (int) ((getMaxTemperature() - 75) * yScale + padding);
			int x2 = graphPoints.get(i + 1).x;
			int y2 = (int) ((getMaxTemperature() - 75) * yScale + padding);
			g2.drawLine(x1, y1, x2, y2);
		}

		//graph occ temp sp
		g2.setColor(occSPColor);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = (int) ((getMaxTemperature() - 77) * yScale + padding);
			int x2 = graphPoints.get(i + 1).x;
			int y2 = (int) ((getMaxTemperature() - 77) * yScale + padding);
			g2.drawLine(x1, y1, x2, y2);
		}

		//graph unocc temp sp
		g2.setColor(unoccSPColor);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = (int) ((getMaxTemperature() - 74) * yScale + padding);
			int x2 = graphPoints.get(i + 1).x;
			int y2 = (int) ((getMaxTemperature() - 74) * yScale + padding);
			g2.drawLine(x1, y1, x2, y2);
		}

		g2.setStroke(oldStroke);
		g2.setColor(pointColor);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}



	}

	//	    @Override
	//	    public Dimension getPreferredSize() {
	//	        return new Dimension(width, heigth);
	//	    }
	private double getMinTemperature() {
		double minTemperature = Double.MAX_VALUE;
		for (Float temperature : temperatures) {
			minTemperature = Math.min(minTemperature, temperature);
		}
		return (int)minTemperature - 1;
	}

	private double getMaxTemperature() {
		double maxTemperature = Double.MIN_VALUE;
		for (Float temperature : temperatures) {
			maxTemperature = Math.max(maxTemperature, temperature);
		}


		return (int) maxTemperature + 1;
	}

	public void setScores(List<Float> temperatures) {
		this.temperatures = temperatures;
		invalidate();
		this.repaint();
	}

	public List<Float> getTemperatures() {
		return temperatures;
	}

	private static void createAndShowGui() {
		List<Float> temperatures = new ArrayList<>();


		temperatures.add((float) 73.0); 
		temperatures.add((float) 73.0);
		temperatures.add((float) 75.0);
		temperatures.add((float) 77.0);
		temperatures.add((float) 79.0);
		temperatures.add((float) 80.0); 
		temperatures.add((float) 80.0);
		temperatures.add((float) 80.0);
		temperatures.add((float) 80.0);
		temperatures.add((float) 80.0);
		temperatures.add((float) 79.0);
		temperatures.add((float) 76.0);
		temperatures.add((float) 74.0);
		temperatures.add((float) 73.0);
		temperatures.add((float) 72.8);

		Graph mainPanel = new Graph(temperatures);
		mainPanel.setPreferredSize(new Dimension(800, 600));
		JFrame frame = new JFrame("Baseline Graph - Optimized On/Off Model");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void newGraph(String roomNumber) {
		eventHandler.fireGetBaseline(roomNumber);
	}

	/*public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}*/
}