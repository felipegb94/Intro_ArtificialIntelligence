public class stimulus {

	/**
	 * In this problem I assumed the starting N millions was always a positive integer greater than or equal to 2. 
	 */
	public static void main(String[] args) {
		
		double N = 2;
		double M = 1;
		int counter = 0;
		if (N == 0) {
			N = N + M;
		}
		if (N == 1) {
			N = N + M;
		}

		while (N > (0.5 * M * (M + 1))) {

			System.out.println("Curr N = " + N);

			System.out.println("M that will be subtracted = " + M);

			N = N - M;

			if (N > (0.5 * M * (M + 1))) {

				M++;

			}
			counter++;

		}

		System.out.println(N);

		System.out.println(M);

		M--;

		while (N != 0) {

			System.out.println("Curr N = " + N);

			System.out.println("M that will be subtracted = " + M);

			N = N - M;

			if (N < (0.5 * M * (M + 1))) {

				M--;

			}
			counter++;


		}

		System.out.println(N);

		System.out.println(M);

		System.out.println("DONE in " + counter);

	}

}
