package ai.model;

import java.util.ArrayList;

public class GraphNode {
	
	private String value;
	private double pathCost;
	private ArrayList<Edge> edges;
	private ArrayList<Edge> inEdges;
	private GraphNode parent;
	
	private double X;
	private double Y;

	public GraphNode(String v) {
		value = v;
		edges = new ArrayList<Edge>();
		inEdges = new ArrayList<Edge>();
	}

	public String toString() {
		return value;
	}

	public double getPathCost() {
		return pathCost;
	}

	public void setPathCost(double pc) {
		pathCost = pc;
	}

	public  ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges( ArrayList<Edge> e) {
		edges = e;
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}

	public GraphNode getParent() {
		return parent;
	}

	public void setParent(GraphNode p) {
		parent = p;
	}

	public String getValue() {
		return value;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public ArrayList<Edge> getInEdges() {
		return inEdges;
	}

	public void setInEdges(ArrayList<Edge> inEdges) {
		this.inEdges = inEdges;
	}

	public void addInEdge(Edge e) {
		inEdges.add(e);
	}
}
