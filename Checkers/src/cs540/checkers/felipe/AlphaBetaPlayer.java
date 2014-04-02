
/* Don't forget to change this line to cs540.checkers.<username> */
package cs540.checkers.felipe;

import cs540.checkers.*;
import static cs540.checkers.CheckersConsts.*;

import java.util.*;

/*
 * This is a skeleton for an alpha beta checkers player. Please copy this file
 * into your own directory, i.e. src/<username>/, and change the package 
 * declaration at the top to read
 *     package cs540.checkers.<username>;
 * , where <username> is your cs department login.
 */
/** This is a skeleton for an alpha beta checkers player. */
public class AlphaBetaPlayer extends CheckersPlayer implements GradedCheckersPlayer
{
    /** The number of pruned subtrees for the most recent deepening iteration. */
    protected int pruneCount;
    private int lastPrunedNodeScore;
    public int tempSide;
    public Move currMove;
    public int alpha;
    public int tmpAlpha;
    public int tmpBeta;
    public int beta;
    protected Evaluator sbe;

    public AlphaBetaPlayer(String name, int side)
    { 
        super(name, side);
        // Use SimpleEvaluator to score terminal nodes
        sbe = new SimpleEvaluator();
        
    }

    public void calculateMove(int[] bs)
    {
        /* Remember to stop expanding after reaching depthLimit */
        /* Also, remember to count the number of pruned subtrees. */
    	
        // Place your code here
    	int depthCutOff = 1;
    	this.pruneCount = 0;
    	//int currDepth = 0;
    	//List<Move> possibleMoves = Utils.getAllPossibleMoves(bs,this.side); //Get all possible moves
   
    	while(depthCutOff < this.depthLimit){
        	this.pruneCount = 0;
        	int score = max(bs,Integer.MIN_VALUE,Integer.MAX_VALUE,depthCutOff,0);
    		depthCutOff++;
    		//System.out.println(depthCutOff);
    	}
    	
    }
    
    private int max(int[] bs,int a,int b,int depth,int currDepth){
    	int counter = 0;
    	List<Move> possibleMoves = Utils.getAllPossibleMoves(bs,this.side); //Get all possible moves
    	if((currDepth == depth)){
    		if(this.side == RED){
    			return sbe.evaluate(bs);
    		}
    		else if(this.side == BLK){
    			return -1*sbe.evaluate(bs);
    		}
    	}
    	if(possibleMoves.isEmpty()){
    		return Integer.MIN_VALUE;
    	}
    	Move bestMove = null;
    	for(Move m:possibleMoves){
			Stack<Integer> reverseBoard = Utils.execute(bs, m);
			int thisChildScore = min(bs, a, b, depth, currDepth+1);
    		Utils.revert(bs, reverseBoard);
			if(a < thisChildScore){
				a = thisChildScore;
				bestMove = m;
			}
    		counter++;
    		if(a >= b){
    			pruneCount += possibleMoves.size() - counter;
    			this.lastPrunedNodeScore = thisChildScore;
    			break;
    		}
    		
    	}
    	if(currDepth == 0){
    		setMove(bestMove);
    	}
    	
    	return a;
    }
    private int min(int[] bs,int a,int b,int depth,int currDepth){
    	int counter = 0;

    	List<Move> possibleMoves = Utils.getAllPossibleMoves(bs,1-this.side); //Get all possible moves
    	if(currDepth == depth){
    		if(this.side == RED){
    			return sbe.evaluate(bs);
    		}
    		else if(this.side == BLK){
    			return -1*sbe.evaluate(bs);
    		}
    	}
    	if(possibleMoves.isEmpty()){
    		return Integer.MAX_VALUE;
    	}
    	for(Move m:possibleMoves){
			Stack<Integer> reverseBoard = Utils.execute(bs, m);
			int thisChildScore = max(bs, a, b, depth, currDepth+1);
    		Utils.revert(bs, reverseBoard);
			if(b > thisChildScore){
				b = thisChildScore;
			}
    		counter++;
    		if(a >= b){
    			pruneCount += possibleMoves.size() - counter;
    			this.lastPrunedNodeScore = thisChildScore;
    			break;
    		}
    	}

    	return b;
    }

    public int changeSide(int currSide){
    	if(currSide == RED){
    		return BLK;
    	}
    	else{
    		return RED; 
    	}	
    }


    public int getPruneCount()
    {
        return pruneCount;
    }

	@Override
	public int getLastPrunedNodeScore() {
		// TODO Auto-generated method stub
		return this.lastPrunedNodeScore;
	}
}
