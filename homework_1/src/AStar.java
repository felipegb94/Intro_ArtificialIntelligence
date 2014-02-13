import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Collections;

public class AStar {

	private Board initialState;
	private Board goalState;
	private AStarHeuristic heuristic;

	public AStar(Board initial, Board goal, AStarHeuristic heur) {
		initialState = initial;
		goalState = goal;
		heuristic = heur;
	}

	public void search() {
		/* Declare and initialize Frontier and Explored data structures */
		boolean inExplored = false;
		boolean inFrontier = false;
		boolean solutionFound = false;
		Comparator<Board> comparator = new heuristicsComparator();
		PriorityQueue<Board> Frontier = new PriorityQueue<Board>(10,comparator);
		ArrayList<Board> Explored = new ArrayList<Board>();
		// Frontier.
		/* Put start node in Fringe list Frontier */
		Frontier.add(initialState);
		int counter = 0;
		while (!Frontier.isEmpty()) {
			/* Remove from Frontier list the node n for which f(n) is minimum */
			/* Add n to Explored list */
			//System.out.println(counter);
			
			Board n = Frontier.remove();
			
			Explored.add(n);
			
			if (n.equals(goalState)) {
				counter=0;
				
				ArrayList<Board> solutionPath = new ArrayList<Board>();
				solutionFound = true;
				System.out.println("Solution Found!");
				n.print();
				solutionPath.add(n);
				try{
					BufferedWriter output = new BufferedWriter(new FileWriter("newOutput.txt"));
					output.write(n.toString());
					output.newLine();
					while (n.getParent() != null) {

						System.out.println("");
						n = n.getParent();
						n.print();
						solutionPath.add(n);
						counter++;
						System.out.println(counter);
					
						output.write(n.toString());
						output.newLine();
	

				}
					output.newLine();
					output.newLine();
					String s = ""+counter;
					output.write(s);
					output.newLine();
					output.write("Done. Explored set size = " + Explored.size());
				
					output.close();
				}catch(IOException e){
					e.printStackTrace();
				}
				

					

				
				break;
				/* Print the solution path and other required information */
				/*
				 * Trace the solution path from goal state to initial state
				 * using getParent() function
				 */
			}

			ArrayList<Board> successors = n.getSuccessors();
			for (int i = 0; i < successors.size(); i++) {
				Board n1 = successors.get(i);
				/**
				 * If n1 is not already in either Frontier or Explored
				 * Compute h(n1), g(n1) = g(n)+c(n, n1), f(n1)=g(n1)+h(n1),
				 * place n1 in Frontier
				 */
				//Cost to get to node n1. Since the cost to make a move is one we just add one to the cost to get to the parent node
				int currCost = n.getG() + 1; 
				n1.setG(currCost);
				//Heuristic cost
				int heuristicCost = heuristic.getCost(n1, goalState); 
				n1.setH(heuristicCost);
				n1.updateF(); // f = g+h
				
				inFrontier = Frontier.contains(n1); //Is n1 in the frontier priority list?
				inExplored = Explored.contains(n1); //Is n1 in the explored array?
				if (!inFrontier && !inExplored) {
					Frontier.add(n1);
				}
				/**
				 * if n1 is already in either Frontier or Explored if g(n1) is
				 * lower for the newly generated n1 Replace existing n1 with
				 * newly generated g(n1), h(n1), set parent of n1 to n if n1 is
				 * in Explored list Move n1 from Explored to Frontier list
				 */
				else {
					if (inFrontier) {
						for (Board b:Frontier) {
							if (b.equals(n1)) {
								if (b.getG() > n1.getG()) {
									Frontier.remove(b);
									Frontier.add(n1);
								}
								break; //Stop traversing the Frontier
							}
						}
					}
					if (inExplored) {
						for (int j = 0; j < Explored.size(); j++) {
							Board b = Explored.get(j);
							if (b.equals(n1)) {
								if (b.getG() > n1.getG()) {
									Explored.remove(b);
									Frontier.add(n1);
								}
								j = Explored.size(); //Stop Traversing the list
							}
						}
					}
				}

			}
		}
		if(!solutionFound){
			System.out.println("No Solution");
		}
		else{
			System.out.println("Done. Explored set size = " + Explored.size());
		}		
	}
}
