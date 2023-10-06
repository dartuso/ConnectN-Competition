package players;
import game.ConnectNBoard;


/**
 * Interface to be implemented by any class That will be used to represent
 * A player in the game of ConnectN
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */
public interface ConnectNChoiceMaker 
{
	/**
	 * This method will return what column to drop the GameToken in
	 * for the game of ConnectN. The choice must be a valid column, 
	 * otherwise the players turn is forfeit. The method is given 
	 * a duplicate copy of the current game board.
	 * 
	 * @param board copy of the current game board
	 * @param whichPlayerAmI indicator of which player in the game you are
	 * @param otherPlayer indicator of which player the other entity is in the game
	 * @param winLength the win length for a combo
	 * @return the column to place the players GameToken in
	 */
	public int playTurn(ConnectNBoard board, Player whichPlayerAmI, Player otherPlayer, int winLength);
	
	
	/**
	 * Called if multiple games are being played and values should be reset
	 */
	public void reset();
	
	/**
	 * Gives the name used when displaying information about this specific choice maker
	 * @return
	 */
	public String getName();
}
