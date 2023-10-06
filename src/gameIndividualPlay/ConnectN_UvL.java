package gameIndividualPlay;
import players.*;
import game.ConnectNGame;

/**
 * Plays the connectN game of a user vs linear chooser
 * @author JKidney
 */
public class ConnectN_UvL {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{	
		ConnectNChoiceMaker player1 = new ConsoleChoiceMaker();
		ConnectNChoiceMaker player2 = new LeftToRightLinearPickerChoiceMaker();
		
		ConnectNGame game = new ConnectNGame(player1, player2,6,7);
	
		game.setWinLength(5);
		Player winner = game.playGame();
		System.out.println("WINNER: " + winner);
	}

}
