package game;
/**
 * Represents information about a single players game token
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */

import players.Player;

public class GameToken
{
	private Player whichPlayer; // which player placed the token 
	private int row;
	private int col;
	/**
	 * Base constructor
	 * @param p the player that placed the game token
	 * @param row the row the token is located at
	 * @param col the col the token is located at
	 */
	public GameToken(Player p,int row, int col) 
	{ 
		whichPlayer = p;
		this.row = row;
		this.col = col;
	}
	/**
	 * Copy constructor
	 * @param gt the object to copy information from
	 */
	public GameToken(GameToken gt) 
	{ 
		if(gt != null)
		{
			whichPlayer = gt.whichPlayer; 
			this.row = gt.row;
			this.col = gt.col;
		}
	}
	public Player getWhichPlayer() { return whichPlayer; }
	public int getRow() { return row; }
	public int getCol() { return col; }
	public void setWhichPlayer(Player whichPlayer) {
		this.whichPlayer = whichPlayer;
	} 

	@Override
	public String toString() {
		return "(row=" + row + ", col=" + col + ")";
	}

	/**
	 * Compares to see if the token matches anothe rgame token
	 */
	public boolean equals(Object obj)
	{
		boolean result = false;

		if(obj instanceof GameToken)
		{
			if(obj != null)
			{
				GameToken comp = (GameToken) obj;
				result = (comp.col == col) &&  (comp.row == row) 
						  &&  (comp.whichPlayer == whichPlayer);
			}
		}

		return result;
	}
}