
public class ManhattanHeuristic implements AStarHeuristic{
	public int getCost(Board state, Board goalState)
	{

		
		int cost = 0;
		for(int i = 0; i < state.rows; i++){
			for (int j = 0; j < state.columns; j++){
				if(state.tiles[i][j] != goalState.tiles[i][j]){
					for(int k = 0; k < goalState.rows; k++){
						for(int l = 0; l < goalState.columns;l++){
							if(state.tiles[i][j] == goalState.tiles[k][l] && state.tiles[i][j] != 0){
								
								int rowDist = Math.abs(i-k);
								int colDist = Math.abs(j-l);
								//System.out.println("i = " + i + ", j = " + j);
								cost = cost + rowDist + colDist;

							}	
						}
					}	
				}
			}
		}
		return cost;
	}
}

