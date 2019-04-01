import java.util.*;
// this class contains all the search methods
public class Searches {
	private Node Root;
	private String GoalState;
	private int maxSize = 0;
	
	public Node getRoot() {
		return Root;
	}
	public void setRoot(Node Root) {
		this.Root = Root;
	}
	public String getGoalState() {
		return GoalState;
	}
	public void setGoalState(String GoalState) {
		this.GoalState= GoalState;
	}
	public Searches(Node Root, String GoalState) {
		// TODO Auto-generated constructor stub
		this.Root = Root;
		this.GoalState = GoalState;
	}
	//breadthfirstsearch- starts at initial state and checks if children are goal by checking first element and added to the end of the queue

	 public void breadthFirstSearch() {
	        Set<String> stateSets = new HashSet<String>();
	        int totalCost = 0;
	        int time = 0;
	        Node node = new Node(Root.getState());
	        Queue<Node> queue = new LinkedList<Node>();
	        Node currentNode = node;
	        while (!currentNode.getState().equals(GoalState)) {
	            stateSets.add(currentNode.getState());
	            List<String> nodeSuccessors = NodeUtility.getSuccessors(currentNode.getState());
	            for (String n : nodeSuccessors) {
	                if (stateSets.contains(n))
	                    continue;
	                stateSets.add(n);
	                Node child = new Node(n);
	                currentNode.addChild(child);
	                child.setParent(currentNode);
	                queue.add(child);

	            }
	            currentNode = queue.poll();
	            time += 1;
	        }

	        NodeUtility.printSolution(currentNode, stateSets, Root, time);

	    }
	 
	 	//depthFirstSearch which uses MyQueue to keep track of successors
	    public void depthFirstSearch() {
	        Set<String> stateSets = new HashSet<String>();
	        int totalCost = 0;
	        int time = 0;
	        Node node = new Node(Root.getState());
	        MyQueue<Node> mainQueue = new MyQueue<>();
	        MyQueue<Node> successorsQueue = new MyQueue<>();
	        Node currentNode = node;
	        while (!currentNode.getState().equals(GoalState)) {
	            stateSets.add(currentNode.getState());
	            List<String> nodeSuccessors = NodeUtility.getSuccessors(currentNode.getState());
	            for (String n : nodeSuccessors) {
	                if (stateSets.contains(n))
	                    continue;
	                stateSets.add(n);
	                Node child = new Node(n);
	                currentNode.addChild(child);
	                child.setParent(currentNode);
	                successorsQueue.enqueue(child);

	            }
	            mainQueue.addQueue(successorsQueue);
	            successorsQueue.clear();
	            currentNode = mainQueue.dequeue();
	            time += 1;
	            nodeSuccessors.clear();
	        }
	        NodeUtility.printSolution(currentNode, stateSets, Root, time);

	    }


	    
	    //Uniform cost algorithm which in each step the node with minimum cost will be expanded using a priorityqueue to see which is cheaper
	    public void uniformCostSearch() {
	        Set<String> stateSets = new HashSet<String>();
	        int totalCost = 0;
	        int time = 0;
	        Node node = new Node(Root.getState());
	        node.SetPathCost(0);
	        NodeComparison NodeComparison = new NodeComparison();
	        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, NodeComparison);
	        Node currentNode = node;
	        while (!currentNode.getState().equals(GoalState)) {
	            stateSets.add(currentNode.getState());
	            List<String> nodeSuccessors = NodeUtility.getSuccessors(currentNode.getState());
	            for (String n : nodeSuccessors) {
	                if (stateSets.contains(n))
	                    continue;
	                stateSets.add(n);
	                Node child = new Node(n);
	                currentNode.addChild(child);
	                child.setParent(currentNode);
	                child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), 0);
	                nodePriorityQueue.add(child);

	            }
	            currentNode = nodePriorityQueue.poll();
	            time += 1;
	        }
	        NodeUtility.printSolution(currentNode, stateSets, Root, time);

	    }


// Find the goal using Best Search First algorithm. The heuristic is  the estimated cost from the current node to the goal node
	    public void bestFirstSearch() {
	        Set<String> stateSets = new HashSet<String>();
	        int totalCost = 0;
	        int time = 0;
	        Node node = new Node(Root.getState());
	        node.SetPathCost(0);
	        NodeComparison NodeComparison = new NodeComparison();
	        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, NodeComparison);
	        Node currentNode = node;
	        while (!currentNode.getState().equals(GoalState)) {
	            stateSets.add(currentNode.getState());
	            List<String> nodeSuccessors = NodeUtility.getSuccessors(currentNode.getState());
	            for (String n : nodeSuccessors) {
	                if (stateSets.contains(n))
	                    continue;
	                stateSets.add(n);
	                Node child = new Node(n);
	                currentNode.addChild(child);
	                child.setParent(currentNode);
	                child.setTotalCost(0, heuristicOne(child.getState(), GoalState));
	                nodePriorityQueue.add(child);

	            }
	            currentNode = nodePriorityQueue.poll();
	            time += 1;
	        }
	        NodeUtility.printSolution(currentNode, stateSets, Root, time);

	    }


	    //A* which uses different heuristics he heuristic is the real cost to the current node from the initial Node plus the estimated cost from the current node to the goal node
	    public void aStar(Heuristics heuristic) {
	        // stateSet is a set that contains node that are already visited
	        Set<String> stateSets = new HashSet<String>();
	        int totalCost = 0;
	        int time = 0;
	        Node node = new Node(Root.getState());
	        node.setTotalCost(0);
	        NodeComparison NodeComparison = new NodeComparison();
	        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, NodeComparison);
	        Node currentNode = node;
	        while (!currentNode.getState().equals(GoalState)) {
	            stateSets.add(currentNode.getState());
	            List<String> nodeSuccessors = NodeUtility.getSuccessors(currentNode.getState());
	            for (String n : nodeSuccessors) {
	                if (stateSets.contains(n))
	                    continue;
	                stateSets.add(n);
	                Node child = new Node(n);
	                currentNode.addChild(child);
	                child.setParent(currentNode);

	                if (heuristic == Heuristics.Heuristic1)
	                    child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), heuristicOne(child.getState(), GoalState));
	                else if (heuristic == Heuristics.Heuristic2)
	                    child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), heuristicTwo(child.getState(), GoalState));
	                nodePriorityQueue.add(child);
	            }
	            currentNode = nodePriorityQueue.poll();
	            time += 1;
	        }
	        NodeUtility.printSolution(currentNode, stateSets, Root, time);
	    }

	    // This heuristic estimates the cost to the goal from current state by counting the number of misplaced tiles
	    private int heuristicOne(String currentState, String GoalState) {
	        int difference = 0;
	        for (int i = 0; i < currentState.length(); i += 1)
	            if (currentState.charAt(i) != GoalState.charAt(i))
	                difference += 1;
	        return difference;
	    }

	    // This heuristic estimates the cost to the goal from current state by calculating the Manhattan distance from each misplaced tile to its right position in the goal state
	    private int heuristicTwo(String currentState, String GoalState) {
	        int difference = 0;
	        for (int i = 0; i < currentState.length(); i += 1)
	            for (int j = 0; j < GoalState.length(); j += 1)
	                if (currentState.charAt(i) == GoalState.charAt(j))
	                    difference = difference + ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j / 3));
	        return difference;
	    }
	}

