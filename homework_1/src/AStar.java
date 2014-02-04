import java.util.ArrayList;
import java.util.Collections;
public class AStar {
	 
	private Board initialState;
	private Board goalState;
	private AStarHeuristic heuristic;

	public AStar(Board initial, Board goal, AStarHeuristic heur)
	{
		initialState = initial;
		goalState = goal;
		heuristic = heur;
	}

	public void search()
	{
      	/* Declare and initialize Frontier and Explored data structures */ 
		/* Put start node in Fringe list Frontier */
		while (!Frontier.isEmpty())
		{
			/* Remove from Frontier list the node n for which f(n) is minimum */
			/* Add n to Explored list*/

			if (n.equals(goalState))
			{
				/* Print the solution path and other required information */
				/* Trace the solution path from goal state to initial state using getParent() function*/
			}

			ArrayList<Board> successors = n.getSuccessors();
			for (int i = 0 ;i<successors.size(); i++)
			{
				Board n1 = successors.get(i);
				/* if n1 is not already in either Frontier or Explored
				      Compute h(n1), g(n1) = g(n)+c(n, n1), f(n1)=g(n1)+h(n1), place n1 in Frontier
				   if n1 is already in either Frontier or Explored
				      if g(n1) is lower for the newly generated n1
				          Replace existing n1 with newly generated g(n1), h(n1), set parent of n1 to n
				          if n1 is in Explored list
				              Move n1 from Explored to Frontier list*/
			}
		}
		System.out.println("No Solution");
	}

}
