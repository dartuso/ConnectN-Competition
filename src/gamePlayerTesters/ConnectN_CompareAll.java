package gamePlayerTesters;
/**
 *Runs a full competition of all included players to determine an overall winner
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */
import game.ConnectNGame;

import java.util.*;
import players.*;

public class ConnectN_CompareAll 
{

	/** the number of games to run per test */
	private int numGamesPerTest = 5; 
	private int numBoardRows = 6;
	private int numBoardCols = 7;
	private int winLength = 5;
	private boolean allowPlayAgainstSelf = true;

	private ArrayList<ConnectNChoiceMaker> choiceMakers = new ArrayList<ConnectNChoiceMaker>();
	private Hashtable<String, Results> OverAllWins = new Hashtable<String, Results>();

	/**
	 * Add a new Choice maker to be included when all tests are run
	 * @param player the player to add
	 */
	public void addChoiceMaker(ConnectNChoiceMaker player) 
	{ 
		choiceMakers.add(player); 
		OverAllWins.put(player.getClass().getName(),  new Results(player.getName() + "("+ player.getClass().getName()+")" ) );
	}

	public void setNumGamesPerTest(int numGamesPerTest) { this.numGamesPerTest = numGamesPerTest; }
	public void setNumBoardRows(int numBoardRows) { this.numBoardRows = numBoardRows; }
	public void setNumBoardCols(int numBoardCols) { this.numBoardCols = numBoardCols; }
	public void setWinLength(int winLength) { this.winLength = winLength; }
	public void setAllowPlayAgainstSelf(boolean allowPlayAgainstSelf) { this.allowPlayAgainstSelf = allowPlayAgainstSelf; }


	/**
	 * Prints the current players in the test to the console
	 * @return the longest name length
	 */
	private int printPlayers()
	{
		int length = Integer.MIN_VALUE;
		System.out.println("Players: ");
		int count = 1;
		for(ConnectNChoiceMaker player1 : choiceMakers)
		{
			System.out.println( String.format("(%2d) " + player1.getClass().getName(), count));
			length = Math.max(player1.getName().length(),length);
			count++;
		}

		return length;
	}

	/**
	 * Prints out the final test results to the console
	 */
	private void printFinalTestResults()
	{
		System.out.println("Final Overall Results: ");
		ArrayList<Results> allRes = new ArrayList<Results>();
		allRes.addAll( OverAllWins.values() );
		Collections.sort(allRes);
		
		for(Results res: allRes )
		{
			System.out.println(res);
		}
	}

	/**
	 * runs test of all players against all other players
	 */
	public void runAllTests()
	{
		int length = printPlayers();
		String frmt = "\tTest: %"+length+"s vs %"+length+"s : ";

		System.out.println("Num games per test: " + numGamesPerTest);
		for(ConnectNChoiceMaker player1 : choiceMakers)
		{
			for(ConnectNChoiceMaker player2 : choiceMakers)
			{
				if(player1 == player2 && !allowPlayAgainstSelf) continue;

				System.out.print(String.format(frmt, player1.getName(), player2.getName()));
				runTest(player1, player2);
			}
		}

		printFinalTestResults();
	}

	/**
	 * Runs an individual round of tests for the given combination of players
	 * @param player1
	 * @param player2
	 */
	private void runTest(ConnectNChoiceMaker player1,ConnectNChoiceMaker player2)
	{
		int player1Win = 0;
		int player2Win = 0;
		int tie=0;
		int error=0;
		String player1Name = player1.getClass().getName();
		String player2Name = player2.getClass().getName();
		String winnerName = "";


		for(int i = 1; i <= numGamesPerTest; i++)
		{
			player1.reset();
			player2.reset();

			ConnectNGame game = new ConnectNGame(player1, player2,numBoardRows,numBoardCols);
			game.toggleDisplay();
			game.setWinLength(winLength);
			Player winner = game.playGame();

			if(winner == Player.PLAYER_ONE) player1Win++;
			else if(winner == Player.PLAYER_TWO) player2Win++;
			else if(winner == Player.NEITHER) tie++;
			else error++;

		}

		if(player1Win > player2Win ) 
		{
			winnerName = player1Name;
			OverAllWins.get(player1Name).incWin();
			OverAllWins.get(player2Name).incLoss();
		}
		else if(player1Win < player2Win ) 
		{
			winnerName = player2Name;
			OverAllWins.get(player2Name).incWin();
			OverAllWins.get(player1Name).incLoss();
		}
		else 
		{
			winnerName = "TIE";
			OverAllWins.get(player1Name).incTie();
			OverAllWins.get(player2Name).incTie();
		}

		System.out.println(String.format("Result: ( P1 Wins %3d, P2 Wins %3d, Ties %3d, Errors %3d) Winner = %s",
				player1Win,player2Win,tie,error,winnerName));
	}

}
