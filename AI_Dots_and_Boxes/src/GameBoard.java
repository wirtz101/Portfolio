import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard implements Cloneable {

    final static int RED = 0;
    final static int BLUE = 1;
    final static int BLACK = 2;
    final static int BLANK = 3;

    private int[][] hEdge;
    private int[][] vEdge;
    private int[][] box;
    private int n, redScore, blueScore; 
    int boxscore;
    Random rand = new Random();
    
    private int randscore() {
    	return rand.nextInt((5 - 1) + 1) + 1;

    }
    public GameBoard(int n) {
        hEdge = new int[n-1][n];
        vEdge = new int[n][n-1];
        box = new int[n-1][n-1];
        boxscore = rand.nextInt((5 - 1) + 1) + 1;
        fill(hEdge,BLANK);
        fill(vEdge,BLANK);
        fill(box,BLANK);
        fill(box,randscore());
        this.n = n;
        redScore = blueScore = 0;
    }

    public GameBoard clone() {
        GameBoard cloned = new GameBoard(n);

        for(int i=0; i<(n-1); i++)
            for(int j=0; j<n; j++)
                cloned.hEdge[i][j] = hEdge[i][j];

        for(int i=0; i<n; i++)
            for(int j=0; j<(n-1); j++)
                cloned.vEdge[i][j] = vEdge[i][j];

        for(int i=0; i<(n-1); i++)
            for(int j=0; j<(n-1); j++)
                cloned.box[i][j] = box[i][j];

        cloned.redScore = redScore;
        cloned.blueScore = blueScore;

        return cloned;
    }

    private void fill(int[][] array, int val) {
        for(int i=0; i<array.length; i++)
            for(int j=0; j<array[i].length; j++)
                array[i][j]=val;
    }

    public int getSize() { return n; }

    public int getRedScore() {
        return redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public int getScore(int color) {
        if(color == RED) return redScore;
        else return blueScore;
    }

    public static int toggleColor(int color) {
        if(color == RED)
            return BLUE;
        else
            return RED;
    }

    public ArrayList<Edge> getAvailableMoves() {
        ArrayList<Edge> ret = new ArrayList<Edge>();
        for(int i=0; i<(n-1);i++)
            for(int j=0; j<n; j++)
                if(hEdge[i][j] == BLANK)
                    ret.add(new Edge(i,j,true));
        for(int i=0; i<n; i++)
            for(int j=0; j<(n-1); j++)
                if(vEdge[i][j] == BLANK)
                    ret.add(new Edge(i,j,false));
        return ret;
    }

    public ArrayList<Point> setHEdge(int x, int y, int color) {
        hEdge[x][y]=BLACK;
        ArrayList<Point> ret = new ArrayList<Point>();
        if(y<(n-1) && vEdge[x][y]==BLACK && vEdge[x+1][y]==BLACK && hEdge[x][y+1]==BLACK) {
            box[x][y]=color;
            ret.add(new Point(x,y));
            if(color == RED) redScore+=boxscore;
            else blueScore+=boxscore;
        }
        if(y>0 && vEdge[x][y-1]==BLACK && vEdge[x+1][y-1]==BLACK && hEdge[x][y-1]==BLACK) {
            box[x][y-1]=color;
            ret.add(new Point(x,y-1));
            if(color == RED) redScore+=boxscore;
            else blueScore+=boxscore;
        }
        return ret;
    }

    public ArrayList<Point> setVEdge(int x, int y, int color) {
        vEdge[x][y]=BLACK;
        ArrayList<Point> ret = new ArrayList<Point>();
        if(x<(n-1) && hEdge[x][y]==BLACK && hEdge[x][y+1]==BLACK && vEdge[x+1][y]==BLACK) {
            box[x][y]=color;
            ret.add(new Point(x,y));
            if(color == RED) redScore+=boxscore;
            else blueScore+=boxscore;
        }
        if(x>0 && hEdge[x-1][y]==BLACK && hEdge[x-1][y+1]==BLACK && vEdge[x-1][y]==BLACK) {
            box[x-1][y]=color;
            ret.add(new Point(x-1,y));
            if(color == RED) redScore+=boxscore;
            else blueScore+=boxscore;
        }
        return ret;
    }

    public boolean isComplete() {
        return getAvailableMoves().isEmpty();
    }

    public int getWinner() {
        if(redScore > blueScore) return RED;
        else if(redScore < blueScore) return BLUE;
        else return BLANK;
    }

    public GameBoard getNewBoard(Edge edge, int color) {
        GameBoard ret = clone();
        if(edge.isHorizontal())
            ret.setHEdge(edge.getX(), edge.getY(), color);
        else
            ret.setVEdge(edge.getX(), edge.getY(), color);
        return ret;
    }

    private int getEdgeCount(int i, int j) {
        int count = 0;
        if(hEdge[i][j] == BLACK) count++;
        if(hEdge[i][j+1] == BLACK) count++;
        if(vEdge[i][j] == BLACK) count++;
        if(vEdge[i+1][j] == BLACK) count++;
        return count;
    }

    public int getBoxCount(int nSides) {
        int count = 0;
        for(int i=0; i<(n-1); i++)
            for(int j=0; j<(n-1); j++) {
                if(getEdgeCount(i, j) == nSides)
                    count++;
            }
        return count;
    }

}
