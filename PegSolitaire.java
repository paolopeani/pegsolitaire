import java.util.Arrays;

public class PegSolitaire {
	static final int width = 7;
	static final int height = 7;


	public static int trueCount(boolean[][] pegs) {
		return trueCount(pegs, 0, 0, 0);
	}

	// Count the number of trues in the given board
	public static int trueCount(boolean[][] pegs, int positionX, int positionY, int trueCount) { 
		// We have reached the last space, so return trueCount
		if (positionX==6 && positionY==6) return trueCount; 
		// If positionX < 6, check the position and increment positionX
		if (positionX<6) {
			if (pegs[positionX][positionY] == true) return trueCount(pegs, positionX+1, positionY, trueCount+1);
			return trueCount(pegs, positionX+1, positionY, trueCount);
			// If we have reached this point, positionX must be 6
			// Reset positionX to 0 and increment positionY
		} else {
			if (pegs[positionX][positionY]==true) return trueCount(pegs, positionX-6, positionY+1, trueCount+1);
			return trueCount(pegs, positionX-6, positionY+1, trueCount);
		}
	}

	public static String solve(boolean[][] pegs) {

		int trueCount = trueCount(pegs);
		return solveX(pegs, trueCount, 0, 0);
	}

	public static String solveX(boolean[][] pegs, int trueCount, int positionX, int positionY) {

		// check if solved
		if(trueCount == 1 && pegs[3][3]) {
			return "";  // save the steps into variables and concatenate backwards
		}

		if (positionX<6) {
			if (pegs[positionX][positionY] == true) {
				String nextSteps = null;
				nextSteps = tryMove(pegs, positionX, positionY, 2, 0, positionX+2, positionY, trueCount);
				if (nextSteps != null) {
					String thisStep = ""+positionX+" "+positionY+" "+(positionX+2)+" "+positionY+" ";
					return thisStep + nextSteps;
				}
				nextSteps = tryMove(pegs, positionX, positionY, -2, 0, positionX-2, positionY, trueCount);
				if (nextSteps!= null) {
					String thisStep = ""+positionX+" "+positionY+" "+(positionX-2)+" "+positionY+" ";
					return thisStep + nextSteps;
				}
				nextSteps = tryMove(pegs, positionX, positionY, 0, 2, positionX, positionY+2, trueCount); 
				if (nextSteps!= null) {
					String thisStep = ""+positionX+" "+positionY+" "+positionX+" "+(positionY+2)+" ";
					return thisStep+nextSteps;
				}
				nextSteps = tryMove(pegs, positionX, positionY, 0, -2, positionX, positionY-2, trueCount);
				if (nextSteps != null) {
					String thisStep = ""+positionX+" "+positionY+" "+positionX+" "+(positionY-2)+" ";
					return thisStep+nextSteps;
				}
				return solveX(pegs, trueCount, positionX+1, positionY);
			} else {
				return solveX(pegs, trueCount, positionX+1, positionY);
			}
		} else if (positionY<6) {
			if (pegs[positionX][positionY]==true) {
				String nextSteps = null;
				nextSteps = tryMove(pegs, positionX, positionY, 2, 0, positionX+2, positionY, trueCount);
				if (nextSteps != null) {
					String thisStep = ""+positionX+" "+positionY+" "+(positionX+2)+" "+positionY+" ";
					return thisStep + nextSteps;
				}

				nextSteps = tryMove(pegs, positionX, positionY, -2, 0, positionX-2, positionY, trueCount);
				if (nextSteps!= null) {
					String thisStep = ""+positionX+" "+positionY+" "+(positionX-2)+" "+positionY+" ";
					return thisStep + nextSteps;
				}
				nextSteps = tryMove(pegs, positionX, positionY, 0, 2, positionX, positionY+2, trueCount); 
				if (nextSteps!= null) {
					String thisStep = ""+positionX+" "+positionY+" "+positionX+" "+(positionY+2)+" ";
					return thisStep+nextSteps;
				}
				nextSteps = tryMove(pegs, positionX, positionY, 0, -2, positionX, positionY-2, trueCount);
				if (nextSteps != null) {
					String thisStep = ""+positionX+" "+positionY+" "+positionX+" "+(positionY-2)+" ";
					return thisStep+nextSteps;
				}
				return solveX(pegs, trueCount, positionX-6, positionY+1);
			} else {
				return solveX(pegs, trueCount, positionX-6, positionY+1);
			}
		}
		return null;
	}


	private static String tryMove(boolean[][] pegs, int startX, int startY, int jumpX, int jumpY, int endX, int endY, int trueCount) {

		int middleX = startX+(jumpX/2);
		int middleY = startY+(jumpY/2);

		if(isOutOfBounds(pegs, endX, endY)) return null;
		if (pegs[middleX][middleY]==false) return null;
		if (pegs[endX][endY]==true) return null;

		pegs[endX][endY] = true;
		pegs[middleX][middleY] = false;
		pegs[startX][startY] = false;

		String s = solveX(pegs, trueCount-1, 0, 0);
		if (s!=null) return s;

		pegs[endX][endY] = false;
		pegs[middleX][middleY] = true;
		pegs[startX][startY] = true;

		return null;
	}

	public static boolean isOutOfBounds(boolean[][] pegs, int cordX, int cordY) {

		if(cordX>=width || cordX<0 || cordY>=height || cordY<0) return true;

		// Check upper left hand corner
		if (cordX == 0 && cordY == 0) return true;
		if (cordX == 0 && cordY == 1) return true;
		if (cordX == 1 && cordY == 0) return true;
		if (cordX == 1 && cordY == 1) return true;

		// Check upper right hand corner
		if (cordX == 5 && cordY == 0) return true;
		if (cordX == 6 && cordY == 0) return true;
		if (cordX == 5 && cordY == 1) return true;
		if (cordX == 6 && cordY == 1) return true;

		// Check lower left hand corner
		if (cordX == 0 && cordY == 5) return true;
		if (cordX == 0 && cordY == 6) return true;
		if (cordX == 1 && cordY == 5) return true;
		if (cordX == 1 && cordY == 6) return true;

		// Check lower right hand corner
		if (cordX == 5 && cordY == 5) return true;
		if (cordX == 6 && cordY == 5) return true;
		if (cordX == 5 && cordY == 6) return true;
		if (cordX == 6 && cordY == 6) return true;

		return false;


	}

	public static void main(String[] args) {
	}

}
