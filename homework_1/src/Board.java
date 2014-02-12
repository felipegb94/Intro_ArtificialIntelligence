import java.util.ArrayList;
import java.util.Comparator;

public class Board implements Comparator<Board> {
	
	public static int rows=5;
	public static int columns=3;
	private Board parent = null; /* only initial board's parent is null */
	public int[][] tiles;
	private int currEmptyRow;
	private int currEmptyCol;
	private int g; //Actual Cost of getting to this board
	private int h; //Heuristic/Estimated Cost to get to goal from here
	private int f; //g+h

	public Board(int[][] cells)                 //Populate states
	{
	  this.g = 0;
	  this.h = 0;
	  tiles = new int[rows][columns];
		for (int i = 0 ;i<rows; i++)
			for (int j = 0; j<columns; j++)
			{
				tiles[i][j] = cells[i][j];
				//Keep track of where the empty tile is for generating successor later
				if(tiles[i][j] == 0){
					this.setCurrEmptyRow(i);
					this.setCurrEmptyCol(j);
				}
			}
	}
	public boolean equals(Object x)         //Can be used for repeated state checking
	{
		Board another = (Board)x;
		if (columns != another.columns || rows != another.rows) return false;
		for (int i = 0; i<rows; i++)
			for (int j = 0; j<columns; j++)
				if (tiles[i][j] != another.tiles[i][j])
					return false;
		return true;
	}
	public ArrayList<Board> getSuccessors()     //Use cyclic ordering for expanding nodes - Right, Down, Left, Up
	{
		/* TODO */
		Board b;
		int tileToMove = 0;
		ArrayList<Board> successors = new ArrayList<Board>();
		int[][] succesorTiles = copyTiles(); //Create copy of current tiles
		
		//Move empty space left/Tile Right. Check that we are not in the left most column and the tile to the left is not an inaccessible square
		if((currEmptyCol > 0) && succesorTiles[currEmptyRow][currEmptyCol-1] != -1){ 
			tileToMove = succesorTiles[currEmptyRow][currEmptyCol-1]; //Get the tile to the left
			succesorTiles[currEmptyRow][currEmptyCol-1] = 0; //Set the tile to the left to be the new empty tile
			succesorTiles[currEmptyRow][currEmptyCol] = tileToMove; //Move tile to its new position (its right)
			b = new Board(succesorTiles);
			successors.add(b);
		}
		
		
		succesorTiles = copyTiles();		
		//Move empty space up/Tile down. Check that we are not in the top most row and that the tile above 0 is accessible
		if((currEmptyRow > 0) && succesorTiles[currEmptyRow-1][currEmptyCol] != -1){
			tileToMove = succesorTiles[currEmptyRow-1][currEmptyCol];
			succesorTiles[currEmptyRow-1][currEmptyCol] = 0;
			succesorTiles[currEmptyRow][currEmptyCol] = tileToMove;
			b = new Board(succesorTiles);
			successors.add(b);
		}
		
		
		succesorTiles = copyTiles();
		//Move empty space right/Tile left. Check that we are not in the right most column and the tile to the right is not an inaccessible square
		if((currEmptyCol < succesorTiles[0].length-1) && succesorTiles[currEmptyRow][currEmptyCol+1] != -1){ 
			tileToMove = succesorTiles[currEmptyRow][currEmptyCol+1]; //Get the tile to the right
			succesorTiles[currEmptyRow][currEmptyCol+1] = 0; //Set the tile to the right to be the new empty tile
			succesorTiles[currEmptyRow][currEmptyCol] = tileToMove; //Move tile to its new position (its left)
			b = new Board(succesorTiles);
			successors.add(b);
		}
		
		
		succesorTiles = copyTiles();
		//Move empty space down/Tile up. Check that we are not in the bottom most row and that the tile below 0 is accessible
		if((currEmptyRow < succesorTiles.length-1) && succesorTiles[currEmptyRow+1][currEmptyCol] != -1){
			tileToMove = succesorTiles[currEmptyRow+1][currEmptyCol];
			succesorTiles[currEmptyRow+1][currEmptyCol] = 0;
			succesorTiles[currEmptyRow][currEmptyCol] = tileToMove;
			b = new Board(succesorTiles);
			successors.add(b);
		}
		
		
		
		return successors;
	}
	
	public void print()
	{
		for (int i = 0; i<rows; i++)
		{
			for (int j = 0 ;j<columns; j++)
			{
				if (j > 0) System.out.print("\t");
				System.out.print(tiles[i][j]);
			}
			System.out.println();
		}
	}
	
	public void setParent(Board parentBoard)
	{
		parent = parentBoard;
	}
	
	public Board getParent()
	{
		return parent;
	}
	
	
	public void setCurrEmptyRow(int currEmptyRow) {
		this.currEmptyRow = currEmptyRow;
	}
	public int getCurrEmptyRow() {
		return currEmptyRow;
	}
	public void setCurrEmptyCol(int currEmptyCol) {
		this.currEmptyCol = currEmptyCol;
	}
	public int getCurrEmptyCol() {
		return currEmptyCol;
	}
	public int[][] copyTiles(){
		int[][] copy = new int[rows][columns];
		for (int i = 0;i<rows; i++)
			for (int j = 0; j<columns; j++)
			{
				copy[i][j] = tiles[i][j];
			}
		
		return copy;
		
	}
	@Override
	public int compare(Board b1, Board b2) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getG() {
		return g;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getH() {
		return h;
	}
	public void setF(int f) {
		this.f = f;
	}
	public void updateF(){
		f = this.g+this.h;
	}
	public int getF() {
		return f;
	}
	
}
