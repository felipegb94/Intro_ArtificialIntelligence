
public class HammingHeuristic implements AStarHeuristic{
	/**
	 * The cost of the hamming heuristic function is calculated by counting the number of tiles in the wrong position.\
	 * If a few number of tiles are in the wrong position that means we should be closer to a solution.
	 */
	public int getCost(Board state, Board goalState)
	{
		int cost = 0;
		for(int i = 0; i < state.rows; i++){
			for (int j = 0; j < state.columns; j++){
				//if(state.tiles[i][j] == 0 )
				if(state.tiles[i][j] != goalState.tiles[i][j]){
					cost++;
				}
			}
		}
		return cost;
	}
}

