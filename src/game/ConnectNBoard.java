package game;
import java.util.*;

import players.Player;

/**
 * Represents the ConnectN game board
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */
public class ConnectNBoard 
{	
	/** used to indicate if debug info should be printed out to the console */
	private static boolean debug = false;	

	/** all tokens placed by player 1 */
	private ArrayList<GameToken> player1Tokens = new ArrayList<GameToken>();
	/**all tokens placed by player 2 */
	private ArrayList<GameToken> player2Tokens = new ArrayList<GameToken>();
	/** 
	 * 2d array to represent the entire game board
	 * The board is represented turned upside down, the bottom of the board is row zero and the top
	 * of the board t=is the last row. Game tokens grow in cols from the bottom to the top 
	 * ( index zero to the number of rows. )
	 *  */
	private GameToken[][] board = null;
	/** array to keep track of top location to place game tokens */	
	private int top[] = null;
	/** the number of rows in the game board */
	private int rows = 0; 

	/** the number of columns in the game board */
	private int cols = 0; 


	/**
	 * Base constructor to set up the empty game board
	 * @param rows The number of rows to have in the game board
	 * @param cols The number of columns to have in the game board
	 */
	public ConnectNBoard(int rows, int cols) { init(rows,cols); }


	/**
	 * Copy Constructor. Creates an exact copy of the given board 
	 * @param board the board to duplicate
	 */
	public ConnectNBoard( ConnectNBoard c4board )
	{
		init(c4board.rows,c4board.cols);

		System.arraycopy(c4board.top,0,top,0,c4board.top.length);

		for(int i =0; i < c4board.rows; i++)
		{
			for(int j=0; j < c4board.cols; j++)
			{
				if(c4board.board[i][j] != null)
				{
					board[i][j] = new GameToken(c4board.board[i][j]);

					if(board[i][j].getWhichPlayer() == Player.PLAYER_ONE)
						player1Tokens.add(board[i][j]);
					else
						player2Tokens.add(board[i][j]);
				}
				else board[i][j] = null;
			}
		}
	}

	/**
	 * Sets up the empty game board
	 * @param rows The number of rows to have in the game board
	 * @param cols The number of columns to have in the game board
	 */
	public void init(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;

		top = new int[cols];
		board = new GameToken[rows][];

		for(int i=0; i < rows; i++)
			board[i] = new GameToken[cols];

		for(int i=0; i < cols; i++)
			top[i] = 0;

	}


	/**
	 * Determines if a specific col is full and no more game tokens can be placed
	 * @param col
	 * @return true if it is full false otherwise
	 */
	public boolean isColFull(int col)
	{
		boolean result = false;
		if( getTop(col) == rows) result = true;
		return result;
	}


	/**
	 * Determines if all columns in the board are full
	 * @return true if all are full false otherwise
	 */
	public boolean allColsFull()
	{
		boolean result = true;

		for(int i=0; i < cols; i++)
			if(!isColFull(i))
			{
				result = false;
				break;
			}

		return result;
	}


	/**
	 * returns the top location of where the token is in the specified column
	 * @param col the col to get the top for
	 * @return
	 */
	private int getTop(int col) { return top[col]; }
	private void incTop(int col) { top[col]++; }



	public int numRows() { return rows; }
	public int numCols() { return cols; }

	public static void debugOn() { debug = true; }
	public static void debugOff() { debug = false; }


	public ArrayList<GameToken> getPlayer1Tokens() { return player1Tokens; }


	public ArrayList<GameToken> getPlayer2Tokens() { return player2Tokens; }


	/**
	 * returns the specific token at the given location
	 * @param row row location of the token
	 * @param col column location of the token
	 * @return the token if found, null if not found
	 */
	public GameToken getTokenAt(int row, int col)
	{
		GameToken result = null;

		// make sure value is within the game board
		if( 
				((row >= 0) && (row < rows))  && 
				((col >= 0) && (col < cols)) 
				)
		{
			if(row < getTop(col))
				result = board[row][col];
		}

		return result;
	}

	/**
	 * Adds a game token to the board for the given user
	 * @param whichPlayer which player is adding the game token
	 * @param col the col to add the token to
	 * @return true of the token was added false if the col was full
	 */
	public boolean addGameToken(Player whichPlayer, int col)
	{
		boolean result = false;

		if(!isColFull(col))
		{
			GameToken gt =  new GameToken(whichPlayer, getTop(col), col );
			board[ getTop(col) ][col] = gt;
			incTop(col);

			if(whichPlayer == Player.PLAYER_ONE)
				player1Tokens.add(gt);
			else
				player2Tokens.add(gt);
		}

		return result;
	}


	/**
	 * returns the current number of tokens that have been placed by player 1 
	 * @return the number of tokens
	 */
	public int numMovesByPlayer1() { return player1Tokens.size(); }

	/**
	 * returns the current number of tokens that have been placed by player 2 
	 * @return the number of tokens
	 */
	public int numMovesByPlayer2() { return player2Tokens.size(); }

	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		String result = nl + "\tGame Board: " + nl;
		String space="";
		String dash="";
		if(debug)
		{
			result += "\tPlayer1: " + player1Tokens + nl;
			result += "\tPlayer2: " + player2Tokens + nl;
			result += "\t   Rows: " + rows + nl;
			result += "\t   Cols: " + cols + nl;
			result += "\t   Top: ";

			for(int t : top) result += "["+t+"]";

			result += nl + nl;
		}

		if(cols >= 10) { space = " "; dash = "_"; }

		for(int i = rows-1; i >= 0; i--)
		{
			result += "\t";
			for(int j = 0; j < cols; j++)
			{
				if(board[i][j] != null)
				{
					String player = "";
					switch(board[i][j].getWhichPlayer())
					{
					case PLAYER_ONE: player = "1"; break;
					case PLAYER_TWO: player = "2"; break;
					case  WINMARKER: player = "W"; break;
					default: player ="E"; break;
					}

					result += "["+space+player+"]";
				}
				else
					result += "["+space+" ]";
			}
			result += nl;
		}

		result += "\t";
		for(int j = 0; j < cols; j++)
			result += dash+"___";

		result += nl + "\t";

		for(int j = 1; j <= cols; j++)
		{
			if(j < 10)
				result += "("+space+j+")";
			else
				result += "("+j+")";
		}

		return result + nl;
	}





}