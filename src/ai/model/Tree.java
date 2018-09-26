package ai.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javafx.scene.control.TextArea;

public class Tree {
	
	private TreeNode root;

	public Tree(TreeNode node) {
		root = node;
	}

	public void breadthFirstSearch() {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		printTreeNode(root);
		root.setVisited(true);
		while (!queue.isEmpty()) {
			TreeNode node = queue.remove();
			TreeNode child = node.getUnvisitedChild();
			while (child != null) {
				child.addPathCost(node.getPathCost());
				child.setVisited(true);
				printTreeNode(child);
				queue.add(child);
				child = node.getUnvisitedChild();
			}
		}
		clearTreeNodes();
	}

	public void depthFirstSearch() {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);
		root.setVisited(true);
		printTreeNode(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.peek();
			TreeNode child = node.getUnvisitedChild();
			if (child != null) {
				child.addPathCost(node.getPathCost());
				child.setVisited(true);
				printTreeNode(child);
				stack.push(child);
			} else {
				stack.pop();
			}
		}
		clearTreeNodes();
	}

	public void clearTreeNodes() {
		if(root != null) {
			Queue<TreeNode> queue = new LinkedList<TreeNode>();
			queue.add(root);
			root.clear();
			while(!queue.isEmpty()) {
				TreeNode node = queue.remove();
				if(node.hasChildren())
					for(TreeNode child : node.getChildren()) {
						child.clear();
						queue.add(child);
					}
			}
		}
	}
	
	public boolean contains(TreeNode newNode) {
		if(root != null) {
			Queue<TreeNode> queue = new LinkedList<TreeNode>();
			queue.add(root);
			root.setVisited(true);
			while (!queue.isEmpty()) {
				TreeNode node = queue.remove();
				if(node.getData().equalsIgnoreCase(newNode.getData()))
					return true;
				TreeNode child = node.getUnvisitedChild();
				while (child != null) {
					child.setVisited(true);
					if(child.getData().equals(newNode.getData()))
						return true;
					queue.add(child);
					child = node.getUnvisitedChild();
				}
			}
			clearTreeNodes();
		}
		return false;
	}

	private void printTreeNode(TreeNode node) {
		consol.appendText(node.getData()+" "+node.getPathCost()+"  <-  ");
	}
	
	private TextArea consol;

	public void setConsol(TextArea c) {
		consol = c;
	}

	public void setRoot(TreeNode node) {
		root = node;
	}

	public TreeNode getRoot() {
		return root;
	}
}
