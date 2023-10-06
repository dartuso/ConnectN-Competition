package players;
import game.ConnectNBoard;

/**
 * Cycles through picking each line
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */
public class LeftToRightLinearPickerChoiceMaker implements ConnectNChoiceMaker {

	private int count = 0;
	/**
	 *  Just steps through picking each col each turn
	 * @see ConnectNChoiceMaker#playTurn(ConnectNBoard, Player)
	 */
	@Override
	public int playTurn(ConnectNBoard board, Player whichPlayerAmI, Player otherPlayer, int winLength) 
	{
		int choice = count;
	    count = (++count) % board.numCols();
		return choice;
	}
	
	public void reset() { count = 0; } 

	/**
	 * Gives the name used when displaying information about this specific choice maker
	 * @return
	 */
	public String getName()
	{
		return "LR-LINEAR";
	}
	
}
