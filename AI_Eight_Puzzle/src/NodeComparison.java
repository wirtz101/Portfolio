import java.util.Comparator;

// This creates a comparator for priorityqueue to store nodes sorted by the action cost
public class NodeComparison implements Comparator<Node> {
	public int compare(Node x, Node y) {
		if (x.getTotalCost() < y.getTotalCost()) {
			return -1;
		}
		if (x.getTotalCost() > y.getTotalCost()) {
			return 1;
		}
		return 0;
	}
}