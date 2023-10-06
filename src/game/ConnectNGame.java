package game;

/**
 * Controls the execution of a game of Connect with two given players
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */


import java.util.ArrayList;

import players.ConnectNChoiceMaker;
import players.Player;


public class ConnectNGame 
{
  private ConnectNChoiceMaker player1 = null;
  private ConnectNChoiceMaker player2 = null;
  private ConnectNBoard board = null;
  /** Delay in milliseconds between players choices, zero means no delay*/
  private int delay = 0; 
  
  //** Used to control if anything is to be displayed to the screen or not 
  private boolean display = true;
  
  /** The combo length needed to win */
  private int winLength = 4;
  
  
 /**
 * toggles the current state of the display field
 */
public void toggleDisplay() { display = !display; }
  
  // Debug control methods
  public static void debugOff()
  {
	  ConnectNBoard.debugOff();
  }
  
  public static void debugOn()
  {
	  ConnectNBoard.debugOn();
  }
  
  /**
   * Base constructor
 * @param player1 player one choice maker in the game
 * @param player2 player two choice maker in the game
 * @param rows the number of rows to have in the game
 * @param cols the number of columns to have in the board
 */
public ConnectNGame(ConnectNChoiceMaker player1,ConnectNChoiceMaker player2, int rows, int cols)
  {
	this.player1 = player1;
	this.player2 = player2;
	board = new ConnectNBoard(rows,cols); // set up the basic connect game board size
  }
 

 /**
  * Plays a single turn for the given player
 * @param player the choice make for the current player
 * @param marker the marker to be used for the current player
 * @return true if the player has won the game false otherwise
 */
private boolean playTurn(ConnectNChoiceMaker player, Player marker, Player otherPlayer) throws BoardFullException
 {
	boolean result = false;
	ConnectNBoard copyBoard = new ConnectNBoard(board);
	
	if(board.allColsFull())
		throw new BoardFullException("Board is full");
	
	print("Turn: " + marker);
	print("Win@: " + winLength);
	
	print(board.toString());
	
	int col = player.playTurn(copyBoard, marker, otherPlayer, winLength); 
	print("Choice: " + (col+1));
	
	if(col >= 0 && col < board.numCols()) // make sure it is in range
	{
	  if(!board.isColFull(col)) // make sure the location is not full
	  {
		  board.addGameToken(marker, col);
		  GameTokenComboFinder finder = new GameTokenComboFinder(board, winLength);
		  
		  if(finder.didPlayerWin(marker, winLength))
		            result = true;
	  }
	  else
		  print("Choice: '" +(col+1)+ "' is full" ); 
		
	}
	else
	  print("Choice: '" +(col+1)+ "' is out of range" );
	
	return result;
 }



 /**
  *  Plays the full game of connect 4
  *  @return the player that won the game 
  */
public Player playGame()
  {
	boolean end = false;
	boolean win = false;
	Player winner = Player.NEITHER;

	try
	{
		while(!end)
		{
			if(delay > 0)
			{
				try {Thread.sleep(delay); } catch(Exception ex){}
			}
			
			win = false;

			//Player 1's turn

			win = playTurn(player1, Player.PLAYER_ONE, Player.PLAYER_TWO);

			if(!win)
			{
				//Player 2's turn
				win = playTurn(player2, Player.PLAYER_TWO, Player.PLAYER_ONE);

				if(win)
				{
					winner = Player.PLAYER_TWO;
					break;
				}
			}
			else
			{
				winner = Player.PLAYER_ONE;
				break;
			}
		}
	}
	catch(BoardFullException fullError)
	{
		print("THE BOARD IS FULL");
		winner = Player.NEITHER;
	}
	catch(Exception e)
	{
		print("ERROR: " + e.getMessage());
		e.printStackTrace();
		winner = Player.ERROR;
	}
	
	print("Final Result Original Board");
	print(board.toString());
	
	GameTokenComboFinder finder = new GameTokenComboFinder(board, winLength);
	ArrayList<GameTokenCombo> combos = null;
	int numPlace = -1;
	
	if(finder.didPlayerWin(Player.PLAYER_ONE, winLength))
	{
		combos = finder.getPlayer1Combos();
		numPlace = board.numMovesByPlayer1();
	}
	else
	{
		combos = finder.getPlayer2Combos();
		numPlace = board.numMovesByPlayer1();
	}
	
	for(GameTokenCombo curr : combos)
		if(curr.size() >= winLength)
		{
			markWinCombo(curr);
		}
	
	if(winner != Player.NEITHER && winner != Player.WINMARKER)
	{
	 print("Final Result Win Combo");
	 print(board.toString());
	 print("Num tokens placed by winner: " + numPlace);
	}
	else
		print("No Win Combo found");
	
	return winner;
  }


  private void markWinCombo(GameTokenCombo winCombo)
  {
     for(int i=0; i < winCombo.size(); i++)
	   {
	      GameToken token = winCombo.get(i);
		  token.setWhichPlayer(Player.WINMARKER);
	   }
  }


 /**
  * helper method to control output from the game
  * @param s the string to output
  */
private void print(String s)
 {
	 if(display)
	   System.out.println("ConnectN: " + s);
 }


public int getDelay() { return delay; }
public void setDelay(int delay) { this.delay = delay; }


public int getWinLength() { return winLength; }
public void setWinLength(int winLength) { this.winLength = winLength; }

}
