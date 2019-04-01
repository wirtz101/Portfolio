import java.util.ArrayList;

public class Node {
	boolean Expanded;
	String State;
	Node Parent;
	ArrayList<Node> Children;
	int PathCost;
	int EstCostToGoal;
	int TotalCost;
	int Depth;
	

	public boolean isExpanded() {
		return Expanded;
	}
	public void setState(String State) {
		this.State = State;
	}
	public Node(String State) {
		// constructor for the class
		this.State = State;
		Children = new ArrayList<Node>();
	}
	public String getState() {
		return State;
	}
	public Node getParent() {
		return Parent;
	}
	public void setParent(Node Parent) {
		this.Parent = Parent;
	}
	public ArrayList<Node> getChildren(){
		return Children;
	}
	public void addChild(Node Child) {
		Children.add(Child);
	}
	public int getPathCost() {
		return PathCost;
	}
	public void SetPathCost(int PathCost) {
		this.PathCost = PathCost;
	}
	public int getEstCostToGoal() {
		return EstCostToGoal;
	}
	public void setEstCostToGoal(int EstCostToGoal) {
		this.EstCostToGoal = EstCostToGoal; 
	}
	public int getTotalCost() {
		return TotalCost;
	}
	public void setTotalCost(int TotalCost) {
		this.TotalCost = TotalCost;
	}
	public void setTotalCost(int PathCost, int EstCostToGoal) {
		this.TotalCost = PathCost + EstCostToGoal;
	}
	public int getDepth() {
		return Depth;
	}
	public void setDepth(int Depth) {
		this.Depth = Depth;
	}
}
