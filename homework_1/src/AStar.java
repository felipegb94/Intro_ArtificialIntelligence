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
			counter++;
			Board n = Frontier.remove();
			Explored.add(n);

			if (n.equals(goalState)) {
				System.out.println("Solution Found! At step = " + counter);
				n.print();
				while (n.getParent() != null) {
					System.out.println("");
					n = n.getParent();
					n.print();
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
				int currCost = n.getG() + 1; // Cost to get to node n1. Since
												// the cost to make a move is
												// one we just add one to the
												// cost to get to the previous
												// node
				n1.setG(currCost);
				int heuristicCost = heuristic.getCost(n1, goalState); // Calculate
																		// H =
																		// heuristic
																		// cost
				n1.setH(heuristicCost);
				n1.updateF();

				if (!(Frontier.contains(n1) && Explored.contains(n1))) {
					/**
					 * If n1 is not already in either Frontier or Explored
					 * Compute h(n1), g(n1) = g(n)+c(n, n1), f(n1)=g(n1)+h(n1),
					 * place n1 in Frontier
					 */

					n1.setParent(n);
					Frontier.add(n1);
					// n1.print();
					// System.out.println("");

				}
				/*
				 * 
				 * if n1 is already in either Frontier or Explored if g(n1) is
				 * lower for the newly generated n1 Replace existing n1 with
				 * newly generated g(n1), h(n1), set parent of n1 to n if n1 is
				 * in Explored list Move n1 from Explored to Frontier list
				 */
				else {
					//System.out.println(Frontier.contains(n1));
					if (Frontier.contains(n1)) {
						
						for (Board b:Frontier) {
							
							if (b.equals(n1)) {
								if (b.getG() > n1.getG()) {
									b.setParent(n);
									b.setG(n1.getG());
									b.setH(n1.getH());
									b.updateF();
								}
								break;
							}
						}
					}
					//System.out.println(Explored.contains(n1));
					if (Explored.contains(n1)) {
						
						for (int j = 0; j < Explored.size(); j++) {
							Board b = Explored.get(j);
							if (b.equals(n1)) {
								if (b.getG() > n1.getG()) {
									b.setParent(n);
									b.setG(n1.getG());
									b.setH(n1.getH());
									b.updateF();
									Explored.remove(b);
									Frontier.add(b);
									// b.print();
									//System.out.println("");

								}
								j = Frontier.size();
							}
						}
					}
				}

			}
		}
		System.out.println("No Solution. Counter = " + counter);

	}

}
