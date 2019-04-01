import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

//This is a utility class that can do some operations on a node such as keeps track of successors
 
public class NodeUtility {
    public static List<String> getSuccessors(String state) {
        List<String> successors = new ArrayList<String>();

        switch (state.indexOf("0")) {
            case 0: {
                successors.add(state.replace(state.charAt(0), '|').replace(state.charAt(1), state.charAt(0)).replace('|', state.charAt(1)));
                successors.add(state.replace(state.charAt(0), '|').replace(state.charAt(3), state.charAt(0)).replace('|', state.charAt(3)));
                break;
            }
            case 1: {
                successors.add(state.replace(state.charAt(1), '|').replace(state.charAt(0), state.charAt(1)).replace('|', state.charAt(0)));
                successors.add(state.replace(state.charAt(1), '|').replace(state.charAt(2), state.charAt(1)).replace('|', state.charAt(2)));
                successors.add(state.replace(state.charAt(1), '|').replace(state.charAt(4), state.charAt(1)).replace('|', state.charAt(4)));
                break;
            }
            case 2: {

                successors.add(state.replace(state.charAt(2), '|').replace(state.charAt(1), state.charAt(2)).replace('|', state.charAt(1)));
                successors.add(state.replace(state.charAt(2), '|').replace(state.charAt(5), state.charAt(2)).replace('|', state.charAt(5)));
                break;
            }
            case 3: {
                successors.add(state.replace(state.charAt(3), '|').replace(state.charAt(0), state.charAt(3)).replace('|', state.charAt(0)));
                successors.add(state.replace(state.charAt(3), '|').replace(state.charAt(4), state.charAt(3)).replace('|', state.charAt(4)));
                successors.add(state.replace(state.charAt(3), '|').replace(state.charAt(6), state.charAt(3)).replace('|', state.charAt(6)));
                break;
            }
            case 4: {
                successors.add(state.replace(state.charAt(4), '|').replace(state.charAt(1), state.charAt(4)).replace('|', state.charAt(1)));
                successors.add(state.replace(state.charAt(4), '|').replace(state.charAt(3), state.charAt(4)).replace('|', state.charAt(3)));
                successors.add(state.replace(state.charAt(4), '|').replace(state.charAt(5), state.charAt(4)).replace('|', state.charAt(5)));
                successors.add(state.replace(state.charAt(4), '|').replace(state.charAt(7), state.charAt(4)).replace('|', state.charAt(7)));
                break;
            }
            case 5: {
                successors.add(state.replace(state.charAt(5), '|').replace(state.charAt(2), state.charAt(5)).replace('|', state.charAt(2)));
                successors.add(state.replace(state.charAt(5), '|').replace(state.charAt(4), state.charAt(5)).replace('|', state.charAt(4)));
                successors.add(state.replace(state.charAt(5), '|').replace(state.charAt(8), state.charAt(5)).replace('|', state.charAt(8)));
                break;
            }
            case 6: {
                successors.add(state.replace(state.charAt(6), '|').replace(state.charAt(3), state.charAt(6)).replace('|', state.charAt(3)));
                successors.add(state.replace(state.charAt(6), '|').replace(state.charAt(7), state.charAt(6)).replace('|', state.charAt(7)));
                break;

            }
            case 7: {
                successors.add(state.replace(state.charAt(7), '|').replace(state.charAt(4), state.charAt(7)).replace('|', state.charAt(4)));
                successors.add(state.replace(state.charAt(7), '|').replace(state.charAt(6), state.charAt(7)).replace('|', state.charAt(6)));
                successors.add(state.replace(state.charAt(7), '|').replace(state.charAt(8), state.charAt(7)).replace('|', state.charAt(8)));
                break;
            }
            case 8: {
                successors.add(state.replace(state.charAt(8), '|').replace(state.charAt(5), state.charAt(8)).replace('|', state.charAt(5)));
                successors.add(state.replace(state.charAt(8), '|').replace(state.charAt(7), state.charAt(8)).replace('|', state.charAt(7)));
                break;
            }
        }
        return successors;
        }

// prints the actions with the states from the starting point to completion

    public static void printSolution(Node goalNode, Set<String> visitedNodes, Node root, int time) {

        int totalCost = 0;
        int maxSize = 0;

        Stack<Node> solutionStack = new Stack<Node>();
        solutionStack.push(goalNode);
        while (!goalNode.getState().equals(root.getState())) {
            solutionStack.push(goalNode.getParent());
            goalNode = goalNode.getParent();
        }
        String sourceState = root.getState();
        String destinationState;
        int cost = 0;
        for (int i = solutionStack.size() - 1; i >= 0; i--) {
            System.out.println("_____________________________________________________");
            destinationState = solutionStack.get(i).getState();
            if(solutionStack.size() > maxSize) {
            	maxSize = solutionStack.size();
            }
            if (!sourceState.equals(destinationState)) {
                System.out.println("Move " + destinationState.charAt(sourceState.indexOf('0')) + " " + findTransition(sourceState, destinationState));
                cost = Character.getNumericValue(destinationState.charAt(sourceState.indexOf('0')));
                totalCost += cost;
            }

            sourceState = destinationState;
            System.out.println("Cost of the movement: " + cost);
            System.out.println("_______");
            System.out.println("| " + solutionStack.get(i).getState().substring(0, 3)+" |");
            System.out.println("| " + solutionStack.get(i).getState().substring(3, 6)+" |");
            System.out.println("| " + solutionStack.get(i).getState().substring(6, 9)+" |");
            System.out.println("_______");

        }
        System.out.println("____________________________________________________________________");
        System.out.println("Number of move to get to the goal state from the initial state:  " + (solutionStack.size() - 1));
        System.out.println("Number of states visited:  " + (visitedNodes.size()));
        System.out.println("Total cost of this solution: " + totalCost);
        System.out.println("Number of Nodes poped out of the queue: " + time);
        System.out.println("Max Size of Queue: " + maxSize );
        System.out.println("____________________________________________________________________");

      
    }
// this checks the movement type and returns the change between states
    public static Moves findTransition(String source, String destination) {
        int zero_position_difference = destination.indexOf('0') - source.indexOf('0');
        switch (zero_position_difference) {
            case -3:
                return Moves.Down;
            case 3:
                return Moves.Up;
            case 1:
                return Moves.Left;
            case -1:
                return Moves.Right;
        }
        return null;
    }
    
}