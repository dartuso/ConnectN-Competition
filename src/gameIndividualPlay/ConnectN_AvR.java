package gameIndividualPlay;
import players.*;
import danielArtuso_Player.AIChoiceMaker;
import game.ConnectNGame;

/**
 * Plays the connectN game of a AI vs Random chooser
 * @author JKidney
 */
public class ConnectN_AvR {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{	
		ConnectNChoiceMaker player1 = new AIChoiceMaker();
		ConnectNChoiceMaker player2 = new RandomChoiceMaker();
		
		ConnectNGame game = new ConnectNGame(player1, player2,6,7);
	
		game.setWinLength(5);
		game.setDelay(200);
		Player winner = game.playGame();
		System.out.println("WINNER: " + winner);
	}

}
