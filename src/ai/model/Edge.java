package ai.model;

import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Edge {
	
	private double cost;
	private GraphNode target;

	public Edge(GraphNode t, double c) {
		setCost(c);
		setTarget(t);
		setText(new Text(String.valueOf(c)));
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double c) {
		cost = c;
	}

	public GraphNode getTarget() {
		return target;
	}

	public void setTarget(GraphNode t) {
		target = t;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line l) {
		line = l;
	}
	
	public Text getText() {
		return text;
	}

	public void setText(Text t) {
		text = t;
	}

	private Line line;
	private Text text;
}
