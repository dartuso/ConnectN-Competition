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
public class RightToLeftLinearPickerChoiceMaker implements ConnectNChoiceMaker {

	private int count = -1;
	/**
	 *  Just steps through picking each col each turn
	 * @see ConnectNChoiceMaker#playTurn(ConnectNBoard, Player)
	 */
	@Override
	public int playTurn(ConnectNBoard board, Player whichPlayerAmI, Player otherPlayer, int winLength) 
	{
		if(count < 0) count = board.numCols();
		count--;
		
		return count;
	}
	
	public void reset() { count = -1; } 

	/**
	 * Gives the name used when displaying information about this specific choice maker
	 * @return
	 */
	public String getName()
	{
		return "RL-LINEAR";
	}
	
}
