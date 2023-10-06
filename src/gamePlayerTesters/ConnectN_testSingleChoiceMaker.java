package gamePlayerTesters;

import java.util.Random;

import danielArtuso_Player.AIChoiceMaker;
import game.*;
import players.*;
import userIO.ConsoleCom;

/**
 * Runs tests on a single choice maker
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */
public class ConnectN_testSingleChoiceMaker 
{
	private ConnectNChoiceMaker choiceMaker = null;

	public ConnectN_testSingleChoiceMaker(ConnectNChoiceMaker maker)
	{
		choiceMaker = maker;
	}
	/**
	 * Runs a single test to find the time it took the AI to complete a choice
	 * @param nRows the number of rows for the board
	 * @param nCols the number of cols for the board
	 * @param winLength the win length required
	 */
	public void runSingleChoiceTimeTest(int nRows, int nCols, int winLength)
	{
		try
		{
			Random rng = new Random();
			ConnectNBoard board = new ConnectNBoard(nRows, nCols);
			choiceMaker.reset();

			for(int count=0; count < 8; count++)
			{
				if((count%2) == 0)
					board.addGameToken(Player.PLAYER_ONE,  rng.nextInt(nCols));
				else
					board.addGameToken(Player.PLAYER_TWO,  rng.nextInt(nCols));
			}


			long start = System.currentTimeMillis();
			choiceMaker.playTurn(board, Player.PLAYER_ONE, Player.PLAYER_TWO, winLength);
			long end = System.currentTimeMillis();
			long time = end -  start;
			System.out.println( String.format("\tTime: R=%2d C=%2d W=%2d Time=" + time, nRows, nCols, winLength));
		}
		catch(Exception ex)
		{
			System.out.println("Test Fail");
			ex.printStackTrace();
		}
	}
	/**
	 * Runs a single game test where the tested choice maker is player 1 against the given choice maker
	 * @param nRows the number of rows for the board
	 * @param nCols the number of cols for the board
	 * @param vs that versus choice maker
	 * @param winLength the win length required
	 */
	public void runsingleGameTest_Player1(int nRows, int nCols, int winLength, ConnectNChoiceMaker vs)
	{
		try
		{

			choiceMaker.reset();

			ConnectNChoiceMaker player1 = choiceMaker;
			ConnectNChoiceMaker player2 = vs;

			ConnectNGame game = new ConnectNGame(player1, player2,nRows,nCols);
			game.setWinLength(winLength);
			game.setDelay(100);

			long start = System.currentTimeMillis();
			Player winner = game.playGame();
			long end = System.currentTimeMillis();
			long time = end -  start;
			System.out.println("WINNER: " + winner);
			System.out.println( String.format("\tTime: R=%2d C=%2d W=%2d Time=" + time, nRows, nCols, winLength));
		}
		catch(Exception ex)
		{
			System.out.println("Test Fail");
			ex.printStackTrace();
		}
	}


	public static void main(String[] args)
	{
		ConnectN_testSingleChoiceMaker tester = new ConnectN_testSingleChoiceMaker( new AIChoiceMaker() );
		ConsoleCom com = new ConsoleCom();

		System.out.println("Testing Time ( cols 6  to 20 )");
		tester.runSingleChoiceTimeTest(9,6,5);
		tester.runSingleChoiceTimeTest(9,10,5);
		tester.runSingleChoiceTimeTest(9,15,5);
		tester.runSingleChoiceTimeTest(9,20,5);
		com.pauseUntilHitEnter();


		int[][] testGames = 
			{
				{6,7,5},
				{3,4,3},
				{8,3,4},
				{20,5,4},
				{8,8,6},
				{15,15,4}
			};


		for(int test =0; test < testGames.length; test++)
		{
			System.out.println("=======================================================================");
			System.out.println( String.format("Next Test: R=%2d C=%2d W=%2d", testGames[test][0],testGames[test][1],testGames[test][2]));
			com.pauseUntilHitEnter();
			tester.runsingleGameTest_Player1(testGames[test][0],testGames[test][1],testGames[test][2], new LeftToRightLinearPickerChoiceMaker() );
		}


	}

}
