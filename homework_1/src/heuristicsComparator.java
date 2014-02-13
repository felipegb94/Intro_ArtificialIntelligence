import java.util.Comparator;
public class heuristicsComparator implements Comparator<Board> {

	

	@Override
	public int compare(Board b1, Board b2) {
		// TODO Auto-generated method stub
		int f1 = b1.getF();
		int f2 = b2.getF();
		
		if(f1 < f2){
			//gives b1 a higher priority that b2
			return -1;
		}
		else if(f1 > f2){
			//gives b2 a higher priority that b1
			return 1;
		}
		//b1 = b2
		return 0;
	}

}
