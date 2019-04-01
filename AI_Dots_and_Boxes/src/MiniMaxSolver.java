import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class MiniMaxSolver extends GameSolver {

    @Override
    public Edge getNextMove(GameBoard board, int color) {
    	/*create the different needed variables:
    	queue, stack, moves, current player, current score and timers
    	*/
        LinkedList<TreeNode> queue =  new LinkedList<TreeNode>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>() ;
        ArrayList<Edge> moves ;
        GameBoard currentBoard;
        int currentColor, currentScore ;
        long oldTime = System.nanoTime();
        long timeOut = 980000000;

        /*
         * Create the root of the tree and add to queue
         */
        TreeNode rootNode = new TreeNode(board,color,null,null), levelNode = null ;
        queue.add(rootNode) ;
        queue.add(levelNode);
        referenceColor = color ;

        /*
         * stack up the tree with with the current node i.e. posistion
         */
        do {
            if((System.nanoTime() - oldTime) > timeOut) break;
            TreeNode currentNode = queue.remove() ;
            if(currentNode!=levelNode) {
                stack.add(currentNode) ;
                currentBoard = currentNode.getBoard();
                currentColor = currentNode.getColor();
                currentScore = currentBoard.getScore(currentColor);
                // get available moves and shuffle
                moves = currentBoard.getAvailableMoves();
                Collections.shuffle(moves);
                //for all available edges check what it's potential score could be if used and add it to queue
                for (Edge i : moves) {
                    GameBoard child = currentBoard.getNewBoard(i, currentColor);
                    int newScore = child.getScore(currentColor);
                    if (newScore == currentScore)
                        queue.add(new TreeNode(child, GameBoard.toggleColor(currentColor), currentNode, i));
                    else
                        queue.add(new TreeNode(child, currentColor, currentNode,i));
                }

            }
            else {
                queue.add(levelNode) ;
            }
        } while(queue.size()!=0) ;
        
        while(queue.size()!=0) {
            TreeNode currentNode = queue.remove() ;
            if(currentNode!=levelNode)
                stack.add(currentNode) ;
        }

        do {
            TreeNode currentNode = stack.removeLast() ;
            TreeNode parentNode = currentNode.getParent() ;
            int currentUtility = currentNode.getUtility() ;

            if(TreeNode.MIN == currentUtility)
                currentNode.setUtility(heuristic(currentNode.getBoard(), currentNode.getColor())) ;
            currentUtility = currentNode.getUtility() ;

            if(parentNode.getColor()==referenceColor) {
                if(parentNode.getUtility()<currentUtility) {
                    parentNode.setUtility(currentUtility);
                    if (parentNode == rootNode)
                        rootNode.setEdge(currentNode.getEdge());
                }
            }
            else {
                if(parentNode.getUtility()>currentUtility)
                    parentNode.setUtility(currentUtility) ;
            }

        } while(stack.size()!=1) ;
        return rootNode.getEdge() ;
    }


}