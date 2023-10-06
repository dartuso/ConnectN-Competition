package gameIndividualPlay;
import players.*;
import game.ConnectNGame;

/**
 * Plays the connectN game of a random vs linear chooser
 * @author JKidney
 */
public class ConnectN_RvL {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{	
		ConnectNChoiceMaker player1 = new RandomChoiceMaker();
		ConnectNChoiceMaker player2 = new LeftToRightLinearPickerChoiceMaker();
		
		ConnectNGame game = new ConnectNGame(player1, player2,6,7);
	
		game.setWinLength(5);
		game.setDelay(200);
		Player winner = game.playGame();
		System.out.println("WINNER: " + winner);
	}

}
