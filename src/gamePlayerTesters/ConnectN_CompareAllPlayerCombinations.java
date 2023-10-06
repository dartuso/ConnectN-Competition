package gamePlayerTesters;

import danielArtuso_Player.AIChoiceMaker;

/**
 *Runs a full comparison of all included players to determine an overall winner
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */


import players.*;

public class ConnectN_CompareAllPlayerCombinations 
{


	public static void main(String[] args) 
	{	

		ConnectN_CompareAll tester = new ConnectN_CompareAll();

		tester.setNumGamesPerTest(10);
		tester.setNumBoardRows(6);
		tester.setNumBoardCols(7);
		tester.setWinLength(5);
		tester.setAllowPlayAgainstSelf(true);

		tester.addChoiceMaker( new LeftToRightLinearPickerChoiceMaker() );
		tester.addChoiceMaker( new RightToLeftLinearPickerChoiceMaker() );
		tester.addChoiceMaker( new RandomChoiceMaker() );
		tester.addChoiceMaker( new AIChoiceMaker() );
		tester.runAllTests();
	}

}
