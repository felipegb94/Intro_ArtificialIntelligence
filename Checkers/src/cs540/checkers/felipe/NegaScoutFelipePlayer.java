
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
public class NegaScoutFelipePlayer extends CheckersPlayer implements GradedCheckersPlayer
{
    /** The number of pruned subtrees for the most recent deepening iteration. */
    protected int pruneCount;
    private int lastPrunedNodeScore;
    protected Evaluator sbe;

    public NegaScoutFelipePlayer(String name, int side)
    { 
        super(name, side);
        sbe = new CompetitiveEvaluator();
        
    }

    public void calculateMove(int[] bs)
    {    	
    	int depthCutOff = 1;
    	this.pruneCount = 0;
    	while(depthCutOff < this.depthLimit){
        	this.pruneCount = 0;
        	int score = negaScout(bs,Integer.MIN_VALUE,Integer.MAX_VALUE,depthCutOff,0,true);
    		depthCutOff++;
    		System.out.println("curr depthCutOff = " + depthCutOff);
    	}
    	
    }
    private int negaScout(int [] bs, int alpha, int beta,int depth, int currDepth, boolean myTurn){
    	List<Move> possibleMoves = null;
    	if(myTurn){
    		possibleMoves = Utils.getAllPossibleMoves(bs,this.side); 
    	}
    	else{
    		possibleMoves = Utils.getAllPossibleMoves(bs, 1-this.side);
    	}
    	if(currDepth == depth){
    		if(this.side == RED){
    			return sbe.evaluate(bs);
    		}
    		else if(this.side == BLK){
    			return -1*sbe.evaluate(bs);
    		}
    	}
    	int a,b,thisChildScore;
    	a = alpha;
    	b = beta;
    	Move bestMove = null;
    	for (Move m:possibleMoves){
			Stack<Integer> reverseBoard = Utils.execute(bs, m);
			thisChildScore = negaScout(bs,-b,-a,depth,currDepth+1,!myTurn);
    		Utils.revert(bs, reverseBoard);   
    		reverseBoard = Utils.execute(bs, m);
    		if((thisChildScore > a) && (thisChildScore < beta)  && currDepth > 1 ){
    			thisChildScore = -negaScout(bs,-beta,-a,depth,currDepth+1,!myTurn);
    		}
    		Utils.revert(bs, reverseBoard);   
    		if(thisChildScore > a){
    			a = thisChildScore;
    			bestMove = m;
    		}
    		if(a >= beta){
    			break;
    		}
    		b = a+1;
			
    	}
    	if(currDepth == 0){
    		setMove(bestMove);
    	}
    	
    	return a;
    }
    private int negamax(int [] bs, int a, int b,int depth, int currDepth, boolean myTurn){
    	List<Move> possibleMoves = null;
    	if(myTurn){
    		possibleMoves = Utils.getAllPossibleMoves(bs,this.side); 
    	}
    	else{
    		possibleMoves = Utils.getAllPossibleMoves(bs, 1-this.side);
    	}
    	if(currDepth == depth){
    		if(this.side == RED){
    			return sbe.evaluate(bs);
    		}
    		else if(this.side == BLK){
    			return -1*sbe.evaluate(bs);
    		}
    	}
    	
    	Move bestMove = null;
    	for (Move m:possibleMoves){
			Stack<Integer> reverseBoard = Utils.execute(bs, m);
			int thisChildScore = negamax(bs,-b,-a,depth,currDepth+1,!myTurn);
    		Utils.revert(bs, reverseBoard);
    		if(thisChildScore > a){
    			a = thisChildScore;
    			bestMove = m;
    		}
    		if(a >= b){
    			break;
    		}
			
    	}
    	if(currDepth == 0){
    		setMove(bestMove);
    	}
    	
    	return a;
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
