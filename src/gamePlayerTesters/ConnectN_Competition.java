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
import players.*;

public class ConnectN_Competition 
{


	public static void main(String[] args) 
	{	

		ConnectN_CompareAll tester = new ConnectN_CompareAll();

		tester.setNumGamesPerTest(10);
		tester.setNumBoardRows(7);
		tester.setNumBoardCols(8);
		tester.setWinLength(5);
		tester.setAllowPlayAgainstSelf(false);

		tester.addChoiceMaker( new LeftToRightLinearPickerChoiceMaker() );
		tester.addChoiceMaker( new RightToLeftLinearPickerChoiceMaker() );
		tester.addChoiceMaker( new RandomChoiceMaker() );
		tester.addChoiceMaker( new danielArtuso_Player.AIChoiceMaker());

		tester.runAllTests();
	}

}
