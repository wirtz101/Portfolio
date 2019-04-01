
public class Test extends MyQueue{
	final static private String GOAL_STATE = "123804765";
	final static private String EASY = "134862705";
	final static private String MEDIUM = "281043765";
	final static private String HARD = "567408321";
	
	/*main program to run the different search methods and difficulties
	 * which also track the time is takes to complete in Milliseconds
	 */
	public static void main(String[] args) {
		// TODO Auto-generated constructor stub
		//String rootState = EASY;
		//String rootState = MEDIUM;
		String rootState = HARD;
		long StartTime = System.currentTimeMillis();
		
		Searches search = new Searches(new Node(rootState),GOAL_STATE);
		search.breadthFirstSearch();
		//search.depthFirstSearch();
		//search.uniformCostSearch();
		//search.bestFirstSearch();
		//search.aStar(Heuristics.Heuristic1);
		search.aStar(Heuristics.Heuristic2);
		long FinishTime =System.currentTimeMillis();
		long TotalTime = FinishTime - StartTime;
		System.out.println("Time to Complete in Milliseconds: " + TotalTime);
	}
}
