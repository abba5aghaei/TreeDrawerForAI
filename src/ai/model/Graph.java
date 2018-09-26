package ai.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
	
	public Graph() {
		
	}

	public void uniformCostSearch(GraphNode source, GraphNode goal) {
		source.setPathCost(0);
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>(20, new Comparator<GraphNode>() {
			public int compare(GraphNode i, GraphNode j) {
				if (i.getPathCost() > j.getPathCost()) {
					return 1;
				} else if (i.getPathCost() < j.getPathCost()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		queue.add(source);
		Set<GraphNode> explored = new HashSet<GraphNode>();
		do {
			GraphNode current = queue.poll();
			explored.add(current);
			for (Edge e : current.getEdges()) {
				GraphNode child = e.getTarget();
				double cost = e.getCost();
				child.setPathCost(current.getPathCost() + cost);
				if (!explored.contains(child) && !queue.contains(child)) {
					child.setParent(current);
					queue.add(child);
					System.out.println(child);
					System.out.println(queue);
					System.out.println();
				} else if ((queue.contains(child)) && (child.getPathCost() > current.getPathCost())) {
					child.setParent(current);
					current = child;
				}
			}
		} while (!queue.isEmpty());
	}

	public List<GraphNode> printPath(GraphNode target) {
		List<GraphNode> path = new ArrayList<GraphNode>();
		for (GraphNode node = target; node != null; node = node.getParent()) {
			path.add(node);
		}
		Collections.reverse(path);
		return path;
	}
}
