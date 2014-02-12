import java.util.Comparator;
public class heuristicsComparator implements Comparator<Board> {

	

	@Override
	public int compare(Board b1, Board b2) {
		// TODO Auto-generated method stub
		int f1 = b1.getF();
		int f2 = b2.getF();
		
		if(f1 < f2){
			return -1;
		}
		else if(f1 > f2){
			return 1;
		}
		return 0;
	}

}
