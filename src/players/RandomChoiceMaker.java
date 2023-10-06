package players;

import game.ConnectNBoard;

import java.util.Random;

/**
 * Picks a random col each time it is called.
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */
public class RandomChoiceMaker implements ConnectNChoiceMaker {

	private Random rng = new Random();
	
	/**
	 * Makes a random choice
	 * @see ConnectNChoiceMaker#playTurn(ConnectNBoard, Player)
	 */
	@Override
	public int playTurn(ConnectNBoard board, Player whichPlayerAmI, Player otherPlayer, int winLength) {
		int choice = rng.nextInt(board.numCols());
		return choice;
	}

	public void reset() { rng = new Random(); }
	
	/**
	 * Gives the name used when displaying information about this specific choice maker
	 * @return
	 */
	public String getName()
	{
		return "RANDOM";
	}
}
