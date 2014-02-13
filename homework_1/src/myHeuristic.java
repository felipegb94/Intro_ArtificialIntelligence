
public class myHeuristic implements AStarHeuristic{

	@Override
	public int getCost(Board state, Board goalState) {
		// TODO Auto-generated method stub
		int cost = 0;
		for(int i = 0; i < state.rows; i++){
			for (int j = 0; j < state.columns; j++){
				if(state.tiles[i][j] != goalState.tiles[i][j]){
					for(int k = 0; k < goalState.rows; k++){
						for(int l = 0; l < goalState.columns;l++){
							if(state.tiles[i][j] == goalState.tiles[k][l] && state.tiles[i][j] != 0){
								
								/**
								 * Manhattan heuristic modification
								 */
								if(k == 0){ //If we are in an upper tile we can check below
									if(state.tiles[k+1][l] == goalState.tiles[k+1][l]){
										cost += 1;
									}
								}
								if(l == 0){ //If we are in a leftmost tile we can check the right
									if(state.tiles[k][l+1] == goalState.tiles[k][l+1]){
										cost += 1;
									}
								}
								if(k == goalState.tiles.length-1){//If we are in a tile in the bottom we can check above
									if(state.tiles[k-1][l] == goalState.tiles[k-1][l]){
										cost+=1;
									}
								}
								if(l == goalState.tiles[0].length){//If we are in a tile in a rightmost we can check the left
									if(state.tiles[k][l-1] == goalState.tiles[k][l-1]){
										cost+=1;
									}
								}
									
								
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
