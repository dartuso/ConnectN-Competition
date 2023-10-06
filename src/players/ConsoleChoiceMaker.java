package players;

import game.ConnectNBoard;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Gets a players choice from the console, it will confirm
 * that it is a legal move.
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */
public class ConsoleChoiceMaker implements ConnectNChoiceMaker 
{
	private Scanner input = new Scanner(System.in);
	
	/**
	 * Gets the players choice from the console 
	 * @see ConnectNChoiceMaker#playTurn(ConnectNBoard)
	 */
	@Override
	public int playTurn(ConnectNBoard board, Player whichPlayerAmI, Player otherPlayer, int winLength) 
	{
		int choice = -1;
		boolean end = false;
		
		while(!end)
		{
			try
			{
			  System.out.print("Enter your choice (1 - "+board.numCols()+"): " );
			  choice = input.nextInt() - 1;
			  
			  if(choice < 0 || choice >= board.numCols())
			  {
				  System.out.println("ERROR: Incorrect input, choice out of range");
			  }
			  else
			  {
				  if(board.isColFull(choice))
				     System.out.println("ERROR: Incorrect input, choice destination is full");  
				  else
					  end = true;
			  }
			  
			}
			catch(InputMismatchException ime)
			{
				System.out.println("ERROR: Incorrect input");
				input.next();
			}
			catch(Exception e)
			{
				System.out.println("ERROR: " + e.getMessage());
				end = true;
			}
		}
		
		return choice;
	}

	
	public void reset() { }
	
	/**
	 * Gives the name used when displaying information about this specific choice maker
	 * @return
	 */
	public String getName()
	{
		return "CONSOLE";
	}
}
