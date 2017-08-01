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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Graph extends JPanel{

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
	private List<Double> temperatures;

	public Graph(List<Double> temperatures) {
		this.temperatures = temperatures;
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
					switch (i)
					{ 
					  case 0:
						   xLabel = "Xunocc";
					        break;
					  case 1:
						  xLabel = "X4";
					        break; 
					  case 2:
						   xLabel = "X5";
					        break;
					  case 3:
						  xLabel = "X6";
					        break;
					  case 4:
						   xLabel = "X7";
					        break;
					  case 5:
						   xLabel = "Xz0";
					        break;
					  case 6:
						   xLabel = "Xz1";
					        break;
					  case 7:
						   xLabel = "Xz2";
					        break;
					  case 8:
						   xLabel = "Xz3";
					        break;
					  case 9:
						  xLabel = "Xocc";
					        break; 
					  case 10:
						   xLabel = "X0";
					        break;
					  case 11:
						  xLabel = "X1";
					        break;
					  case 12:
						   xLabel = "X2";
					        break;
					  case 13:
						  xLabel = "X3";
					        break;
					  default:
						  xLabel = "X";;
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
		for (Double temperature : temperatures) {
			minTemperature = Math.min(minTemperature, temperature);
		}
		return (int)minTemperature - 1;
	}

	private double getMaxTemperature() {
		double maxTemperature = Double.MIN_VALUE;
		for (Double temperature : temperatures) {
			maxTemperature = Math.max(maxTemperature, temperature);
		}
		
		
		return (int) maxTemperature + 1;
	}

	public void setScores(List<Double> temperatures) {
		this.temperatures = temperatures;
		invalidate();
		this.repaint();
	}

	public List<Double> getTemperatures() {
		return temperatures;
	}

	private static void createAndShowGui() {
		List<Double> temperatures = new ArrayList<>();
		//Random random = new Random();
		//int maxDataPoints = 12;
		//int maxTemperature = 10;
		//for (int i = 0; i < maxDataPoints; i++) {
		//	temperatures.add((double) random.nextDouble() * maxTemperature);
			//	            scores.add((double) i);
		//}
		
		temperatures.add(73.0); 
		temperatures.add(73.0);
		temperatures.add(75.0);
		temperatures.add(77.0);
		temperatures.add(79.0);
		temperatures.add(80.0); 
		temperatures.add(80.0);
		temperatures.add(80.0);
		temperatures.add(80.0);
		temperatures.add(80.0);
		temperatures.add(79.0);
		temperatures.add(76.0);
		temperatures.add(74.0);
		temperatures.add(73.0);
		temperatures.add(72.8);
		
		Graph mainPanel = new Graph(temperatures);
		mainPanel.setPreferredSize(new Dimension(800, 600));
		JFrame frame = new JFrame("Baseline Graph - Optimized On/Off Model");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
}