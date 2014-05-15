package cs540.checkers;

import static cs540.checkers.CheckersConsts.*;

import java.util.*;

/**
 * This simplistic static board evaluator assigns points for material. Each pawn
 * remaining on the board contributes one point, and each remaining king
 * remaining on the board contributes two points.
 */
public class CompetitiveEvaluator implements Evaluator {
	public int evaluate(int[] bs) {
		int[] pawns = new int[2], kings = new int[2], safePawns = new int[2], safeKings = new int[2], defensePieces = new int[2], triangle = new int[2], safeAttackingPawns = new int[2];

		for (int i = 0; i < H * W; i++) {
			int v = bs[i];

			if (v % 4 == 0) { // IS RED
				try {
					if ((bs[i + 7] % 4 == 0) && (bs[i + 9] % 4 == 0)) {
						triangle[v % 4] += 1;
					}
				} catch (IndexOutOfBoundsException e) {

				}
				if (i > 48 && i < 63) {
					defensePieces[v % 4] += 1;
				}

			} else if (v % 4 == 1) { //IS BLACK
				try {
					if ((bs[i - 7] % 4 == 1) && (bs[i - 9] % 4 == 1)) {
						triangle[v % 4] += 1;
					}
				} catch (IndexOutOfBoundsException e) {

				}
				if (i > 0 && i < 15) {
					defensePieces[v % 4] += 1;
				}
			}

			switch (v) {

			case RED_PAWN:
			case BLK_PAWN:

				if ((i % 8 == 0) || ((i + 9) % 16 == 0)) {
					safePawns[v % 4] += 1;
				}

				try {
					if ((v == RED_PAWN) && (bs[i - 16] == BLK_PAWN)
							&& (bs[i - 7] == BLANK) && (bs[i - 9] == BLANK)) {
						// System.out.println("safeAttackingPawn!");
						safeAttackingPawns[v % 4] += 1;
					} else if ((v == BLK_PAWN) && (bs[i + 16] == RED_PAWN)
							&& (bs[i + 7] == BLANK) && (bs[i - 9] == BLANK)) {
						// System.out.println("safeAttackingPawn!");
						safeAttackingPawns[v % 4] += 1;
					}
					if (v == RED_PAWN) {

					}

				} catch (IndexOutOfBoundsException e) {

				}
				pawns[v % 4] += 1;

				break;
			case RED_KING:
			case BLK_KING:

				if ((i % 8 == 0) || ((i + 9) % 16 == 0)) {
					safeKings[v % 4] += 1;
				}
				kings[v % 4] += 1;

				break;

			}
		}

		return 30 * (pawns[RED] - pawns[BLK]) + 90 * (kings[RED] - kings[BLK])
				+ 30 * (defensePieces[RED] - defensePieces[BLK]) + 5
				* (safePawns[RED] - safePawns[BLK]) + 10
				* (safeKings[RED] - safeKings[BLK]) + 40
				* (safeAttackingPawns[RED] - safeAttackingPawns[BLK]) + 
				10*(triangle[RED] - triangle[BLK]);
	}
}
