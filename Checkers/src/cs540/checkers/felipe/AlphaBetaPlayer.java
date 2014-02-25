
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
    public int currDepth;
    public int depthCutOff;
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
        this.tempSide = this.side;
        this.currDepth = 0;
        this.depthCutOff = 1;
        this.alpha = -99999;
        this.beta = 99999;

    }

    public void calculateMove(int[] bs)
    {
        /* Remember to stop expanding after reaching depthLimit */
        /* Also, remember to count the number of pruned subtrees. */
    	
        // Place your code here
    	this.depthCutOff = 2;
    	while(this.depthCutOff < this.depthLimit){
        	this.currDepth = 0;
        	CheckersNode root = new CheckersNode(bs,this.alpha,this.beta);
        	root.isRoot = true;
        	miniMax(root);
        	System.out.println("miniMax");

        	this.setMove(root.getBestMove());
    		this.depthCutOff++;
    	}

    	
    	
    }
    public int miniMax(CheckersNode state){
    	currDepth++;
    	if(this.currDepth == this.depthCutOff){
        	//System.out.println("miniMax");
    		System.out.println("cutOff");
    		return sbe.evaluate(state.bs);
    	}
    	List<Move> possibleMoves = Utils.getAllPossibleMoves(state.bs,this.tempSide); //Get all possible moves
		//System.out.println(possibleMoves.size());

    	if(possibleMoves.isEmpty()){
    		//System.out.println("isEmpty");

    		return sbe.evaluate(state.bs);
    	}

    	if(this.side == this.tempSide){//MAX VALUE
        	int v = -999999;
    		for(Move m:possibleMoves){
    			
    			Stack<Integer> reverseBoard = Utils.execute(state.bs, m);
    			CheckersNode child = new CheckersNode(state.bs,state.getAlpha(),state.getBeta());
    			//System.out.println(miniMax(child));
    			changeSide(this.tempSide);
    			int miniMaxVal = miniMax(child);
    			child.setAlpha(miniMaxVal);
        		state.setAlpha(Math.max(state.getAlpha(), miniMaxVal));

        		Utils.revert(state.bs, reverseBoard);
        		//state.setAlpha(Math.max(child.getAlpha(),state.getAlpha()));

        		if(state.isRoot){
		        	System.out.println("Root");
		        	System.out.println(state.getAlpha());
		        	System.out.println(child.getAlpha());
    				if(state.getAlpha() == child.getAlpha()){
    					state.setBestMove(m);
    					System.out.println("Settingmove");
    					this.setMove(state.getBestMove());
    				}
    			}
        		if(state.getAlpha() >= state.getBeta()){ //Pruning occurs by exiting from foreach loop
        			pruneCount++;
        			System.out.println("pruning");
        			return state.getAlpha();
        		}

        	}
    		return state.getAlpha();
    	}
    	
    	else if(this.side != this.tempSide){//MIN VALUE
        	int v = 999999;
    		for(Move m:possibleMoves){
    			Stack<Integer> reverseBoard = Utils.execute(state.bs, m);
    			CheckersNode child = new CheckersNode(state.bs,state.getAlpha(),state.getBeta());
    			changeSide(this.tempSide);
    			
        		int miniMaxVal = miniMax(child);
    			child.setBeta(miniMaxVal);
    			state.setBeta(Math.min(state.getBeta(),miniMaxVal));
        		Utils.revert(state.bs, reverseBoard);

        		//state.setBeta(Math.min(child.getBeta(),state.getBeta()));

        		if(state.getBeta() <= state.getAlpha()){
        			pruneCount++;
        			System.out.println("pruning");

        			return state.getBeta();
        		}
        	}
    		return state.getBeta();
    	}
    	return 0;
    	
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
