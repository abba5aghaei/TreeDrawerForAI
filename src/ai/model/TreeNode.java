package ai.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TreeNode {

	private String data;
	private double cost;
	private double pathCost;
	private int depth;
	private List<TreeNode> children;
	private boolean visited;
	
	private double X;
	private double Y;

	public TreeNode(String d) {
		data = d;
		cost = 0;
		pathCost = cost;
		depth = 0;
		children = new ArrayList<TreeNode>();
		visited = false;
	}
	
	public TreeNode(String d, double c) {
		data = d;
		cost = c;
		pathCost = cost;
		depth = 0;
		children = new ArrayList<TreeNode>();
		visited = false;
	}
	
	public TreeNode(String d, double c, Collection<TreeNode> childs) {
		data = d;
		cost = c;
		pathCost = cost;
		children = new ArrayList<TreeNode>();
		children.addAll(childs);
		visited = false;
	}

	public TreeNode getUnvisitedChild() {
		if(hasChildren())
			return children.stream().filter(n -> !n.visited).findFirst().orElse(null);
		return null;
	}
	
	public String getData() {
		return data;
	}
	
	public void addChildren(TreeNode node) {
		children.add(node);
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}
	
	public boolean hasChildren() {
		return (children != null);
	}
	
	public void setVisited(boolean b) {
		visited = b;
	}
	
	public boolean getVisited() {
		return visited;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double c) {
		cost = c;
	}

	public double getPathCost() {
		return pathCost;
	}

	public void addPathCost(double pc) {
		pathCost += pc;
	}

	public void clear() {
		pathCost = cost;
		visited = false;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int d) {
		depth = d;
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
}