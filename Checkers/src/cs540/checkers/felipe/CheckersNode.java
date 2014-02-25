package cs540.checkers.felipe;

import java.util.List;

import cs540.checkers.Move;

public class CheckersNode {
	
	private CheckersNode parent;
	private List<CheckersNode> children;
	public int[] bs;
	private int alpha;
	private int beta;
	private Move bestMove;
	public boolean isRoot;
	
	public CheckersNode(int[] boardState,int alpha,int beta){
		
		parent = null;
		setAlpha(alpha);
		setBeta(beta);
		bs = boardState;
		isRoot = false;
		
		
	}

	
	
	public CheckersNode getParent() {
		return parent;
	}
	public void setParent(CheckersNode parent) {
		this.parent = parent;
	}
	public List<CheckersNode> getChildren() {
		return children;
	}
	public void setChildren(List<CheckersNode> children) {
		this.children = children;
	}


	public int getAlpha() {
		return alpha;
	}


	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}


	public int getBeta() {
		return beta;
	}


	public void setBeta(int beta) {
		this.beta = beta;
	}


	

	public Move getBestMove() {
		return bestMove;
	}


	public void setBestMove(Move bestMove) {
		this.bestMove = bestMove;
	}

}
